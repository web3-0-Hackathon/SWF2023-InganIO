package web3_hackathon.humanio.data.api.octet

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import web3_hackathon.humanio.data.model.GetWithdrawalInfoResponse

interface WithdrawalApi {

    // 특정 출금 신청 정보 조회
    @GET("wallets/{walletIdx}/withdrawals/{uuid}")
    fun getWithdrawalInfo(
        @Path("walletIdx") walletIdx: Int,
        @Path("uuid") uuid: String
    ): Call<GetWithdrawalInfoResponse>

    // 출금 신청 목록 조회
    @GET("wallets/{walletIdx}/withdrawals")
    fun getWithdrawalList(
        @Path("walletIdx") walletIdx: Int,
        @Query("pos") pos: Int = 0,
        @Query("offset") offset: Int = 10,
        @Query("order") order: String = "desc",
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("type") type: String,
        @Query("status") status: String,
        @Query("uuid") uuid: String,
        @Query("address") address: String,
        @Query("symbol") symbol: String,
        @Query("contractAddress") contractAddress: String
    ): Call<List<GetWithdrawalInfoResponse>>
}