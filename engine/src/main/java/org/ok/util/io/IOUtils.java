package org.ok.util.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

public final class IOUtils {

    public static byte[] read(File file) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(file);
        byte[] data = read(fis);
        fis.close();
        return data;
    }

    public static byte[] read(InputStream is) throws IOException {
        byte[] isData = new byte[512];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        while ((nRead = is.read(isData, 0, isData.length)) != -1) {
            buffer.write(isData, 0, nRead);
        }
        return buffer.toByteArray();
    }

    /**
     * 把顶点集合的数据结构由Float[]转换为FloatBuffer
     * @param floats
     * @return
     */
    public static FloatBuffer createFloatBuffer(int floats) {
        return createNativeByteBuffer(floats*4).asFloatBuffer();
    }

    public static FloatBuffer createFloatBuffer(List<float[]> vectorArray, int stride) {
        final FloatBuffer floatBuffer = createFloatBuffer(vectorArray.size() * stride);
        for (int i=0; i<vectorArray.size(); i++){
            floatBuffer.put(vectorArray.get(i));
        }
        return floatBuffer;
    }

    public static IntBuffer createIntBuffer(int integers) {
        return createNativeByteBuffer(integers*4).asIntBuffer();
    }

    public static ShortBuffer createShortBuffer(int shorts) {
        return createNativeByteBuffer(shorts*2).asShortBuffer();
    }

    /**
     * 将浮点数组float[]转换为浮点缓存FloatBuffer(浮点数组不能直接传给OpenGL处理，OpenGL底层用C语音实现)
     * @param length
     * @return
     */
    public static ByteBuffer createNativeByteBuffer(int length) {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(length);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());
        return bb;
    }



}
