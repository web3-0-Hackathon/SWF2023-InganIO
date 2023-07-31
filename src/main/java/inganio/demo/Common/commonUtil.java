package inganio.demo.Common;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

public class commonUtil {
	/**************************************************
	* @MethodName : getMapFromJsonObject
	* @Description: json to map
	* @return map
	* @Author : se-in shin
	**************************************************/
	 @SuppressWarnings("unchecked")
	    public static Map<String, Object> getMapFromJsonObject( JSONObject jsonObj )
	    {
	        Map<String, Object> map = null;
	        try {
	            map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class) ;
	            
	        } catch (JsonParseException e) {
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return map;
	    }
	 
	 
	 
	 /**************************************************
	 * @MethodName : convertMap
	 * @Description: request to hashMap
	 * @return hashmap
	 * @Author :se-in shin
	 **************************************************/
	 public HashMap<String, Object> convertMap(HttpServletRequest request) {
		    HashMap<String, Object> hmap = new HashMap<String, Object>();
		    String key;
		 
		    Enumeration<?> en = request.getParameterNames();
		 
		    while (en.hasMoreElements()) {
		        key = (String) en.nextElement();
		        if (request.getParameterValues(key).length > 1) {
		            hmap.put(key, request.getParameterValues(key));
		        } else {
		            hmap.put(key, request.getParameter(key));
		        }
		 
		    }
		 
		    return hmap;
		}
	 
	 /**************************************************
	 * @MethodName : StatusCode
	 * @Description: http status code
	 * @return code
	 * @Author :se-in shin
	 **************************************************/
	 public class StatusCode {
		    public static final int OK = 200;
		    public static final int CREATED = 201;
		    public static final int NO_CONTENT = 204;
		    public static final int BAD_REQUEST =  400;
		    public static final int UNAUTHORIZED = 401;
		    public static final int FORBIDDEN = 403;
		    public static final int NOT_FOUND = 404;
		    public static final int INTERNAL_SERVER_ERROR = 500;
		    public static final int SERVICE_UNAVAILABLE = 503;
		    public static final int DB_ERROR = 600;
		}
}
