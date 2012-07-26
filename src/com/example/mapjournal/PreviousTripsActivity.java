package com.example.mapjournal;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_previous_trips);
        db.addPoint(new Point("hi", "cali", 5, 6, 6, "fun"));
        db.addPoint(new Point("new", "sanfran", 5, 6, 6, "dogs"));
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
		Toast.makeText(this, "You Have Selected: " + keyword, Toast.LENGTH_LONG).show();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_previous_trips, menu);
        return true;
    }
}
