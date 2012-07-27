package com.example.mapjournal;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PreviousTripsActivity extends ListActivity 
{
	DBOpenHelper db = new DBOpenHelper(this);
    public static final String PREFS_NAME = "PrefsFile";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_previous_trips);
        ArrayList<String> tripNames = new ArrayList<String>();
        tripNames = db.getAllTrips();
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
        
//        TextView txtProduct = (TextView) findViewById(R.id.product_label);
//        
//        Intent i = getIntent();
//        // getting attached intent data
//        String product = i.getStringExtra("product");
//        // displaying selected product name
//        txtProduct.setText(product);
    }

    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("current", keyword);
		editor.commit();
		
		Intent intent = new Intent(this, MapJournalMapActivity.class);
		startActivity(intent);
		
		Toast.makeText(this, "You Have Selected: " + keyword, Toast.LENGTH_LONG).show();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_previous_trips, menu);
        return true;
    }
}
