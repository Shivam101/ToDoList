package com.example.todolist;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class AddListItem extends Activity {

	EditText mToDoEditText;
	TextView tv1;
	CheckBox mPriorityCBHigh, mPriorityCBMedium, mPriorityCBLow;
	RadioButton mPriorityRBHigh, mPriorityRBMedium, mPriorityRBLow;
	Button mConfirmButton;
	Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_list_item);
		tv1 = (TextView) findViewById(R.id.title_todo);
		tv1.setVisibility(View.GONE);
		ActionBar ab = getActionBar();
		Resources r = getResources();
		Drawable d = r.getDrawable(R.color.blue_ab);
		ab.setBackgroundDrawable(d);

		mToDoEditText = (EditText) findViewById(R.id.todo_details);
		mToDoEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionId==EditorInfo.IME_ACTION_DONE){
				String todo = mToDoEditText.getText().toString();
				boolean checkedHigh = mPriorityRBHigh.isChecked();
				boolean checkedMedium = mPriorityRBMedium.isChecked();
				boolean checkedLow = mPriorityRBLow.isChecked();
				if (todo.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							AddListItem.this);
					builder.setMessage(R.string.empty_text);
					builder.setTitle(R.string.error_title);
					builder.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				if ((!mPriorityRBHigh.isChecked())
						&& (!mPriorityRBLow.isChecked())
						&& (!mPriorityRBMedium.isChecked())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							AddListItem.this);
					builder.setMessage(R.string.priority_level_error);
					builder.setTitle(R.string.error_title);
					builder.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}

				else {
					AddListItem.this.progressDialog = ProgressDialog.show(
							AddListItem.this, "", "Saving...", true);
					ParseObject todoObject = new ParseObject("todoItems");
					todoObject.put("itemName", todo);
					if (checkedHigh == true) {
						todoObject.put("itemPriority", 10);
					} else if (checkedLow == true) {
						todoObject.put("itemPriority", 0);
					} else if (checkedMedium == true) {
						todoObject.put("itemPriority", 5);
					}

					todoObject.saveInBackground(new SaveCallback() {

						@Override
						public void done(ParseException e) {
							// TODO Auto-generated method stub
							if (e == null) {
								AddListItem.this.progressDialog.dismiss();
								Intent i = new Intent(AddListItem.this,
										ToDoListActivity.class);
								i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(i);
							} else {
								AddListItem.this.progressDialog.dismiss();
								AlertDialog.Builder builder = new AlertDialog.Builder(
										AddListItem.this);
								builder.setMessage(R.string.save_error);
								builder.setTitle(R.string.error_title);
								builder.setPositiveButton(android.R.string.ok,
										null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
				}
			
				}
				return false;
			}
		});
		mConfirmButton = (Button) findViewById(R.id.save_todo);
		mPriorityRBHigh = (RadioButton) findViewById(R.id.priorityHigh);
		mPriorityRBMedium = (RadioButton) findViewById(R.id.priorityMedium);
		mPriorityRBLow = (RadioButton) findViewById(R.id.priorityLow);
		mConfirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String todo = mToDoEditText.getText().toString();
				boolean checkedHigh = mPriorityRBHigh.isChecked();
				boolean checkedMedium = mPriorityRBMedium.isChecked();
				boolean checkedLow = mPriorityRBLow.isChecked();
				if (todo.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							AddListItem.this);
					builder.setMessage(R.string.empty_text);
					builder.setTitle(R.string.error_title);
					builder.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				if ((!mPriorityRBHigh.isChecked())
						&& (!mPriorityRBLow.isChecked())
						&& (!mPriorityRBMedium.isChecked())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							AddListItem.this);
					builder.setMessage(R.string.priority_level_error);
					builder.setTitle(R.string.error_title);
					builder.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}

				else {
					AddListItem.this.progressDialog = ProgressDialog.show(
							AddListItem.this, "", "Saving...", true);
					ParseObject todoObject = new ParseObject("todoItems");
					todoObject.put("itemName", todo);
					if (checkedHigh == true) {
						todoObject.put("itemPriority", 10);
					} else if (checkedLow == true) {
						todoObject.put("itemPriority", 0);
					} else if (checkedMedium == true) {
						todoObject.put("itemPriority", 5);
					}

					todoObject.saveInBackground(new SaveCallback() {

						@Override
						public void done(ParseException e) {
							// TODO Auto-generated method stub
							if (e == null) {
								AddListItem.this.progressDialog.dismiss();
								Intent i = new Intent(AddListItem.this,
										ToDoListActivity.class);
								i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(i);
							} else {
								AddListItem.this.progressDialog.dismiss();
								AlertDialog.Builder builder = new AlertDialog.Builder(
										AddListItem.this);
								builder.setMessage(R.string.save_error);
								builder.setTitle(R.string.error_title);
								builder.setPositiveButton(android.R.string.ok,
										null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
				}
			}
		});
	}
}
