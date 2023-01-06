package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Handler {
    public void handleRequest(Object event, Context context) {
        System.out.println("Hello world");
//        LambdaLogger logger = context.getLogger();
//        logger.log("Hello world from logger");

        try {
            System.out.println("Setting env variables");
            setVariables();
            RideService rideService = new RideService();
            ArrayList<RideMessage> messages = new ArrayList<>();

            System.out.println("Getting rides");
            List<Ride> rideList = rideService.getRides();

            System.out.println("Update rides");
            rideList.stream().map(ride -> updateRide(ride)).forEach(ride -> {
                rideService.updateRide(ride);
                messages.add(getRideMessage(ride));
            });

            System.out.println("Send rides to SNS topic");
            sendToSns(messages);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void sendToSns(List<RideMessage> rideMessagesList) {
        SNSService snsService = new SNSService();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writeValueAsString(rideMessagesList);
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
        System.out.println("reading sys vars");
        System.out.println(System.getenv("DDBtable"));

        AppSettings.MASTER_TABLE = System.getenv("DDBtable");
        AppSettings.TOPIC_ARN = System.getenv("TopicArn");
    }
}
