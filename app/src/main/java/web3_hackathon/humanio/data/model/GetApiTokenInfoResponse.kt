package web3_hackathon.humanio.data.model

data class GetApiTokenInfoResponse(
    val idx: Int,
    val status: String,
    val content: String,
    val expiredDate: String,
    val lastUsedDate: String,
    val createdDate: String,
    val modifiedDate: String,
    val type: Type,
    val company: Company,
    val user: User,
    val wallet: Wallet
)

data class Type(
    val type: String
)

data class Company(
    val xompany: String
)

data class User(
    val user: String
)

data class Wallet(
    val wallet: String
)