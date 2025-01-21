package common.kmqtt.model

class KMqttHeader (val byte : Int) {
    val retain : Int = 1;
    val qos : Int = 2;
    val dup : Int = 1;
    val type : Int = 4;
}
