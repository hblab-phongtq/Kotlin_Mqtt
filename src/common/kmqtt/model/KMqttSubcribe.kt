package common.kmqtt.model

class KMqttSubcribe(
    val pktId : Int,
    val tuplesLen : Int,
    val qos : Int,
    val topicLen : Int,
    val topic : String,
)
