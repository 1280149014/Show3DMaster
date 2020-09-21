package com.demo.show3dmaster.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.demo.show3dmaster.R;
import com.demo.show3dmaster.widget.ModelViewerGUI;

import org.andresoviedo.android_3d_model_engine.camera.CameraController;
import org.andresoviedo.android_3d_model_engine.collision.CollisionController;
import org.andresoviedo.android_3d_model_engine.controller.TouchController;
import org.andresoviedo.android_3d_model_engine.services.LoaderTask;
import org.andresoviedo.android_3d_model_engine.services.SceneLoader;
import org.andresoviedo.android_3d_model_engine.view.ModelRenderer;
import org.andresoviedo.android_3d_model_engine.view.ModelSurfaceView;
import org.andresoviedo.util.android.ContentUtils;
import org.andresoviedo.util.event.EventListener;

import java.io.IOException;
import java.net.URI;
import java.util.EventObject;

/**
 * 2020-9-17   能够显示 obj文件, 并且不会出现
 *
 * 2020-9-18   独立出几个obj文件, 通过layout加载, 显示
 *
 */
public class MainActivity extends AppCompatActivity implements EventListener {

    private static final int REQUEST_CODE_LOAD_TEXTURE = 1000;
    private static final int FULLSCREEN_DELAY = 10000;
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Type of model if file name has no extension (provided though content provider)
     */
    private int paramType;    //文件类型
    /**
     * The file to load. Passed as input parameter
     */
    private URI paramUri;    //

    /**
     * 沉浸模式
     * Enter into Android Immersive mode so the renderer is full screen or not
     */
    private boolean immersiveMode;

    /**
     * 背景
     * Background GL clear color. Default is light gray
     */
    private float[] backgroundColor = new float[]{0f, 0f, 0f, 0f};

    private ModelSurfaceView gLView;  // 这个就是真正的view
    private ModelSurfaceView app1View;  //
    private View mLayout;

    private TouchController touchController;
    private ModelViewerGUI gui;
    private CollisionController collisionController;
    private Handler handler;
    private CameraController cameraController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        handler = new Handler(getMainLooper());
        gLView = (ModelSurfaceView)findViewById(R.id.backView);
        app1View = findViewById(R.id.app1);
        app1View.setOnClickListener(v -> {
            Log.d(TAG,"clicked");
            app1View.animateFast();
        });
        mLayout = findViewById(R.id.appLayout);
        mLayout.setOnClickListener(v -> {
            Log.d(TAG,"mLayout clicked");
            app1View.animateFast();
        });
        // Create our 3D scenario
        Log.i("ModelActivity", "Loading Scene...");

        try {
            Log.i("ModelActivity", "Loading GLSurfaceView...");
//            gLView = new ModelSurfaceView(this, backgroundColor, this.scene);
//            gLView.addListener(this);
//            setContentView(gLView);
//            scene.setView(gLView);
        } catch (Exception e) {
            Log.e("ModelActivity", e.getMessage(), e);
            Toast.makeText(this, "Error loading OpenGL view:\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


        Log.i("ModelActivity", "Finished loading");

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
            collisionController = new CollisionController(gLView, gLView.getScene());
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

        gLView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        // load model
//        gLView.getScene().init();
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
        if (!this.immersiveMode) {
            return;
        }
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
