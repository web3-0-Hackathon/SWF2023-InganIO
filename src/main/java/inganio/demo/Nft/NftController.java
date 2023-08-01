package inganio.demo.Nft;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inganio.demo.Common.commonUtil;
import inganio.demo.Nft.Service.NftService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@RestController
@RequestMapping("/api/octet")
public class NftController {
	private static final Logger logger = LoggerFactory.getLogger(NftController.class);

	@Autowired
	private NftService nftService;
	
	private String HOST_API_KEY= "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbklkeCI6MTkyMjk0LCJ0b2tlblR5cGUiOiJXQUxMRVQiLCJ0b2tlbkV4cGlyZWREYXRlIjoiMjAyMy0xMC0yMVQxMTo0NjowNy44MzJaIn0.ERsVBCzmL0k9SNbqZdZ1Rud6bx2BaKy-71S2ZyUD1Xw";
	 
	private String HOST_RSA_KEY = "d+d7t7N5LJ6fCg05vAKKqblKYbwdmn8ZwsQjdU9p/voU/E6CyBeTVmrdhbj3pQAIa8Y/45X6Q HWw83KZpeHmbwpgL+5XYGPmjTiAaELXUP9PDNQl/QhRIBn993ZF6PYr+htnQWl8/QYATMyyLP dKdxllkuKzMfjy2Em+A4gEJgVFehzulwFCctBkngGjq7VG/BgOrbERHMmvahdRIZEEWaMdPow 0TFywljPEghhfp957dRN1l6MzekmPPDPVoK1WKzLMavdiDyfHzvATSIPiw+BFmb02F9qfpQd2 6qmEZmxiengAixe5enZq4qIgJuk2quada/yzjaqGYqi4GpJgsqCSshYGZMfVdTRGnNHqT6E0X jNNpM6cuITo6MyFSxmFo+gEi1v7N+IW7GfMoIu+OXhsqy4sXbQwB1/sDqlW4AAvfu//tncyqI O8q154p6mvsSMPvPzhAY3bWgz8dMFIx8nGBj4lzoRlSjabPiZQsEsEawhHt9kIVW6l/2qPG+de";
	
	/**************************************************
	* @MethodName : nftContractInfo
	* @Description: NFT 컨트랙트 배포 조회
	* @return : int
	* @Author : se-in shin
	**************************************************/
//	@GetMapping({"/contractInfo/{uuid}"})
//	@PathVariable("uuid") String uuid
	public HashMap<String, String> nftContractInfo(String uuid) throws SQLException, IOException {
		HashMap<String, String> rstMap = null;
		int status = commonUtil.StatusCode.INTERNAL_SERVER_ERROR;
		try {
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
			  .url("https://tetco-api.blockchainapi.io/2.0/wallets/1050/nfts/contracts/deployments/"+uuid)
			  .get()
			  .addHeader("accept", "application/json")
			  .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbklkeCI6MTkyMjk0LCJ0b2tlblR5cGUiOiJXQUxMRVQiLCJ0b2tlbkV4cGlyZWREYXRlIjoiMjAyMy0xMC0yMVQxMTo0NjowNy44MzJaIn0.ERsVBCzmL0k9SNbqZdZ1Rud6bx2BaKy-71S2ZyUD1Xw")
			  .build();

			Response response = client.newCall(request).execute();
			
			// 컨트랙트 정보 조회 등록 후 컨트랙트 주소와 고유 인덱스 map에 저장
			rstMap = nftService.contractInfoIns(response);
			System.out.println("컨트랙트 정보 조회 후 컨트랙트 주소, 고유값 조회 맵----"+ rstMap);
			// 테스트 출력문
//			rst.put("rst",response.body().string());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return rstMap;
	}
	
	/**************************************************
	* @MethodName : nftContractOffer
	* @Description: NFT 컨트랙트 배포 신청 api
	* @return : int
	* @Author : se-in shin
	**************************************************/
	@PostMapping({"/nftContractOffer"})
	public HashMap<String, String> nftContractOffer(@RequestBody HashMap<String, String> paramMap) throws SQLException{
		//System.out.println(paramMap);
		//행사 정보
		HashMap<String, String> rstMap = null;
		String uuid = null;
		
		try {
			//컨트랙트 배포 신청한 후 트랜잭션 uuid 값으로 컨트랙트 배포 정보 조회로 이동
			uuid = nftService.contractOfferIns(paramMap);
			rstMap = nftContractInfo(uuid);
			
			//조회 완료 후 컨트랙트 주소 조회
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("error:" + e);
		}
		return rstMap;
	}
	
	
	/**************************************************
	* @MethodName : nftItem
	* @Description: NFT 아이템 발행
	* @return : String
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws IOException 
	* @Author : se-in shin
	**************************************************/
	public HashMap<String, String> nftItemOffer(@RequestBody HashMap<String, String> paramMap) throws IOException, SQLException, ParseException {
		HashMap<String, String> rstMap = new HashMap();
		rstMap.put("status", "500");
		String status = nftService.nftItemOffer(paramMap);
		rstMap.put("status", status);
		return rstMap;
	}
	
}

