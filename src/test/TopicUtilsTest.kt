package test

import common.kmqtt.utils.TopicUtils.containsWildCard
import common.kmqtt.utils.TopicUtils.getSharedTopicFilter
import common.kmqtt.utils.TopicUtils.getSharedTopicShareName
import common.kmqtt.utils.TopicUtils.isSharedTopicFilter
import common.kmqtt.utils.TopicUtils.isValidTopic
import common.kmqtt.utils.TopicUtils.matchesWildCard
import org.testng.annotations.Test
import org.testng.asserts.Assertion

class TopicUtilsTest {
    val a = Assertion()
    @Test
    fun testContainWildCard() {
        a.assertTrue ( "sport/#".containsWildCard() )
        a.assertTrue ( "#".containsWildCard() )
        a.assertTrue ( "+".containsWildCard() )
        a.assertTrue ( "+/tennis/#".containsWildCard() )
        a.assertFalse ( "sport/".containsWildCard() )
        a.assertFalse ( "/tennis/test".containsWildCard() )
    }

    @Test
    fun testIsValidTopic() {
        a.assertTrue( "sport/+".isValidTopic() )
        a.assertFalse( "sport/#".isValidTopic() )
        a.assertFalse( "#".isValidTopic() )
        a.assertFalse( "".isValidTopic() )
        a.assertFalse( "+tennis/#".isValidTopic() )
        a.assertFalse( "+".isValidTopic() )
        a.assertTrue( "sport/".isValidTopic() )
        a.assertTrue( "/sport/test".isValidTopic() )
        a.assertTrue( "/sport/test/".isValidTopic() )
    }

    @Test
    fun testMatchesWildCard() {
        a.assertTrue( "sport/test".matchesWildCard("sport/#") )
    }

    @Test
    fun testIsSharedTopicFilter() {
        a.assertTrue( "sport/+".isSharedTopicFilter() )
    }

    @Test
    fun testGetSharedTopicFilter() {
        a.assertEquals( "sport/+","sport/+".getSharedTopicFilter() )
    }

    @Test
    fun testGetSharedTopicShareName() {
        a.assertEquals( "sport/+","sport/+".getSharedTopicShareName() )
    }
}
