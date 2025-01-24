package test

import common.utils.CommonUtils.encodeBase64
import common.utils.CommonUtils.sha1
import common.utils.CommonUtils.validateUTF8String
import org.testng.annotations.Test
import org.testng.asserts.Assertion

class CommonUtilsTest {

    val a = Assertion()

    @Test
    fun testValidate() {
        // Test NULL
        a.assertFalse( ubyteArrayOf(0x00u, 0x00u).toByteArray().decodeToString().validateUTF8String() )

        // Test control characters
        a.assertFalse ( ubyteArrayOf(0x00u, 0x01u).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x00u, 0x0Cu).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x00u, 0x1Au).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x00u, 0x1Fu).toByteArray().decodeToString().validateUTF8String() )

        // Test control characters
        a.assertFalse ( ubyteArrayOf(0x00u, 0x7Fu).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x00u, 0x8Cu).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x00u, 0x9Fu).toByteArray().decodeToString().validateUTF8String() )

        // Noncharacters
        a.assertFalse ( ubyteArrayOf(0xFFu, 0xFEu).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0xFFu, 0xFFu).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x01u, 0xFFu, 0xFEu).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x01u, 0xFFu, 0xFFu).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x10u, 0xFFu, 0xFEu).toByteArray().decodeToString().validateUTF8String() )
        a.assertFalse ( ubyteArrayOf(0x10u, 0xFFu, 0xFFu).toByteArray().decodeToString().validateUTF8String() )

        a.assertTrue ( ubyteArrayOf(0xEFu, 0xBBu, 0xBFu).toByteArray().decodeToString().validateUTF8String() )
    }

    @Test
    fun testSHA1() {
        val str1 = "The quick brown fox jumps over the lazy dog".encodeToByteArray().sha1()
        a.assertEquals("L9ThxnotKPzthJ7hu3bnORuT6xI=", str1.encodeBase64())

        val str2 = "The quick brown fox jumps over the lazy cog".encodeToByteArray().sha1()
        a.assertEquals("3p8sf9JeGzr60+haC9F9mxANtLM=", str2.encodeBase64())

        val str3 = "".encodeToByteArray().sha1()
        a.assertEquals("2jmj7l5rSw0yVb/vlWAYkK/YBwk=", str3.encodeBase64())
    }
}
