package inganio.demo.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//	
//	@GetMapping({"/userList/{title}"})
//	public Object userList(HttpServletRequest request) throws SQLException {
//		CamelMap paramMap = HttpUtil.getRequestCamelMap(request);
//		List<CamelMap> rstList = null;
//		ModelAndView mv = new ModelAndView("jsonView");
//		
//		try {
//			rstList = userMapper.userList(paramMap);
//			System.out.println(rstList);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		mv.addObject("rstList", rstList);
//		
//		return mv;
//		
//	} 
//	
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
//		String pass = paramMap.get("pass");
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
	
	//?로 붙음
//	@GetMapping({"/test"})
//	public Map<String, String>  user(@RequestParam Map<String, String> paramMap) {
//		logger.debug("파라미터 찍업", paramMap.get("name"));
//		/* HashMap<String, String> rstMap = new HashMap<String, String>(); */
//		
//		/* JSONObject jsonObject = new JSONObject(rstMap); */
//		  
//		return userMapper.user(paramMap);
//		
//	}
	
	/**************************************************
	* @MethodName : userWithdrawals
	* @Description: 유저 지갑 출금 신청
	* @return int
	* @Author : se-in shin
	**************************************************/
	@PostMapping({"/userWithdrawals"})
	public int userWithdrawals(@RequestBody HashMap<String, String> paramMap){
		int status = commonUtil.StatusCode.INTERNAL_SERVER_ERROR;
		String uuid = null;
		try {
			uuid = userService.userWithdrawals(paramMap);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("error:"+ e);
		}
		return status = 0;
	} 
	

	 
}
