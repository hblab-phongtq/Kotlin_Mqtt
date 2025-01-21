package common.kmqtt.model

class KMqttConnack(val byte : Int, val rc : Int) {
    val sessionPresent : Int = 1;
    val reserved : Int = 7;
}
