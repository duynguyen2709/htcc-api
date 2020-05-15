import htcc.common.util.DateTimeUtil;
import org.junit.Test;

public class TestUtil {

    @Test
    public void testTime(){
        System.out.println(DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd"));
    }
}
