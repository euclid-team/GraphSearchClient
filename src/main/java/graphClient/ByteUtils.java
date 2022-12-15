package graphClient;

import java.nio.ByteBuffer;

//https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java

public class ByteUtils {
	// TODO: Simplify code when the project moves to java 8+
//    private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	static int sizeOfLong = 8;
    private static ByteBuffer buffer = ByteBuffer.allocate(sizeOfLong);

    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip 
        return buffer.getLong();
    }
}