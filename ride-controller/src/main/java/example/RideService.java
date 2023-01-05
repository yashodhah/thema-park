package example;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.util.Date;
import java.util.List;

public class RideService {
    AmazonDynamoDB client;

    public RideService() {
        client = AmazonDynamoDBClientBuilder.standard().build();
    }

    public List<Ride> getRides() {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        return mapper.scan(Ride.class, new DynamoDBScanExpression());
    }

    public void updateRide(Ride ride) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        ride.lastUpdated = new Date();

        mapper.save(ride);
    }
}
