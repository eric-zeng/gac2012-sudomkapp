package com.example.mapjournal;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper{

   private static final int VERSION = 5;  
   private static int lastID = 1;
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

   
   private static final String CREATE_POINTS_TABLE =
		   " create table points" +
		   " (id integer primary key autoincrement," +
		   " title text not null, " +
		   " tripname text not null," +
		   " latitude integer not null," +
		   " longitude integer not null," +
		   " time integer not null," +
		   " note text);";

	 
   public DBOpenHelper(Context context) {  
       super(context, DATABASE_NAME, null, VERSION);  
   }  
   
   @Override  
   public void onCreate(SQLiteDatabase db) {  
       db.execSQL(CREATE_POINTS_TABLE);
   }
 
   @Override  
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	   //Change this--only for testing purposes
	   Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
			   + newVersion + ", which will destroy all old data");
			   db.execSQL("DROP TABLE IF EXISTS points");
			   onCreate(db);
   }
   
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
   
   public void deletePoint(Point point) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_POINTS, KEY_ID + " = ?",
	            new String[] { String.valueOf(point.getId()) });
	    db.close();
	}
   
   public void updatePoint(Point point) {
	   SQLiteDatabase db = this.getWritableDatabase();
	   ContentValues values = new ContentValues();
	   values.put(KEY_TITLE, point.getTitle());
   		values.put(KEY_TRIPNAME, point.getTripname());
   		values.put(KEY_LATITUDE, point.getLatitude());
   		values.put(KEY_LONGITUDE, point.getLongitude());
   		values.put(KEY_TIME, point.getTime());
   		values.put(KEY_NOTE, point.getNote());

   		db.update(TABLE_POINTS, values, KEY_ID + " = ?",
   				new String[] { String.valueOf(point.getId()) });
   }
   
   public void updateNote(long id, String note){
	   Point point = getPoint(id);
	   point.setNote(note);
   }
   
   public String getNote(long id){
	   Point point = getPoint(id);
	   String text = point.getNote();
	   return text;
   }
   
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
	   return point;
   }  
   
   public ArrayList<String> getAllTrips(){
	   
	   ArrayList<String> trips = new ArrayList<String>();
	   String selectQuery = "SELECT DISTINCT " + KEY_TRIPNAME + " as " + KEY_ID + " FROM " + TABLE_POINTS + " ORDER BY " + KEY_TIME  + " DESC"; 
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);

       if (cursor.moveToFirst()) {
           do {
        	   trips.add(cursor.getString(0));               
           } while (cursor.moveToNext());
       }
       return trips;
   } 
   
   public ArrayList<Point> getTrip(String tripname){
	   ArrayList<Point> trip = new ArrayList<Point>();
	   String selectQuery = "SELECT * FROM " + TABLE_POINTS + " WHERE " + KEY_TRIPNAME + "=" + "'" + tripname + "'";

       SQLiteDatabase db = this.getWritableDatabase();
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
       return trip;
   } 
   
   public ArrayList<Point> getAllPoints() {
       ArrayList<Point> allPoints = new ArrayList<Point>();
       String selectQuery = "SELECT  * FROM " + TABLE_POINTS;

       SQLiteDatabase db = this.getWritableDatabase();
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
       return allPoints;
   } 
   
}
   


