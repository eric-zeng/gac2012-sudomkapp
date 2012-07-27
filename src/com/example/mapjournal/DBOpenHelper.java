package com.example.mapjournal;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class to interact with SQLite database to store points that make up trips
 * @author dinalamdany
 *
 */

public class DBOpenHelper extends SQLiteOpenHelper{

   private static final int VERSION = 6;  
   static final String DATABASE_NAME = "MAPS";
   private static final String TAG = "UPDATE_MAPS";
   private static final String TABLE_POINTS = "points";
   
   private static final String KEY_ID = "id";
   private static final String KEY_TITLE = "title";
   private static final String KEY_TRIPNAME = "tripname";
   private static final String KEY_LATITUDE = "latitude";
   private static final String KEY_LONGITUDE = "longitude";
   private static final String KEY_TIME = "time";
   private static final String KEY_NOTE = "note";

   
   /**
    * Create database to store points on map
    */
   private static final String CREATE_POINTS_TABLE =
		   " create table points" +
		   " (id integer primary key autoincrement," +
		   " title text not null, " +
		   " tripname text not null," +
		   " latitude integer not null," +
		   " longitude integer not null," +
		   " time integer not null," +
		   " note text);";

   /**
    * Instantiate class, which creates actual database if not created
    * Note: db not created until getWriteableDatabase or getReadableDatabase is called	
    * @param context Application in which to create db
    */
   public DBOpenHelper(Context context) {  
       super(context, DATABASE_NAME, null, VERSION);  
   }  
   
   /**
    * Create points table by executing SQL query
    * @param db the database in which to create the table
    */
   @Override  
   public void onCreate(SQLiteDatabase db) {  
       db.execSQL(CREATE_POINTS_TABLE);
   }
 
   /**
    * Method called when database must be updated to latest version
    */
   @Override  
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	   //Change this--only for testing purposes
	   Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
			   + newVersion + ", which will destroy all old data");
			   db.execSQL("DROP TABLE IF EXISTS points");
			   onCreate(db);
   }
   
   /**
    * Adds point to database
    * @param point to add
    * @return ID of point in points table
    */
   public long addPoint(Point point){
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues values = new ContentValues();
	   values.put(KEY_TITLE, point.getTitle());
	   values.put(KEY_TRIPNAME, point.getTripname());
	   values.put(KEY_LATITUDE, point.getLatitude());
	   values.put(KEY_LONGITUDE, point.getLongitude());
	   values.put(KEY_TIME, point.getTime());
	   values.put(KEY_NOTE, point.getNote());
	   
	   long id = db.insert(TABLE_POINTS, null, values);
	   db.close();
	   return id;
	   
   }
   
   /**
    * Deletes trip from db
    * @param tripName trip to delete
    */
   public void deleteTrip(String tripName){
	   SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_POINTS, KEY_TRIPNAME + " = ?",
	            new String[] { tripName });
	    db.close();
   }
   
   /**
    * Deletes trip from db
    * @param id id of trip
    */
   public void deletePoint(Long id) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_POINTS, KEY_ID + " = ?",
	            new String[] { String.valueOf(id) });
	    db.close();
	}
   
   /**
    * Updates note for a specific point in db
    * @param Id Id of point
    * @param note Text of note
    */
   public void updateNote(long Id, String note){
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues values = new ContentValues();
	   values.put(KEY_NOTE, note);
	   db.update(TABLE_POINTS, values, KEY_ID + " = ?",
	  				new String[] { String.valueOf(Id) });
	   db.close();
   }

   /**
    * Retrieves text of note of specified point
    * @param id Id of point
    * @return text of note
    */
   public String getNote(long id){
	   Point point = getPoint(id);
	   String text = point.getNote();
	   return text;
   }
   
   /**
    * Retrieves point from db 
    * @param id Id of point
    * @return point object
    */
   public Point getPoint(long id){
	   SQLiteDatabase db = this.getReadableDatabase();
	   Cursor cursor = db.query(TABLE_POINTS, new String[] {KEY_ID,
           KEY_TITLE, KEY_TRIPNAME, KEY_LATITUDE, KEY_LONGITUDE, KEY_TIME, KEY_NOTE}, KEY_ID + "=?",
           new String[] { String.valueOf(id) }, null, null, null, null);
	  
	   if (cursor != null)
		   cursor.moveToFirst();
	   Point point = new Point(Integer.parseInt(cursor.getString(0)),
           cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
           Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), cursor.getString(6));
	   db.close();
	   return point;
   }  
   
   /**
    * Retrieves names of all trips in db
    * @return arraylist of all trips
    */
   public ArrayList<String> getAllTrips(){
	   
	   ArrayList<String> trips = new ArrayList<String>();
	   String selectQuery = "SELECT DISTINCT " + KEY_TRIPNAME + " as " + KEY_ID + " FROM " + TABLE_POINTS + " ORDER BY " + KEY_TIME  + " ASC"; 
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);

       if (cursor.moveToFirst()) {
           do {
        	   trips.add(cursor.getString(0));               
           } while (cursor.moveToNext());
       }
       db.close();
       return trips;
   } 
   
   /**
    * Retrieves all points in a specified trip
    * @param tripname Trip to search for
    * @return Arraylist of points in trip
    */
   public ArrayList<Point> getTrip(String tripname){
	   ArrayList<Point> trip = new ArrayList<Point>();
	   String selectQuery = "SELECT * FROM " + TABLE_POINTS + " WHERE " + KEY_TRIPNAME + "=" + "'" + tripname + "'";

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);

       if (cursor.moveToFirst()) {
           do {
               Point point = new Point();
               point.setID(Integer.parseInt(cursor.getString(0)));
               point.setTitle(cursor.getString(1));
               point.setTripname(cursor.getString(2));
               point.setLatitude(Integer.parseInt(cursor.getString(3)));
               point.setLongitude(Integer.parseInt(cursor.getString(4)));
               point.setTime(Integer.parseInt(cursor.getString(5)));
               point.setNote(cursor.getString(6));
               
               trip.add(point);
           } while (cursor.moveToNext());
       }
       db.close();
       return trip;
   } 
   
   /**
    * Retrieves all points visited in history
    * @return Arraylist of all points visited
    */
   public ArrayList<Point> getAllPoints() {
       ArrayList<Point> allPoints = new ArrayList<Point>();
       String selectQuery = "SELECT  * FROM " + TABLE_POINTS;

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);

       if (cursor.moveToFirst()) {
           do {
               Point point = new Point();
               point.setID(Integer.parseInt(cursor.getString(0)));
               point.setTitle(cursor.getString(1));
               point.setTripname(cursor.getString(2));
               point.setLatitude(Integer.parseInt(cursor.getString(3)));
               point.setLongitude(Integer.parseInt(cursor.getString(4)));
               point.setTime(Integer.parseInt(cursor.getString(5)));
               point.setNote(cursor.getString(6));
               
               allPoints.add(point);
           } while (cursor.moveToNext());
       }
       db.close();
       return allPoints;
   } 
   
}
   


