import htcc.common.constant.ManagerActionEnum;
import htcc.common.constant.ManagerRoleGroupEnum;
import htcc.common.entity.order.CreateOrderRequest;
import htcc.common.util.StringUtil;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestUtil {

    @Test
    public void testTime() throws ParseException {

        String yyyyMM = "202006";
        int n = 3;
        for (int i=0; i< n ;i++) {
            // do buz
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate         date      = LocalDate.parse(yyyyMM + "01", formatter);

            int totalDaysInMonth = date.lengthOfMonth();

            LocalDate startDate = date.plusDays(i * totalDaysInMonth / n);
            LocalDate endDate = startDate.plusDays(totalDaysInMonth / n - 1);

            if (i == n - 1) {
                endDate = date.plusDays(totalDaysInMonth - 1);
            }

            System.out.println(startDate.format(formatter) + " - " + endDate.format(formatter));

        }
    }

    @Test
    public void testString() {
        Map<String, Object> map = new HashMap<>();
        map.put("abc", 123);
        String ab = "?params=" + StringUtil.toJsonString(map);
        System.out.println(ab);
        char quote = '"';
        ab = ab.replaceAll("\"", "\\\\\"");
        System.out.println(ab);
    }

    @Test
    public void testRole() {
        Map<String, Map<String, Boolean>> map = new HashMap<>();
        for (ManagerRoleGroupEnum group : ManagerRoleGroupEnum.values()) {
            map.put(group.getRoleGroup(), new HashMap<>());
            List<ManagerActionEnum> actions = group.getActions();
            for (ManagerActionEnum action : actions) {
                map.get(group.getRoleGroup()).put(action.getValue(), true);
            }
        }

        System.out.println(StringUtil.toJsonString(map));
    }
}
