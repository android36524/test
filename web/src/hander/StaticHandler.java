package hander;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * 静态文件处理器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年8月2日 下午5:06:34
 */
public class StaticHandler extends Handler {

	// 需要排除的目录...
	public final String[] dirs;

	public StaticHandler(String... dirs) {
		this.dirs = dirs;
	}

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {

		// 判定是否要排除
		boolean needExclude = false;
		for (String dir : dirs) {
			if (target.startsWith(dir)) {
				needExclude = true;
				break;
			}
		}
		if (needExclude) {
			return;
		}
		next.handle(target, request, response, isHandled);
	}

}
