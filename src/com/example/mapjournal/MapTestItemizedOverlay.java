package com.example.mapjournal;
//Created by Leo
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

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
	protected boolean onTap(int index)
	{
//		OverlayItem item=mOverlays.get(index);
//		AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
//		dialog.setTitle(item.getTitle());
//		dialog.setMessage(item.getSnippet());
//		dialog.show();
		Toast.makeText(mContext, mOverlays.get(index).getTitle().toString()
					+", Latitude: "+mOverlays.get(index).getPoint().getLatitudeE6(),
						Toast.LENGTH_SHORT).show();
		return super.onTap(index);
	}
}
