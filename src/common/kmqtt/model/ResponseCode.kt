package common.kmqtt.model

enum class PackingErrorCode( val value: Int) {
    // Packing/Unpacking error codes
    MQTT_OK(0),
    MQTT_ERROR(1),
    MQTT_HEADER_LEN(2),
    MQTT_ACK_LEN(4),
    MQTT_CLIENT_ID_LEN(64);

    companion object {
        fun toValue(value : Int) = PackingErrorCode.values().first { it.value == value }
    }
}

enum class ConnectPacketCode( val value : Int){
    // Return codes for connect packet
    MQTT_CONNECTION_ACCEPTED(0),
    MQTT_UNACCEPTABLE_PROTOCOL_VERSION(1),
    MQTT_IDENTIFIER_REJECTED(2),
    MQTT_SERVER_UNAVAILABLE(3),
    MQTT_BAD_USERNAME_OR_PASSWORD(4),
    MQTT_NOT_AUTHORIZED(5);

    companion object {
        fun toValue(value : Int) = ConnectPacketCode.values().first { it.value == value }
    }
}

enum class HeaderCode( val value : Int){
    CONNACK_B(32),
    PUBLISH_B(48),
    PUBACK_B(64),
    PUBREC_B(80),
    PUBREL_B(98),
    PUBCOMP_B(112),
    SUBPACK_B(144),
    UNSUBACK_B(176),
    PINGRESP_B(208);

    companion object {
        fun toValue(value : Int) = HeaderCode.values().first { it.value == value }
    }
}

enum class PacketType( val value : Int){
    RESERVED(0),
    CONNECT(1),
    CONNACK(2),
    PUBLISH(3),
    PUBACK (4),
    PUBREC (5),
    PUBREL (6),
    PUBCOMP(7),
    SUBSCRIBE(8),
    SUBACK (9),
    UNSUBSCRIBE(10),
    UNSUBACK(11),
    PINGREQ(12),
    PINGRESP(13),
    DISCONNECT(14),
    AUTH(15);

    companion object {
        fun toValue(value : Int) = PacketType.values().first { it.value == value }
    }
}

enum class QoSLevel(val value : Int) {
    AT_MOST_ONCE(0),
    AT_LEAST_ONCE(1),
    EXACTLY_ONCE(2);

    companion object {
        fun toValue(value : Int) = QoSLevel.values().first { it.value == value }
    }
}
