package common.kmqtt.model.v

class KMqttBits {
    val reserved : Int = 1;
    val cleanSession : Int = 1;
    val will : Int = 1;
    val willQos : Int = 2;
    val willRetain : Int = 1;
    val userName : Int = 1;
    val password : Int = 1;
}

class KMqttPayload (
    val keepAlive : Boolean,
    val clientId : Int,
    val userName : String,
    val password : String,
    val willTopic : String,
    val willMessage : String
)

class KMqttConnect(val byte : Int) {
    inner class KMqttBits() {
        val reserved : Int = 1;
        val cleanSession : Int = 1;
        val will : Int = 1;
        val willQos : QoSLevel = QoSLevel.AT_MOST_ONCE;
        val willRetain : Int = 1;
        val userName : Int = 1;
        val password : Int = 1;
    }
    inner class KMqttConnect(val bytes : Int)
}
