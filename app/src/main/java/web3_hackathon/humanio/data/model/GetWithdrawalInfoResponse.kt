package web3_hackathon.humanio.data.model

data class GetWithdrawalInfoResponse(
    val idx: Int,
    val coin: Coin,
    val uuid: String,
    val fromAddress: String,
    val toAddress: String,
    val amount: String,
    val memo: String,
    val requestId: String,
    val type: String,
    val description: String,
    val requiredApprovalCount: Int,
    val useApiAutoApproval: Boolean,
    val useFeeDelegation: Boolean,
    val feePayerAddress: String,
    val status: String,
    val createdDate: String,
    val modifiedDate: String,
    val rejectedDate: String,
    val transaction: Transaction,
    val errorCode: String,
    val errorMessage: String
)

data class Coin(
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

data class Transaction(
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
    val serialized: String,
    val memo: String,
    val status: String,
    val outputIndex: Int,
    val createdDate: String,
    val modifiedDate: String,
    val unfinalizedDate: String,
    val finalizedDate: String,
    val failedDate: String,
    val coin: Coin,
)