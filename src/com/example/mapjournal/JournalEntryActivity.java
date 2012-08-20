
package com.example.mapjournal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.VideoView;

/**
 * Displays the title and notes associated with the given point and allows the
 * user to edit the notes or delete the point.
 * @author Eric, Dina
 *
 */

public class JournalEntryActivity extends Activity {
	
	private long pointID;
	private EditText editor;
	private String note;
	private DBOpenHelper db;
	private ImageView mImageView;
	private Bitmap mImageBitmap;
	//private VideoView mVideoView;
	//private Uri mVideoUri;
	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";

	/*private static final String VIDEO_STORAGE_KEY = "viewvideo";
	private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";*/
	
	/**
	 * When created, this activity displays information about the chosen point and 
	 * allows it to be edited. 
	 */
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journal_entry);
		
	    mImageView = (ImageView) findViewById(R.id.imageView);
	    //mVideoView = (VideoView) findViewById(R.id.vidView);
		mImageBitmap = null;
		//mVideoUri = null;
		
		// Retrieve point information from map through intents
		Intent intent = getIntent();
		pointID = intent.getLongExtra(MapJournalMapActivity.ID, pointID);
		
		// Retrieve current text of point from database
		db = new DBOpenHelper(this);
		Point point = db.getPoint(pointID);
		note = point.getNote();
		String title = point.getTitle();
		String photoPath = point.getPhotoPath();
		Log.d("ppath", photoPath);
		if (null != photoPath)
			getPic(photoPath);
				
		// Put data and title from point into the empty text views
		TextView noteTitle = (TextView) findViewById(R.id.note_title);
		noteTitle.setText(title);
		
		TextView noteData = (TextView) findViewById(R.id.notes);
		noteData.setText("Notes: " + note);
		
		// Put data into editor text box for editing
		editor = (EditText) findViewById(R.id.text_editor);
		editor.setText(note);

	}
	
	private void getPic(String photoPath) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) & (targetH > 0)) {			
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}
		else if ((targetW > 0))
			scaleFactor = photoW/targetW;
		else if (targetH > 0)
			scaleFactor = photoH/targetH;

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
		
		/* Associate the Bitmap to the ImageView */
		mImageView.setImageBitmap(bitmap);
		//mVideoUri = null;
		mImageView.setVisibility(View.VISIBLE);
		//mVideoView.setVisibility(View.INVISIBLE);
	}

	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		//outState.putParcelable(VIDEO_STORAGE_KEY, mVideoUri);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
	//	outState.putBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY, (mVideoUri != null) );
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		//mVideoUri = savedInstanceState.getParcelable(VIDEO_STORAGE_KEY);
		mImageView.setImageBitmap(mImageBitmap);
		mImageView.setVisibility(
				savedInstanceState.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);
	/*	mVideoView.setVideoURI(mVideoUri);
		mVideoView.setVisibility(
				savedInstanceState.getBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);*/
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
		super.onBackPressed();	
	}	
	
}
