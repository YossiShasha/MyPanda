import akka.actor.ActorSystem;
import data.EventsOutputConsumer;
import server.HttpServer;
import utils.ExecutableUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main class. Starts the data consumer and the Http server.
 * Created by Yossi on 01/10/2016.
 */
public class Main {

    public static final String EXECUTABLE_NAME = "generator-windows-amd64.exe";

    public static void main(String[] args) throws IOException {
        // Consume the output from the process in different thread.
        Process process = ExecutableUtils.getProcessOfExecutable(EXECUTABLE_NAME);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new EventsOutputConsumer(process.getInputStream()));
        // boot up server using the route as defined below
        ActorSystem system = ActorSystem.create();

        // HttpApp.bindRoute expects a route being provided by HttpApp.createRoute
        new HttpServer().bindRoute("localhost", 8080, system);
        System.out.println("Type RETURN to shutdown server and exit");
        System.in.read();
        // Shutdown the server.
        system.terminate();
        // Stop the hanging process.
        process.destroy();
        // Shutdown the consumer.
        executor.shutdownNow();
    }
}
