package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class CreateNftItemRequest(
    @SerializedName("requestId")
    val requestId: String,
    @SerializedName("encryptedUserKey")
    val encryptedUserKey: String = "",
    @SerializedName("nftTransactionMemo")
    val nftTransactionMemo: String = "",
    @SerializedName("useFeeDelegation")
    val useFeeDelegation: Boolean = false,
    @SerializedName("contractAddress")
    val contractAddress: String,
    @SerializedName("tokenId")
    val tokenId: String,
    @SerializedName("tokenUri")
    val tokenUri: String = "",
    @SerializedName("receiverAddress")
    val receiverAddress: String,
    @SerializedName("amount")
    val amount: Int = 0
)