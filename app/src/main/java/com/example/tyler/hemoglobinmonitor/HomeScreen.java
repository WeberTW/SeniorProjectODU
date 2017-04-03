package com.example.tyler.hemoglobinmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HomeScreen extends AppCompatActivity {

    //Need these
    TextView myLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        myLabel = (TextView)findViewById(R.id.label);

        //Make sure the file we want exists
        File file = new File(getApplicationContext().getFilesDir(), "Values.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Get the most recent hemoglobin value
        //Need to get from output file
        FileInputStream inStream = null;
        try {
            inStream = openFileInput("Values.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int c;
        String displayValue = "";
        try {
            c = inStream.read();
            while(c != -1 && c != 32){
                displayValue = displayValue + Character.toString((char) c);
                c = inStream.read();
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //See if we got a valid result
        if(displayValue != "") {
            //Now display it
            String displaying = displayValue + " g/DL";
            myLabel.setText(displaying);
        }
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
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
