package org.ok.android_3d_model_engine.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.ok.android_3d_model_engine.model.Object3DData;

public class VrBroadCastReceiver extends BroadcastReceiver {
    public static final String LAUNCHER_ACTION = "com.demo.show3dmaster.action";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (null == action) {
            return;
        }
        Log.d(" VrBroadCastReceiver: "," action is " + action);

        String id = intent.getStringExtra( "click_type");
        Log.d(" VrBroadCastReceiver: "," id is " + id);

        if (listener != null) {
            listener.VoiceChanged(id);
        }
    }

    public VoiceChangeListener listener;

    public interface VoiceChangeListener {
        void VoiceChanged(String id);
    }

    public void setVolumeChangeListener(VoiceChangeListener listener) {
        this.listener = listener;
    }
}
