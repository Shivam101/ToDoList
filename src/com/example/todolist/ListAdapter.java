package com.example.todolist;



import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<String> {
	
	 	static Context context;
	    static int layoutResourceId;   
	    //Level data[] = null;
	    ArrayList<String> data;
	   
	 public ListAdapter(Context context, int layoutResourceId, ArrayList<String> data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }
	    
	   
		public long getItemId(int position) {
            return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        WeatherHolder holder = null;
	       
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	           //row.setMinimumHeight(200);
	           holder = new WeatherHolder();
	          holder.txtTitle = (TextView)row.findViewById(R.id.todo_text);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (WeatherHolder)row.getTag();
	        }
	       
	        //Level weather = data[position];
	        holder.txtTitle.setText(data.get(position));
	        //holder.imgIcon.setImageResource(weather.icon);
	       // holder.txtTitle2.setText(weather.title2);
	       // holder.txtTitle2.setText(weather.title2);
	       // holder.txtTitle3.setText(weather.title3);
	        return row;
	    }
	   
	    static class WeatherHolder
	    {
	       //ImageView imgIcon;
	        TextView txtTitle;
	       // TextView txtTitle2;
	       // TextView txtTitle3;
	    //    ImageView imgIcon2;
	    }

}
