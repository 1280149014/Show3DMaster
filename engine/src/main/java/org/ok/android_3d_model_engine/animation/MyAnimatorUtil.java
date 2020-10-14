package org.ok.android_3d_model_engine.animation;

import android.util.Log;

import org.ok.android_3d_model_engine.model.Object3DData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 *  我自己写的Animation 类, 通过这个类, 可以实现单个object 的旋转,缩放动画
 *
 *  为了显示旋转之后返回原位, 0 -> 360
 *
 *  ps : 比较遗憾的是没有看懂开源库作者的类
 */
public class MyAnimatorUtil {

    /**
     * List of 3D models
     */
    private List<Object3DData> objects = new ArrayList<>();

    private String TAG = MyAnimatorUtil.class.getSimpleName();

    /**
     *  存储当前obj的 x,y,z 轴的比例尺寸
     *  提供方法计算
     */
    class ObjectStatus{
        int degree = 0;
        float[] initScale = new float[3]; //初始时 x,y,z 轴的比例变化尺寸
        int rotateDegree = 360;   //旋转角度
        float[] newScale = new float[3];  //变换后 x,y,z 轴的比例变化尺寸

        public ObjectStatus(int degree, float[] initScale,int rotateDegree) {
            this.degree = degree;
            this.initScale = initScale;
            this.rotateDegree = rotateDegree;
        }

        /**
         *  计算放大缩小的倍数,
         * @param newScale    放大缩小的具体数组, 三个数依次为x,y,z的比例缩放值
         * @param degree   旋转的进度 从 0 到 360
         */
        public float[] calculateScale(float[] newScale, int degree){
            if(degree <= 180){
                float zoom = degree / 180.0f;
                newScale[0] = initScale[0] * (zoom + 0.5f);
                newScale[1] = initScale[1] *(zoom + 0.5f);
                newScale[2] = initScale[2] * (zoom + 0.5f);
            }else{
                float zoom = (degree - 180) / 180.0f;
                newScale[0] = initScale[0] * (1.5f - zoom);
                newScale[1] = initScale[1]  * (1.5f - zoom);
                newScale[2] = initScale[2]  * (1.5f - zoom);
            }
            return newScale;
        }

        /**
         *  提供给外界的接口去操作这个obj
         * @param obj
         */
        private void rotateAnimationObj(Object3DData obj) {
            // 正常的旋转角度, 应该是 从 0 到 360度
            // 参照黄工意见, 改为步长 处理, 考虑到大致的针数在 60,
            // 步长设成5,需要72次一圈 360 度,耗时1.2s左右
            degree = degree + 5;
            calculateScale(newScale,degree);
//            obj.translate(
//                        new float[]{
//                                - obj.getLocationX(),
//                                - obj.getLocationY(),
//                                - obj.getLocationZ()
//                        }
//                    );
//            obj.setRotation2(new float[]{-10,degree, 0},
//                    new float[]{0
//
//                            ,obj.getLocation()[1]/2,obj.getLocation()[2]/2});
            obj.setScale(1,newScale[1],1);
//            obj.setRotation(new float[]{0,degree, 0});
            Log.d(TAG,"1111 scale = " + obj.getScaleX() + " , y = " + obj.getScaleY());
//            obj.translate(
//                    new float[]{
//                             obj.getLocationX(),
//                             obj.getLocationY(),
//                             obj.getLocationZ()
//                    }
//            );

            if(degree >= rotateDegree){

                obj.setRotation1(new float[]{0,0, 0});
                obj.setScale(initScale);

                obj.setNeedRotate(false);
                obj.setNeedScale(false);
                degree = 0;
            }
        }

    }

    HashMap<Object3DData,ObjectStatus> map;

    public MyAnimatorUtil(List<Object3DData> objects){
        this.objects = objects;
        map = new HashMap<>();
        for(Object3DData o : objects){
            if(!map.containsKey(o)){
                ObjectStatus objectStatus = new ObjectStatus(0,o.getScale(),360);
                map.put(o,objectStatus);
            }
        }

    }

    public void startAnimation(Object3DData object3DData){
        if(map.containsKey(object3DData)){
            ObjectStatus status = map.get(object3DData);
            status.rotateAnimationObj(object3DData);
            map.put(object3DData,status);
        }
        if(object3DData != null && map.containsKey(object3DData.getFriend())){
            ObjectStatus status = map.get(object3DData.getFriend());
            status.rotateAnimationObj(object3DData.getFriend());
            map.put(object3DData.getFriend(),status);
        }
    }



}
