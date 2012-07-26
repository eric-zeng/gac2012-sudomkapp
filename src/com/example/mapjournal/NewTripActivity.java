package com.example.mapjournal;
//Created by Eric
//Changed by Leo
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
public class NewTripActivity extends Activity {
	
	private EditText textBox;
	private String currentTrip;
	
	public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);
        textBox = (EditText) findViewById(R.id.text_box);
    }
	
	public void onStop(){
		super.onStop();
		if(currentTrip != null){
			SharedPreferences prefs = getPreferences(0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("current", currentTrip);
			editor.commit();
		}
	}
	
	public void submitName(View view){
		if(textBox.getText().toString().length() > 0){
			Log.i("NewTrip", "String submitted: " + textBox.getText().toString());
			currentTrip = textBox.getText().toString();
			
//		Changed by Leo to hook the mapView
			Intent intent = new Intent (this, MapJournalMapActivity.class);
	    	startActivity(intent);
//	    	End Change
	    	
	    	
		}
		else{ 
			Log.i("NewTrip", "String is null");
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
