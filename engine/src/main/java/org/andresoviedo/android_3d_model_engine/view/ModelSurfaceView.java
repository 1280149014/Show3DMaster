package org.andresoviedo.android_3d_model_engine.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import org.andresoviedo.android_3d_model_engine.R;
import org.andresoviedo.android_3d_model_engine.controller.TouchController;
import org.andresoviedo.android_3d_model_engine.services.LoaderTask;
import org.andresoviedo.android_3d_model_engine.services.SceneLoader;
import org.andresoviedo.util.android.AndroidUtils;
import org.andresoviedo.util.event.EventListener;

import java.net.URI;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * This is the actual opengl view. From here we can detect touch gestures for example
 * 
 * @author andresoviedo
 *
 */

/**
 *
 * 修改自定义view ,
 * 使得 能够根据 attr加载不同的 obj 文件,
 * 响应不同的事件
 * 是否自定旋转
 * 是否响应点击事件
 *
 */

public class ModelSurfaceView extends GLSurfaceView implements EventListener {

	public static String TAG = ModelSurfaceView.class.getSimpleName();

	private ModelRenderer mRenderer;

	private TouchController touchController;

	private final List<EventListener> listeners = new ArrayList<>();

	/**
	 * 背景
	 * Background GL clear color. Default is light gray
	 */
	private float[] backgroundColor = new float[]{0f, 0f, 0f, 0f};


	public ModelSurfaceView(Context context) {
		this(context, null);
	}

	private SceneLoader scene;

	public SceneLoader getScene(){
		return  scene;
	}
	/**
	 * Type of model if file name has no extension (provided though content provider)
	 */
	private int paramType;    //文件类型
	/**
	 * The file to load. Passed as input parameter
	 */
	private URI paramUri;    //

	public ModelSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//this(context, null,null);
		getAttrs(context,attrs);   // 获取自定义属性
		init(context);     // 初始化scene
	}

	/** 初始化 */
	private void init(Context parent) {
		scene = new SceneLoader(parent, paramUri, paramType,this);
		scene.setAutoAnimation(isAutoAnimation);
		if (paramUri == null) {
			final LoaderTask task = new DemoLoaderTask(parent, null, scene);
			task.execute();
		}
		setTranslucent();

		try{
			Log.i("ModelSurfaceView","Loading [OpenGL 2] ModelSurfaceView...");

			// Create an OpenGL ES 2.0 context. 设置OpenGL的版本号2.0
			setEGLContextClientVersion(2);

			// This is the actual renderer of the 3D space
			mRenderer = new ModelRenderer(parent, this, backgroundColor, scene);
			mRenderer.addListener(this);
			setRenderer(mRenderer);
		}catch (Exception e){
			Log.e("ModelActivity",e.getMessage(),e);
			Toast.makeText(parent, "Error loading shaders:\n" +e.getMessage(), Toast.LENGTH_LONG).show();
			throw new RuntimeException(e);
		}
		scene.setView(this);

	}

	Boolean isAutoAnimation;
	Boolean isClickAble;

	public void getAttrs(Context context, AttributeSet attrs){
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ModelSurfaceView);
		isAutoAnimation = ta.getBoolean(R.styleable.ModelSurfaceView_autoAnimation,false);
		isClickAble = ta.getBoolean(R.styleable.ModelSurfaceView_clickAble,false);
		ta.recycle();  //注意回收
		Log.v(TAG,"ModelSurfaceView:" + isAutoAnimation);
	}


	/**
	 * <pre>
	 *  设置透明背景的方法，根据实际情况，可能setEGLConfigChooser中的alpha可能要设置成0
	 *  再者就是这个方法需要在setRenderer之前调用才有效
	 * </pre>
	 */
	public void setTranslucent() {
		// 设置背景透明，否则一般加载时间长的话会先黑一下，但是也有问题，就是在它之上无法再有View了，因为它是top的，用的时候需要注意，必要的时候将其设置为false
		setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		setZOrderOnTop(true);
	}

	public ModelSurfaceView(Context parent, float[] backgroundColorOld, SceneLoader sceneOld){
		super(parent);
		scene = new SceneLoader(parent, paramUri, paramType,this);
		if (paramUri == null) {
			final LoaderTask task = new DemoLoaderTask(parent, null, scene);
			task.execute();
		}

		try{
			Log.i("ModelSurfaceView","Loading [OpenGL 2] ModelSurfaceView...");

			// Create an OpenGL ES 2.0 context. 设置OpenGL的版本号2.0
			setEGLContextClientVersion(2);

			// This is the actual renderer of the 3D space
			mRenderer = new ModelRenderer(parent, this, backgroundColor, scene);
			mRenderer.addListener(this);
			setRenderer(mRenderer);
		}catch (Exception e){
			Log.e("ModelActivity",e.getMessage(),e);
			Toast.makeText(parent, "Error loading shaders:\n" +e.getMessage(), Toast.LENGTH_LONG).show();
			throw new RuntimeException(e);
		}
		scene.setView(this);
	}

	public void setTouchController(TouchController touchController){
		this.touchController = touchController;
	}



	public float[] getProjectionMatrix() {
		return mRenderer.getProjectionMatrix();
	}

	public float[] getViewMatrix() {
		return mRenderer.getViewMatrix();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			return touchController.onTouchEvent(event);
		} catch (Exception ex) {
			Log.e("ModelSurfaceView","Exception: "+ ex.getMessage(),ex);
		}
		return false;
	}

	public ModelRenderer getModelRenderer() {
		return mRenderer;
	}

	private void fireEvent(EventObject event) {
		AndroidUtils.fireEvent(listeners,event);
	}

	@Override
	public boolean onEvent(EventObject event) {
		fireEvent(event);
		return true;
	}

	public void toggleLights() {
		Log.i("ModelSurfaceView","Toggling lights...");
		mRenderer.toggleLights();
	}

    public void toggleWireframe() {
		Log.i("ModelSurfaceView","Toggling wireframe...");
        mRenderer.toggleWireframe();
    }

	public void toggleTextures() {
		Log.i("ModelSurfaceView","Toggling textures...");
		mRenderer.toggleTextures();
	}

	public void toggleColors() {
		Log.i("ModelSurfaceView","Toggling colors...");
		mRenderer.toggleColors();
	}

	public void toggleAnimation() {
		Log.i("ModelSurfaceView","Toggling textures...");
		mRenderer.toggleAnimation();
	}

	public boolean isLightsEnabled() {
		return mRenderer.isLightsEnabled();
	}
}