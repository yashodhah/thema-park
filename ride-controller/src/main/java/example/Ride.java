package example;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

@DynamoDBTable(tableName = "THEME_PARK_RIDES")
public class Ride {
    private String id;
    private int wait;
    private int targetWait;
    private int maxWait;
    private int minWait;
    private String rideName;
    private int waitChangeRate;
    private boolean inService;
    private double closureProbability;
    private int lastUpdated;

    @DynamoDBHashKey(attributeName = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "wait")
    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    @DynamoDBAttribute(attributeName = "targetWait")
    public int getTargetWait() {
        return targetWait;
    }

    public void setTargetWait(int targetWait) {
        this.targetWait = targetWait;
    }

    @DynamoDBAttribute(attributeName = "maxWait")
    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    @DynamoDBAttribute(attributeName = "minWait")
    public int getMinWait() {
        return minWait;
    }

    public void setMinWait(int minWait) {
        this.minWait = minWait;
    }

    @DynamoDBAttribute(attributeName = "rideName")
    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    @DynamoDBAttribute(attributeName = "waitChangeRate")
    public int getWaitChangeRate() {
        return waitChangeRate;
    }

    public void setWaitChangeRate(int waitChangeRate) {
        this.waitChangeRate = waitChangeRate;
    }

    @DynamoDBAttribute(attributeName = "inService")
    public boolean isInService() {
        return inService;
    }

    public void setInService(boolean inService) {
        this.inService = inService;
    }

    @DynamoDBAttribute(attributeName = "closureProbability")
    public double getClosureProbability() {
        return closureProbability;
    }

    public void setClosureProbability(double closureProbability) {
        this.closureProbability = closureProbability;
    }

    @DynamoDBAttribute(attributeName = "lastUpdated")
    public int getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
