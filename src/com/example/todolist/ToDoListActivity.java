package com.example.todolist;


import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ToDoListActivity extends Activity {

	List<ParseObject> pItems;
	ListView todolist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);
		todolist = (ListView)findViewById(R.id.todo_items);
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
		
		ParseQuery<ParseObject> itemQuery = ParseQuery.getQuery("todoItems");
		itemQuery.orderByDescending("itemPriority").findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> items, ParseException e) {
				// TODO Auto-generated method stub
				pItems = items;
				String[] todos = new String[pItems.size()];
				int i = 0;
				for(ParseObject obj : pItems)
				{
					todos[i] = obj.getString("itemName"); 
					i++;
				}
				ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(ToDoListActivity.this, android.R.layout.simple_list_item_1,todos);
				todolist.setAdapter(mAdapter);
			
			}
		});
		
	}
}
