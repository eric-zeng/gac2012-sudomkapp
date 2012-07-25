package com.example.mapjournal;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        DBOpenHelper db = new DBOpenHelper(this);
        Log.d("Insert: ", "Inserting ..");
        db.addPoint(new Point("Ice Cream", "SanFran", 122, 67, 1230, "It was so fun!" ));
        db.addPoint(new Point("starbucks", "SanFran", 124, 45, 3133, "yum"));
        Log.d("Reading: ", "Reading all points");
        ArrayList<Point> points = db.getTrip("SanFran");
        for (Point p : points){
        	String log = "Id: " + p.getId() + "Title: " + p.getTitle() + " Lat: " + p.getLatitude();
        	Log.d("Name: ", log);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
    
    public void createNewTrip(View view){
    	Intent intent = new Intent (MainMenuActivity.this, NewTrip.class);
    	startActivity(intent);
    }
}
