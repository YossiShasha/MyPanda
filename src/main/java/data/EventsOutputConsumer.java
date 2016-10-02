package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * This class consumes an InputStream data and sends it to process.
 * Implements callable for being able to consume and process the data in a different thread.
 * Created by Yossi on 01/10/2016.
 */
public class EventsOutputConsumer implements Callable<Void>{

    private InputStream inputStream;

    public EventsOutputConsumer(InputStream inputStream){
        this.inputStream = inputStream;
    }

    @Override
    public Void call() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        try {
            // Checks that the next line is not null and this thread is not interrupted.
            while ((line = input.readLine()) != null && !Thread.interrupted()) {
                // Send it to processing.
                DataProcessor.INSTANCE.processEvent(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
