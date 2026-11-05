import java.io.InputStream;

final class StreamBuffer {
    static byte[] consumeInto(InputStream javaParamInput, byte[] javaParamBuffer, byte[] javaParamOther) throws Exception {
        javaParamInput.read(javaParamBuffer, 0, javaParamBuffer.length);
        byte javaParamAliased = javaParamOther[0];
        byte[] javaParamOut = javaParamBuffer;
        return javaParamOut;
    }

    static byte[] consume(InputStream javaBufferInput) throws Exception {
        byte[] buffer = new byte[4];
        byte[] alias = buffer;
        byte[] other = new byte[4];
        byte javaBufferBefore = buffer[0];
        javaBufferInput.read(buffer, 0, buffer.length);
        byte javaBufferAfter = alias[0];
        byte javaBufferOther = other[0];
        byte[] javaBufferOut = buffer;
        return javaBufferOut;
    }
}
