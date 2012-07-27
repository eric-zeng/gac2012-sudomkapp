package com.example.mapjournal;


import java.util.ArrayList;

import android.content.ClipData.Item;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PreviousTripsActivity extends ListActivity 
{
	private DBOpenHelper db;
	private boolean delete;
	private ArrayList<String> tripNames;
    public static final String PREFS_NAME = "PrefsFile";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBOpenHelper(this);
        tripNames = db.getAllTrips();
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
  
        delete = false;
        
    }

    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
    	
    	if(delete){
    		tripNames.remove(keyword);
    		db.deleteTrip(keyword);
    		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
    	}else{
			SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("current", keyword);
			editor.commit();
			
			Intent intent = new Intent(this, MapJournalMapActivity.class);
			startActivity(intent);
			
    	}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_previous_trips, menu);
        return true;
    }
	
	public void toggleDelete(MenuItem button){
		if(delete){
			delete = false;
			button.setTitle("Delete Trips");
		}else{
			delete = true;
			button.setTitle("Done");
		}
	
	}
}
