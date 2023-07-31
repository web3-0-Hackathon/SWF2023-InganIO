package inganio.demo.Common;

import java.util.HashMap;

/**************************************************
* @FileName   : CamelMap.java
* @Description: 
* @Author     : Seung-Jun. Kim
* @Version    : 2021. 2. 8.
* @Copyright  : ⓒADUP. All Right Reserved
**************************************************/
public class CamelMap extends HashMap<String, Object> {

	static final long serialVersionUID = -3144044422514529794L;

	/**
     * key 에 대하여 Camel Case 변환하여 super.put
     * (ListOrderedMap) 을 호출한다.
     * @param key
     *        - '_' 가 포함된 변수명
     * @param value
     *        - 명시된 key 에 대한 값 (변경 없음)
     * @return previous value associated with specified
     *         key, or null if there was no mapping for
     *         key
     */
	public Object put(String key, Object value) {
		return super.put(CamelUtil.convert2CamelCase(key), value);
	}
	
	public String getString(String key){
		if(this.get(key) == null){
			return "";
		}else{
			return String.valueOf(this.get(key));
		}
	}
	
	public int getInt(String key){
		try {
			return Integer.parseInt(this.getString(key));
		}catch (Exception e) {
			return 0;
		}
	}
	public int getInt(String key, int d) {
		int v = getInt(key);
		return (v==0)?d:v;
	}
}
