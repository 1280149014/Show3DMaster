package org.ok.android_3d_model_engine.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.ok.android_3d_model_engine.model.Object3DData;
import org.ok.android_3d_model_engine.view.FullScreenDialog;

import java.net.URI;
import java.util.List;

/**
 * This component allows loading the model without blocking the UI.
 *
 * @author andresoviedo
 */
public abstract class LoaderTask extends AsyncTask<Void, String, List<Object3DData>> implements LoadListener {

	/**
	 * URL to the 3D model
	 */
	protected final URI uri;
	/**
	 * Callback to notify of events
	 */
	private final LoadListener callback;
	/**
	 * The dialog that will show the progress of the loading
	 */
	private final FullScreenDialog dialog;

	/**
	 * Build a new progress dialog for loading the data model asynchronously
     * @param uri        the URL pointing to the 3d model
     *
	 */
	public LoaderTask(Context parent, URI uri, LoadListener callback) {
		this.uri = uri;
		this.dialog = new FullScreenDialog(parent);
		this.callback = callback;
	}


	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.dialog.startAnimation();
		//this.dialog.getWindow().setGravity(Gravity.BOTTOM);
		this.dialog.show();
	}



	@Override
	protected List<Object3DData> doInBackground(Void... params) {
		try {
		    callback.onStart();
			List<Object3DData> data = build();
            callback.onLoadComplete();
			return  data;
		} catch (Exception ex) {
            callback.onLoadError(ex);
			return null;
		}
	}

	protected abstract List<Object3DData> build() throws Exception;

	public void onLoad(Object3DData data){
		callback.onLoad(data);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);

	}

	@Override
	protected void onPostExecute(List<Object3DData> data) {
		super.onPostExecute(data);
		if (dialog.isShowing()) {
			this.dialog.stopAnimation();
			dialog.dismiss();
		}
	}

	@Override
	public void onStart() {
		callback.onStart();
	}

	@Override
	public void onProgress(String progress) {
		super.publishProgress(progress);
		callback.onProgress(progress);
	}

	@Override
	public void onLoadError(Exception ex) {
		callback.onLoadError(ex);
	}

	@Override
	public void onLoadComplete() {
		callback.onLoadComplete();
	}
}