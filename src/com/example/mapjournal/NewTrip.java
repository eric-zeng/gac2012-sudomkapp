package com.example.mapjournal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

public class NewTrip extends Activity {
	
	private EditText textBox;
	
	public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
        setContentView(R.layout.new_trip);
        textBox = (EditText) findViewById(R.id.text_box);
    }
	
	public void submitName(View view){
		if(textBox.getText().toString().length() > 0){
			Log.i("NewTrip", "String submitted: " + textBox.getText().toString());
			String tripName = textBox.getText().toString();
		}else{ 
			Log.i("NewTrip", "String is null");
			final AlertDialog ad = new AlertDialog.Builder(NewTrip.this).create();
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
