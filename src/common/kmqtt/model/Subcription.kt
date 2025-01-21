package common.kmqtt.model

import common.kmqtt.utils.TopicUtils.getSharedTopicFilter
import common.kmqtt.utils.TopicUtils.getSharedTopicShareName
import common.kmqtt.utils.TopicUtils.isSharedTopicFilter

data class Subcription (
    val topicFilter : String,
    val options : SubcriptionOptions = SubcriptionOptions(),
    val subcriptionIdentifier : UInt? = null,
) {
    val matchTopicFilter : String = topicFilter.getSharedTopicFilter() ?: topicFilter
    val shareName : String? = topicFilter.getSharedTopicShareName()
    val timestampShareSend : Long = 0
    val isValid : Boolean = topicFilter.isSharedTopicFilter()
}
