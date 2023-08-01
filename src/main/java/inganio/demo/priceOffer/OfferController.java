package inganio.demo.priceOffer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inganio.demo.priceOffer.Mapper.OfferMapper;

@RestController
@RequestMapping("/api/offer")
public class OfferController {

	@Autowired
	private OfferMapper offerMapper;
	
	/**************************************************
	* @MethodName : priceOffer
	* @Description: 가격 제안 저장 
	* @return : int
	* @throws SQLException 
	* @Author : se-in shin
	**************************************************/
	@PostMapping("/priceOffer")
	public HashMap<String, String> priceOffer(@RequestBody HashMap<String, String> paramMap) throws SQLException {
		String offerPrice = paramMap.get("offerPrice");
		String conSeq = paramMap.get("conSeq");
		
		HashMap<String, String> rstMap = new HashMap<String, String>();
		int rst = offerMapper.offerPriceIns(paramMap);
		if(rst>0) {
			rstMap.put("statusCode", "200");
		}else {
			rstMap.put("statusCode", "500");
		}
		return rstMap;
	}
	
	/**************************************************
	* @MethodName : offerPriceList
	* @Description: 가격 제안 리스트 조회
	* @return : List<HashMap<String, String>> 
	* @throws SQLException 
	* @Author : se-in shin
	**************************************************/
	@GetMapping("/offerPriceList")
	public List<HashMap<String, String>> offerPriceList() throws SQLException{
			List<HashMap<String, String>> offerPriceList = offerMapper.offerList();
			System.out.println(offerPriceList);
			return offerPriceList;
	} 
}
