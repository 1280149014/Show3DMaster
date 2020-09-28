package com.demo.show3dmaster.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.demo.show3dmaster.R;

import org.ok.android_3d_model_engine.camera.CameraController;
import org.ok.android_3d_model_engine.collision.CollisionController;
import org.ok.android_3d_model_engine.controller.TouchController;
import org.ok.android_3d_model_engine.controller.TouchEvent;
import org.ok.android_3d_model_engine.view.ModelRenderer;
import org.ok.android_3d_model_engine.view.ModelSurfaceView;

import org.ok.util.android.ContentUtils;
import org.ok.util.event.EventListener;

import java.io.IOException;
import java.util.EventObject;
import java.util.logging.Level;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * 2020-9-17   能够显示 obj文件, 并且不会出现
 *
 * 2020-9-18   独立出几个obj文件, 通过layout加载, 显示
 *
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements EventListener {

    private static final int REQUEST_CODE_LOAD_TEXTURE = 1000;

    private static final String TAG = MainActivity.class.getSimpleName();

    private ModelSurfaceView gLView;  // 这个就是真正的view
    private ModelSurfaceView app1View;  //
    private TouchController touchController;
    private CollisionController collisionController;


    private Handler handler;
    private CameraController cameraController;

//    View app1Layout ;
    View app2Layout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        startFuping3D();
        handler = new Handler(getMainLooper());
        gLView = (ModelSurfaceView)findViewById(R.id.backView);
//        app1View = findViewById(R.id.app1);
//        app1Layout = findViewById(R.id.app1Layout);
//
//        app1Layout.setOnClickListener(v -> {
//            Toast.makeText(MainActivity.this,"app1 icon is clicked",Toast.LENGTH_SHORT).show();
//            app1View.rotateAnimate();
//        });

//        gLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        gLView.setOnClickListener(v -> Log.i(TAG, "1111111111111 "));

        try {
            Log.i("ModelActivity", "Loading GLSurfaceView...");
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading OpenGL view:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


        Log.i("ModelActivity", "Finished loading");

    }

    private void startFuping3D(){
        android.content.Intent intentLauncher = new android.content.Intent();
        intentLauncher.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentLauncher.setComponent(new android.content.ComponentName(
                "jp.clouds_inc.android.renesaschina20","jp.clouds_inc.android.renesas.china20.MainActivity"));
        android.app.ActivityOptions launcherOptions = android.app.ActivityOptions.makeBasic();
        launcherOptions.setLaunchDisplayId(1);
        startActivity(intentLauncher,launcherOptions.toBundle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Log.i("ModelActivity", "Loading TouchController...");
            touchController = new TouchController(this);
            touchController.addListener(this);
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading TouchController:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        try {
            Log.i("ModelActivity", "Loading CollisionController...");
            collisionController = new CollisionController(gLView, gLView.getScene()){
                @Override
                public boolean onEvent(EventObject event) {
                    if(event instanceof TouchEvent){
                        TouchEvent touchEvent = (TouchEvent)event;
                        if (touchEvent.getAction() == TouchEvent.CLICK) {
                            gLView.rotateAnimate();
                        }
                    }
                    return super.onEvent(event);
                }
            };
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

        try {
            gLView.setTouchController(touchController);
        }catch (Exception e) {
            e.printStackTrace();
        }


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
    public boolean onEvent (EventObject event){
        if (event instanceof ModelRenderer.ViewEvent) {
            ModelRenderer.ViewEvent viewEvent = (ModelRenderer.ViewEvent) event;
            if (viewEvent.getCode() == ModelRenderer.ViewEvent.Code.SURFACE_CHANGED) {
                Log.d(TAG," viewEvent.getWidth() = " + viewEvent.getWidth() +
                        ", viewEvent.getWidth()=" +  viewEvent.getHeight());
                touchController.setSize(viewEvent.getWidth(), viewEvent.getHeight());
                gLView.setTouchController(touchController);
            }
        }
        return true;
    }

}
