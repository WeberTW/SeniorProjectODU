package com.example.tyler.hemoglobinmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    /** Called when the user clicks the View Trend Information button */
    public void goToTrend(View view) {
        Intent intent = new Intent(this, TrendActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Settings button */
    public void goToSettings(View view) {
        Intent intent = new Intent(this, NewSettings.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Help button */
    public void goToHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}
