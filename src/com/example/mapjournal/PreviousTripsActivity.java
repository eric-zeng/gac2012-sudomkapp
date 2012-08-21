package com.example.mapjournal;


import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Displays trips saved in the database in a list, and allows the user
 * to load them into the map. The user can also delete previous trips. 
 * @author Eric
 *
 */

public class PreviousTripsActivity extends ListActivity 
{
	private DBOpenHelper db;
	private boolean delete;
	private ArrayList<String> tripNames;
    public static final String PREFS_NAME = "PrefsFile";
    private String keyword;
    
    /**
     * Loads trips from the database into the list. 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBOpenHelper(this);
        tripNames = db.getAllTrips();
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
        
        // Delete mode is set to "off" initially - delete mode turned on when delete button is pressed
        delete = false;
        
    }
    
    /**
     * Either loads the trip selected in the list, or deletes it.
     */
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		keyword = o.toString();
    	
		// Delete mode on
    	if(delete){
    		
    		// Dialog box for delete confirmation. Not working
    		/*
    		final AlertDialog ad = new AlertDialog.Builder(this).create();
    		ad.setTitle("Delete confirmation");
    		ad.setMessage("Do you really want to delete " + keyword +"?");
    		
    		ad.setButton(-2, "No",  new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {
			    	  ad.dismiss();
			    	  return;
			      }   
			} );
    		ad.setButton(-1, "Yes",  new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {
			    	  deleteHelper(keyword);		    	  
			    	  ad.dismiss();
			      }
			} );
    		
    		ad.show(); */
    		
    		tripNames.remove(keyword);
        	db.deleteTrip(keyword);
    		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));

    		
    	// Delete mode off
    	}else{
			SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("current", keyword);
			editor.commit();
			
			Intent intent = new Intent(this, MapJournalMapActivity.class);
			startActivity(intent);
			finish();
    	}
	}
    
    
    /**
     * Creates the delete button in the action bar. 
     */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_previous_trips, menu);
        return true;
    }
	
	/**
	 * Toggles the delete option in the list on and off depending on the current state. 
	 * If delete is off, the button reads "Delete Trips", if delete is off, it reads "Done
	 * Deleting"
	 * @param button
	 */
	public void toggleDelete(MenuItem button){
		if(delete){
			delete = false;
			button.setTitle("Delete Trips");
		}else{
			delete = true;
			button.setTitle("Done Deleting");
		}
	
	}
}
