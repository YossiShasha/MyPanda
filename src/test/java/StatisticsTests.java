import data.Event;
import org.junit.Assert;
import org.junit.Test;
import statistics.Statistics;

/**
 * Created by Yossi on 02/10/2016.
 */
public class StatisticsTests {

    @Test
    public void eventTypePositiveCounter(){
        testEventTypeCounter("type1", 11);
    }

    @Test
    public void eventTypeZeroCounter(){
        testEventTypeCounter("type2", 0);
    }

    private void testEventTypeCounter(String type, int count){
        String dummyData = "dummy";
        long timestamp = 12876182l;
        createEventAndGatherStatistics(type, dummyData, timestamp, count);
        Assert.assertEquals(count, Statistics.INSTANCE.getEventTypeCount(type));
    }

    private void createEventAndGatherStatistics(String eventType, String data, long timestamp, int countOfType){
        Event event = new Event(eventType, data, timestamp);
        for(int i = 1; i <= countOfType; i++){
            Statistics.INSTANCE.gatherEventStats(event);
        }
    }
}
