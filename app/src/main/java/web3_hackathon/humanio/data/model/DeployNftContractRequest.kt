package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class DeployNftContractRequest(
    @SerializedName("requestId")
    val requestId: String,
    @SerializedName("encryptedUserKey")
    val encryptedUserKey: String = "",
    @SerializedName("nftTransactionMemo")
    val nftTransactionMemo: String = "",
    @SerializedName("useFeeDelegation")
    val useFeeDelegation: Boolean = false,
    @SerializedName("contractSpec")
    val contractSpec: String,
    @SerializedName("contractName")
    val contractName: String,
    @SerializedName("tokenSymbol")
    val tokenSymbol: String,
    @SerializedName("baseUri")
    val baseUri: String = "",
    @SerializedName("nftIconUrl")
    val nftIconUrl: String = ""
)