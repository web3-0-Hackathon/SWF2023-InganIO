package inganio.demo.Nft.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inganio.demo.Common.commonUtil;
import inganio.demo.Nft.Mapper.NftMapper;
import inganio.demo.User.Mapper.UserMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


@Service
public class NftService {
	
	private static final Logger logger = LoggerFactory.getLogger(NftService.class);
	
	private String HOST_API_KEY= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbklkeCI6MTkyMjk0LCJ0b2tlblR5cGUiOiJXQUxMRVQiLCJ0b2tlbkV4cGlyZWREYXRlIjoiMjAyMy0xMC0yMVQxMTo0NjowNy44MzJaIn0.ERsVBCzmL0k9SNbqZdZ1Rud6bx2BaKy-71S2ZyUD1Xw";
	
	private String HOST_RSA_KEY = "d+d7t7N5LJ6fCg05vAKKqblKYbwdmn8ZwsQjdU9p/voU/E6CyBeTVmrdhbj3pQAIa8Y/45X6Q HWw83KZpeHmbwpgL+5XYGPmjTiAaELXUP9PDNQl/QhRIBn993ZF6PYr+htnQWl8/QYATMyyLP dKdxllkuKzMfjy2Em+A4gEJgVFehzulwFCctBkngGjq7VG/BgOrbERHMmvahdRIZEEWaMdPow 0TFywljPEghhfp957dRN1l6MzekmPPDPVoK1WKzLMavdiDyfHzvATSIPiw+BFmb02F9qfpQd2 6qmEZmxiengAixe5enZq4qIgJuk2quada/yzjaqGYqi4GpJgsqCSshYGZMfVdTRGnNHqT6E0X jNNpM6cuITo6MyFSxmFo+gEi1v7N+IW7GfMoIu+OXhsqy4sXbQwB1/sDqlW4AAvfu//tncyqI O8q154p6mvsSMPvPzhAY3bWgz8dMFIx8nGBj4lzoRlSjabPiZQsEsEawhHt9kIVW6l/2qPG+de";

	
	@Autowired
	private NftMapper nftMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	/**************************************************
	* @MethodName : contractInfoIns
	* @Description: NFT 컨트랙트 배포 조회 후 db 저장
	* @return String
	* @throws org.json.simple.parser.ParseException 
	* @Author : se-in shin
	**************************************************/
	@SuppressWarnings("unchecked")
	public HashMap<String, String> contractInfoIns(Response response) throws IOException, ParseException, org.json.simple.parser.ParseException {
        HashMap<String, String> rstMap = new HashMap<String, String>();
		
		ResponseBody responseBody = response.body();
        int rst = 0;
        int rst2 = 0;
        
        JSONParser paser = new JSONParser();
        JSONObject obj = (JSONObject) paser.parse(responseBody.string());
        
        String status = (String) commonUtil.getMapFromJsonObject(obj).get("status");
        System.out.println("status:::"+status);
        if(status.equals("SENT")) {
        	HashMap<String, String> nftMap = (HashMap<String, String>) commonUtil.getMapFromJsonObject(obj).get("nft");
    		HashMap<String, String> nftTrMap = (HashMap<String, String>) commonUtil.getMapFromJsonObject(obj).get("nftTransaction");
//            System.out.println("xml="+ ((HashMap<?, ?>) commonUtil.getMapFromJsonObject(obj).get("nft")).get("nameKo"));
    		
    		String contractAddress = nftMap.get("contractAddress");
    		rstMap.put("contractAddress", contractAddress);
    	
    		//아이템 등록 시 저장하기 위한 컨트랙트 고유 인덱스
    		String conSeq = "";
    		try {
    			//nft 컨트랙트 저장
				rst = nftMapper.nftContractIns(nftMap);
				conSeq = String.valueOf(nftMap.get("conSeq"));
				rstMap.put("conSeq", conSeq);
		
    			if (rst>0) {
    				nftTrMap.put("conSeq", conSeq);
    				// nft 트랜잭션 저장
    				rst2 = nftMapper.nftTrIns(nftTrMap);
    			}else{
    				logger.debug("nft 컨트랙트 -- nftContractIns 저장 에러  --");
    			}
    			
    		} catch (Exception e) {
    			// TODO: handle exception
    			logger.debug("error:"+e);
    		}
    		
    		rstMap.put("statusCode", "SENT");
        }else {
        	rstMap.put("statusCode", "AWAITING_WITHDRAWAL");
        }
		System.out.println("결과::: "+ rstMap);
		return  rstMap;
	}
	
	/**************************************************
	* @MethodName : contractOfferIns
	* @Description: NFT 컨트랙트 배포 신청
	* @return : String
	 * @throws SQLException 
	 * @throws org.json.simple.parser.ParseException 
	* @Author : se-in shin
	**************************************************/
	public String contractOfferIns(HashMap<String, String> paramMap) throws IOException, SQLException, org.json.simple.parser.ParseException {
		String eventNm = paramMap.get("eventNm");	//requestId, contractName
		String symbol = paramMap.get("symbol");		// symbol, baseuri
		
		//행사 등록
//		String eventLocation = paramMap.get("eventLocation");
//		String eventDate = paramMap.get("eventDate");
//		String startPrice = paramMap.get("startPrice");
//		String amount = paramMap.get("amount");
//		String startDate = paramMap.get("startDate");
//		String endDate = paramMap.get("endDate");
//		
		int eventRst = nftMapper.eventIns(paramMap);
		System.out.println(paramMap+"----------paramMAp");
		// 컨트랙트 배포 신청
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"contractSpec\":\"ERC1155\",\"requestId\":\""+eventNm+"\",\"encryptedUserKey\":\""+ HOST_RSA_KEY+"\",\"contractName\":\""+eventNm+"\",\"tokenSymbol\":\""+symbol+"\",\"baseUri\":\""+symbol+"/\"}");
		
		Request request = new Request.Builder()
		  .url("https://tetco-api.blockchainapi.io/2.0/wallets/1050/nfts/contracts/deployments")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer "+ HOST_API_KEY)
		  .addHeader("content-type", "application/json")
		  .build();

		Response response = client.newCall(request).execute();
		ResponseBody responseBody = response.body();
		
		JSONParser paser = new JSONParser();
        JSONObject nftInfo = (JSONObject) paser.parse(responseBody.string());
        System.out.println(nftInfo+"---------nftInfo");
        String uuid = (String) nftInfo.get("uuid");
        
		System.out.println("contractOfferIns 컨트랙트 배포 신청 후 uuid : "+uuid);
		return uuid;
	}

	
	/**************************************************
	* @MethodName : nftItemOffer
	* @Description: NFT 아이템 발행
	* @return : String
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws org.json.simple.parser.ParseException 
	* @Author : se-in shin
	**************************************************/
	public String nftItemOffer(HashMap<String, String> paramMap) throws IOException, SQLException, org.json.simple.parser.ParseException {
		// 아이템 발행 파라미터들 
		String tokenUri = paramMap.get("tokenUri");
		String amount = paramMap.get("amount");
		String symbol = paramMap.get("symbol");
		String tokenId = paramMap.get("tokenId");
		String contractAddress = paramMap.get("contractAddress");
		
		
		//호스트 정보 조회 - 로그인을 구현한다면 세션에서 받아옴
		Map<String, Object> hostMap = userMapper.getHostInfo();
		String rsaKey = (String) hostMap.get("rsaKey");
		String apiToken =(String) hostMap.get("apiToken");
		String walletNum =(String) hostMap.get("walletNum");
		String hostAddress = (String) hostMap.get("hostAddress");
		hostMap.put("contractAddress", contractAddress);
		//외부 api 연동
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"requestId\":\""+symbol+"\",\"encryptedUserKey\":\""+rsaKey+"\",\"contractAddress\":\""+contractAddress+"\",\"tokenId\":\""+tokenId+"\",\"tokenUri\":\""+tokenUri+"\",\"amount\":"+amount+"}");
		Request request = new Request.Builder()
		  .url("https://octet-api.blockchainapi.io/2.0/wallets/"+walletNum+"/nfts/items/creations")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer "+apiToken)
		  .addHeader("content-type", "application/json")
		  .build();

		Response response = client.newCall(request).execute();
		ResponseBody responseBody = response.body();
		
		JSONParser paser = new JSONParser();
        JSONObject nftInfo = (JSONObject) paser.parse(responseBody.string());
        
        String uuid = (String) nftInfo.get("uuid");
        
        System.out.println("nftInfo-----"+nftInfo);
        System.out.println("uuid-----"+uuid);
		
		String statusCode= "";
		//uuid 즉 아이템이 발행되면 정보조회 후 상태값 조회
		if(!uuid.isEmpty() || uuid != null) {
			hostMap.put("uuid", uuid);
			statusCode = nftItemStatusInfo(hostMap);
		} 
		
		System.out.println("statusCode: " +statusCode);
		
		
		return statusCode;
	}

	
	
	/**************************************************
	* @MethodName : nftItemStatusInfo
	* @Description: NFT 아이템 발행 신청 정보 조회
	* @return : String
	 * @throws IOException 
	 * @throws org.json.simple.parser.ParseException 
	* @Author : se-in shin
	**************************************************/
	public String nftItemStatusInfo(Map<String, Object> hostMap) throws IOException, org.json.simple.parser.ParseException {
		String status =  "";
		String walletNum = (String) hostMap.get("walletNum");
		String uuid = (String) hostMap.get("uuid");
		String apiToken = (String) hostMap.get("apiToken");
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		  .url("https://octet-api.blockchainapi.io/2.0/wallets/"+walletNum+"/nfts/items/creations/"+uuid)
		  .get()
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer "+ apiToken)
		  .build();

		Response response = client.newCall(request).execute();
		ResponseBody responseBody = response.body();
		
		JSONParser paser = new JSONParser();
        JSONObject nftInfo = (JSONObject) paser.parse(responseBody.string());
        
        status = (String) commonUtil.getMapFromJsonObject(nftInfo).get("status");
        System.out.println("nftItemStatusInfo---nftInfo 정보 조회-----"+nftInfo);
        System.out.println("nftItemStatusInfo---status 정보 조회 상태-----"+status);
        
        //정보조회가 잘 된다면 db 등록해야 함
        //nftMapper.nftItemIns();
        
		return status;
	}
	
	
	
	
}
