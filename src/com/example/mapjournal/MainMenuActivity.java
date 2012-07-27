package com.example.mapjournal;
//Created by Eric
//Changed by Leo
import java.util.prefs.Preferences;


import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MainMenuActivity extends ListActivity {

	private String currentTrip;
    public static final String PREFS_NAME = "PrefsFile";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //setContentView(R.layout.activity_main_menu);
        String[] tripNames = new String[]{"New Trip", "Resume Trip", "Previous Trips"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
        ListView lv = getListView();
        
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
 
              // selected item
              String product = ((TextView) view).getText().toString();
              if(product == "New Trip"){
            	  Intent intent = new Intent(getApplicationContext(), NewTripActivity.class);
                  startActivity(intent);
              }
              else if(product == "Resume Trip"){
            	  Intent intent = new Intent(getApplicationContext(), MapJournalMapActivity.class);
              	  startActivity(intent);
              }
              else{
            	  Intent intent = new Intent(getApplicationContext(), PreviousTripsActivity.class);
                  startActivity(intent);
              }
              // sending data to new activity
              
          }
        });
    }
       
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
}
