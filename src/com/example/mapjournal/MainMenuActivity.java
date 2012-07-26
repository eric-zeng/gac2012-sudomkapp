package com.example.mapjournal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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
    
    public void previousTrips(View vew){
    	Intent intent = new Intent (MainMenuActivity.this, PreviousTripsActivity.class);
    	startActivity(intent);
    }
}
