package com.example.mapjournal;

public class Point {

	private int _id;
	private String _tripname;
	private int _latitude;
	private int _longitude;
	private int _time;
	private String _title;
	private String _text;
	
	public Point (int id, String title, String tripname, int lat, int lng, int time, String text){
		this._id = id;
		this._tripname = tripname;
		this._longitude = lng;
		this._latitude = lat;
		this._time = time;
		this._title = title;
		this._text = text;
	}
	
	public void setText(String text){
		this._text = text;
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

	public String getText() {
		return _text;
	}
}
