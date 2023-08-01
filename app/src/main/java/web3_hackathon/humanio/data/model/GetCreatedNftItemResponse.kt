package web3_hackathon.humanio.data.model

data class GetCreatedNftItemResponse(
    val tokenId: String,
    val amount: Int,
    val tokenUri: String,
    val receiverAddress: String,
    val nftTransaction: NftTransaction,
    val fee: Fee,
    val uuid: String,
    val status: String,
    val createdDate: String,
    val modifiedDate: String,
    val errorCode: String,
    val errorMessage: String
)

data class NftTransaction(
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
    val failedDate: String
)