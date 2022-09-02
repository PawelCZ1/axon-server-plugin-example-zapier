package io.axoniq.plugin;

import io.axoniq.axonserver.grpc.MetaDataValue;
import io.axoniq.axonserver.grpc.event.Event;
import io.axoniq.axonserver.plugin.ExecutionContext;
import io.axoniq.axonserver.plugin.interceptor.AppendEventInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class InterceptWhenAppending implements AppendEventInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(InterceptWhenAppending.class);





    @Override
    public Event appendEvent(Event event, ExecutionContext executionContext) {

        if(event.getAggregateType().equals("Investment")){
            URL url = null;
            try {
                url = new URL("https://hooks.zapier.com/hooks/catch/13255320/beb85na/");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            URLConnection con;
            try {
                con = url.openConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            HttpURLConnection http = (HttpURLConnection)con;
            try {
                http.setRequestMethod("POST"); // PUT is another valid option
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            }
            http.setDoOutput(true);
            byte[] out = event.getPayload().getData().toByteArray();
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            try {
                http.connect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        logger.info("appendEvent: {}", event);
        return Event.newBuilder(event)
                    .putMetaData("aggregate", MetaDataValue.newBuilder()
                                                        .setTextValue(event.getAggregateType())
                                                        .build())

                    .build();
    }
}
