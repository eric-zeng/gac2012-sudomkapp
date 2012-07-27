package com.example.mapjournal;

/**
 * Class to represent a geographic point visited on a trip
 * @author dinalamdany
 *
 */
public class Point {

	private int _id;
	private String _tripname;
	private int _latitude;
	private int _longitude;
	private int _time;
	private String _title;
	private String _note;
	
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
	 */
	public Point (String title, String tripname, int lat, int lng, int time, String text){
		this._tripname = tripname;
		this._longitude = lng;
		this._latitude = lat;
		this._time = time;
		this._title = title;
		this._note = text;
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
	 */
	public Point (int id, String title, String tripname, int lat, int lng, int time, String text){
		this._id = id;
		this._tripname = tripname;
		this._longitude = lng;
		this._latitude = lat;
		this._time = time;
		this._title = title;
		this._note = text;
	}
	
	
	public int getId() {
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

	public int getTime() {
		return _time;
	}

	public String getTitle() {
		return _title;
	}

	public String getNote() {
		return _note;
	}
	
	public void setID(int id) {
		this._id = id;
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

	public void setTime(int time) {
		this._time = time;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public void setNote(String note) {
		this._note = note;
	}
}
