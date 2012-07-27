// Eric Zeng
// Retrieves information about a selected map point and allows the
// user to edit it.

package com.example.mapjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class JournalEntryActivity extends Activity {
	
	private long pointID;
	private EditText editor;
	private String note;
	private DBOpenHelper db;
	
	// Gets text associate with a point and puts it in the text editor.
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journal_entry);
		
		// Retrieve point from map
		Intent intent = getIntent();
		pointID = intent.getLongExtra(MapJournalMapActivity.ID, pointID);
		
		// Retrieve current text of point from database
		db = new DBOpenHelper(this);
		note = db.getNote(pointID);
		
		// Put current text into text box
		editor = (EditText) findViewById(R.id.text_editor);
		editor.setText(note);
		
	}
	
	// Adds changes to text to the database. 
	public void submitEdit(View view){
		note = editor.getText().toString();
		db.updateNote(pointID, note);
		Log.i("Editor", "Note edit submitted to database: " + note);
		
		gotoMap();
	}
	
	public void deletePoint(){
		db.deletePoint(pointID);
		Log.i("Editor", "Point deleted");
		gotoMap();
	}
	
	// Returns to the map activity.
	public void gotoMap(){
		Log.i("Editor", "Going to map activity");
		Intent intent = new Intent(this, MapJournalMapActivity.class);
		startActivity(intent);		
	}	
	
}
