package com.scriptedpapers.pickadate;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Customize the weekday gridview
 */
public class WeekdayArrayAdapter extends ArrayAdapter<String> {
    public static int textColor = Color.DKGRAY;

    public WeekdayArrayAdapter(Context context, int textViewResourceId,
                               List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    // To prevent cell highlighted when clicked
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    int height;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // To customize text size and color
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) inflater.inflate(R.layout.weekday_textview, null);

        // Set content
        String item = getItem(position);
        textView.setText(item);

        if (height > 0) {
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, height);
            textView.setLayoutParams(layoutParams);
        }

        textView.setTextColor(textColor);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public void setHeight(int height) {
        this.height = height;
        notifyDataSetChanged();
    }
}
