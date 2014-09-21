package com.example.todolist;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ToDoListApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, "FmvhOyEusDWkXMCmq1adBn5alJohGuCefczir0Bx",
				"k2gZOAFaW7f0y3TkULrTpjMKkp16faGE0Ai5bDXw");
	}

}
