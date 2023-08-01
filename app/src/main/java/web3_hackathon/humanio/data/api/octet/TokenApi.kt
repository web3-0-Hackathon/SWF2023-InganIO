package web3_hackathon.humanio.data.api.octet

import retrofit2.Call
import retrofit2.http.GET
import web3_hackathon.humanio.data.model.GetApiTokenInfoResponse

interface TokenApi {

    // API 토큰 정보 조회 (Authorization 헤더에 담긴 API 토큰에 대한 정보를 조회)
    @GET("token")
    fun getApiTokenInfo(): Call<GetApiTokenInfoResponse>
}