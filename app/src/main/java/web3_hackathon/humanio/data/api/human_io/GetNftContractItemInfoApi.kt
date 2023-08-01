package web3_hackathon.humanio.data.api.human_io

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetNftContractItemInfoApi {
    @GET("user/nftContractItemInfo")
    fun getNftContractItemInfo(
    @Query("hostId") hostId: Int = 1,
    @Query("eventId") eventId: Int = 1,
    ): Call<ContractItemInfoResponse>
}

data class ContractItemInfoResponse(
    @SerializedName("tokenId") val tokenId: String,
    @SerializedName("contractAddress") val contractAddress: String
)