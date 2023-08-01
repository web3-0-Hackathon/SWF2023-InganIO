package inganio.demo.User.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inganio.demo.Common.commonUtil;
import inganio.demo.User.Mapper.UserMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	/**************************************************
	* @MethodName : userWithdrawals
	* @Description: 사용자 지갑 출금 신청 - 자식 주소 저장
	* @return String
	* @throws ParseException 
	* @Author : se-in shin
	**************************************************/
	public String userWithdrawals(HashMap<String, String> paramMap) throws SQLException, IOException, ParseException {
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
			logger.debug("error: "+e);
		}
		
		//자식주소로 출금 신청
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"symbol\":\"ETH\",\"requestId\":\""+requestId+"\",\"amount\":\""+amount+"\",\"senderAddress\":\""+userAddress+"\",\"receiverAddress\":\""+childAddress+"\"}");
		Request request = new Request.Builder()
		  .url("https://tetco-api.blockchainapi.io/2.0/wallets/"+walletNum+"/withdrawals")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer "+apiToken)
		  .addHeader("content-type", "application/json")
		  .build();

		Response response = client.newCall(request).execute();	
		uuid = response.body().toString();
		
		
		//출금 상태 조회 - 출금 신청 정보 조회 api
		String tranStatus = null;
		if(!uuid.isEmpty()) {
			OkHttpClient client2 = new OkHttpClient();

			Request request2 = new Request.Builder()
			  .url("https://tetco-api.blockchainapi.io/2.0/wallets/"+walletNum+"/withdrawals/"+uuid)
			  .get()
			  .addHeader("accept", "application/json")
			  .addHeader("Authorization", "Bearer ")
			  .build();

			Response response2 = client2.newCall(request2).execute();
			ResponseBody responseBody = response2.body();
			
			JSONParser paser = new JSONParser();
	        JSONObject tranInfo = (JSONObject) paser.parse(responseBody.string());
	        
	        tranStatus = (String) tranInfo.get("state");
	        System.out.println("트랜잭션 상태 ---- "+ tranStatus);
		}
		
		return tranStatus;
	}
	
	/**************************************************
	* @MethodName : NftWithdrawalsOffer
	* @Description: 사용자가 호스트에게 NFT 출금 신청 후 트랜잭션 등록
	* @return String
	* @throws IOException 
	* @throws SQLException 
	* @throws ParseException 
	* @Author : se-in shin
	**************************************************/
	public String NftWithdrawalsOffer(HashMap<String, String> paramMap) throws IOException, SQLException, ParseException {
		// 프론트에서 넘겨준 유저 + 행사 정보
		String requestId = paramMap.get("requestId");
		String amount = paramMap.get("amount");
		String childAddress = paramMap.get("childAddress");
		String contractAddress = paramMap.get("contractAddress");
				
		//유저 1 정보 조회
		Map<String, Object> userMap = userMapper.getUserInfo();
		String rsaKey = (String) userMap.get("rsaKey");
		String apiToken =(String) userMap.get("apiToken");
		String walletNum =(String) userMap.get("walletNum");
		String userAddress = (String) userMap.get("userAddress");
		
		//nft 출금 신청
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"requestId\":\""+requestId+"\",\"encryptedUserKey\":\""+rsaKey+"\",\"contractAddress\":\""+contractAddress+"\",\"tokenId\":\""+requestId+"\",\"receiverAddress\":\""+childAddress+"\",\"amount\":1,\"gasPrice\":\""+amount+"}");
		Request request = new Request.Builder()
		  .url("https://tetco-api.blockchainapi.io/2.0/wallets/"+walletNum+"/nfts/main-address/withdrawals")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer "+apiToken)
		  .addHeader("content-type", "application/json")
		  .build();

		Response response = client.newCall(request).execute();
		
		String uuid = response.body().toString();
		
		
		String status = "";
		//uuid값이 발행되면 nft 출금 신청 정보 조회 후 디비 등록
		if (!uuid.isEmpty()){
			OkHttpClient client2 = new OkHttpClient();

			Request request2 = new Request.Builder()
			  .url("https://tetco-api.blockchainapi.io/2.0/wallets/"+walletNum+"/nfts/withdrawals/"+uuid)
			  .get()
			  .addHeader("accept", "application/json")
			  .addHeader("Authorization", "Bearer "+apiToken)
			  .build();

			Response response2 = client2.newCall(request2).execute();
			
			
			//저장 보류 일단 상태값만 반환
			ResponseBody responseBody = response2.body();
			JSONParser paser = new JSONParser();
	        JSONObject nftTxn = (JSONObject) paser.parse(responseBody.string());
	        status = (String) nftTxn.get("status");
			
			
//			ResponseBody responseBody = response2.body();
//			JSONParser paser = new JSONParser();
//	        JSONObject obj = (JSONObject) paser.parse(responseBody.string());
//			HashMap<String, String> nftMap = (HashMap<String, String>) commonUtil.getMapFromJsonObject(obj).get("nft");
		}else {
			logger.debug("nft 출금 신청 에러");
		}
		
		return "NFT_"+status;
	}

}
