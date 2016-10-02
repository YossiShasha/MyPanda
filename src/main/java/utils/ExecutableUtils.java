package utils;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Yossi on 02/10/2016.
 */
public class ExecutableUtils {

    /**
     * Receives an executable name that exists as a resource.
     * @param executableName
     * @return the process of the executed executable.
     * @throws IOException when the file is n ot found or when the execution fails.
     */
    public static Process getProcessOfExecutable(String executableName) throws IOException {
        // Get the path of the exe file.
        URL url = ClassLoader.getSystemResource(executableName);
        if(url == null){
            throw new IOException("Couldn't find the Executable");
        }
        Process p;
        // Execute the events generator.
        try {
            p = Runtime.getRuntime().exec(url.getPath());
        } catch (IOException exc) {
            System.out.println("Failed to run the events generator executable");
            exc.printStackTrace();
            throw exc;
        }
        return p;
    }

}
