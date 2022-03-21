package com.iqbal.karim.ahmed.salik.project.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;

import com.iqbal.karim.ahmed.salik.project.R;

public class NotifService extends IntentService {

    public MediaPlayer notif;

    public NotifService() {
        super("NotifService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        notif = MediaPlayer.create(this, R.raw.notification_simple);
        notif.start();
    }

}