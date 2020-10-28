package com.demo.show3dmaster.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.demo.show3dmaster.R;

import org.ok.android_3d_model_engine.animation.MyAnimatorUtil;
import org.ok.android_3d_model_engine.camera.CameraController;
import org.ok.android_3d_model_engine.collision.CollisionController;
import org.ok.android_3d_model_engine.controller.TouchController;
import org.ok.android_3d_model_engine.model.Object3DData;
import org.ok.android_3d_model_engine.receiver.VrBroadCastReceiver;
import org.ok.android_3d_model_engine.view.ModelRenderer;
import org.ok.android_3d_model_engine.view.ModelSurfaceView;

import org.ok.util.android.ContentUtils;
import org.ok.util.event.EventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * 2020-9-17   能够显示 obj文件, 并且不会出现
 * <p>
 * 2020-9-18   独立出几个obj文件, 通过layout加载, 显示
 */
public class MainActivity extends AppCompatActivity implements EventListener {

    private static final int REQUEST_CODE_LOAD_TEXTURE = 1000;

    private static final String TAG = MainActivity.class.getSimpleName();

    private ModelSurfaceView gLView;  // 这个就是真正的view

    private TouchController touchController;
    private CollisionController collisionController;
    MyAnimatorUtil myAnimator;
    private List<Object3DData> objects = new ArrayList<>();
    private Handler handler;
    private CameraController cameraController;



    private IntentFilter mIntentFilter = null;

    private VrBroadCastReceiver mMyBroadcastRecvier = null;
    Object3DData objSingle = new Object3DData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        startFuping3D();
        handler = new Handler(getMainLooper());
        gLView = (ModelSurfaceView) findViewById(R.id.backView);

        gLView.setOnClickListener(v -> Log.i(TAG, "1111111111111 "));

        try {
            Log.i("ModelActivity", "Loading GLSurfaceView...");
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading OpenGL view:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


        //过滤器
        mIntentFilter = new IntentFilter(VrBroadCastReceiver.LAUNCHER_ACTION);
        //创建广播接收者的对象
        mMyBroadcastRecvier = new VrBroadCastReceiver();
        //注册广播接收者的对象
        registerReceiver(mMyBroadcastRecvier, mIntentFilter);

    }


    private void startFuping3D() {
        try {
            Intent intentLauncher = new Intent();
            intentLauncher.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentLauncher.setComponent(new ComponentName(
                    "jp.clouds_inc.android.renesaschina20", "jp.clouds_inc.android.renesas.china20.MainActivity"));
            ActivityOptions launcherOptions = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                launcherOptions = ActivityOptions.makeBasic();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                launcherOptions.setLaunchDisplayId(1);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(intentLauncher, launcherOptions.toBundle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            Log.i("ModelActivity", "Loading TouchController...");
            touchController = new TouchController(this);
            touchController.addListener(new EventListener() {
                @Override
                public boolean onEvent(EventObject event) {
//                    Object3DData objectToSelect = ((CollisionEvent) event).getObject();
                    Log.d("SceneLoader...", event.toString());
                    return false;
                }
            });
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading TouchController:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            Log.i("ModelActivity", "Loading CollisionController...");
            collisionController = new CollisionController(gLView, gLView.getScene()) {
                @Override
                public boolean onEvent(EventObject event) {
                    return super.onEvent(event);
                }
            };
            //可以将点击事件移交到这处理
            collisionController.addListener(gLView.getScene());
            touchController.addListener(collisionController);
            touchController.addListener(gLView.getScene());
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading CollisionController\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            Log.i("ModelActivity", "Loading CameraController...");
            cameraController = new CameraController(gLView.getScene().getCamera());
            gLView.getModelRenderer().addListener(cameraController);
            touchController.addListener(cameraController);
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading CameraController" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        setupOnSystemVisibilityChangeListener();
        gLView.getScene().toggleLighting();
        try {
            gLView.setTouchController(touchController);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objects = gLView.getScene().getObjects();
        if (objects != null && objects.size() > 0) {
            myAnimator = new MyAnimatorUtil(objects);
        }

        mMyBroadcastRecvier.setVolumeChangeListener(new VrBroadCastReceiver.VoiceChangeListener() {
            @Override
            public void VoiceChanged(String id) {
                objects = gLView.getScene().getObjects();
                if (objects != null && objects.size() > 0) {
                    myAnimator = new MyAnimatorUtil(objects);
                    Log.i("ModelActivity", "Finished loading");
                    Log.i("ModelActivity", objects.toString());
                    Log.i(TAG, "收到广播执行动画id" + id);
                    for (int i = 0; i < objects.size(); i++) {
                        Object3DData obj = objects.get(i);
                        if (obj.getId().equals(id)) {
                            objSingle=obj;
                            objSingle.setNeedScale(true);
                            objSingle.setNeedRotate(true);
                            myAnimator.startAnimation(objSingle);
                            Log.i("ModelActivity", "执行动画完成");
                        }

                    }
                }
                Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_LONG).show();
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setupOnSystemVisibilityChangeListener() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                // Note that system bars will only be "visible" if none of the
                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    // The system bars are visible. Make any desired
                    MainActivity.this.hideSystemUIDelayed();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUIDelayed();
        }
    }

    public void hideSystemUIDelayed() {
        handler.removeCallbacksAndMessages(null);
//        handler.postDelayed(this::hideSystemUI, FULLSCREEN_DELAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_LOAD_TEXTURE:
                // The URI of the selected file
                final Uri uri = data.getData();
                if (uri != null) {
                    Log.i("ModelActivity", "Loading texture '" + uri + "'");
                    try {
                        ContentUtils.setThreadActivity(this);
                        gLView.getScene().loadTexture(null, uri);
                    } catch (IOException ex) {
                        Log.e("ModelActivity", "Error loading texture: " + ex.getMessage(), ex);
                        Toast.makeText(this, "Error loading texture '" + uri + "'. " + ex
                                .getMessage(), Toast.LENGTH_LONG).show();
                    } finally {
                        ContentUtils.setThreadActivity(null);
                    }
                }

        }
    }

    @Override
    public boolean onEvent(EventObject event) {
        if (event instanceof ModelRenderer.ViewEvent) {
            ModelRenderer.ViewEvent viewEvent = (ModelRenderer.ViewEvent) event;
            if (viewEvent.getCode() == ModelRenderer.ViewEvent.Code.SURFACE_CHANGED) {
                Log.d(TAG, " viewEvent.getWidth() = " + viewEvent.getWidth() +
                        ", viewEvent.getWidth()=" + viewEvent.getHeight());
                touchController.setSize(viewEvent.getWidth(), viewEvent.getHeight());
                gLView.setTouchController(touchController);
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastRecvier);
    }


}
