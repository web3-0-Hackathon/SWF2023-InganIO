package web3_hackathon.humanio.data.api.octet

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import web3_hackathon.humanio.data.model.GetWalletCoinsResponse

interface WalletCoinApi {

    // 지갑에 등록된 자산 목록 조회 (지갑에 추가되어 사용할 수 있는 자산의 목록을 조회)
    @GET("wallets/{walletIdx}/coins")
    fun getWalletCoins(
        @Path("walletIdx") walletIdx: Int // 지갑 번호
    ): Call<List<GetWalletCoinsResponse>>
}