package inganio.demo.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inganio.demo.Common.commonUtil;
import inganio.demo.User.Mapper.UserMapper;
import inganio.demo.User.Service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	//headers - get 성공
	//.join 성공
	//url 뒤에 바로 붙음
	@GetMapping({"/userList/{pass}"})
	public List<Map<String, Object>> userList(@PathVariable("pass") String pass) throws SQLException {
		return userMapper.userList(pass);
	}
	
	//headers - get 성공
	//.join 성공
	@GetMapping({"/test"})
	public List<Map<String, Object>> test(@RequestParam HashMap<String,String> paramMap) throws SQLException {
		System.out.println(paramMap);
		List<Map<String, Object>> rst = userMapper.test(paramMap);
		return rst;
	}

	/**************************************************
	* @MethodName : getHostInfo
	* @Description: 호스트 정보 조회
	* @return Map
	* @Author : se-in shin
	**************************************************/
	@GetMapping({"/getHostInfo"})
	public Map<String, Object> getHostInfo() throws SQLException{
		Map<String, Object> hostInfo = userMapper.getHostInfo();
		return hostInfo;
	}  
	
	/**************************************************
	* @MethodName : getUserInfo
	* @Description: 사용자 정보 조회
	* @return Map
	* @Author : se-in shin
	**************************************************/
	@GetMapping({"/getUserInfo"})
	public Map<String, Object> getUserInfo() throws SQLException{
		Map<String, Object> userInfo = userMapper.getUserInfo();
		return userInfo;
	}  
	
	/**************************************************
	* @MethodName : userWithdrawals
	* @Description: 사용자 지갑 출금 신청 (자식주소 생성 후 출금상태 전송)
	* @return String
	* @throws ParseException 
	* @throws SQLException 
	* @throws IOException 
	* @Author : se-in shin
	**************************************************/
	@SuppressWarnings("null")
	@PostMapping({"/userWithdrawals"})
	public Map<String, String> userWithdrawals(@RequestBody HashMap<String, String> paramMap) throws IOException, SQLException, ParseException{
		String tranStatus = "";
		try {
			tranStatus = userService.userWithdrawals(paramMap);
			// 출금이 완료되었다면 호스트 대표주소에서 유저에게 자식 주소 출금
			if (tranStatus.equals("SENT")){
				tranStatus = userService.NftWithdrawalsOffer(paramMap);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("error:"+ e);
		}
		
		Map<String, String> statusMap = null;
		statusMap.put("statusCode", tranStatus);
		
		//아니면 에러코드 반환.
		return statusMap;
	} 
	
	
	/**************************************************
	* @MethodName : nftContractItemInfo
	* @Description: 호스트의 컨트랙트주소와 아이템의 토큰 아이디 조회 (로그인이 없으므로 유저 행사 모두 1로 받음)
	* @return HashMap
	* @Author : se-in shin
	**************************************************/
	@GetMapping("/nftContractItemInfo")
	public HashMap<String, String> nftContractItemInfo(@RequestParam HashMap<String, String> paramMap) {
		HashMap<String, String> rstMap = null;
		
		String hostId = (String) paramMap.get("hostId");
		String eventId = (String) paramMap.get("eventId");
//		String tokenId = (String) paramMap.get("tokenId");
		
		rstMap = userMapper.nftContractItemInfo(paramMap);
		
		return rstMap;
	}

	 
}
