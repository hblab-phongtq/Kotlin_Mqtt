package common.network

interface NetworkInterface {
    fun send(data: UByteArray)

    fun sendRemaining()

    fun read(): UByteArray?

    fun close()

    fun onCreate()

    fun onDestroy()

    fun onBind()
}
