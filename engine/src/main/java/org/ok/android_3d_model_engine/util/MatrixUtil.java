package org.ok.android_3d_model_engine.util;

import android.opengl.Matrix;

import org.ok.android_3d_model_engine.model.Dimensions;

public class MatrixUtil {
    /**
     * 将提供的坐标系原点平移到指定Dimensions的中心
     *
     * @param result     最终输出的坐标系，必须是16位数组
     * @param res        需要平移的坐标系，必须是16位数组
     * @param dimensions 提供的模型尺寸，将会移到此模型的中心位置
     * @return result
     */
    public static float[] getObjectCenterMatrixBaseRes(float[] result, float[] res, Dimensions dimensions) {
        return getObjectCenterMatrixBaseRes(result, res, dimensions.getCenter());
    }

    /**
     * 将提供的坐标系原点平移到指定坐标center
     *
     * @param result 最终输出的坐标系，必须是16位数组
     * @param res    需要平移的坐标系，必须是16位数组
     * @param center 目标坐标系的中心位置，例如{1,1,1}
     * @return result
     */
    public static float[] getObjectCenterMatrixBaseRes(float[] result, float[] res, float[] center) {
        float[] center1 = new float[]{
                1,         0,         0,         0,
                0,         1,         0,         0,
                0,         0,         1,         0,
                center[0], center[1], center[2], 1};
        Matrix.multiplyMM(result, 0, res, 0, center1, 0);
        return result;
    }

    /**
     * 将提供的坐标系原点从Dimensions的中心移动到物体所在坐标系的原点
     *
     * @param result     最终输出的坐标系，必须是16位数组
     * @param res        需要平移的坐标系，必须是16位数组
     * @param dimensions 物体在目标坐标系中的位置尺寸
     * @return result
     */
    public static float[] resetMatrixFromObjectCenter(float[] result, float[] res, Dimensions dimensions) {
        return resetMatrixFromObjectCenter(result, res, dimensions.getCenter());
    }

    /**
     * 将提供的坐标系原点从坐标center移动到center坐标相对的原点
     *
     * @param result 最终输出的坐标系，必须是16位数组
     * @param res    需要平移的坐标系，必须是16位数组
     * @param center 当前坐标系原点相对目的坐标系的位置，例如{1,1,1}
     * @return result
     */
    public static float[] resetMatrixFromObjectCenter(float[] result, float[] res, float[] center) {
        float[] center1 = new float[]{
                1,          0,          0,          0,
                0,          1,          0,          0,
                0,          0,          1,          0,
                -center[0], -center[1], -center[2], 1};
        Matrix.multiplyMM(result, 0, res, 0, center1, 0);
        return result;
    }
}
