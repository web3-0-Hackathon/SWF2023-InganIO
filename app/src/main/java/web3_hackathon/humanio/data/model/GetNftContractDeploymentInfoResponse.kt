package web3_hackathon.humanio.data.model

data class GetNftContractDeploymentInfoResponse(
    val nft: GetNftContractResponse,
    val nftTransaction: Transaction,
    val fee: Fee,
    val uuid: String,
    val status: String,
    val createdDate: String,
    val modifiedDate: String,
    val errorCode: String,
    val errorMessage: String
)

data class Fee(
    val idx: Int,
    val gasLimit: String,
    val gasPrice: String,
    val rate: String,
    val unit: String,
    val createdDate: String,
    val modifiedDate: String
)