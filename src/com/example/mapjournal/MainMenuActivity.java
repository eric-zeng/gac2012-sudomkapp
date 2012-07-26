package com.example.mapjournal;
//Created by Eric
//Changed by Leo


import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //setContentView(R.layout.activity_main_menu);
        String[] tripNames = new String[]{"New Trip", "Resume Trip", "Previous Trip"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "You Have Selected: " + keyword, Toast.LENGTH_LONG).show();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
    
    public void createNewTrip(View view){
    	Intent intent = new Intent (MainMenuActivity.this, NewTripActivity.class);
    	startActivity(intent);
    }
    
    // Temporary - leads to journal entry
    public void resumeTrip(View view){
    	Intent intent = new Intent (MainMenuActivity.this, JournalEntryActivity.class);
    	startActivity(intent);    	
    }
    
    public void previousTrips(View view){
    	Intent intent = new Intent (MainMenuActivity.this, PreviousTripsActivity.class);
    	startActivity(intent);
    }
}
