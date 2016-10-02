package statistics;

import data.Event;
import utils.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Singleton class for gathering and providing Event statistics.
 * This class is thread safe.
 * Created by Yossi on 01/10/2016.
 */
public enum Statistics {
    INSTANCE;

    private ConcurrentHashMap<String, LongAdder> eventTypeCountMap = new ConcurrentHashMap<>();
    private LongAdder eventsDataWordCount = new LongAdder();

    private void incrementEventTypeCount(String eventType){
        if(eventType == null || eventType.isEmpty()){
            throw new IllegalArgumentException("Null or empty event type string!");
        }
        LongAdder eventTypeCounter = eventTypeCountMap.get(eventType);
        if(eventTypeCounter == null){
            // Synchronize only when it's the first time that this event type appears.
            synchronized(eventTypeCountMap){
                eventTypeCounter = eventTypeCountMap.get(eventType);
                if(eventTypeCounter == null){
                    eventTypeCounter = new LongAdder();
                    eventTypeCountMap.put(eventType, eventTypeCounter);
                }
            }
        }
        eventTypeCounter.increment();
    }

    private void incrementEventsDataWordCount(long wordsCount){
        eventsDataWordCount.add(wordsCount);
    }

    public long getEventTypeCount(String eventType){
        LongAdder eventTypeCounter = eventTypeCountMap.get(eventType);
        if(eventTypeCounter != null){
            return eventTypeCounter.longValue();
        } else {
            // Event type does not exists yet, return 0.
            return 0;
        }
    }

    public long getEventsWordCount(){
        return eventsDataWordCount.longValue();
    }

    public void gatherEventStats(Event event) {
        String eventType = event.getEventType();
        if(eventType != null && !eventType.isEmpty()){
            incrementEventTypeCount(eventType);
        }
        incrementEventsDataWordCount(StringUtils.getWordCount(event.getData()));
    }
}
