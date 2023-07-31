package inganio.demo.User.Mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	

	//headers - get 성공
	//.join 성공
	public List<Map<String, Object>> userList(String pass) throws SQLException;

	public List<Map<String, Object>> test(String pass) throws SQLException;
	
	
	//host 조회
	public Map<String, Object> getHostInfo() throws SQLException;

	//user 조회
	public Map<String, Object> getUserInfo() throws SQLException;

	public List<Map<String, Object>> test(HashMap<String, String> paramMap) throws SQLException;

	//사용자 자식주소 저장
	public int childAddress(Map<String, Object> userMap) throws SQLException;

	
	

	
}
