package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Handler {
    public void handleRequest(Object event, Context context) {
        try {
            setVariables();
            RideService rideService = new RideService();
            SNSService snsService = new SNSService();

            handlerFunction(rideService, snsService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handlerFunction(RideService rideService, SNSService snsService) {
        ArrayList<RideMessage> messages = new ArrayList<>();
        System.out.println("get rides");
        List<Ride> rideList = rideService.getRides();

        rideList.stream().map(ride -> updateRide(ride)).forEach(updateRide -> {
            updateRide.setLastUpdated((int) new Timestamp(System.currentTimeMillis()).getTime());

            rideService.updateRide(updateRide);
            messages.add(getRideMessage(updateRide));
        });

        System.out.println("send ride updates to SNS");
        sendToSns(snsService, messages);
    }

    private void printDBRides(List<Ride> rideList) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("rides " + mapper.writeValueAsString(rideList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToSns(SNSService snsService, List<RideMessage> rideMessagesList) {
        JSONObject snsMsg = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();

        try {
            snsMsg.put("msg", mapper.writeValueAsString(rideMessagesList));
            snsMsg.put("type", "summary");

            snsService.pubTopic(snsMsg.toString(), AppSettings.TOPIC_ARN);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private RideMessage getRideMessage(Ride ride) {
        RideMessage rideMessage = new RideMessage();

        rideMessage.rideId = ride.getId();
        rideMessage.inService = ride.isInService();
        rideMessage.wait = ride.getWait();
        rideMessage.lastUpdated = ride.getLastUpdated();

        return rideMessage;
    }

    public Ride updateRide(Ride ride) {
        if (ride.getWait() == 0) {
            ride.setInService(true);
        }

        if (ride.isInService()) {
            if (Math.random() < ride.getClosureProbability()) {
                ride.setInService(false);
                ride.setWait(5 * ride.getWaitChangeRate());
                ride.setTargetWait(0);

                return ride;
            }
        }

        // If current wait is current target wait, set new targetWait
        if (ride.getWait() == ride.getTargetWait()) {
            ride.setTargetWait((int) Math.floor(Math.random() * ride.getMaxWait()));
        }

        // Move wait towards targetWait
        if (ride.getWait() < ride.getTargetWait()) {
            ride.setWait(ride.getWait() + ride.getWaitChangeRate());
            ride.setWait(Math.min(ride.getWait(), ride.getTargetWait()));
        } else {
            ride.setWait(ride.getWait() - ride.getWaitChangeRate());
            ride.setWait(Math.max(ride.getWait(), ride.getTargetWait()));
        }

        return ride;
    }

    private void setVariables() {
        AppSettings.MASTER_TABLE = System.getenv("DDBtable");
        AppSettings.TOPIC_ARN = System.getenv("TopicArn");
    }
}
