package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class CreateChildAddressPinRequest(
    @SerializedName("recoveryPin")
    val recoveryPin: String,
    @SerializedName("pin")
    val pin: String
)