package com.example.todolist;


import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class ToDoListActivity extends Activity {

	List<ParseObject> pItems;
	ListView todolist;
	NetworkInfo nf;
	ArrayAdapter<String> mAdapter;
	SwipeRefreshLayout mSwipeRefreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);
		ActionBar ab=getActionBar();
		Resources r=getResources();
		Drawable d=r.getDrawable(R.color.blue_ab);
		ab.setBackgroundDrawable(d);
		ParseObject pob = new ParseObject("todoItems");
		
		/*if(!isConnectedtoNet(ToDoListActivity.this))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ToDoListActivity.this);
			builder.setMessage(R.string.internet_error);
			builder.setTitle(R.string.error_title);
			builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Intent.ACTION_MAIN);
					i.addCategory(Intent.CATEGORY_HOME);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
				}
				
				
				
				
				

				
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}*/
		
		todolist = (ListView)findViewById(R.id.todo_items);
		todolist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		SwipeDismissListViewTouchListener touchListener =
	            new SwipeDismissListViewTouchListener(
	                    todolist,
	                    new SwipeDismissListViewTouchListener.DismissCallbacks() {
	                        @Override
	                        public boolean canDismiss(int position) {
	                            return true;
	                        }

	                        @Override
	                        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
	                            for (final int position : reverseSortedPositions) {
	                                mAdapter.remove(mAdapter.getItem(position));
	                                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("todoItems");
	                                //query.whereEqualTo("itemName", todolist.getItemAtPosition(position));
	                                query.findInBackground(new FindCallback<ParseObject>() {
										
										@Override
										public void done(List<ParseObject> items, ParseException e) {
											// TODO Auto-generated method stub
											if(e==null)
											{
												pItems = items;
												ArrayList<String> todo_items = new ArrayList<String>();
												for(ParseObject obj : pItems)
												{
													//ParseObject pob = lists.get(i);
													obj = pItems.get(position);
													obj.deleteInBackground();
												}
											}
										}
									});
	                                
	                                //ParseObject.createWithoutData("todoItems", arg1)
	                                Toast.makeText(getBaseContext(), "Done with this task", Toast.LENGTH_SHORT).show();
	                            }
	                            mAdapter.notifyDataSetChanged();
	                        }
	                    });
		todolist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				//if(todolist.isItemChecked(position))
				//{
					//ParseQuery<ParseObject> pquery;
					//pquery.whereEqualTo(key, value)
				//}
			}
		});
		todolist.setOnTouchListener(touchListener);
		todolist.setOnScrollListener(touchListener.makeScrollListener());
		mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
		mSwipeRefreshLayout.setColorScheme(R.color.blue_200, R.color.blue_400, R.color.blue_600, R.color.blue_800);
		//mSwipeRefreshLayout.setColorScheme(R.color.red,R.color.blue_600,R.color.green,R.color.orange_600);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "Retrieving Todos", Toast.LENGTH_SHORT).show();
				//if(isConnectedtoNet(ToDoListActivity.this))
				//{
					getTodos();
				//}
				//else
				//{
					//Toast.makeText(getBaseContext(), "No Connection", Toast.LENGTH_SHORT).show();
					//mSwipeRefreshLayout.setRefreshing(false);
					
				//}
				//if(nf.isConnected())
				
			}
		});
		Fab fab = (Fab) findViewById(R.id.fabbutton);
		fab.setFabColor(Color.parseColor("#2a36b1"));
		fab.setFabDrawable(getResources().getDrawable(
				R.drawable.ic_action_new));
		fab.showFab();		
		fab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent addtodoIntent = new Intent(ToDoListActivity.this,AddListItem.class);
				startActivity(addtodoIntent);
			}
		});
		
		getTodos();
		
	}
	
		public void getTodos() {
		ParseQuery<ParseObject> itemQuery = ParseQuery.getQuery("todoItems");
		itemQuery.orderByDescending("itemPriority");
		itemQuery.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);

		itemQuery.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> items, ParseException e) {
				// TODO Auto-generated method stub
				if(mSwipeRefreshLayout.isRefreshing())
				{
					mSwipeRefreshLayout.setRefreshing(false);
				}
				pItems = items;
				String[] todos = new String[pItems.size()];
				int i = 0;
				ArrayList<String> todo_items = new ArrayList<String>(); //very important line. Fixes issue with swipe to dismiss
				for(ParseObject obj : pItems)
				{
					todo_items.add(obj.getString("itemName"));
					todos[i] = obj.getString("itemName"); 
					i++;
				}
				mAdapter = new ArrayAdapter<String>(ToDoListActivity.this, android.R.layout.simple_list_item_1,todo_items);
				todolist.setAdapter(mAdapter);
			
			}
		});
	}
	
    
	
	public static boolean isConnectedtoNet(Context ct)
	{
		boolean connected = false;
		ConnectivityManager cm = (ConnectivityManager)ct.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm!=null)
		{
			if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
			{
				connected = true;
			}
			else
			{
				connected = false;
			}
			return connected;
		}
		else
		{
			return false;
		}
	}
			
		
	
}
