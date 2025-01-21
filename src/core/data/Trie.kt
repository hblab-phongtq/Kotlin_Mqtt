package core.data

import common.kmqtt.model.Subcription
import common.kmqtt.utils.TopicUtils.getSharedTopicFilter

class Trie(subcription: Map<String, Subcription>? = null) {
    private val root = TrieNode(Char.MIN_VALUE)

    init {
        subcription?.forEach {
            insert(it.key, it.value)
        }
    }

    fun insert(key: String, value: Subcription) {
        _insert(root, value.matchTopicFilter, 0, value, key)
    }

    private fun _insert(
        node: TrieNode,
        topic: String,
        index: Int,
        subcription: Subcription,
        key: String
    ): Boolean {
        var character = topic.getOrNull(index)
        var childNode = node.children[character]
        return if (childNode != null) {
            _insert(childNode, topic, index + 1, subcription, key)
        } else {
            if (character == null) {
                val replaced = node.subcription[key] != null
                node.subcription[key] = subcription
                replaced
            } else {
                val newNode = TrieNode(character)
                node.children[character] = newNode
                _insert(newNode, topic, index + 1, subcription, key)
            }
        }
    }

    fun match(topic: String): Set<Map.Entry<String, Subcription>> {
        if(topic.isNullOrEmpty())
            throw Exception("Topic not found")
        val realTopic = topic.getSharedTopicFilter() ?: topic
        return _match(root, realTopic, -1, realTopic.startsWith("$"))
    }

    private fun _match(
        node: TrieNode,
        topic: String,
        index: Int,
        dollarStart: Boolean
    ): Set<MutableMap.MutableEntry<String, Subcription>> {
        if (node.character == '#') {
            return if (dollarStart)
                emptySet()
            else
                node.subcription.entries
        } else if (topic.length == index) {
            return emptySet()
        } else if (!(node.character == '+' || node.character == topic.getOrNull(index) || node.character == Char.MIN_VALUE)) {
            return emptySet()
        } else {
            val subscriptions = mutableSetOf<MutableMap.MutableEntry<String, Subcription>>()
            var nextIndex = index + 1
            if (topic.length - 1 == index) {
                subscriptions += node.subcription.entries
                node.children['/']?.let {
                    it.children['#']?.let {
                        subscriptions += it.subcription.entries
                    }
                }
            } else if (node.character == '+') {
                if (dollarStart) {
                    return emptySet()
                } else {
                    val levels = topic.substring(index).split("/")
                    val moreLevels = levels.size > 1
                    if (!moreLevels) {
                        return node.subcription.entries
                    } else {
                        nextIndex = index + levels[0].length
                    }
                }
            }
            node.children.forEach {
                subscriptions += _match(it.value, topic, nextIndex, dollarStart)
            }
            return subscriptions
        }
    }

    fun deleteWithTopic(topic: String, clientId: String) : Boolean {
        if (topic.isNullOrEmpty()) {
            return false
        }
        val realTopic = topic.getSharedTopicFilter() ?: topic
        return _delete(root, realTopic, 0, clientId)
    }

    private fun _delete(
        node: TrieNode,
        topic: String,
        index: Int,
        clientId: String
    ): Boolean {

        return true
    }

    fun deleteWithCLientId(clientId: String) {
        if (clientId == null)
           throw Exception()
        __delete(root, clientId)
    }

    private fun __delete(root: TrieNode, clientId: String) {

    }

    class TrieNode(
        val character: Char,
        val children: HashMap<Char, TrieNode> = HashMap(),
        val subcription: HashMap<String, Subcription> = HashMap()
    )
}
