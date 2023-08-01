package web3_hackathon.humanio.data.model

data class GetNftItemInfoResponse(
    val tokenId: String,
    val amount: Int,
    val tokenUri: String,
    val status: String = "ACTIVATED",
    val createdDate: String,
    val modifiedDate: String,
    val nftItemBalances: List<String>
)