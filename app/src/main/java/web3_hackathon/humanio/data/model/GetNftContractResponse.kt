package web3_hackathon.humanio.data.model

data class GetNftContractResponse(
    val symbol: String,
    val nameKo: String,
    val nameEn: String,
    val uri: String,
    val status: String = "ACTIVATED",
    val type: String,
    val contractAddress: String,
    val iconUrl: String,
    val isExternal: Boolean,
    val createdDate: String,
    val modifiedDate: String
)