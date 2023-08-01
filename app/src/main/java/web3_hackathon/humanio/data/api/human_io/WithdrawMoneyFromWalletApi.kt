package web3_hackathon.humanio.data.api.human_io

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import web3_hackathon.humanio.data.model.human_io.EventInfoRequest
import web3_hackathon.humanio.data.model.human_io.EventInfoResponse

interface WithdrawMoneyFromWalletApi {

    @POST("user/userWithdrawals")
    fun withdrawMoneyFromWallet(
        @Body eventInfoRequest: EventInfoRequest
    ): Call<EventInfoResponse>
}