package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class GetNftContractDeploymentInfoResponse(
    @SerializedName("conSeq") val conSeq: String,
    @SerializedName("contractAddress") val contractAddress: String,
    @SerializedName("statusCode") val statusCode: String
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