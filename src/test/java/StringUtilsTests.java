import org.junit.Assert;
import org.junit.Test;
import utils.StringUtils;

/**
 * Created by Yossi on 02/10/2016.
 */
public class StringUtilsTests {

    @Test
    public void wordCount(){
        Assert.assertEquals(1, StringUtils.getWordCount("one"));
        String str = "one two three four five six seven";
        Assert.assertEquals(7, StringUtils.getWordCount(str));
        Assert.assertEquals(0, StringUtils.getWordCount(""));
        Assert.assertEquals(0, StringUtils.getWordCount("     "));
        Assert.assertEquals(0, StringUtils.getWordCount(null));
        str = "  one two three four five six seven  8  ";
        Assert.assertEquals(8, StringUtils.getWordCount(str));
    }
}
