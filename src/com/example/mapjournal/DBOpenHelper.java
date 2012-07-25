package com.example.mapjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper{

   private static final int VERSION = 1;  
   static final String DATABASE_NAME = "MAPS";
   private static final String TAG = "UPDATE_MAPS";
   private static final String TABLE_POINTS = "points";
   private static final String TABLE_TRIPS = "trips";
   
   private static final String KEY_ID = "id";
   private static final String KEY_TITLE = "title";
   private static final String KEY_TRIPNAME = "tripname";
   private static final String KEY_LATITUDE = "latitude";
   private static final String KEY_LONGITUDE = "longitude";
   private static final String KEY_TIME = "time";
   private static final String KEY_TEXT = "text";

   
   private static final String CREATE_TRIPS_TABLE =
		   " create table trips" +
		   " (_id integer primary key autoincrement," +
		   " tripname text not null);";

		   private static final String CREATE_POINTS_TABLE =
		   " create table points" +
		   " (_id integer primary key autoincrement," +
		   " tripname text not null," +
		   " latitude integer not null," +
		   " longitude integer not null," +
		   " time integer not null," +
		   " title text not null, " +
		   " note text);";

	 
   public DBOpenHelper(Context context) {  
       super(context, DATABASE_NAME, null, VERSION);  
   }  
   
   @Override  
   public void onCreate(SQLiteDatabase db) {  
       db.execSQL(CREATE_TRIPS_TABLE);
       db.execSQL(CREATE_POINTS_TABLE);
   }
 
   @Override  
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	   Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
			   + newVersion + ", which will destroy all old data");
			   db.execSQL("DROP TABLE IF EXISTS trips");
			   db.execSQL("DROP TABLE IF EXISTS points");
			   onCreate(db);
   }
   
   public void addTrip(String tripname){
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues values = new ContentValues();
	   values.put("tripname", tripname);
	   
	   db.insert(TABLE_TRIPS, null, values);
	   db.close();
   }
   
   public void addPoint(Point point){
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues values = new ContentValues();
	   values.put(KEY_TITLE, point.getTitle());
	   values.put(KEY_TRIPNAME, point.getTripname());
	   values.put(KEY_LATITUDE, point.getLatitude());
	   values.put(KEY_LONGITUDE, point.getLongitude());
	   values.put(KEY_TIME, point.getTime());
	   values.put(KEY_TEXT, point.getText());
	   
	   db.insert(TABLE_POINTS, null, values);
	   db.close();
   }
   
   public Point getPoint(int id){
	   SQLiteDatabase db = this.getReadableDatabase();
	   Cursor cursor = db.query(TABLE_POINTS, new String[] {KEY_ID,
           KEY_TITLE, KEY_TRIPNAME, KEY_LATITUDE, KEY_LONGITUDE, KEY_TIME, KEY_TEXT}, KEY_ID + "=?",
           new String[] { String.valueOf(id) }, null, null, null, null);
	  
	   if (cursor != null)
		   cursor.moveToFirst();

	   Point point = new Point(Integer.parseInt(cursor.getString(0)),
           cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
           Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), cursor.getString(6));
	   return point;
   }  
   
}
   


