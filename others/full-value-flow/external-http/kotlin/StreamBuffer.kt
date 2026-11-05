import java.io.InputStream

fun kotlinReadBuffer(kotlinBufferInput: InputStream, kotlinBuffer: ByteArray, kotlinBufferOther: ByteArray): ByteArray {
    val kotlinBufferBefore = kotlinBuffer[0]
    kotlinBufferInput.read(kotlinBuffer, 0, kotlinBuffer.size)
    val kotlinBufferAliased = kotlinBufferOther[0]
    val kotlinBufferOut = kotlinBuffer
    return kotlinBufferOut
}

fun kotlinLocalBuffer(kotlinLocalInput: InputStream): ByteArray {
    val buffer = ByteArray(4)
    val alias = buffer
    val other = ByteArray(4)
    val kotlinLocalBefore = buffer[0]
    kotlinLocalInput.read(buffer, 0, buffer.size)
    val kotlinLocalAfter = alias[0]
    val kotlinLocalOther = other[0]
    val kotlinLocalOut = buffer
    return kotlinLocalOut
}
