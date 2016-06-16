package test;


import com.jfinal.kit.HttpKit;
import org.junit.Test;
import utils.DesUtil;

import java.util.HashMap;
import java.util.Map;

public class Httptest {
    @Test
    public void http(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "oscid=xxx;");
        String html = HttpKit.get("http://my.oschina.net/qq596392912/admin/profile", null, headers);

        System.out.println(html);
    }
    @Test
    public void des() throws Exception {
        String test = DesUtil.encode("12324");
        System.out.println(test);
        System.out.println(DesUtil.decode(test));
    }
}
