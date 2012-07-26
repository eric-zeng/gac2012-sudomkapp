package com.example.mapjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addPointActivity extends Activity {
	double longitude, latitude;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        Intent intent = getIntent();
<<<<<<< HEAD
        longitude = intent.getDoubleExtra(MapJournelMapActivity.LONGITUDE, Integer.MAX_VALUE);
        latitude = intent.getDoubleExtra(MapJournelMapActivity.LATITUDE, Integer.MAX_VALUE);
=======
        longitude = intent.getDoubleExtra(MapJournalMapActivity.LONGITUDE, Integer.MAX_VALUE);
        latitude = intent.getDoubleExtra(MapJournalMapActivity.LATITUDE, Integer.MAX_VALUE);
>>>>>>> 968537c4d7da62c30b9f955a793f5b49c085c093
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_point, menu);
        return true;
    }
    
    public int onAddClick(View view)
    {
    	String name = ((EditText)findViewById(R.id.name)).getText().toString();
    	String note = ((EditText)findViewById(R.id.notes)).getText().toString();
		Context context = getApplicationContext();
		CharSequence text = "Call database write here. name: "+name+"   note: "+ note + "   Latitude: "+Double.toString(latitude)
							+ "   Longitude: "+Double.toString(longitude);
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		toast.show();
		
<<<<<<< HEAD
=======
		Intent intent = new Intent(this, MapJournalMapActivity.class);
		startActivity(intent);
>>>>>>> 968537c4d7da62c30b9f955a793f5b49c085c093
    	return 0;
    }
    
}
