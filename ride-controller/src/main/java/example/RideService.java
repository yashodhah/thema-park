package example;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import java.util.Date;
import java.util.List;

public class RideService {
    private final AmazonDynamoDB client;
    private final DynamoDBMapper mapper;
//    private final DynamoDBMapperConfig config;

    public RideService() {
//        config = new DynamoDBMapperConfig.Builder().withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(AppSettings.MASTER_TABLE))
//                .build();
        client = AmazonDynamoDBClientBuilder.standard().build();
        mapper = new DynamoDBMapper(client);
    }

    public List<Ride> getRides() {
        return mapper.scan(Ride.class, new DynamoDBScanExpression());
    }

    public void updateRide(Ride ride) {
        ride.lastUpdated = new Date();
        mapper.save(ride);
    }
}
