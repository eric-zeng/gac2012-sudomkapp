package com.example.mapjournal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class NewTrip extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        Log.d("NewTrip", "Made it");
		super.onCreate(savedInstanceState);
        setContentView(R.layout.new_trip);
        
    }
	
}
