package web3_hackathon.humanio.data.model

data class WalletCoinsData(
    val idx: Int,
    val symbol: String,
    val nameKo: String,
    val nameEn: String,
    val status: String,
    val type: String,
    val contractAddress: String,
    val decimals: Int,
    val createdDate: String,
    val modifiedDate: String
)
