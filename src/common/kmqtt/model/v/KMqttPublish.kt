package common.kmqtt.model.v

class KMqttPublish(
    val pktId : Int,
    val topicLen : Int,
    val topic : String,
    val payloadLen : Int,
    val payload : String,
)
