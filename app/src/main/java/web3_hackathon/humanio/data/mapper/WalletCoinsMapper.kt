package web3_hackathon.humanio.data.mapper

import web3_hackathon.humanio.data.model.GetWalletCoinsResponse
import web3_hackathon.humanio.data.model.WalletCoinsData

fun GetWalletCoinsResponse.toData(): WalletCoinsData {
    return WalletCoinsData(
        idx = idx,
        symbol = symbol,
        nameKo = nameKo,
        nameEn = nameEn,
        status = status,
        type = type,
        contractAddress = contractAddress,
        decimals = decimals,
        createdDate = createdDate,
        modifiedDate = modifiedDate
    )
}