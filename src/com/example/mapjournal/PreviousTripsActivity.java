package com.example.mapjournal;

<<<<<<< HEAD
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
=======

import android.os.Bundle;
>>>>>>> 968537c4d7da62c30b9f955a793f5b49c085c093
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PreviousTripsActivity extends ListActivity 
{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_previous_trips);
        String[] tripNames = new String[]{"Trip 1", "Trip 2", "Trip 3", "View All"};
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tripNames));
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
