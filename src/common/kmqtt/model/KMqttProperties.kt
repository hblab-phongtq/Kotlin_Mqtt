package common.kmqtt.model

class KMqttProperties(
    var payloadFormatIndicator: UInt? = null,
    var messageExpiryInterval: UInt? = null,
    var contentType: String? = null,
    var responseTopic: String? = null,
    var correlationData: UByteArray? = null,
    val subscriptionIdentifier: MutableList<UInt> = mutableListOf(),
    var sessionExpiryInterval: UInt? = null,
    var assignedClientIdentifier: String? = null,
    var serverKeepAlive: UInt? = null,
    var authenticationMethod: String? = null,
    var authenticationData: UByteArray? = null,
    var requestProblemInformation: UInt? = null,
    var willDelayInterval: UInt? = null,
    var requestResponseInformation: UInt? = null,
    var responseInformation: String? = null,
    var serverReference: String? = null,
    var reasonString: String? = null,
    var receiveMaximum: UInt? = null,
    var topicAliasMaximum: UInt? = null,
    var topicAlias: UInt? = null,
    var maximumQos: UInt? = null,
    var retainAvailable: UInt? = null,
    val userProperty: MutableList<Pair<String, String>> = mutableListOf(),
    var maximumPacketSize: UInt? = null,
    var wildcardSubscriptionAvailable: UInt? = null,
    var subscriptionIdentifierAvailable: UInt? = null,
    var sharedSubscriptionAvailable: UInt? = null
) {
    fun addUserProperty(property: Pair<String, String>) {
        userProperty += property
    }
}

enum class Property(val value: UInt) {
    PAYLOAD_FORMAT_INDICATOR(1u),
    MESSAGE_EXPIRY_INTERVAL(2u),
    CONTENT_TYPE(3u),
    RESPONSE_TOPIC(8u),
    CORRELATION_DATA(9u),
    SUBSCRIPTION_IDENTIFIER(11u),
    SESSION_EXPIRY_INTERVAL(17u),
    ASSIGNED_CLIENT_IDENTIFIER(18u),
    SERVER_KEEP_ALIVE(19u),
    AUTHENTICATION_METHOD(21u),
    AUTHENTICATION_DATA(22u),
    REQUEST_PROBLEM_INFORMATION(23u),
    WILL_DELAY_INTERVAL(24u),
    REQUEST_RESPONSE_INFORMATION(25u),
    RESPONSE_INFORMATION(26u),
    SERVER_REFERENCE(28u),
    REASON_STRING(31u),
    RECEIVE_MAXIMUM(33u),
    TOPIC_ALIAS_MAXIMUM(34u),
    TOPIC_ALIAS(35u),
    MAXIMUM_QOS(36u),
    RETAIN_AVAILABLE(37u),
    USER_PROPERTY(38u),
    MAXIMUM_PACKET_SIZE(39u),
    WILDCARD_SUBSCRIPTION_AVAILABLE(40u),
    SUBSCRIPTION_IDENTIFIER_AVAILABLE(41u),
    SHARED_SUBSCRIPTION_AVAILABLE(42u);

    companion object {
        public fun valueOf(value: UInt): Property? = values().firstOrNull { it.value == value }
    }
}
