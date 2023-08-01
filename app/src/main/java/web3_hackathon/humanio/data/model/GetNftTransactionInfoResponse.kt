package web3_hackathon.humanio.data.model

data class GetNftTransactionInfoResponse(
    val idx: Int,
    val uuid: String,
    val type: String,
    val txid: String,
    val fromAddress: String,
    val toAddress: String,
    val amount: String,
    val usedFee: String,
    val nonce: Int,
    val blockHeight: String,
    val memo: String,
    val status: String,
    val outputIndex: Int,
    val createdDate: String,
    val modifiedDate: String,
    val unfinalizedDate: String,
    val finalizedDate: String,
    val failedDate: String,
    val nft: NftTransactionInfo,
    val nftItemInfo: NftItemInfo
)

data class NftTransactionInfo(
    val symbol: String,
    val nameKo: String,
    val nameEn: String,
    val uri: String,
    val status: String,
    val type: String,
    val contractAddress: String,
    val iconUrl: String,
    val isExternal: Boolean,
    val createdDate: String,
    val modifiedDate: String
)

data class NftItemInfo(
    val tokenId: String,
    val amount: Int,
    val tokenUri: String,
    val status: String = "ACTIVATED",
    val createdDate: String,
    val modifiedDate: String
)