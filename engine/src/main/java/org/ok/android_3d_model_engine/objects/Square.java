package org.ok.android_3d_model_engine.objects;

import android.opengl.GLES20;

import org.ok.android_3d_model_engine.model.Object3DData;
import org.ok.util.io.IOUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {

    final static int[] cubeIndices = new int[]{
            // @formatter:off
            // front
            0, 1, 2,
            0, 2, 3,
//            // back
//            7, 6, 5,
//            4, 7, 5,
//            // up
//            4, 0, 3,
//            7, 4, 3,
//            // bottom
//            1, 5, 6,
//            2, 1, 6,
//            // left
//            4, 5, 1,
//            0, 4, 1,
//            // right
//            3, 2, 6,
//            7, 3, 6
            // @formatter:on
    };

    final static float[] cubeVertices = new float[]{
            // @formatter:off
            -1f, 1f, 1f, // top left front
            -1f, -1f, 1f, // bottom left front
            1f, -1f, 1f, // bottom right front
            1f, 1f, 1f, // upper right front
//            -1f, 1f, -1f, // top left back
//            -1f, -1f, -1f, // bottom left back
//            1f, -1f, -1f, // bottom right back
//            1f, 1f, -1f // upper right back
            // @formatter:on
    };

    private final static float[] cubePositionData = {
            //@formatter:off
            // Front face
//            -1.0f, 1.0f, 1.0f,
//            -1.0f, -1.0f, 1.0f,
//            1.0f, 1.0f, 1.0f,
//            -1.0f, -1.0f, 1.0f,
//            1.0f, -1.0f, 1.0f,
//            1.0f, 1.0f, 1.0f,

//            // Right face
//            1.0f, 1.0f, 1.0f,
//            1.0f, -1.0f, 1.0f,
//            1.0f, 1.0f, -1.0f,
//            1.0f, -1.0f, 1.0f,
//            1.0f, -1.0f, -1.0f,
//            1.0f, 1.0f, -1.0f,
//
//            // Back face
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
//
//            // Left face
//            -1.0f, 1.0f, -1.0f,
//            -1.0f, -1.0f, -1.0f,
//            -1.0f, 1.0f, 1.0f,
//            -1.0f, -1.0f, -1.0f,
//            -1.0f, -1.0f, 1.0f,
//            -1.0f, 1.0f, 1.0f,
//
//            // Top face
//            -1.0f, 1.0f, -1.0f,
//            -1.0f, 1.0f, 1.0f,
//            1.0f, 1.0f, -1.0f,
//            -1.0f, 1.0f, 1.0f,
//            1.0f, 1.0f, 1.0f,
//            1.0f, 1.0f, -1.0f,
//
//            // Bottom face
//            1.0f, -1.0f, -1.0f,
//            1.0f, -1.0f, 1.0f,
//            -1.0f, -1.0f, -1.0f,
//            1.0f, -1.0f, 1.0f,
//            -1.0f, -1.0f, 1.0f,
//            -1.0f, -1.0f, -1.0f
    };

    //每个面设置不同的颜色
    private final static float[] cubeColorDataWithTransparency = {

            // Front face (red)
            1.0f, 0.0f, 0.0f, 1f,
            1.0f, 0.0f, 0.0f, 1f,
            1.0f, 0.0f, 0.0f, 1f,
            1.0f, 0.0f, 0.0f, 1f,
            1.0f, 0.0f, 0.0f, 1f,
            1.0f, 0.0f, 0.0f, 1f,

//            // Right face (green)
//            0.0f, 1.0f, 0.0f, 0.5f,
//            0.0f, 1.0f, 0.0f, 0.5f,
//            0.0f, 1.0f, 0.0f, 0.5f,
//            0.0f, 1.0f, 0.0f, 0.5f,
//            0.0f, 1.0f, 0.0f, 0.5f,
//            0.0f, 1.0f, 0.0f, 0.5f,
//
//            // Back face (blue)
//            0.0f, 0.0f, 1.0f, 0.5f,
//            0.0f, 0.0f, 1.0f, 0.5f,
//            0.0f, 0.0f, 1.0f, 0.5f,
//            0.0f, 0.0f, 1.0f, 0.5f,
//            0.0f, 0.0f, 1.0f, 0.5f,
//            0.0f, 0.0f, 1.0f, 0.5f,
//
//            // Left face (yellow)
//            1.0f, 1.0f, 0.0f, 0.5f,
//            1.0f, 1.0f, 0.0f, 0.5f,
//            1.0f, 1.0f, 0.0f, 0.5f,
//            1.0f, 1.0f, 0.0f, 0.5f,
//            1.0f, 1.0f, 0.0f, 0.5f,
//            1.0f, 1.0f, 0.0f, 0.5f,
//
//            // Top face (cyan)
//            0.0f, 1.0f, 1.0f, 0.5f,
//            0.0f, 1.0f, 1.0f, 0.5f,
//            0.0f, 1.0f, 1.0f, 0.5f,
//            0.0f, 1.0f, 1.0f, 0.5f,
//            0.0f, 1.0f, 1.0f, 0.5f,
//            0.0f, 1.0f, 1.0f, 0.5f,
//
//            // Bottom face (magenta)
//            1.0f, 0.0f, 1.0f, 0.5f,
//            1.0f, 0.0f, 1.0f, 0.5f,
//            1.0f, 0.0f, 1.0f, 0.5f,
//            1.0f, 0.0f, 1.0f, 0.5f,
//            1.0f, 0.0f, 1.0f, 0.5f,
//            1.0f, 0.0f, 1.0f, 0.5f
    };

    private final static float[] cubeTextureCoordinateData =
            {
                    // Front face
                    0.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 0.0f,
                    0.0f, 1.0f,
                    1.0f, 1.0f,
                    1.0f, 0.0f,

//                    // Right face
//                    0.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 1.0f,
//                    1.0f, 0.0f,
//
//                    // Back face
//                    0.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 1.0f,
//                    1.0f, 0.0f,
//
//                    // Left face
//                    0.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 1.0f,
//                    1.0f, 0.0f,
//
//                    // Top face
//                    0.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 1.0f,
//                    1.0f, 0.0f,
//
//                    // Bottom face
//                    0.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 0.0f,
//                    0.0f, 1.0f,
//                    1.0f, 1.0f,
//                    1.0f, 0.0f
            };

    public static Object3DData buildCubeV2Face() {
        IntBuffer drawBuffer = IOUtils.createIntBuffer(cubeIndices.length).put(cubeIndices);
        FloatBuffer vertexBuffer = IOUtils.createFloatBuffer(cubeVertices.length ).put(cubeVertices);
        return new Object3DData(vertexBuffer,drawBuffer).setDrawMode(GLES20.GL_TRIANGLES).setId("cubeV2face")
                .setDrawOrder(drawBuffer).setVertexBuffer(vertexBuffer);
    }


    public static Object3DData buildCubeV4face(byte[] textureData) {
        return new Object3DData(
                IOUtils.createFloatBuffer(cubePositionData.length).put(cubePositionData),
                IOUtils.createFloatBuffer(cubeColorDataWithTransparency.length).put(cubeColorDataWithTransparency)
                        .asReadOnlyBuffer(),
                IOUtils.createFloatBuffer(cubeTextureCoordinateData.length)
                        .put(cubeTextureCoordinateData).asReadOnlyBuffer(),
                textureData).setDrawMode(GLES20.GL_TRIANGLES).setId("cubeV4face");
    }

    public static Object3DData buildCubeV3face(byte[] textureData) {
        return new Object3DData(
                IOUtils.createFloatBuffer(cubePositionData.length).put(cubePositionData),
                IOUtils.createFloatBuffer(cubeTextureCoordinateData.length)
                        .put(cubeTextureCoordinateData).asReadOnlyBuffer(),
                textureData).setDrawMode(GLES20.GL_TRIANGLES).setId("cubeV3face");
    }

}
