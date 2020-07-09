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
    public void testAES() throws NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setComboId("CB1");
        request.setCompanyId("TEST");
        request.setEmail("naduy.hcmus@gmail.com");
        request.setRequestedFeatures(new HashMap<>());
        request.setFirstPay(true);

        String raw = StringUtil.toJsonString(request);
        String encode = URLEncoder.encode(raw, StandardCharsets.UTF_8.toString());
        System.out.println(encode);
    }
}
