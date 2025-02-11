package org.sonit.awsapi;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashMap;

public class HandlerWeatherData implements RequestHandler<WeatherData, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(WeatherData weatherData, Context context) {
        LambdaLogger logger = context.getLogger();
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        weatherData.setHumidityPct(50.5);
        weatherData.setPressureHPa(1005);
        weatherData.setWindKmh(28);
        // Assumes temperature of event is already set
        weatherData.setTemperatureK(132);

        String jsonData = "";
        try {
            jsonData = objectWriter.writeValueAsString(weatherData);
        } catch (Exception e) {
            logger.log("Error: " + e.getMessage());
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(new HashMap<>() {{
                    put("Content-Type", "application/json");
                }})
                .withBody(jsonData);
    }
}
