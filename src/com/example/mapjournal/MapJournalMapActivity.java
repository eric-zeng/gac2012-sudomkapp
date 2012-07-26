package com.example.mapjournal;
//Created by Leo
import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapJournalMapActivity extends MapActivity {
	public final static String ID = "com.example.maptest.MainActivity.ID"; 
    public LocationManager locationManager;
    private final static String TAG="MainActivity";
    public LocationListener locationListener;
    public MapView mapView;
    public final static String LONGITUDE = "com.example.maptest.MainActivity.LONGITUDE";
    public final static String LATITUDE = "com.example.maptest.MainActivity.LATITUDE";
    private double latitude, longitude;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener()
        {

    		public void onLocationChanged(Location arg0) {
    			// TODO Auto-generated method stub
    			
    		}

    		public void onProviderDisabled(String arg0) {
    			// TODO Auto-generated method stub
    			
    		}

    		public void onProviderEnabled(String arg0) {
    			// TODO Auto-generated method stub
    			
    		}

    		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    			// TODO Auto-generated method stub
    			
    		}
        };
        // Load the mapView and make it zoomable
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5, locationListener);
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		double latitude = lastKnownLocation.getLatitude();
		double longitude = lastKnownLocation.getLongitude();
		Intent intent = getIntent();
		if(intent.getBooleanExtra(addPointActivity.ADD_SUCCESS, false))
		{
			addPoint(intent.getDoubleExtra(LATITUDE, -10000), 
						intent.getDoubleExtra(LONGITUDE, -100000),
						Long.toString(intent.getLongExtra(addPointActivity.POINT_ID, -1)), "");
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_map_menu, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	public int onGetLocationClicked(MenuItem menu)
	{
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		latitude = lastKnownLocation.getLatitude();
		longitude = lastKnownLocation.getLongitude();
		
		Context context = getApplicationContext();
		CharSequence text = "Latitude: "+Double.toString(latitude)
							+ "   Longitude: "+Double.toString(longitude);
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
		
		addPoint(latitude, longitude, "Hello map", "I'm in somewhere");
		
		Intent intent = new Intent(this, addPointActivity.class);
		intent.putExtra(LATITUDE, latitude);
		intent.putExtra(LONGITUDE, longitude);
		
		startActivity(intent);
		return 0;
	}
	public int addAllPoints(ArrayList <Integer> tripPoint)// to be added
	{
		for(int iter=0; iter<tripPoint.size(); iter++)
		{
			
		}
		return 0;
	}
	private int addPoint(double latitude, double longitude, String title, String snippet)
	{
		List <Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable=this.getResources().getDrawable(R.drawable.androidmarker);
		MapTestItemizedOverlay itemizedOverlay = new MapTestItemizedOverlay(drawable, this);
		GeoPoint point =new GeoPoint((int)(latitude*1E6), (int)(longitude*1E6));
		OverlayItem overlayItem = new OverlayItem(point, "Hello map", "I'm in somewhere");
		itemizedOverlay.addOverlay(overlayItem);
		mapOverlays.add(itemizedOverlay);
		return 0;
	}
    
}
