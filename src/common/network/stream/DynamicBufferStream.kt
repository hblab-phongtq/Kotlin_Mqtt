package common.network.stream

class DynamicBufferStream : InputStream, OutputStream {
    private var writePosition = 0
    private var readCounter = 0
    private var lastReadComplete = 0
    private var array : UByteArray = UByteArray(32)
    override fun read(): UByte {
        TODO("Not yet implemented")
    }

    override fun readBytes(length: Int): UByteArray {
        TODO("Not yet implemented")
    }

    override fun write(b: UByte) {
        TODO("Not yet implemented")
    }

    override fun write(b: UByteArray) {
        TODO("Not yet implemented")
    }
}
