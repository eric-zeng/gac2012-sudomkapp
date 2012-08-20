package com.example.mapjournal;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
//import android.widget.VideoView;

/**
 * The activity handles new point creating and inserting it into the database
 * @author Leo Dihong Gao, Dina Lamdany
 *
 */
public class addPointActivity extends Activity {
    public static final String PREFS_NAME = "PrefsFile";
	double longitude, latitude;
	public static final String POINT_ID = "com.example.mapjournal.addPointActivity.POINT_ID"; 
	
	private static final int ACTION_TAKE_PHOTO_B = 1;
	//private static final int ACTION_TAKE_VIDEO = 3;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	private ImageView mImageView;
	private Bitmap mImageBitmap;

	/*private static final String VIDEO_STORAGE_KEY = "viewvideo";
	private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";
	private VideoView mVideoView;
	private Uri mVideoUri;*/

	private String mCurrentPhotoPath;
	private String photoPath;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
	
    @Override
    /**
     * Load the activity and get the longitude and latitude from the instance
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        Intent intent = getIntent();
        longitude = intent.getDoubleExtra(MapJournalMapActivity.LONGITUDE, Integer.MAX_VALUE);
        latitude = intent.getDoubleExtra(MapJournalMapActivity.LATITUDE, Integer.MAX_VALUE); 
        
        mImageView = (ImageView) findViewById(R.id.imageView);
		//mVideoView = (VideoView) findViewById(R.id.vidView);
		mImageBitmap = null;
		//mVideoUri = null;

		Button picBtn = (Button) findViewById(R.id.btnPic);
		setBtnListenerOrDisable( 
				picBtn, 
				mTakePicOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE
		);

		/*Button vidBtn = (Button) findViewById(R.id.btnVid);
		setBtnListenerOrDisable( 
				vidBtn, 
				mTakeVidOnClickListener,
				MediaStore.ACTION_VIDEO_CAPTURE
		);*/
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
    }

    @Override
    /**
     * load the action bar
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_point, menu);
        return true;
    }
    /**
     * add the point into the database
     * @param view
     * @return
     */
    public int onAddClick(View view){
    	String name = ((EditText)findViewById(R.id.name)).getText().toString();
    	String note = ((EditText)findViewById(R.id.notes)).getText().toString();
		
	    //Retrieve current trip from preferences 
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        String currentTrip = prefs.getString("current", null);
		DBOpenHelper db = new DBOpenHelper(this);
		long id = db.addPoint(new Point(name, currentTrip, (int)(latitude*1E6), (int)(longitude*1E6), /**Calendar.getInstance().getTimeInMillis()*/ 1234, note, photoPath));
		
		super.onBackPressed();
//		Intent intent = new Intent(this, MapJournalMapActivity.class);
//		intent.putExtra(POINT_ID, id);
//		intent.putExtra(MapJournalMapActivity.LATITUDE, latitude);
//		intent.putExtra(MapJournalMapActivity.LONGITUDE, longitude);				
//		startActivity(intent);
    	return 0;
    }
    
	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("Camera", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}
 
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();
		return f;
	}

	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) & (targetH > 0)) {			
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}
		else if ((targetW > 0))
			scaleFactor = photoW/targetW;
		else
			scaleFactor = photoH/targetH;

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		
		/* Associate the Bitmap to the ImageView */
		mImageView.setImageBitmap(bitmap);
//		mVideoUri = null;
		mImageView.setVisibility(View.VISIBLE);
//		mVideoView.setVisibility(View.INVISIBLE);
		
	}

	private void galleryAddPic() {
		    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
			File f = new File(mCurrentPhotoPath);
		    Uri contentUri = Uri.fromFile(f);
		    mediaScanIntent.setData(contentUri);
		    this.sendBroadcast(mediaScanIntent);
		    
			photoPath = mCurrentPhotoPath;
	}

	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		switch(actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;
			
			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
			break;

		default:
			break;			
		} // switch

		startActivityForResult(takePictureIntent, actionCode);
	}

/*	private void dispatchTakeVideoIntent() {
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
	}
*/
	private void handleBigCameraPhoto() {

		if (mCurrentPhotoPath != null) {
			setPic();
			galleryAddPic();
			mCurrentPhotoPath = null;
		}

	}

/*	private void handleCameraVideo(Intent intent) {
		mVideoUri = intent.getData();
		mVideoView.setVideoURI(mVideoUri);
		mImageBitmap = null;
		mVideoView.setVisibility(View.VISIBLE);
		mImageView.setVisibility(View.INVISIBLE);
	}
*/
	
	Button.OnClickListener mTakePicOnClickListener = 
		new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
		}
	};


/*	Button.OnClickListener mTakeVidOnClickListener = 
		new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			dispatchTakeVideoIntent();
		}
	};
*/
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTION_TAKE_PHOTO_B: {
			if (resultCode == RESULT_OK) {
				handleBigCameraPhoto();
			}
			break;
		} // ACTION_TAKE_PHOTO_B

	/*	case ACTION_TAKE_VIDEO: {
			if (resultCode == RESULT_OK) {
				handleCameraVideo(data);
			}
			break;
		} // ACTION_TAKE_VIDEO*/
		} // switch
	}

	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		//outState.putParcelable(VIDEO_STORAGE_KEY, mVideoUri);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
		//outState.putBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY, (mVideoUri != null) );
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
		/*mVideoView.setVideoURI(mVideoUri);
		mVideoView.setVisibility(
				savedInstanceState.getBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY) ? 
						ImageView.VISIBLE : ImageView.INVISIBLE
		);*/
	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 *
	 * @param context The application's environment.
	 * @param action The Intent action to check for availability.
	 *
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list =
			packageManager.queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private void setBtnListenerOrDisable( 
			Button btn, 
			Button.OnClickListener onClickListener,
			String intentName
	) {
		if (isIntentAvailable(this, intentName)) {
			btn.setOnClickListener(onClickListener);        	
		} else {
			btn.setText( 
				getText(R.string.cannot).toString() + " " + btn.getText());
			btn.setClickable(false);
		}
	}
}
