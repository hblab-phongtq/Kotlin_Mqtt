package common.kmqtt.model.v

class KMqttUnsubcribe(
    val pktId : Int,
    val tuplesLen : Int,
    val topicLen : Int,
    val topic : String,
)
