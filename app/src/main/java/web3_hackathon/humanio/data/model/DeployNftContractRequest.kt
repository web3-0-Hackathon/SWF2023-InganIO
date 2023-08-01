package web3_hackathon.humanio.data.model


data class DeployNftContractRequest(
    val eventNm: String? = "event1",
    val eventLocation: String? = "seoul",
    val eventDate: String?,
    val tokenUri: String = "QmdwxwiHgu5kJH6p6S7APXaVT7D5Hu1nwydWFbAzac58QE",
    val startPrice: String? = "0.000000000000000001",
    val amount: String? = "2",
    val startDate: String?,
    val endDate: String?,
    val symbol: String? = "AAA",
    val tokenId: String? = "123",
)