package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class UpdateChildAddressPinRequest(
    @SerializedName("pinHash")
    val pinHash: String,
    @SerializedName("updatePinHash")
    val updatePinHash: String
)