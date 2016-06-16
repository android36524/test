package common;

import com.jfinal.config.*;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.weixin.demo.WeixinApiController;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import controller.admin.AdminRoutes;
import controller.front.FrontRoutes;
import controller.weixin.WeixinMsgController;
import hander.StaticHandler;
import model._MappingKit;
import org.beetl.ext.jfinal.BeetlRenderFactory;
public class Config extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        PropKit.use("config.properties");
        me.setMainRenderFactory(new BeetlRenderFactory());
        ApiConfigKit.setDevMode(true);
        me.setJsonFactory(new FastJsonFactory());
        me.setError401View("/common/404.html");
        me.setError403View("/common/404.html");
        me.setError404View("/common/404.html");
        me.setError500View("/common/404.html");
    }

    @Override
    public void configRoute(Routes me) {
        me.add(new AdminRoutes());
        me.add(new FrontRoutes());
        me.add("/weixin", WeixinMsgController.class);
        me.add("/api", WeixinApiController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setShowSql(true);
        arp.setDevMode(true);
        _MappingKit.mapping(arp);
        me.add(c3p0Plugin);
        me.add(arp);
//        RedisPlugin bbsRedis = new RedisPlugin("bbs", "localhost");
//        bbsRedis.setSerializer(JdkSerializer.me);
//        me.adderror(bbsRedis);
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {
           //me.adderror(new FakeStaticHandler());
        me.add(new StaticHandler("/bootstrap","/img","static"));
    }
}
