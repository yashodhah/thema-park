package example;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

@DynamoDBTable(tableName="THEME_PARK_RIDES")
public class Ride {
    @DynamoDBAttribute(attributeName = "ID")
    public String id;
    @DynamoDBAttribute(attributeName = "wait")
    public double wait;
    @DynamoDBAttribute(attributeName = "targetWait")
    public double targetWait;
    @DynamoDBAttribute(attributeName = "maxWait")
    public double maxWait;

    @DynamoDBAttribute(attributeName = "minWait")
    public double minWait;

    @DynamoDBAttribute(attributeName = "rideName")
    public String rideName;
    @DynamoDBAttribute(attributeName = "waitChangeRate")
    public int waitChangeRate;
    @DynamoDBAttribute(attributeName = "inService")
    public boolean inService;
    @DynamoDBAttribute(attributeName = "closureProbability")
    public double closureProbability;

    @DynamoDBAttribute(attributeName = "lastUpdated")
    public Date lastUpdated;
}
