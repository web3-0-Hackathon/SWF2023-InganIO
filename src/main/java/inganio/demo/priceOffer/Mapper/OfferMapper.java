package inganio.demo.priceOffer.Mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OfferMapper {

	int offerPriceIns(HashMap<String, String> paramMap) throws SQLException;

	List<HashMap<String, String>> offerList(String eventId) throws SQLException;

	List<HashMap<String, String>> offerList() throws SQLException;
	
}
