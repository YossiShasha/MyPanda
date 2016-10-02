package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import statistics.Statistics;

import java.io.IOException;

/**
 * Created by Yossi on 01/10/2016.
 */
public enum DataProcessor {
    INSTANCE;

    /**
     * Parses the JSON encoded Event and gathers statistics.
     * @param eventJsonStr the JSON encoded Event.
     */
    public void processEvent(String eventJsonStr){
        ObjectMapper mapper = new ObjectMapper();
        Event event;
        try {
            // Parse the JSON and create an Event object.
            event = mapper.readValue(eventJsonStr, Event.class);
        } catch (IOException e) {
            System.out.println("JSON string is invalid");
            return;
        }
        Statistics.INSTANCE.gatherEventStats(event);
    }
}
