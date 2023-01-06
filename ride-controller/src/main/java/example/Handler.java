package example;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Handler {
    public void handleRequest(Object event, Context context) {
        setVariables();
        RideService rideService = new RideService();
        ArrayList<RideMessage> messages = new ArrayList<>();

        List<Ride> rideList = rideService.getRides();

        rideList.stream().map(ride -> updateRide(ride)).forEach(ride -> {
            rideService.updateRide(ride);
            messages.add(getRideMessage(ride));
        });
    }

    private void sendToSns(Ride ride) {
        SNSService snsService = new SNSService();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writeValueAsString(ride);
            snsService.pubTopic(json, AppSettings.TOPIC_ARN);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private RideMessage getRideMessage(Ride ride) {
        RideMessage rideMessage = new RideMessage();

        rideMessage.rideId = ride.id;
        rideMessage.inService = ride.inService;
        rideMessage.wait = ride.wait;
        rideMessage.lastUpdated = ride.lastUpdated;

        return rideMessage;
    }

    public Ride updateRide(Ride ride) {
        if (ride.wait == 0) {
            ride.inService = true;
        }

        if (ride.inService) {
            if (Math.random() < ride.closureProbability) {
                ride.inService = false;
                ride.wait = (5 * ride.waitChangeRate);
                ride.targetWait = 0;
                return ride;
            }
        }

        // If current wait is current target wait, set new targetWait
        if (ride.wait == ride.targetWait) {
            ride.targetWait = Math.floor(Math.random() * ride.maxWait);
        }

        // Move wait towards targetWait
        if (ride.wait < ride.targetWait) {
            ride.wait += ride.waitChangeRate;
            ride.wait = Math.min(ride.wait, ride.targetWait);
        } else {
            ride.wait -= ride.waitChangeRate;
            ride.wait = Math.max(ride.wait, ride.targetWait);
        }

        return ride;
    }

    private void setVariables() {
        AppSettings.MASTER_TABLE = System.getenv("DDBtable");
        AppSettings.TOPIC_ARN = System.getenv("TopicArn");
//        AppSettings.REGION = Region.getRegion(Regions.valueOf(System.getenv("REGION").toUpperCase())); ;
    }
}
