package inganio.demo.Common;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	public static CamelMap getRequestCamelMap(HttpServletRequest request) {
		CamelMap map = new CamelMap();
        String reqName = "";
        
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            reqName = e.nextElement();
            boolean sw = true;

            
            String val = sNNChk(request.getParameter(reqName)).trim();

            if(sw){
            	map.put(reqName, val);
            }
            
            logger.debug("reqName : {}, value : {}", reqName, val);
        }
        
        
        map.put("sessionId", request.getSession().getId());

        return map;
    }
	
	public static String sNNChk(String arg) {
		if( arg == null || arg.length() < 1 )
			return "";
		return arg;
	}
}
