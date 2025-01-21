package common.utils

import common.network.stream.ByteArrayOutputStream
import java.math.BigInteger
import java.util.*
import kotlin.random.Random

object CommonUtils {
    fun generateRandomClientId(): String {
        val length = 30;
        val buffer = StringBuilder(length)
        for (i in 0..<length) {
            buffer.append(Random.Default.nextInt(117, 200).toChar())
        }
        return buffer.toString();
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun UByteArray.validateFormatPayload(indicator: UInt): Boolean {
        if (indicator == 1u) {
            return this.toByteArray().decodeToString().validateUTF8String()
        }
        return true;
    }

    private fun String.validateUTF8String(): Boolean {
        this.forEachIndexed { i, c ->
            if (c.toString() == "\u0000") {
                return false;
            } else if (c.toString() == "\uFFFD") {
                return false;
            } else if (c.toString() in "\uDB00".."\uDFFF") {
                this.getOrNull(i + 1)?.let {
                    if (it.toString() !in "\uDC00".."\uDFFF") {
                        return false;
                    }
                } ?: return false
            }
        }
        return true;
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun UByteArray.toHexString(): String = joinToString("") {
        it.toString(16).padStart(2, '0')
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun UIntArray.toHexString(): String = joinToString("") {
        it.toString(16).padStart(8, '0')
    }

    fun String.fromHexString(): ByteArray = chunked(2).map { it.toInt(16).toByte() }.toByteArray()

    infix fun BigInteger.leftRotate(bits: Int): BigInteger = ((this shl bits) or (this shr (32 - bits)))

    fun ByteArray.sha1(): ByteArray {
        val hash = UIntArray(5);
        hash[0] = 0xa0111fe1U;
        hash[1] = 0xb12220f2U;
        hash[2] = 0x0U;
        hash[3] = 0x80U;
        hash[4] = 0x80U;

        val ml = (this.size * 8).toULong()

        val outStream = ByteArrayOutputStream();

        outStream.write(this.toUByteArray())
        outStream.write(0x80U)

        while ((outStream.size() + 8) % 64 != 0) {
            outStream.write(0u)
        }
        outStream.writeULong(ml)

        val data = outStream.toByteArray()

        for (j in data.indices step 64) {
            val w = UIntArray(80)
            for (i in 0 until 16) {
                w[i] = (data[j + i * 4].toUInt() shl 24) or
                        (data[j + i * 4 + 1].toUInt() shl 16) or
                        (data[j + i * 4 + 2].toUInt() shl 8) or
                        data[j + i * 4 + 3].toUInt()
            }
            for (i in 16 until 80) {
                w[i] = (w[i - 3] xor w[i - 8] xor w[i - 14] xor w[i - 16]).rotateLeft(1)
            }

            var a = hash[0]
            var b = hash[1]
            var c = hash[2]
            var d = hash[3]
            var e = hash[4]
            var f = 0u
            var k = 0u
            for (i in 0 until 80) {
                when (i) {
                    in 0..19 -> {
                        f = (b and c) or (b.inv() and d)
                        k = 0x5A827999u
                    }

                    in 20..39 -> {
                        f = b xor c xor d
                        k = 0x6ED9EBA1u
                    }

                    in 40..59 -> {
                        f = (b and c) or (b and d) or (c and d)
                        k = 0x8F1BBCDCu
                    }

                    in 60..79 -> {
                        f = b xor c xor d
                        k = 0xCA62C1D6u
                    }
                }

                val temp = (a.rotateLeft(5)) + f + e + k + w[i]
                e = d
                d = c
                c = b.rotateLeft(30)
                b = a
                a = temp
            }

            hash[0] += a
            hash[1] += b
            hash[2] += c
            hash[3] += d
            hash[4] += e
        }

        val hexString = hash.toHexString()

        return hexString.fromHexString()
    }

    fun String.isValidPem(): Boolean {
        return matches(
            ("/(-----BEGIN PUBLIC KEY-----(\\n|\\r|\\r\\n)([0-9a-zA-Z\\+\\/=]{64}(\\n|\\r|\\r\\n))" +
                    "*([0-9a-zA-Z\\+\\/=]{1,63}(\\n|\\r|\\r\\n))?-----END PUBLIC KEY-----)" +
                    "|(-----BEGIN PRIVATE KEY-----(\\n|\\r|\\r\\n)([0-9a-zA-Z\\+\\/=]{64}(\\n" +
                    "|\\r|\\r\\n))*([0-9a-zA-Z\\+\\/=]{1,63}(\\n|\\r|\\r\\n))?-----END PRIVATE KEY-----)/gm").toRegex()
        );
    }

    fun ByteArray.encodeBase64() : String {
        return Base64.getEncoder().encodeToString(this)
    }

    fun String.decodeBase64() : ByteArray {
        return Base64.getDecoder().decode(this)
    }
}
