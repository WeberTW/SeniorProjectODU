package com.example.tyler.hemoglobinmonitor;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TrendActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setContentView(R.layout.activity_trend);
        createGraph();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Setup the action bar to have a back button to home screen (parent)
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void createGraph() {
        //Get the values
        //Need the input file
        FileInputStream inStream = null;
        try {
            inStream = openFileInput("Values.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Now we need to get the values
        int c; int count = 0;
        String displayValue = "";
        ArrayList<Float> myList = new ArrayList<Float>();
        try {
            while( (c = inStream.read()) != -1 && count <= 10){
                //If we have a space, do the next part
                if(c == 32) {
                    //Turn the string into a float
                    Float value = Float.parseFloat(displayValue);

                    //Save to a running list we will display
                    myList.add(value);

                    //Stop at 10 values
                    count++;

                    //Reset displayValue for next value
                    displayValue = "";
                }
                //If not, then we can continue making the value
                else {
                    displayValue = displayValue + Character.toString((char)c);
                }
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create the graph
        GraphView graph = (GraphView) findViewById(R.id.graph);

        //Add the values
        DataPoint[] dataValues = new DataPoint[myList.size()];
        int size = myList.size();
        int j = size-1;
        for(int i=0; i < myList.size(); i++) {
            DataPoint k = new DataPoint(i, myList.get(j));
            j--;
            dataValues[i] = k;
        }

        //Graph the values
        LineGraphSeries<DataPoint> series;
        series = new LineGraphSeries<>(dataValues);
        series.setColor(Color.BLUE);
        graph.addSeries(series);

        // Format the graph
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time (Newest Measurement on Right)");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Hemoglobin Value (g/dL)");
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(20);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Trend Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
