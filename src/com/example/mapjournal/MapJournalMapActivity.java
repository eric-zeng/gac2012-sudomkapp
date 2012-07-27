package com.example.mapjournal;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
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
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import android.view.Menu;
import android.view.MenuItem;

/**
 * The activity to let user interact with the mapview, initiate addPointActivity and provide location information
 * @author Leo Dihong Gao
 *
 */
public class MapJournalMapActivity extends MapActivity 
{	
	public final static String ID = "com.example.maptest.MainActivity.ID"; //Point ID identifier
    public LocationManager locationManager;
    public LocationListener locationListener;
    public MapView mapView;
    public final static String LONGITUDE = "com.example.maptest.MainActivity.LONGITUDE";//longitude label in outbound intent
    public final static String LATITUDE = "com.example.maptest.MainActivity.LATITUDE";//latitude label in outbound intent
    private double latitude, longitude;
    public static final String PREFS_NAME = "PrefsFile";//the file name of the shared preference
    
    
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
		
		// add all trip points, and update location		
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        String currentTrip = prefs.getString("current", null);	        
        if(null != currentTrip){
        	DBOpenHelper db = new DBOpenHelper(this);
        	addAllPoints(db.getTrip(currentTrip));
        }
		
		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		latitude = lastKnownLocation.getLatitude();
		longitude = lastKnownLocation.getLongitude();
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
	
	/**
	 * When the check in button is hit, get the current location and pass that info in the intent to start addPointActivity
	 * @param menu
	 * @return 0 on success
	 */
	public int onGetLocationClicked(MenuItem menu)
	{
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		latitude = lastKnownLocation.getLatitude();
		longitude = lastKnownLocation.getLongitude();
		addPoint(latitude, longitude, "Hello map", "I'm in somewhere");
		
		Intent intent = new Intent(this, addPointActivity.class);
		intent.putExtra(LATITUDE, latitude);
		intent.putExtra(LONGITUDE, longitude);		
		startActivity(intent);
		return 0;
	}
	
	/**
	 * Add all points onto the map
	 * @param tripPoint all the points to be added
	 * @return 0 return 0 on success
	 */
	public int addAllPoints(ArrayList <Point> tripPoint)// to be added
	{
		for(int iter=0; iter<tripPoint.size(); iter++)
		{
			addPoint(tripPoint.get(iter).getLatitude(), 
						tripPoint.get(iter).getLongitude(),
						Long.toString(tripPoint.get(iter).getId()),
						"");
		}
		return 0;
	}
	
	/**
	 * The class used to add layers to mapview
	 * @author Leo Dihong Gao
	 *
	 */
	@SuppressWarnings("rawtypes")
	public class MapTestItemizedOverlay extends  ItemizedOverlay {

		private ArrayList <OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		Context mContext;
		public MapTestItemizedOverlay(Drawable defaultMarker, Context context) {
			super(boundCenterBottom(defaultMarker));
			// TODO Auto-generated constructor stub
			mContext=context;
		}

		@Override
		protected OverlayItem createItem(int arg0) {
			// TODO Auto-generated method stub
			return mOverlays.get(arg0);
		}
		
		public void addOverlay(OverlayItem overlay)
		{
			mOverlays.add(overlay);
			populate();
		}
		
		@Override
		public int size() {
			// TODO Auto-generated method stub
			return mOverlays.size();
		}

		@Override
		/**
		 * start the JournalEntryActivity te display info of the point being tapped
		 */
		protected boolean onTap(int index)
		{
			Intent intent = new Intent(MapJournalMapActivity.this, JournalEntryActivity.class);
			intent.putExtra(ID, Long.parseLong(mOverlays.get(index).getTitle().toString()));
			startActivity(intent);
			return super.onTap(index);
		}
	}
	/**
	 * add a single point onto the MapOverlay
	 * @param latitude the latitude of the point
	 * @param longitude the longitude of the point
	 * @param title the point ID
	 * @param snippet It's not being used. Put an empty string here (not null!) 
	 * @return 0 on success
	 */
	private int addPoint(double latitude, double longitude, String title, String snippet){
		List <Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable=this.getResources().getDrawable(R.drawable.androidmarker);
		MapTestItemizedOverlay itemizedOverlay = new MapTestItemizedOverlay(drawable, this);
		GeoPoint point =new GeoPoint((int)(latitude), (int)(longitude));
		OverlayItem overlayItem = new OverlayItem(point, title, snippet);
		itemizedOverlay.addOverlay(overlayItem);
		mapOverlays.add(itemizedOverlay);
		return 0;
	}
    
}

