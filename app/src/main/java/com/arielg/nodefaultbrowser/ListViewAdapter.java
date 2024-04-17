package com.arielg.nodefaultbrowser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ListViewAdapter extends ArrayAdapter<String> {

    private final Activity    m_context;
    private final Drawable[]  m_icons;
    private final String[]    m_labels;

    public ListViewAdapter(Activity context, Drawable[] icons, String[] labels) {
        super(context, R.layout.list_view_item, labels);
        this.m_context = context;
        this.m_icons = icons;
        this.m_labels = labels;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        LayoutInflater inflater = m_context.getLayoutInflater();
        if(convertView == null) {
            row = inflater.inflate(R.layout.list_view_item, null, true);
        }

        ImageView   imageIcon = row.findViewById(R.id.icon);
        TextView    textLabel = row.findViewById(R.id.label);

        imageIcon.setImageDrawable(m_icons[position]);
        textLabel.setText(m_labels[position]);
        return row;
    }
}
