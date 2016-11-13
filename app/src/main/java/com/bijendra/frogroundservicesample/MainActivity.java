package com.bijendra.frogroundservicesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void gotoPlayer(View v) {
        Button button = (Button) v;
        Intent service = new Intent(MainActivity.this, AudioService.class);
        if (!AudioService.IS_RUNNING) {
            service.setAction(Constants.AUDIO_SERVICE_ACTION.STARTFOREGROUND);
            AudioService.IS_RUNNING = true;
            button.setText(getString(R.string.stop));
        } else {
            service.setAction(Constants.AUDIO_SERVICE_ACTION.STOPFOREGROUND);
            AudioService.IS_RUNNING = false;
            button.setText(getString(R.string.start));

        }
        startService(service);
    }
}
