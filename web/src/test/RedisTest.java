package test;

import org.junit.Test;
import utils.EmailUtils;
import utils.EmailUtils.MailData;
public class RedisTest {
    @Test
    public void Test1(){
        EmailUtils.sendMail(MailData.New()
                .subject("你好我是你的朋友，希望萨芬的撒")
                .content("中秋快乐！萨芬十大发发送电工十大撒")
                .to("543091757@qq.com"));
    }
}
