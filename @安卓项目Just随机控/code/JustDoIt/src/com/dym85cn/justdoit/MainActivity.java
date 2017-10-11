package com.dym85cn.justdoit;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author dym85cn1
 * 
 */
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnRefreshListener,OnItemLongClickListener {

		public PlaceholderFragment() {
		}

		private SwipeRefreshLayout swipeLayout;
		
		private ListView lv;
		
		private List<String> strArr;
		
		private ArrayAdapter<String> adapter;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
		 */
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			initView();
		}

		private void initView() {
			strArr = new ArrayList<String>();
			lv = (ListView) getActivity().findViewById(R.id.list);
			adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,strArr);
			lv.setOnItemLongClickListener(this);
			swipeLayout = (SwipeRefreshLayout) getActivity().findViewById(
					R.id.swipe_container);
			swipeLayout.setOnRefreshListener(this);
			swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
					android.R.color.holo_green_light,
					android.R.color.holo_orange_light,
					android.R.color.holo_red_light);
		}

		public void onRefresh() {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					swipeLayout.setRefreshing(false);
					strArr.add(PowerTools.getRandom2ColorBall());
					lv.setAdapter(adapter);
					adapter.setNotifyOnChange(true);
					//www.javaapk.com
				}
			}, 4000);
		}

		int menuIndex = 0;
		
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			
			menuIndex = arg2;
			
			lv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
				
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
					menu.add(0,0,0,"删除"); 					
				}
			});
			return false;
		}
		
		// 长按菜单响应函数  
        public boolean onContextItemSelected(MenuItem item) {  
   
                switch(item.getItemId()) {  
                case 0:  
                	strArr.remove(menuIndex);
        			lv.setAdapter(adapter);
        			adapter.setNotifyOnChange(true);
                        break;  
   
                default:  
                        break;  
                }  
   
                return super.onContextItemSelected(item);  
        }  

	}

}
