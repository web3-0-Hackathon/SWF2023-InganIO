package web3_hackathon.humanio.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import web3_hackathon.humanio.data.model.CreateNftItemResponse
import web3_hackathon.humanio.data.model.DeployNftContractRequest
import web3_hackathon.humanio.data.model.DeployNftContractResponse
import web3_hackathon.humanio.data.model.GetNftContractDeploymentInfoResponse
import web3_hackathon.humanio.data.model.GetNftContractResponse
import web3_hackathon.humanio.data.model.GetNftItemInfoResponse
import web3_hackathon.humanio.data.model.GetNftTransactionInfoResponse
import web3_hackathon.humanio.data.model.RequestCreateNftItem

interface NftApi {

    // NFT 아이템 발행 신청
    @POST("wallets/{walletIdx}/nfts/items/creations")
    fun createNftItem(
        @Path("walletIdx") walletIdx: Int,
        @Body requestCreateNftItem: RequestCreateNftItem
    ): Call<CreateNftItemResponse>

    // 발행 신청한 NFT 아이템 정보를 조회
    @GET("wallets/{walletIdx}/nfts/items/creations/{uuid}")
    fun getCreatedNftItem(
        @Path("walletIdx") walletIdx: Int,
        @Path("uuid") uuid: String
    ): Call<GetNftContractResponse>


    // NFT 컨트랙트 정보 조회
    @GET("wallets/{walletIdx}/nfts/contracts/{contractAddress}")
    fun getNftContract(
        @Path("walletIdx") walletIdx: Int,
        @Path("contractAddress") contractAddress: String
    ): Call<GetNftContractResponse>

    // 특정 지갑에서 배포한 NFT 컨트랙트 목록 조회
    @GET("wallets/{walletIdx}/nfts/contracts")
    fun getNftContractList(
        @Path("walletIdx") walletIdx: Int,
        @Query("pos") pos: Int = 0,
        @Query("offset") offset: Int = 10,
        @Query("order") order: String = "desc",
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("type") type: String,
        @Query("origin") origin: String
    ): Call<List<GetNftContractResponse>>

    // NFT 컨트랙트 배포 신청 / 정상적으로 신청 완료 시 배포 트랜잭션의 uuid를 반환.
    @POST("wallets/{walletIdx}/nfts/contracts/deployments")
    fun deployNftContract(
        @Path("walletIdx") walletIdx: Int,
        @Body deployNftContractRequest: DeployNftContractRequest
    ): Call<DeployNftContractResponse>

    // NFT 컨트랙트 배포 신청 정보 조회
    @GET("wallets/{walletIdx}/nfts/contracts/deployments/{uuid}")
    fun getNftContractDeploymentInfo(
        @Path("walletIdx") walletIdx: Int,
        @Path("uuid") uuid: String
    ): Call<GetNftContractDeploymentInfoResponse>

    // NFT 아이템 상세 정보 조회
    @GET("wallets/{walletIdx}/nfts/{contractAddress}/items/{tokenId}")
    fun getNftItemInfo(
        @Path("walletIdx") walletIdx: Int,
        @Path("contractAddress") contractAddress: String,
        @Path("tokenId") tokenId: String
    ): Call<GetNftItemInfoResponse>

    // 특정 컨트랙트에서 배포한 NFT 아이템 목록 조회
    @GET("wallets/{walletIdx}/nfts/{contractAddress}/items/{tokenId}")
    fun getNftItemList(
        @Path("walletIdx") walletIdx: Int,
        @Path("contractAddress") contractAddress: String,
        @Query("pos") pos: Int = 0,
        @Query("offset") offset: Int = 10,
        @Query("order") order: String = "desc",
        @Query("startDate") startDate: String = "",
        @Query("endDate") endDate: String = "",
        @Query("type") type: String = "",
        @Query("address") address: String = ""
    ): Call<List<GetNftItemInfoResponse>>

    // NFT 트랜잭션 정보 조회
    @GET("wallets/{walletIdx}/nfts/transactions/{uuid}")
    fun getNftTransactionInfo(
        @Path("walletIdx") walletIdx: Int,
        @Path("uuid") uuid: String
    ): Call<GetNftTransactionInfoResponse>

    // 지갑에서 발생한 NFT 트랜잭션 목록 조회
    @GET("wallets/{walletIdx}/nfts/transactions")
    fun getNftTransactionListInfo(
        @Path("walletIdx") walletIdx: Int,
        @Query("pos") pos: Int = 0,
        @Query("offset") offset: Int = 10,
        @Query("order") order: String = "desc",
        @Query("startDate") startDate: String = "",
        @Query("endDate") endDate: String = "",
        @Query("type") type: String = "",
        @Query("txid") txid: String = "",
        @Query("address") address: String = "",
        @Query("contractAddress") contractAddress: String = "",
        @Query("tokenId") tokenId: String = ""
    ): Call<List<GetNftTransactionInfoResponse>>

    // NFT 수수료 계산 (NFT 트랜잭션 생성시의 최적의 수수료를 계산
    @GET("wallets/{walletIdx}/nfts/fees")
    fun getNftFee(
        @Path("walletIdx") walletIdx: Int,
        @Query("estimateNftType") estimateNftType: String,
        @Query("gasPrice") gasPrice: String = ""
    ): Call<GetNftTransactionInfoResponse>
}