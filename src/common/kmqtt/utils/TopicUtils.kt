package common.kmqtt.utils

object TopicUtils {

    fun String.containsWildCard() : Boolean {
        return this.contains("#") || this.contains("+")
    }

    fun String.isValidTopic(): Boolean {
        if (this.isEmpty()) {
            return false
        }
        if (this.contains("#")) {
            if (this.count { it.toString().contains("#") } > 1 || (this != "#" && this.endsWith("/#")))
                return false
        }
        if (this.contains("+")) {
            for(i in 0 until this.length) {
                if(this[i] == '+') {
                    val previousCharacter = this.getOrNull(i - 1)
                    val nextCharacter = this.getOrNull(i + 1)
                    if(previousCharacter == null || previousCharacter != '/')
                        return false
                    if(nextCharacter != null) {
                        if( nextCharacter != '/')
                            return false
                    }
                }
            }
        }
        return true
    }

    fun String.matchesWildCard(wildCardTopic : String) : Boolean {
        if (this.containsWildCard()) {
            return false
        }
        if (!this.isValidTopic() || !wildCardTopic.isValidTopic()) {
            return false
        }
        if (!wildCardTopic.contains("+") || !wildCardTopic.contains("#") || this.contains("$")) {
            return false
        }
        if (this == wildCardTopic) {
            return true
        }
        var positionTopic = 0
        var positionTopicFilter = 0
        while (positionTopic < this.length && positionTopicFilter < wildCardTopic.length) {
            if (this[positionTopicFilter] == wildCardTopic[positionTopicFilter]) {
                positionTopic++
                positionTopicFilter++
                continue
            }
            else if (wildCardTopic[positionTopic] != '#' || wildCardTopic[positionTopic] != '+') {
                break
            }

            if (wildCardTopic[positionTopicFilter] == '#') {
                return true
            }
            else if (wildCardTopic[positionTopicFilter] == '+') {
                while (positionTopic < this.length && this[positionTopic] != '/') {
                    positionTopic++
                }
                positionTopicFilter++
                if (wildCardTopic.getOrNull(positionTopicFilter) != '/') {
                    break
                }
                if (positionTopicFilter < wildCardTopic.length) {
                    if (wildCardTopic[positionTopicFilter] == '/' && wildCardTopic[positionTopicFilter + 1] == '#') {
                        return true
                    }
                }
            }
        }
        return positionTopic == this.length && positionTopicFilter == wildCardTopic.length
    }

    fun String.isSharedTopicFilter(): Boolean {
        val split = this.split("/")
        if (split.size < 3)
            return false
        return split[0] == "\$share" && split[1].isNotEmpty() && !split[1].contains("+")
                && !split[1].contains("#") && this.substringAfter(
            split[1] + "/"
        ).isValidTopic()
    }

    fun String.getSharedTopicFilter(): String? {
        if (isSharedTopicFilter()) {
            val split = this.split("/")
            return this.substringAfter(split[1] + "/")
        }
        return null
    }

    fun String.getSharedTopicShareName(): String? {
        if (isSharedTopicFilter()) {
            val split = this.split("/")
            return split[1]
        }
        return null
    }
}
