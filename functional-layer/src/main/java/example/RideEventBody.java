package example;

public class RideEventBody {
    public String rideId;
    public boolean inService;
    public int wait;
    public int lastUpdated;

    public RideEventBody(String rideId, boolean inService, int wait, int lastUpdated) {
        this.rideId = rideId;
        this.inService = inService;
        this.wait = wait;
        this.lastUpdated = lastUpdated;
    }
}
