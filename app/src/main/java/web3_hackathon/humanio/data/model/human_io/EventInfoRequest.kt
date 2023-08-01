package web3_hackathon.humanio.data.model.human_io

data class EventInfoRequest(
    val requestId: String,
    val amount: String,
    val childAddress: String,  //자식 주소 생성 api를 날려서 보내면 된다.
    val contractAddress: String
)