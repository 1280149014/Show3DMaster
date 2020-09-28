package org.ok.android_3d_model_engine.objects;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.ok.android_3d_model_engine.model.Object3DData;
import org.ok.android_3d_model_engine.services.LoaderTask;
import org.ok.util.android.ContentUtils;
import org.ok.util.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppIconCube {

    // list of errors found
    final static List<Exception> errors = new ArrayList<>();

    /**
     * @param icon     icon 的名字
     * @param Location icon 的三维空间定位
     * @param color    icon的颜色
     * @param scale    icon的比例
     * @return objects 创建的实体对象
     */
    public static List<Object3DData> createAppIconCube(InputStream open,
                                                       float[] Location,
                                                       float[] color,
                                                       float[] scale,
                                                       LoaderTask task) {
        List<Object3DData> res = new ArrayList<>();
        try {
//            InputStream opens = ContentUtils.getInputStream(icon);
            Object3DData iconObj = Square.buildCubeV3face(IOUtils.read(open));
            open.close();
            iconObj.setColor(color);
            iconObj.setLocation(Location);
            iconObj.setScale(scale);
            res.add(iconObj);
            task.onLoad(iconObj);

            Object3DData iconCube = Cube.buildCubeV1();
            iconCube.setColor(color);
            iconCube.setLocation(Location);
            iconCube.setScale(scale);

            //设置朋友关系 , 实现动画的联动
            iconCube.setFriend(iconObj);
            iconCube.getFriend().setFriend(iconCube);
            task.onLoad(iconCube);

            //添加到返回结果
            res.add(iconCube);
            return res;
        } catch (Exception e) {
            errors.add(e);
            if (!errors.isEmpty()) {
                StringBuilder msg = new StringBuilder("There was a problem loading the data");
                for (Exception error : errors) {
                    Log.e("Example", error.getMessage(), error);
                    msg.append("\n").append(error.getMessage());
                }
            }
        }

        return null;
    }

    private static Drawable appIcon;

    /**
     * 通过包名获取应用icon
     * @param mContext
     * @param pakgename
     * @return
     */
    public static InputStream getIcon(Context mContext, String pakgename) {
        PackageManager pm = mContext.getPackageManager();
        String imgName="";
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(pakgename, PackageManager.GET_META_DATA);
            // 应用名称
            // pm.getApplicationLabel(appInfo)
            //应用图标
            appIcon = pm.getApplicationIcon(appInfo);

            BitmapDrawable bd = (BitmapDrawable) appIcon;
            Bitmap bm= bd.getBitmap();


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            InputStream isBm = new ByteArrayInputStream(baos.toByteArray());

            return isBm;


        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AppIconCube_error", e.toString());
            e.printStackTrace();
            InputStream opens=null;
            try {
            if(e.toString().contains("com.tencent.mm")){
                opens = ContentUtils.getInputStream("wechat.png");
            }else if(e.toString().contains("com.tencent.wecarflow")){
                opens = ContentUtils.getInputStream("facebook.png");
            }else if(e.toString().contains("com.android.car.carlauncher")){
                opens = ContentUtils.getInputStream("home.png");
            }else if(e.toString().contains("com.android.deskclock")){
                opens = ContentUtils.getInputStream("google.png");
            }else if(e.toString().contains("com.tencent.wecar")){
                opens = ContentUtils.getInputStream("music.png");
            } else if(e.toString().contains("com.android.calendar")){
                opens = ContentUtils.getInputStream("calendar_icon.png");
            }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return opens;
        }
    }

}
