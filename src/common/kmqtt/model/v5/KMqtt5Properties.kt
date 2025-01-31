package common.kmqtt.model.v5

enum class KMqtt5Properties(val value : Int) {
    MQTT_PROP_PAYLOAD_FORMAT_INDICATOR(1),		/* Byte :				PUBLISH, Will Properties */
    MQTT_PROP_MESSAGE_EXPIRY_INTERVAL(2),		/* 4 byte int :			PUBLISH, Will Properties */
    MQTT_PROP_CONTENT_TYPE(3),					/* UTF-8 string :		PUBLISH, Will Properties */
    MQTT_PROP_RESPONSE_TOPIC(8),				/* UTF-8 string :		PUBLISH, Will Properties */
    MQTT_PROP_CORRELATION_DATA(9),				/* Binary Data :		PUBLISH, Will Properties */
    MQTT_PROP_SUBSCRIPTION_IDENTIFIER(11),		/* Variable byte int :	PUBLISH, SUBSCRIBE */
    MQTT_PROP_SESSION_EXPIRY_INTERVAL(17),		/* 4 byte int :			CONNECT, CONNACK, DISCONNECT */
    MQTT_PROP_ASSIGNED_CLIENT_IDENTIFIER(18),	/* UTF-8 string :		CONNACK */
    MQTT_PROP_SERVER_KEEP_ALIVE(19),			/* 2 byte int :			CONNACK */
    MQTT_PROP_AUTHENTICATION_METHOD(21),		/* UTF-8 string :		CONNECT, CONNACK, AUTH */
    MQTT_PROP_AUTHENTICATION_DATA(22),			/* Binary Data :		CONNECT, CONNACK, AUTH */
    MQTT_PROP_REQUEST_PROBLEM_INFORMATION(23),	/* Byte :				CONNECT */
    MQTT_PROP_WILL_DELAY_INTERVAL(24),			/* 4 byte int :			Will properties */
    MQTT_PROP_REQUEST_RESPONSE_INFORMATION(25),/* Byte :				CONNECT */
    MQTT_PROP_RESPONSE_INFORMATION(26),		/* UTF-8 string :		CONNACK */
    MQTT_PROP_SERVER_REFERENCE(28),			/* UTF-8 string :		CONNACK, DISCONNECT */
    MQTT_PROP_REASON_STRING(31),				/* UTF-8 string :		All except Will properties */
    MQTT_PROP_RECEIVE_MAXIMUM(33),				/* 2 byte int :			CONNECT, CONNACK */
    MQTT_PROP_TOPIC_ALIAS_MAXIMUM(34),			/* 2 byte int :			CONNECT, CONNACK */
    MQTT_PROP_TOPIC_ALIAS(35),					/* 2 byte int :			PUBLISH */
    MQTT_PROP_MAXIMUM_QOS(36),					/* Byte :				CONNACK */
    MQTT_PROP_RETAIN_AVAILABLE(37),			/* Byte :				CONNACK */
    MQTT_PROP_USER_PROPERTY(38),				/* UTF-8 string pair :	All */
    MQTT_PROP_MAXIMUM_PACKET_SIZE(39),			/* 4 byte int :			CONNECT, CONNACK */
    MQTT_PROP_WILDCARD_SUB_AVAILABLE(40),		/* Byte :				CONNACK */
    MQTT_PROP_SUBSCRIPTION_ID_AVAILABLE(41),	/* Byte :				CONNACK */
    MQTT_PROP_SHARED_SUB_AVAILABLE(42);

    companion object {
        fun toValue(value : Int) = entries.first { it.value == value }

        fun fromCode(code : Int) = entries.first { it.value == code }
    }
}
