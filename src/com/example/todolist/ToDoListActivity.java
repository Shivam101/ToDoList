package com.example.todolist;


import com.parse.ParseObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ToDoListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);
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
		
	}
}
