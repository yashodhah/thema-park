package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Handler {
    public void handleRequest(SNSEvent event, Context context) {
        SNSEvent.SNS sns = event.getRecords().get(0).getSNS();
        String message = sns.getMessage();

        processMessage(message);
    }

    public void processMessage(String message) {
        try {
            System.out.println("Parsing message " + message);
            MessageFormat msgFormat = new ObjectMapper().readValue(message, MessageFormat.class);
            System.out.println("msg format" + msgFormat);

            if (!msgFormat.type.equals("summary")) {
                return;
            }

            List<RideEventBody> rideDetailsList = Arrays.asList(new ObjectMapper().readValue(msgFormat.msg, RideEventBody[].class));
            putEventsToEventBridge(message, rideDetailsList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putEventsToEventBridge(String eventBody, List<RideEventBody> rideDetailsList) {
        System.out.println("Start put events to EB");
        EventBridgeClient eventBridgeClient = EventBridgeClient.builder().build();

        putRideEvents(rideDetailsList, eventBridgeClient);
        putSummary(eventBody, eventBridgeClient);
    }

    private void putRideEvents(List<RideEventBody> rideDetailsList, EventBridgeClient eventBridgeClient) {
        List<PutEventsRequestEntry> requestEntries = new ArrayList<>();

        for (RideEventBody ride : rideDetailsList) {
            PutEventsRequestEntry requestEntry = PutEventsRequestEntry.builder()
                    .source("themepark.rides")
                    .eventBusName("default")
                    .detailType("waitTimes")
                    .time(new Date().toInstant())
                    .detail(new RideEventBody(ride.rideId, ride.inService, ride.wait, ride.lastUpdated).toString())
                    .build();

            requestEntries.add(requestEntry);

            // TODO: Check this limitation
            if (requestEntries.size() == 10) {
                PutEventsRequest eventsRequest = PutEventsRequest.builder()
                        .entries(requestEntries)
                        .build();

                System.out.println("sending a batch of events to EB");
                eventBridgeClient.putEvents(eventsRequest);

                requestEntries = new ArrayList<>();
            }
        }

        // Send rest of the messages left in the array
        if (requestEntries.size() > 0) {
            System.out.println("sending rest of the events to EB");
            PutEventsRequest eventsRequest = PutEventsRequest.builder()
                    .entries(requestEntries)
                    .build();

            eventBridgeClient.putEvents(eventsRequest);
        }
    }

    private void putSummary(String eventBody, EventBridgeClient eventBridgeClient) {
        List<PutEventsRequestEntry> requestEntries = new ArrayList<>();

        PutEventsRequestEntry requestEntry = PutEventsRequestEntry.builder()
                .source("themepark.rides")
                .eventBusName("default")
                .detailType("waitTimesSummary")
                .time(new Date().toInstant())
                .detail(eventBody)
                .build();

        requestEntries.add(requestEntry);

        PutEventsRequest eventsRequest = PutEventsRequest.builder()
                .entries(requestEntries)
                .build();

        System.out.println("put summary to EB");
        eventBridgeClient.putEvents(eventsRequest);
    }
}


