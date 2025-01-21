package common.kmqtt.model

class KMqttUnsubcribe(
    val pktId : Int,
    val tuplesLen : Int,
    val topicLen : Int,
    val topic : String,
)
