import htcc.common.constant.ManagerActionEnum;
import htcc.common.constant.ManagerRoleGroupEnum;
import htcc.common.entity.order.CreateOrderRequest;
import htcc.common.util.DateTimeUtil;
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

        String yyyyMMdd1 = "20200726";
        String yyyyMMdd2 = "20200826";
        Date dt1 = DateTimeUtil.parseStringToDate(yyyyMMdd1, "yyyyMMdd");
        Date dt2 = DateTimeUtil.parseStringToDate(yyyyMMdd2, "yyyyMMdd");
        long diff = Math.abs(dt2.getTime() - dt1.getTime());
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        System.out.println(diffDays);

    }

    @Test
    public void testString() {
        String str = "VNG-200726-0001";
        System.out.println(str.substring(str.length() - 4));
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
