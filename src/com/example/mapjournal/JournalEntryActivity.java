// Eric Zeng
// Retrieves information about a selected map point and allows the
// user to edit it.

package com.example.mapjournal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

public class JournalEntryActivity extends Activity {
	
	private long pointID;
	private EditText editor;
	private String note;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journal_entry);
		
		// Retrieve point from map
		Intent intent = getIntent();
		pointID = intent.getLongExtra(ID, pointID);
		
		// Retrieve current text of point from database
		DBOpenHelper db = new DBOpenHelper(this);
		note = db.getNote(pointID);
		
		
		editor = (EditText) findViewById(R.id.text_editor);
		
		
	}
	
	
	
	
	
}
