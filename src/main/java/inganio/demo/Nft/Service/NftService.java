package inganio.demo.Nft.Service;

import java.io.IOException;
import java.util.HashMap;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inganio.demo.Common.commonUtil;
import inganio.demo.Nft.Mapper.NftMapper;
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
	
	/**************************************************
	* @MethodName : contractInfoIns
	* @Description: NFT 컨트랙트 배포 조회 후 db 저장
	* @return response
	* @throws org.json.simple.parser.ParseException 
	* @Author : se-in shin
	**************************************************/
	@SuppressWarnings("unchecked")
	public boolean contractInfoIns(Response response) throws IOException, ParseException, org.json.simple.parser.ParseException {
        ResponseBody responseBody = response.body();
        int rst = 0;
        int rst2 = 0;
        
        JSONParser paser = new JSONParser();
        JSONObject obj = (JSONObject) paser.parse(responseBody.string());
		HashMap<String, String> nftMap = (HashMap<String, String>) commonUtil.getMapFromJsonObject(obj).get("nft");
		HashMap<String, String> nftTrMap = (HashMap<String, String>) commonUtil.getMapFromJsonObject(obj).get("nftTransaction");
//        System.out.println("xml="+ ((HashMap<?, ?>) commonUtil.getMapFromJsonObject(obj).get("nft")).get("nameKo"));
		try {
			//nft 컨트랙트 저장
			rst = nftMapper.nftContractIns(nftMap);
			
			if (rst>0) {
				nftTrMap.put("conSeq", String.valueOf(nftMap.get("conSeq")));
				//nft 트랜잭션 저장
				rst2 = nftMapper.nftTrIns(nftTrMap);
			}else{
				logger.debug("nft 컨트랙트 -- nftContractIns 저장 에러  --");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("error:"+e);
		}
		return rst2 > 0 ? true : false;
	}
	
	/**************************************************
	* @MethodName : contractOfferIns
	* @Description: NFT 컨트랙트 배포 신청
	* @return : String
	* @Author : se-in shin
	**************************************************/
	public String contractOfferIns(HashMap<String, String> paramMap) throws IOException {
		String eventNm = paramMap.get("eventNm");	//requestId, contractName
		String symbol = paramMap.get("symbol");		// symbol, baseuri
		
		HashMap<String, Object> resultMap = null;
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\"contractSpec\":\"ERC1155\",\"requestId\":\""+eventNm+"\",\"encryptedUserKey\":\""+ HOST_RSA_KEY+"\",\"contractName\":\""+eventNm+"\",\"tokenSymbol\":\""+symbol+"\",\"baseUri\":\""+symbol+"/\"}");
		
		Request request = new Request.Builder()
		  .url("https://tetco-api.blockchainapi.io/2.0/wallets/1050/nfts/contracts/deployments")
		  .addHeader("accept", "application/json")
		  .addHeader("Authorization", "Bearer "+ HOST_API_KEY)
		  .addHeader("content-type", "application/json")
		  .build();

		Response response = client.newCall(request).execute();
		String uuid = response.body().string();
		//System.out.println(uuid);
//		JSONParser paser = new JSONParser();
//        JSONObject uuid = (JSONObject) paser.parse(responseBody.string());
		return uuid;
		// 컨트랙트 배포 후 조회정보 등록
	}

	
	
	
	
	
}
