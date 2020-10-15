package org.ok.android_3d_model_engine.services.wavefront;

import android.content.Context;
import android.net.Uri;
import android.opengl.GLES20;
import android.util.Log;

import org.ok.android_3d_model_engine.model.Object3DData;
import org.ok.android_3d_model_engine.services.LoadListener;
import org.ok.android_3d_model_engine.services.LoaderTask;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wavefront loader implementation
 *
 * @author andresoviedo
 */

public class WavefrontLoaderTask extends LoaderTask {

    public WavefrontLoaderTask(final Context parent, final URI uri, final LoadListener callback) {
        super(parent, uri, callback);
    }

    @Override
    protected List<Object3DData> build() {

        final WavefrontLoader wfl = new WavefrontLoader(GLES20.GL_TRIANGLE_FAN, this);

        super.publishProgress("Loading model...");

        List<Object3DData> load = new ArrayList<>();//
//        load =  wfl.load(uri);
        try {
            load.addAll(wfl.load(new URI(
                    Uri.parse("assets://asserts/models/" + "Butten_1.obj").toString())
            ));
            load.get(load.size()-1).setId("wechat");
            load.addAll(wfl.load(new URI(
                    Uri.parse("assets://asserts/models/" + "Butten_2.obj").toString())
            ));
            load.addAll(wfl.load(new URI(
                    Uri.parse("assets://asserts/models/" + "Butten_3.obj").toString())
            ));
            load.addAll(wfl.load(new URI(
                    Uri.parse("assets://asserts/models/" + "Butten_4.obj").toString())
            ));
            load.addAll(wfl.load(new URI(
                    Uri.parse("assets://asserts/models/" + "Butten_5.obj").toString())
            ));
            load.addAll(wfl.load(new URI(
                    Uri.parse("assets://asserts/models/" + "Butten_6.obj").toString())
            ));






//            load.addAll(wfl.load(new URI(
//                    Uri.parse("assets://asserts/models/" + "BackGround.obj").toString())
//            ));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("longquan","e = " + e.toString());
        }
        for(Object3DData o : load){

        }

        return load;
    }

    @Override
    public void onProgress(String progress) {
        super.publishProgress(progress);
    }
}
