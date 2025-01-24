package core.kmqtt

import common.kmqtt.model.v.KMqttConnect
import common.kmqtt.model.v.QoSLevel

class WillSet(val packet : KMqttConnect) {
    val qos : QoSLevel

    init {
        this.qos = packet.KMqttBits().willQos
    }
}
