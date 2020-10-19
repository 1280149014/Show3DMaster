package org.ok.android_3d_model_engine.view;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import org.ok.android_3d_model_engine.model.Object3DData;
import org.ok.android_3d_model_engine.objects.AppIconCube;
import org.ok.android_3d_model_engine.objects.Cube;
import org.ok.android_3d_model_engine.objects.Square;
import org.ok.android_3d_model_engine.services.LoadListener;
import org.ok.android_3d_model_engine.services.LoadListenerAdapter;
import org.ok.android_3d_model_engine.services.LoaderTask;
import org.ok.android_3d_model_engine.services.wavefront.WavefrontLoader;
import org.ok.android_3d_model_engine.util.Exploder;
import org.ok.android_3d_model_engine.util.Rescaler;
import org.ok.util.android.ContentUtils;
import org.ok.util.io.IOUtils;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class loads a 3D scene as an example of what can be done with the app
 *
 * @author andresoviedo
 *
 */
public class DemoLoaderTask extends LoaderTask {
    private Context mContext;
    private String  appIconList[]={"assets://assets/models/Butten_1.obj","assets://assets/models/TxMusic.obj",
            "assets://assets/models/TxCar.obj", "assets://assets/models/Calendar.obj",
           "assets://assets/models/Clock.obj","assets://assets/models/Setting.obj"};
    /**
     * Build a new progress dialog for loading the data model asynchronously
     *
     * @param parent parent activity
     * @param uri      the URL pointing to the 3d model
     * @param callback listener
     */
    public DemoLoaderTask(Context parent, URI uri, LoadListener callback) {
        super(parent, uri, callback);
        this.mContext=parent;
        ContentUtils.provideAssets(parent);
    }

    @Override
    protected List<Object3DData> build() throws Exception {

        // notify user
        super.publishProgress("Loading demo...");

        // list of errors found
        final List<Exception> errors = new ArrayList<>();

        try {

            List<Object3DData> res = new ArrayList<>();

            float iconRotateAngle = -10;
//            List<Object3DData> app0 = AppIconCube.createAppIconCube("setting_icon.png",
//                    new float[]{-4f, -5f, 0f},
//                    new float[]{0f, 1f, 0, 0.75f},
//                    new float[]{0.5f, 0.5f, 0.5f},
//                    DemoLoaderTask.this);
//            res.addAll(app0);
//            setId(app0,"settings");

            try {
                // this has heterogeneous faces
                Object3DData app0 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
                    @Override
                    public void onLoad(Object3DData obj53) {
                        obj53.setLocation(new float[] {-4f, -10f, 0f});
                        obj53.setColor(new float[] {0f, 1f, 0, 0.75f });
                        obj53.setScale(new float[]{0.5f, 0.5f, 0.5f});
                        Rescaler.rescale(obj53, 2f);
                        DemoLoaderTask.this.onLoad(obj53);
                    }
                }).load(new URI(appIconList[5])).get(0);
                setId(app0,"settings");
                res.add(app0);
                //super.onLoad(obj53);
            } catch (Exception ex) {
                errors.add(ex);
            }
            try {
                // this has heterogeneous faces
                Object3DData app1 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
                    @Override
                    public void onLoad(Object3DData obj53) {
                        obj53.setLocation(new float[] {-2f, -10f, 0f});
                        obj53.setColor(new float[] {0f, 1f, 0, 0.75f });
                        obj53.setScale(new float[]{0.5f, 0.5f, 0.5f});
                        Rescaler.rescale(obj53, 2f);
                        DemoLoaderTask.this.onLoad(obj53);
                    }
                }).load(new URI(appIconList[4])).get(0);
//                setId(app1,"calendar");
                setId(app1,"deskclock");
                res.add(app1);
                //super.onLoad(obj53);
            } catch (Exception ex) {
                errors.add(ex);
            }

            try {
                // this has heterogeneous faces
                Object3DData app2 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
                    @Override
                    public void onLoad(Object3DData obj53) {
                        obj53.setLocation(new float[] {0f, -10f, 0f});
                        obj53.setColor(new float[] {0f, 1f, 0, 0.75f });
                        obj53.setScale(new float[]{0.5f, 0.5f, 0.5f});
                        Rescaler.rescale(obj53, 2f);
                        DemoLoaderTask.this.onLoad(obj53);
                    }
                }).load(new URI(appIconList[3])).get(0);
                setId(app2,"calendar");
                res.add(app2);
                //super.onLoad(obj53);
            } catch (Exception ex) {
                errors.add(ex);
            }

            try {
                // this has heterogeneous faces
                Object3DData app3 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
                    @Override
                    public void onLoad(Object3DData obj53) {
                        obj53.setLocation(new float[] {2f, -10f, 0f});
                        obj53.setColor(new float[] {0f, 1f, 0, 0.75f });
                        obj53.setScale(new float[]{0.5f, 0.5f, 0.5f});
                        Rescaler.rescale(obj53, 2f);
                        DemoLoaderTask.this.onLoad(obj53);
                    }
                }).load(new URI(appIconList[2])).get(0);
//                setId(app3,"wecar");
                setId(app3,"wecar");
                res.add(app3);
                //super.onLoad(obj53);
            } catch (Exception ex) {
                errors.add(ex);
            }

            try {
                // this has heterogeneous faces
                Object3DData app4 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
                    @Override
                    public void onLoad(Object3DData obj53) {
                        obj53.setLocation(new float[] {4f,-10f, 0f});
                        obj53.setColor(new float[] {0f, 1f, 0, 0.75f });
                        obj53.setScale(new float[]{0.5f, 0.5f, 0.5f});
                        Rescaler.rescale(obj53, 2f);
                        DemoLoaderTask.this.onLoad(obj53);
                    }
                }).load(new URI(appIconList[1])).get(0);
//                setId(app4,"deskclock");
                setId(app4,"wecarflow");
                res.add(app4);
                //super.onLoad(obj53);
            } catch (Exception ex) {
                errors.add(ex);
            }

            try {
                // this has heterogeneous faces
                Object3DData app5 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
                    @Override
                    public void onLoad(Object3DData obj53) {
                        obj53.setLocation(new float[] {6f, -10f, 0f});
                        obj53.setColor(new float[] {0f, 1f, 0, 0.75f });
                        obj53.setScale(new float[]{0.5f, 0.5f, 0.5f});
                        Rescaler.rescale(obj53, 2f);
                        DemoLoaderTask.this.onLoad(obj53);
                    }
                }).load(new URI(appIconList[0])).get(0);
                setId(app5,"wechat");
                res.add(app5);
                //super.onLoad(obj53);
            } catch (Exception ex) {
                errors.add(ex);
            }

          /*  List<Object3DData> app1 = AppIconCube.createAppIconCube("wechat.png",
                    new float[]{-2f, -5f, 0f},
                    new float[]{0f, 1f, 0, 0.75f},
                    new float[]{0.5f, 0.5f, 0.5f},
                    DemoLoaderTask.this);
            res.addAll(app1);
            setId(app1,"wechat");

            List<Object3DData> app2 = AppIconCube.createAppIconCube("wecarflow.png",
                    new float[]{0f, -5f, 0f},
                    new float[]{0f, 1f, 0, 0.75f},
                    new float[]{0.5f, 0.5f, 0.5f},
                    DemoLoaderTask.this);
            res.addAll(app2);
            setId(app2,"wecarflow");

            List<Object3DData> app3 = AppIconCube.createAppIconCube("wecarui.png",
                    new float[]{2f, -5f, 0f},
                    new float[]{0f, 1f, 0, 0.75f},
                    new float[]{0.5f, 0.5f, 0.5f},
                    DemoLoaderTask.this);
            res.addAll(app3);
            setId(app3,"wecar");

            List<Object3DData> app4 = AppIconCube.createAppIconCube("clock_icon.png",
                    new float[]{4f, -5f, 0f},
                    new float[]{0f, 1f, 0, 0.55f},
                    new float[]{0.5f, 0.5f, 0.5f},
                    DemoLoaderTask.this);
            res.addAll(app4);
            setId(app4 , "deskclock");

            List<Object3DData> app6 = AppIconCube.createAppIconCube("calendar_icon.png",
                    new float[]{6f, -5f, 0f},
                    new float[]{0f, 1f, 0, 0.75f},
                    new float[]{0.5f, 0.5f, 0.5f},
                    DemoLoaderTask.this);
            res.addAll(app6);
            setId(app6 , "calendar");*/

//            List<Object3DData> app5 = AppIconCube.createAppIconCube(AppIconCube.getIcon(mContext,"com.android.car.carlauncher"),
//                    new float[]{ 8f, -1f, 0f},
//                    new float[]{0f, 1f, 0, 0.75f},
//                    new float[]{0.5f, 0.5f, 0.5f},
//                    DemoLoaderTask.this);
//            res.addAll(app5);
//            setId(app5 , "carlauncher");


            for(Object3DData obj : res){
                obj.setRotation2(new float[]{iconRotateAngle
                                ,0,0},
                        new float[]{
                                obj.getLocationX(),
                                obj.getLocationY(),
                                obj.getLocationZ()
                        });
            }


//            Object3DData obj21 = Square.buildCubeV2Face();
//            obj21.setColor(new float[] { 0f, 1f, 0, 0.25f });
//            obj21.setLocation(new float[] { 2f, -5f, 0f });
//            obj21.setScale(0.5f, 0.5f, 0.5f);
//            super.onLoad(obj21);


            //6个面都是
            // test cube with texture
//            try {
//                InputStream open10 = ContentUtils.getInputStream("phone.png");
//                Object3DData obj3 = Cube.buildCubeV2();
//                open10.close();
//                obj3.setColor(new float[] { 1f, 1f, 1f, 0f });
//                obj3.setLocation(new float[] { -2f, -5f, 0f });
//                obj3.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj3);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }

            // 总结, x 为 正, 往左, 为负, 往右
            //      y 为 正, 往上, 为负, 往下
            // 纹理是门的 cube , 切每个面的颜色不一样
            // test cube with texture & colors
//            try {
//                InputStream open =  ContentUtils.getInputStream("cube.bmp");
//                Object3DData obj4 = Cube.buildCubeV4(IOUtils.read(open));
//                open.close();
//                obj4.setColor(new float[] { 1f, 1f, 1f, 1f });
//                obj4.setLocation(new float[] { 0f, -2f, 0f });
//                obj4.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj4);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }

            //
            // test loading object
//            try {
//                // this has no color array
//                Object3DData obj51 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
//                    @Override
//                    public void onLoad(Object3DData obj53) {
//                        obj53.setLocation(new float[] { -2f, 0f, 0f });
//                        obj53.setColor(new float[] { 1.0f, 1.0f, 0f, 1.0f });
//                        Rescaler.rescale(obj53, 2f);
//                        DemoLoaderTask.this.onLoad(obj53);
//                    }
//                }).load(new URI("assets://assets/models/teapot.obj")).get(0);
//
//                //obj51.setScale(2f,2f,2f);
//                //obj51.setSize(0.5f);
//                //super.onLoad(obj51);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }

            // test loading object with materials
//            try {
//                // this has color array
//                Object3DData obj52 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
//                    @Override
//                    public void onLoad(Object3DData obj53) {
//                        obj53.setLocation(new float[] { 4f, -5f, 0f });
//                        obj53.setColor(new float[] { 0.0f, 1.0f, 1f, 1.0f });
//                        DemoLoaderTask.this.onLoad(obj53);
//                    }
//                }).load(new URI("assets://assets/models/cube.obj")).get(0);
//
//                obj52.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj52);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }

            // test loading object made of polygonal faces
//            try {
//                // this has heterogeneous faces
//                Object3DData obj53 = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, new LoadListenerAdapter(){
//                    @Override
//                    public void onLoad(Object3DData obj53) {
//                        obj53.setLocation(new float[] { 2f, 0f, 0f });
//                        obj53.setColor(new float[] { 1.0f, 1.0f, 1f, 1.0f });
//                        Rescaler.rescale(obj53, 2f);
//                        DemoLoaderTask.this.onLoad(obj53);
//                    }
//                }).load(new URI("assets://assets/models/box_unit_1.obj")).get(0);
//
//                //super.onLoad(obj53);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }

            // test loading object made of polygonal faces
//            try {
//                // this has heterogeneous faces
//                Object3DData obj53 = new ColladaLoader().load(new URI("assets://assets/models/cowboy.dae"), new LoadListenerAdapter(){
//                    @Override
//                    public void onLoad(Object3DData obj53) {
//                        obj53.setLocation(new float[] { 0f, -1f, 0f});
//                        obj53.setColor(new float[] { 1.0f, 1.0f, 1f, 1.0f });
//                        obj53.setRotation(new float[]{-90,0,0});
//                        Rescaler.rescale(obj53, 2f);
//                        DemoLoaderTask.this.onLoad(obj53);
//                    }
//                }).get(0);
//
//                //super.onLoad(obj53);
//            } catch (Exception ex) {
//                errors.add(ex);
//            }


            // test loading object without normals
                    /*try {
                        Object3DData obj = Object3DBuilder.loadV5(parent, Uri.parse("assets://assets/models/cube4.obj"));
                        obj.setPosition(new float[] { 0f, 2f, -2f });
                        obj.setColor(new float[] { 0.3f, 0.52f, 1f, 1.0f });
                        addObject(obj);
                    } catch (Exception ex) {
                        errors.add(ex);
                    }*/

            // more test to check right position
//            {
//                Object3DData obj111 = Cube.buildCubeV1();
//                obj111.setColor(new float[]{1f, 0f, 0f, 0.25f});
//                obj111.setLocation(new float[]{-1f, -2f, -1f});
//                obj111.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj111);
//
//                // more test to check right position
//                Object3DData obj112 = Cube.buildCubeV1();
//                obj112.setColor(new float[]{1f, 0f, 1f, 0.25f});
//                obj112.setLocation(new float[]{1f, -2f, -1f});
//                obj112.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj112);
//
//            }
//            {
//                // more test to check right position
//                Object3DData obj111 = Cube.buildCubeV1();
//                obj111.setColor(new float[] { 1f, 1f, 0f, 0.25f });
//                obj111.setLocation(new float[] { -1f, -2f, 1f });
//                obj111.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj111);
//
//                // more test to check right position
//                Object3DData obj112 = Cube.buildCubeV1();
//                obj112.setColor(new float[] { 0f, 1f, 1f, 0.25f });
//                obj112.setLocation(new float[] { 1f, -2f, 1f });
//                obj112.setScale(0.5f, 0.5f, 0.5f);
//                super.onLoad(obj112);
//
//            }

        } catch (Exception ex) {
            errors.add(ex);
            if (!errors.isEmpty()) {
                StringBuilder msg = new StringBuilder("There was a problem loading the data");
                for (Exception error : errors) {
                    Log.e("Example", error.getMessage(), error);
                    msg.append("\n").append(error.getMessage());
                }
                throw new Exception(msg.toString());
            }
        }
        return null;
    }

    private void setId(List<Object3DData> objects,String id  ){
        for(Object3DData o : objects){
            o.setId(id);
        }
    }
    private void setId(Object3DData obj,String id){
        obj.setId((id));
    }

    @Override
    public void onProgress(String progress) {
        super.publishProgress(progress);
    }



}
