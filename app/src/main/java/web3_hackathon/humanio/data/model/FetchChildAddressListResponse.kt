package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class FetchChildAddressListResponse(
    @SerializedName("idx")
    val idx: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("derivationIndex")
    val derivationIndex: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("modifiedDate")
    val modifiedDate: String
)