package data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yossi on 01/10/2016.
 */
public class Event {

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("data")
    private String data;

    public Event(){}

    public Event(String eventType, String data, long timestamp) {
        this.eventType = eventType;
        this.data = data;
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}