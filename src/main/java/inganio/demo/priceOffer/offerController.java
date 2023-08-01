package inganio.demo.priceOffer;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offer")
public class offerController {

	
	@PostMapping("/priceOffer")
	public priceOffer(@RequestBody HashMap<String, String> paramMap) {
			
	}
}
