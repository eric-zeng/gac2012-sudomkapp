package com.example.mapjournal;
//Created by Leo
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addPointActivity extends Activity {
	double longitude, latitude;
	public static final String ADD_SUCCESS = "com.example.mapjournal.addPointActivity.ADD_SUCCESS";
	public static final String POINT_ID = "com.example.mapjournal.addPointActivity.POINT_ID";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        Intent intent = getIntent();
        longitude = intent.getDoubleExtra(MapJournalMapActivity.LONGITUDE, Integer.MAX_VALUE);
        latitude = intent.getDoubleExtra(MapJournalMapActivity.LATITUDE, Integer.MAX_VALUE);
        
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
		
	    //Retrieve current trip from preferences 
        SharedPreferences prefs = getPreferences(0);
        String currentTrip = prefs.getString("current", null);		
		DBOpenHelper db = new DBOpenHelper(this);
		long id = db.addPoint(new Point(name, currentTrip, (int)(latitude*1E6), (int)(longitude*1E6), 123456, note));
		
		
		Intent intent = new Intent(this, MapJournalMapActivity.class);
		intent.putExtra(ADD_SUCCESS, true);
		intent.putExtra(POINT_ID, id);
		intent.putExtra(MapJournalMapActivity.LATITUDE, latitude);
		intent.putExtra(MapJournalMapActivity.LONGITUDE, longitude);
				
		startActivity(intent);
    	return 0;
    }
    
}
