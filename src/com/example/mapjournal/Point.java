package com.example.mapjournal;

/**
 * Class to represent a geographic point visited on a trip
 * @author dinalamdany
 *
 */
public class Point {

	private long _id;
	private String _tripname;
	private int _latitude;
	private int _longitude;
	private long _time;
	private String _title;
	private String _note;
	private String _imgLoc;
	
	/**
	 * Empty constructor
	 */
	public Point(){
		
	}

	/**
	 * Constructor without id
	 * @param title
	 * @param tripname
	 * @param lat latitude
	 * @param lng longitude
	 * @param time Time point was visited
	 * @param text Text of note
	 * @param imgLoc Location of image in memory
	 */
	public Point (String title, String tripname, int lat, int lng, long time, String text, String imgLoc){
		this._tripname = tripname;
		this._longitude = lng;
		this._latitude = lat;
		this._time = time;
		this._title = title;
		this._note = text;
		this._imgLoc = imgLoc;
	}
	
	/**
	 * Constructor with id
	 * @param id Id in db
	 * @param title
	 * @param tripname
	 * @param lat latitude
	 * @param lng longitude
	 * @param time Time point was visited
	 * @param text Text of note
	 * @param imgLoc Location of image in memory
	 */
	public Point (int id, String title, String tripname, int lat, int lng, long time, String text, String imgLoc){
		this._id = id;
		this._tripname = tripname;
		this._longitude = lng;
		this._latitude = lat;
		this._time = time;
		this._title = title;
		this._note = text;
		this._imgLoc = imgLoc;
	}
	
	
	public long getId() {
		return _id;
	}

	public String getTripname() {
		return _tripname;
	}

	public int getLatitude() {
		return _latitude;
	}

	public int getLongitude() {
		return _longitude;
	}

	public long getTime() {
		return _time;
	}

	public String getTitle() {
		return _title;
	}

	public String getPhotoPath() {
		return _imgLoc;
	}
	
	public String getNote() {
		return _note;
	}
	
	public void setID(long Id) {
		this._id = Id;
	}

	public void setTripname(String tripname) {
		this._tripname = tripname;
	}

	public void setLatitude(int latitude) {
		this._latitude = latitude;
	}

	public void setLongitude(int longitude) {
		this._longitude = longitude;
	}

	public void setTime(long time) {
		this._time = time;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public void setNote(String note) {
		this._note = note;
	}
	
	public void setImgLoc(String loc){
		this._imgLoc = loc;
	}
}
