import example.Handler;
import example.Ride;
import example.RideService;
import example.SNSService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HandlerTest {
    @Mock
    SNSService snsService;
    @Mock
    RideService rideService;

    @Test
    public void testHandlerFunction() {
        Handler handler = new Handler();

        ArrayList<Ride> rides = new ArrayList<>();
        rides.add(getTestRideObj());

        when(rideService.getRides()).thenReturn(rides);
        doNothing().when(rideService).updateRide(any(Ride.class));
        doNothing().when(snsService).pubTopic(anyString(), anyString());

        handler.handlerFunction(rideService, snsService);
    }

    private Ride getTestRideObj() {
        Ride ride = new Ride();

        ride.setId("ride-001");
        ride.setWait(10);
        ride.setTargetWait(1);
        ride.setMaxWait(20);
        ride.setMinWait(0);
        ride.setRideName("Test Ride");
        ride.setWaitChangeRate(1);
        ride.setInService(true);
        ride.setClosureProbability(0.01);
        ride.setLastUpdated((int) new Timestamp(System.currentTimeMillis()).getTime());

        return ride;
    }
}
