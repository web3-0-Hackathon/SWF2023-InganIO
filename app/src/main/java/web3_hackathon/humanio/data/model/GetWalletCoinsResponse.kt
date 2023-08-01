package web3_hackathon.humanio.data.model

import com.google.gson.annotations.SerializedName

data class GetWalletCoinsResponse(
    @SerializedName("idx") val idx: Int,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("nameKo") val nameKo: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("status") val status: String,
    @SerializedName("type") val type: String,
    @SerializedName("contractAddress") val contractAddress: String,
    @SerializedName("decimals") val decimals: Int,
    @SerializedName("createdDate") val createdDate: String,
    @SerializedName("modifiedDate") val modifiedDate: String
)