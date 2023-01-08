import com.fasterxml.jackson.core.JsonProcessingException;
import example.Handler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class HandlerTest {

    @Test
    public void shouldProcessMsg() {
        Handler eventPublisherHandler = new Handler();

        try {
            eventPublisherHandler.processMessage(getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    private String getMessage() {
        return "{\"msg\":\"[{\"rideId\":\"ride-001\",\"inService\":true,\"wait\":9,\"lastUpdated\":-1843310038}]\",\"type\":\"summary\"}";
    }
}
