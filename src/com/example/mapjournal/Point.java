package com.example.mapjournal;

public class Point {

	private int _id;
	private String _tripname;
	private int _latitude;
	private int _longitude;
	private int _time;
	private String _title;
	private String _note;
	
	public Point(){
		
	}

	public Point (String title, String tripname, int lat, int lng, int time, String text){
		this._tripname = tripname;
		this._longitude = lng;
		this._latitude = lat;
		this._time = time;
		this._title = title;
		this._note = text;
	}
	
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
	
	public void setID(int _id) {
		this._id = _id;
	}

	public void setTripname(String _tripname) {
		this._tripname = _tripname;
	}

	public void setLatitude(int _latitude) {
		this._latitude = _latitude;
	}

	public void setLongitude(int _longitude) {
		this._longitude = _longitude;
	}

	public void setTime(int _time) {
		this._time = _time;
	}

	public void setTitle(String _title) {
		this._title = _title;
	}

	public void setNote(String _note) {
		this._note = _note;
	}
}
