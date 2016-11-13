package com.bijendra.frogroundservicesample;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Bijendra Singh
 * This is used to play audio using
 */

public class AudioService extends Service {

    //Local variables
    public static boolean IS_RUNNING = false;
    MediaPlayer mMediaPlayer;
    int[] audioFileId = {R.raw.aufile1, R.raw.aufile2};
    int index = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals(Constants.AUDIO_SERVICE_ACTION.STARTFOREGROUND))
            showNotification();
        else if (intent.getAction().equals(Constants.AUDIO_SERVICE_ACTION.PREV))
            prevAudioFile();
        else if (intent.getAction().equals(Constants.AUDIO_SERVICE_ACTION.PLAY))
            playAudioFile();
        else if (intent.getAction().equals(Constants.AUDIO_SERVICE_ACTION.NEXT))
            nextAudioFile();
        else if (intent.getAction().equals(Constants.AUDIO_SERVICE_ACTION.STOPFOREGROUND)) {
            releaseMediaPlayer();
            stopForeground(true);
            stopSelf();
        }

        return START_STICKY;
    }

    /**
     * This function is used to play audio file
     * on clicking play button
     */
    private void playAudioFile() {
        init();
    }
    /**
     * This function is used to play previous audio file
     * on clicking previous button
     */
    private void prevAudioFile() {
        --index;
        if (index < 0)
            index = audioFileId.length - 1;

        init();
    }

    /**
     * This function is used to play next audio file
     * on clicking next button
     */
    private void nextAudioFile() {
        ++index;
        if (index == audioFileId.length)
            index = 0;
        init();

    }

    /**
     * This function is used to init media player
     * play audio file
     */
    private void init() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), audioFileId[index]);
            mMediaPlayer.start();
        }
    }

    /**
     * This function is used to destroy media player
     */
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * This function is used to show notification,on service start
     */
    private void showNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.AUDIO_SERVICE_ACTION.MAIN);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent previousIntent = new Intent(this, AudioService.class);
        previousIntent.setAction(Constants.AUDIO_SERVICE_ACTION.PREV);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0, previousIntent, 0);

        Intent playIntent = new Intent(this, AudioService.class);
        playIntent.setAction(Constants.AUDIO_SERVICE_ACTION.PLAY);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Intent nextIntent = new Intent(this, AudioService.class);
        nextIntent.setAction(Constants.AUDIO_SERVICE_ACTION.NEXT);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0, nextIntent, 0);


        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.service_title))
                .setContentText(getString(R.string.coded_by))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous, getString(R.string.prev), ppreviousIntent)
                .addAction(android.R.drawable.ic_media_play, getString(R.string.play), pplayIntent)
                .addAction(android.R.drawable.ic_media_next, getString(R.string.next), pnextIntent)
                .build();
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
