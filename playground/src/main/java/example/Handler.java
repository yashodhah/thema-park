package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Handler {
    public void handleRequest(Object event, Context context) {
        System.out.println("Hello world, I am using a layer !!");

        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = mapper.writeValueAsString(new Date());
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
