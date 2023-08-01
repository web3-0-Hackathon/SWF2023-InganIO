package web3_hackathon.humanio.data.api.human_io

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import web3_hackathon.humanio.data.model.*

interface HumanIONftApi {
    // NFT 컨트랙트 배포 신청 / 정상적으로 신청 완료 시 배포 트랜잭션의 uuid를 반환.
    @POST("octet/nftContractOffer")
    fun deployNftContract(
        @Body deployNftContractRequest: DeployNftContractRequest
    ): Call<DeployNftContractResponse>

    // NFT 컨트랙트 배포 신청 정보 조회
    @GET("/api/octet/contractInfo/{uuid}")
    fun getNftContractDeploymentInfo(
        @Path("uuid") uuid: String?
    ): Call<GetNftContractDeploymentInfoResponse>

    // NFT 아이템 발행 신청
    @POST("/api/octet/nftContractOffer")
    fun createNftItem(
        @Body createNftItemRequest: CreateNftItemRequest
    ): Call<CreateNftItemResponse>
}