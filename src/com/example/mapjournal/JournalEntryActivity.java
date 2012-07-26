package com.example.mapjournal;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;


// This Activity contains the notes and pictures functionality
// for the map journal. This class is for adding tabs to the action
// bar. Notes and pictures are both separate fragments.

public class JournalEntryActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journal_entry);
		
		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab notesTab = ab.newTab().setText("Notes");
		ActionBar.Tab picsTab = ab.newTab().setText("Pictures");
		
		Fragment notesFragment = new NotesTabFragment();
		Fragment picsFragment = new PicsTabFragment();
		
		notesTab.setTabListener(new MyTabsListener(notesFragment));
		picsTab.setTabListener(new MyTabsListener(picsFragment));
		
		ab.addTab(notesTab);
		ab.addTab(picsTab);
	}
	
	protected class MyTabsListener implements ActionBar.TabListener {
		
		private Fragment fragment;
		
		public MyTabsListener(Fragment fragment){
			this.fragment = fragment;			
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.add(R.id.fragment_container, fragment, null);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub			
		}
		
		
	}
}
