
package com.example.mapjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Displays the title and notes associated with the given point and allows the
 * user to edit the notes or delete the point.
 * @author Eric
 *
 */


public class JournalEntryActivity extends Activity {
	
	private long pointID;
	private EditText editor;
	private String note;
	private DBOpenHelper db;
	
	/**
	 * When created, this activity displays information about the chosen point and 
	 * allows it to be edited. 
	 */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journal_entry);
		
		// Retrieve point information from map through intents
		Intent intent = getIntent();
		pointID = intent.getLongExtra(MapJournalMapActivity.ID, pointID);
		
		// Retrieve current text of point from database
		db = new DBOpenHelper(this);
		note = db.getNote(pointID);
		String title = db.getPoint(pointID).getTitle();
		
		// Put data and title from point into the empty text views
		TextView noteTitle = (TextView) findViewById(R.id.note_title);
		noteTitle.setText(title);
		
		TextView noteData = (TextView) findViewById(R.id.notes);
		noteData.setText("Notes: " + note);
		
		// Put data into editor text box for editing
		editor = (EditText) findViewById(R.id.text_editor);
		editor.setText(note);
		
	}
	
	/**
	 * Submits the edited note text to the database and returns to
	 * the map.
	 * @param view
	 */
	public void submitEdit(View view){
		note = editor.getText().toString();
		db.updateNote(pointID, note);
		
		gotoMap(view);
	}
	/**
	 * Deletes the point being edited from the database and returns
	 * to the map.
	 * @param view
	 */
	public void deletePoint(View view){
		db.deletePoint(pointID);
		gotoMap(view);
	}
	
	/**
	 * Returns to the map.
	 * @param view
	 */
	public void gotoMap(View view){
		Intent intent = new Intent(this, MapJournalMapActivity.class);
		startActivity(intent);		
	}	
	
}
