package inganio.demo.User.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import inganio.demo.User.Mapper.UserMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public String userWithdrawals(HashMap<String, String> paramMap) throws SQLException, IOException {
		String uuid = null;
		
		// 부여받은 자식 주소 정보
		String requestId = paramMap.get("requestId");
		String amount = paramMap.get("amount");
		String childAddress = paramMap.get("childAddress");
		//유저 1 정보 조회
		Map<String, Object> userMap = userMapper.getUserInfo();
//		String rsaKey = (String) userMap.get("rsaKey");
		String apiToken =(String) userMap.get("apiToken");
		String walletNum =(String) userMap.get("walletNum");
		String userAddress = (String) userMap.get("userAddress");
		
		userMap.put("childAddress", childAddress);
		
		//사용자 - 자식 주소 저장
		int rst = 0;
		try {
			rst=userMapper.childAddress(userMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//자식주소로 출금 신청
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"symbol\":\"ETH\",\"requestId\":\""+requestId+"\",\"amount\":\""+amount+"\",\"senderAddress\":\""+childAddress+"\",\"receiverAddress\":\""+childAddress+"\"}");
		Request request = new Request.Builder()
		  .url("https://octet-api.blockchainapi.io/2.0/wallets/"+walletNum+"/withdrawals")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer "+apiToken)
		  .addHeader("content-type", "application/json")
		  .build();

		Response response = client.newCall(request).execute();	
		uuid = response.body().toString();
	
		return uuid;
	}

}
