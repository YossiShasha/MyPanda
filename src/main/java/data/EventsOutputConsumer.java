package data;

import data.DataProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

/**
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
            while ((line = input.readLine()) != null && !Thread.interrupted()) {
                DataProcessor.INSTANCE.processEvent(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
