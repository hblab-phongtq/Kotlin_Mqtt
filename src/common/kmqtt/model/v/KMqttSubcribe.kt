package common.kmqtt.model.v

class KMqttSubcribe(
    val pktId : Int,
    val tuplesLen : Int,
    val qos : Int,
    val topicLen : Int,
    val topic : String,
)
