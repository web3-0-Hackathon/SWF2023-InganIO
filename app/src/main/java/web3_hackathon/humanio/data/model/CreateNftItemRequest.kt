package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class CreateNftItemRequest(
    @SerializedName("conSeq")
    val conSeq: String?,
    @SerializedName("contractAddress")
    val contractAddress: String?
)