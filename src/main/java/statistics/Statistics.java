package statistics;

import data.Event;
import utils.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Singleton class for gathering and providing Event statistics.
 * This class is completely thread safe.
 * Created by Yossi on 01/10/2016.
 */
public enum Statistics {
    INSTANCE;

    /**
     * A map between event type and the number of times it appeared.
     */
    private ConcurrentHashMap<String, LongAdder> eventTypeCountMap = new ConcurrentHashMap<>();
    /**
     * A counter of the events data word count.
     */
    private LongAdder eventsDataWordCount = new LongAdder();

    /**
     * Increments the counter of the suitable event type.
     * @param eventType
     */
    private void incrementEventTypeCount(String eventType){
        // Checks that the event type is valid.
        if(eventType == null || eventType.isEmpty()){
            throw new IllegalArgumentException("Null or empty event type string!");
        }
        // Find the event type counter.
        LongAdder eventTypeCounter = eventTypeCountMap.get(eventType);
        if(eventTypeCounter == null){
            // Synchronize only when it's the first time that this event type appears.
            synchronized(eventTypeCountMap){
                eventTypeCounter = eventTypeCountMap.get(eventType);
                // Make sure that the counter have not created already.
                if(eventTypeCounter == null){
                    eventTypeCounter = new LongAdder();
                    eventTypeCountMap.put(eventType, eventTypeCounter);
                }
            }
        }
        // At last increment the counter.
        eventTypeCounter.increment();
    }

    private void incrementEventsDataWordCount(long wordsCount){
        eventsDataWordCount.add(wordsCount);
    }

    /**
     * @param eventType
     * @return the event type count. If the event type doesn't exists returns 0.
     */
    public long getEventTypeCount(String eventType){
        LongAdder eventTypeCounter = eventTypeCountMap.get(eventType);
        if(eventTypeCounter != null){
            return eventTypeCounter.longValue();
        }
        // Event type does not exists yet, return 0.
        return 0;
    }

    public long getEventsWordCount(){
        return eventsDataWordCount.longValue();
    }

    /**
     * This method gathers all the relevant statistics that related to Events.
     * @param event
     */
    public void gatherEventStats(Event event) {
        String eventType = event.getEventType();
        // Checks whether the event type filed is not null and not empty.
        if(eventType != null && !eventType.isEmpty()){
            incrementEventTypeCount(eventType);
        }
        // Increment word count
        incrementEventsDataWordCount(StringUtils.getWordCount(event.getData()));
    }
}
