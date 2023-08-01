package web3_hackathon.humanio.data.api.octet

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import web3_hackathon.humanio.data.model.CreateChildAddressPinRequest
import web3_hackathon.humanio.data.model.CreateChildAddressPinResponse
import web3_hackathon.humanio.data.model.CreateChildAddressRequest
import web3_hackathon.humanio.data.model.CreateChildAddressResponse
import web3_hackathon.humanio.data.model.FetchChildAddressListResponse
import web3_hackathon.humanio.data.model.UpdateChildAddressPinRequest
import web3_hackathon.humanio.data.model.UpdateChildAddressPinResponse

interface ChildAddressApi {
    // 자식 주소 생성
    @POST("wallets/{walletIdx}/child-addresses")
    fun createChildAddress(
        @Path("walletIdx") walletIdx: Int,
        @Body request: CreateChildAddressRequest
    ): Call<CreateChildAddressResponse>

    // 자식 주소 목록 조회
    @GET("wallets/{walletIdx}/child-addresses")
    fun fetchChildAddressList(
        @Path("walletIdx") walletIdx: Int,
        @Query("pos") pos: Int = 0,
        @Query("offset") offset: Int = 10,
        @Query("order") order: String = "desc",
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<List<FetchChildAddressListResponse>>

    // 자식 주소 PIN 생성
    @POST("wallets/{walletIdx}/child-addresses/{address}/pin")
    fun createChildAddressPin(
        @Path("walletIdx") walletId: Int,
        @Path("address") address: String,
        @Body createChildAddressPinRequest: CreateChildAddressPinRequest
    ): Call<CreateChildAddressPinResponse>

    // 자식 주소 PIN 수정
    @PUT("wallets/{walletIdx}/child-addresses/{address}/pin")
    fun updateChildAddressPin(
        @Path("walletIdx") walletId: Int,
        @Path("address") address: String,
        @Body updateChildAddressPinRequest: UpdateChildAddressPinRequest
    ): Call<UpdateChildAddressPinResponse>
}