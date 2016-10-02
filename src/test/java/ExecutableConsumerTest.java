import org.junit.Assert;
import org.junit.Test;
import utils.ExecutableUtils;

import java.io.IOException;

/**
 * Created by Yossi on 02/10/2016.
 */
public class ExecutableConsumerTest {

    @Test(expected = IOException.class)
    public void runExecutableWithBadPath() throws IOException {
        ExecutableUtils.getProcessOfExecutable("Bad name");
    }

    @Test
    public void runExecutableSuccessfully(){
        Process p = null;
        try {
            p = ExecutableUtils.getProcessOfExecutable(Main.EXECUTABLE_NAME);
        } catch (IOException e) {
            Assert.fail();
        }
        Assert.assertTrue(p != null);
    }
}
