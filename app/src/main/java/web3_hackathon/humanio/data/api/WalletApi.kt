package web3_hackathon.humanio.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import web3_hackathon.humanio.data.model.GetWalletInfoResponse
import web3_hackathon.humanio.data.model.WithdrawMoneyFromWalletRequest
import web3_hackathon.humanio.data.model.WithdrawMoneyFromWalletResponse

interface WalletApi {

    // 지갑 정보 조회
    @GET("wallets/{walletIdx}")
    fun getWalletInfo(
        @Path("walletIdx") walletIdx: Int,
    ): Call<GetWalletInfoResponse>

    // 출금 신청 (지갑이 가지고 있는 주소에서 출금을 신청)
    @POST("wallets/{walletIdx}/withdrawals")
    fun withdrawMoneyFromWallet(
        @Path("walletIdx") walletIdx: Int,
        @Body withdrawMoneyFromWalletRequest: WithdrawMoneyFromWalletRequest
    ): Call<WithdrawMoneyFromWalletResponse>
}