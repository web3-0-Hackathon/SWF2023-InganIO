package inganio.demo.Nft.Mapper;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NftMapper {

	public int nftContractIns(HashMap<String, String> nftMap) throws SQLException;

	public int nftTrIns(HashMap<String, String> nftTrMap) throws SQLException;
	
}
