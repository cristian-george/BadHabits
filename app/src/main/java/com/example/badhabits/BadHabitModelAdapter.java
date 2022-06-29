package com.example.badhabits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class BadHabitModelAdapter extends ArrayAdapter<BadHabitModel> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView userId;
        TextView habit;
        TextView date;
    }

    public BadHabitModelAdapter(Context context, int resource, ArrayList<BadHabitModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        int userId = getItem(position).getUserId();
        String habit = getItem(position).getHabit();
        LocalDate date = getItem(position).getDate();

        //Create the person object with the information
        BadHabitModel badHabitModel = new BadHabitModel(userId,habit,date);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.userId = (TextView) convertView.findViewById(R.id.textView1);
            holder.habit = (TextView) convertView.findViewById(R.id.textView2);
            holder.date = (TextView) convertView.findViewById(R.id.textView3);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;
        //String s = String.valueOf(badHabitModel.getUserId());
        holder.userId.setText(badHabitModel.getHabit());
        //holder.habit.setText(badHabitModel.getHabit());
        holder.date.setText(badHabitModel.getDate().toString());

        return convertView;
    }
}
