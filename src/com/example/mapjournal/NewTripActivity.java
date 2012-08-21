package com.example.mapjournal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Activity for creating a new trip. Allows user to specify a name. Once the trip has been
 * named, it will then launch the map interface. 
 * @author Eric
 *
 */

public class NewTripActivity extends Activity {
	
	private EditText textBox;
	private String currentTrip;
    public static final String PREFS_NAME = "PrefsFile";
    
    /**
     * Called when creating the activity. 
     */
    public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        textBox = (EditText) findViewById(R.id.text_box);
    }
	
	/**
	 * Submits the name typed into the text box to the database. User is prevented
	 * from using the name if it is blank. 
	 * @param view
	 */
	public void submitName(View view){
		
		// User has submitted a valid string
		if(textBox.getText().toString().length() > 0){
			currentTrip = textBox.getText().toString();
					
			if(currentTrip != null){
				SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("current", currentTrip);
				editor.commit();
			}
			
			Intent intent = new Intent (this, MapJournalMapActivity.class);
	    	startActivity(intent);    	
	    	finish();
	    
	    // User has not submitted a valid string (launches dialog box)
		} else { 
			final AlertDialog ad = new AlertDialog.Builder(NewTripActivity.this).create();
			ad.setTitle("Invalid name");
			ad.setMessage("Your trip name must contain at least one character!");
			
			ad.setButton(-2, "Close",  new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {
			    	  ad.dismiss();
			      }   
			} );
			
			ad.show();
		}
	}
}
