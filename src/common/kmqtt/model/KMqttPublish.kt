package common.kmqtt.model

class KMqttPublish(
    val pktId : Int,
    val topicLen : Int,
    val topic : String,
    val payloadLen : Int,
    val payload : String,
)
