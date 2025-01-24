package common.kmqtt.utils

import common.kmqtt.model.v.KMqttPacket
import common.kmqtt.model.v.KMqttProperties
import common.kmqtt.model.v.Property
import common.kmqtt.utils.TopicUtils.containsWildCard
import common.network.stream.ByteArrayInputStream
import common.network.stream.StreamDataExtension.decodeVariableByteInteger
import common.utils.CommonUtils.validateUTF8String

internal interface KMqttDeserializer {
    fun fromByteArray(flags: Int, data: UByteArray): KMqttPacket

    fun checkFlags(flags: Int) {
        if (flags.flagsBit(0) != 0 ||
            flags.flagsBit(1) != 0 ||
            flags.flagsBit(2) != 0 ||
            flags.flagsBit(3) != 0
        )
            throw Exception()
    }

    fun Int.flagsBit(bit: Int): Int {
        require(bit in 0..3)
        return (this shr bit) and 0b1
    }

    fun ByteArrayInputStream.read4BytesInt(): UInt {
        return (read().toUInt() shl 24) or (read().toUInt() shl 16) or (read().toUInt() shl 8) or read().toUInt()
    }

    fun ByteArrayInputStream.read2BytesInt(): UInt {
        return (read().toUInt() shl 8) or read().toUInt()
    }

    fun ByteArrayInputStream.readByte(): UInt {
        return read().toUInt()
    }

    fun ByteArrayInputStream.readUTF8String(): String {
        val length = read2BytesInt().toInt()
        val string = readBytes(length).toByteArray().decodeToString()
        if (!string.validateUTF8String())
            throw Exception()
        return string
    }

    fun ByteArrayInputStream.readBinaryData(): UByteArray {
        val length = read2BytesInt().toInt()
        return readBytes(length)
    }

    fun ByteArrayInputStream.readUTF8StringPair(): Pair<String, String> {
        return Pair(readUTF8String(), readUTF8String())
    }

    fun ByteArrayInputStream.deserializeProperties(validProperties: List<Property>): KMqttProperties {
        val propertyLength = decodeVariableByteInteger()
        val initialTotalRemainingLength = available()

        val properties = KMqttProperties()
        while (initialTotalRemainingLength - available() < propertyLength.toInt()) {
            val propertyIdByte = decodeVariableByteInteger()
            val propertyId = Property.valueOf(propertyIdByte)
            if (propertyId !in validProperties)
                throw IllegalArgumentException("Invalid property for this packet")
            when (propertyId) {
                Property.PAYLOAD_FORMAT_INDICATOR -> {
                    if (properties.payloadFormatIndicator != null)
                        throw Exception("Error")
                    properties.payloadFormatIndicator = readByte()
                }
                Property.MESSAGE_EXPIRY_INTERVAL -> {
                    if (properties.messageExpiryInterval != null)
                        throw Exception("Error")
                    properties.messageExpiryInterval = read4BytesInt()
                }
                Property.CONTENT_TYPE -> {
                    if (properties.contentType != null)
                        throw Exception("Error")
                    properties.contentType = readUTF8String()
                }
                Property.RESPONSE_TOPIC -> {
                    if (properties.responseTopic != null)
                        throw Exception("Error")
                    val responseTopic = readUTF8String()
                    if (responseTopic.containsWildCard())
                        throw Exception("Error")
                    properties.responseTopic = responseTopic
                }
                Property.CORRELATION_DATA -> {
                    if (properties.correlationData != null)
                        throw Exception("Error")
                    properties.correlationData = readBinaryData()
                }
                Property.SUBSCRIPTION_IDENTIFIER -> {
                    properties.subscriptionIdentifier.add(decodeVariableByteInteger())
                }
                Property.SESSION_EXPIRY_INTERVAL -> {
                    if (properties.sessionExpiryInterval != null)
                        throw Exception("Error")
                    properties.sessionExpiryInterval = read4BytesInt()
                }
                Property.ASSIGNED_CLIENT_IDENTIFIER -> {
                    if (properties.assignedClientIdentifier != null)
                        throw Exception("Error")
                    properties.assignedClientIdentifier = readUTF8String()
                }
                Property.SERVER_KEEP_ALIVE -> {
                    if (properties.serverKeepAlive != null)
                        throw Exception("Error")
                    properties.serverKeepAlive = read2BytesInt()
                }
                Property.AUTHENTICATION_METHOD -> {
                    if (properties.authenticationMethod != null)
                        throw Exception("Error")
                    properties.authenticationMethod = readUTF8String()
                }
                Property.AUTHENTICATION_DATA -> {
                    if (properties.authenticationData != null)
                        throw Exception("Error")
                    properties.authenticationData = readBinaryData()
                }
                Property.REQUEST_PROBLEM_INFORMATION -> {
                    if (properties.requestProblemInformation != null)
                        throw Exception("Error")
                    properties.requestProblemInformation = readByte()
                }
                Property.WILL_DELAY_INTERVAL -> {
                    if (properties.willDelayInterval != null)
                        throw Exception("Error")
                    properties.willDelayInterval = read4BytesInt()
                }
                Property.REQUEST_RESPONSE_INFORMATION -> {
                    if (properties.requestResponseInformation != null)
                        throw Exception("Error")
                    properties.requestResponseInformation = readByte()
                }
                Property.RESPONSE_INFORMATION -> {
                    if (properties.responseInformation != null)
                        throw Exception("Error")
                    properties.responseInformation = readUTF8String()
                }
                Property.SERVER_REFERENCE -> {
                    if (properties.serverReference != null)
                        throw Exception("Error")
                    properties.serverReference = readUTF8String()
                }
                Property.REASON_STRING -> {
                    if (properties.reasonString != null)
                        throw Exception("Error")
                    properties.reasonString = readUTF8String()
                }
                Property.RECEIVE_MAXIMUM -> {
                    if (properties.receiveMaximum != null)
                        throw Exception("Error")
                    properties.receiveMaximum = read2BytesInt()
                }
                Property.TOPIC_ALIAS_MAXIMUM -> {
                    if (properties.topicAliasMaximum != null)
                        throw Exception("Error")
                    properties.topicAliasMaximum = read2BytesInt()
                }
                Property.TOPIC_ALIAS -> {
                    if (properties.topicAlias != null)
                        throw Exception("Error")
                    properties.topicAlias = read2BytesInt()
                }
                Property.MAXIMUM_QOS -> {
                    if (properties.maximumQos != null)
                        throw Exception("Error")
                    properties.maximumQos = readByte()
                }
                Property.RETAIN_AVAILABLE -> {
                    if (properties.retainAvailable != null)
                        throw Exception("Error")
                    properties.retainAvailable = readByte()
                }
                Property.USER_PROPERTY -> {
                    properties.addUserProperty(readUTF8StringPair())
                }
                Property.MAXIMUM_PACKET_SIZE -> {
                    if (properties.maximumPacketSize != null)
                        throw Exception("Error")
                    properties.maximumPacketSize = read4BytesInt()
                }
                Property.WILDCARD_SUBSCRIPTION_AVAILABLE -> {
                    if (properties.wildcardSubscriptionAvailable != null)
                        throw Exception("Error")
                    properties.wildcardSubscriptionAvailable = readByte()
                }
                Property.SUBSCRIPTION_IDENTIFIER_AVAILABLE -> {
                    if (properties.subscriptionIdentifierAvailable != null)
                        throw Exception("Error")
                    properties.subscriptionIdentifierAvailable = readByte()
                }
                Property.SHARED_SUBSCRIPTION_AVAILABLE -> {
                    if (properties.sharedSubscriptionAvailable != null)
                        throw Exception("Error")
                    properties.sharedSubscriptionAvailable = readByte()
                }
                null -> throw Exception("Error")
            }
        }
        return properties
    }
}
