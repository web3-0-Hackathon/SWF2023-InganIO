package web3_hackathon.humanio.data.model

data class GetWalletInfoResponse(
    val idx: Int,
    val name: String,
    val type: String = "hot",
    val keyManagementType: String,
    val childAddressType: String,
    val status: String = "ACTIVATED",
    val mainAddress: WalletMainAddressInfo,
    val feeAddress: WalletFeeAddressInfo,
    val createdDate: String,
    val modifiedDate: String
)

data class WalletMainAddressInfo(
    val idx: Int,
    val address: String,
    val name: String,
    val derivationIndex: Int,
    val type: String,
    val status: String = "ACTIVATED",
    val usePin: Boolean,
    val createdDate: String,
    val modifiedDate: String
)

data class WalletFeeAddressInfo(
    val idx: Int,
    val address: String,
    val name: String,
    val derivationIndex: Int,
    val type: String,
    val status: String = "ACTIVATED",
    val usePin: Boolean,
    val createdDate: String,
    val modifiedDate: String
)