package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class WithdrawMoneyFromWalletRequest(
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("contractAddress")
    val contractAddress: String = "",
    @SerializedName("requestId")
    val requestId: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("senderAddress")
    val senderAddress: String,
    @SerializedName("receiverAddress")
    val receiverAddress: String,
    @SerializedName("receiverMemo")
    val receiverMemo: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("useFeeDelegation")
    val useFeeDelegation: String = "",
    @SerializedName("encryptedUserKey")
    val encryptedUserKey: String = "",
    @SerializedName("pin")
    val pin: String = ""
)