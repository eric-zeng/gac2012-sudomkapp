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
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        //Retrieve current trip from preferences 
        SharedPreferences prefs = getPreferences(0);
        currentTrip = prefs.getString("current", null);

        //setContentView(R.layout.activity_main_menu);
        String[] tripNames = new String[]{"New Trip", "Resume Trip", "Previous Trip"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
        ListView lv = getListView();
        
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) 
          {
 
              // selected item
              String product = ((TextView) view).getText().toString();
              if(product == "New Trip")
              {
            	  Intent i = new Intent(getApplicationContext(), NewTripActivity.class);
            	  i.putExtra("product", product);
                  startActivity(i);
              }
              /*else if(product == "Resume Trip")
              {
            	  Intent i = new Intent(getApplicationContext(), ResumeTripActivity.class);
            	  i.putExtra("product", product);
              	  startActivity(i);
              }*/
              else
              {
            	  Intent i = new Intent(getApplicationContext(), PreviousTripsActivity.class);
            	  i.putExtra("product", product);
                  startActivity(i);
              }
              // sending data to new activity
              
    
    }
        });
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
    
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		if(keyword == "New Trip")
		{
			createNewTrip(v);
		}
		else if(keyword == "Resume Trip")
		{
			resumeTrip(v);
		}
		else
		{
			previousTrips(v);
		}
		//Toast.makeText(this, "You Have Selected: " + keyword, Toast.LENGTH_LONG).show();
	}

    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
}
