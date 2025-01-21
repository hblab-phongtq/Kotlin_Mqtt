package common.kmqtt.model

data class SubcriptionOptions(
    val qos : QoSLevel = QoSLevel.AT_MOST_ONCE,
    val noLocal : Boolean = false,
    val retainAsPublished : Boolean = false,
    val retainHandling : UInt = 0u
) {
    fun toByte() : UInt {
        if (retainHandling !in 0u .. 2u) {
            throw Exception()
        }
        val optionsByte = 0 or
                ((retainHandling.toInt() shl 4) and 0x30) or
                (((if (retainAsPublished) 1 else 0) shl 3) and 0x8) or
                (((if (noLocal) 1 else 0) shl 2) and 0x4) or
                (qos.value and 0x3)
        return optionsByte.toUInt()
    }
}
