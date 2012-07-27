package com.example.mapjournal;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * The main menu and start page for the app. Either switches to a new trip, resumes the
 * most recent trip, or allows you to view and resume older trips. 
 * @author Eric
 *
 */

public class MainMenuActivity extends ListActivity {

    public static final String PREFS_NAME = "PrefsFile";
    
    /**
     * Shows three options to continue: new trip, resume trip, or view previous trips.
     */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Creates an array containing the available options and uses the list adapter to display them. 
        String[] tripNames = new String[]{"New Trip", "Resume Trip", "Previous Trips"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
        ListView lv = getListView();
        
        // Listens for a click on the list
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 
        		// Pulls the text from the option chosen and uses it to decide which activity to switch to. 
        		String menuChoice = ((TextView) view).getText().toString();
        		if(menuChoice == "New Trip"){
        			Intent intent = new Intent(getApplicationContext(), NewTripActivity.class);
        			startActivity(intent);
        		} else if(menuChoice == "Resume Trip") {
        			Intent intent = new Intent(getApplicationContext(), MapJournalMapActivity.class);
              	  	startActivity(intent);
        		} else {
            	  Intent intent = new Intent(getApplicationContext(), PreviousTripsActivity.class);
                  startActivity(intent);
        		}  
        	}
        });
    }
}
