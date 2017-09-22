package com.sureshale.motorconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.sureshale.motorconnect.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sureshale on 14-09-2017.
 */

public class VehiclesExpandableListAdapter extends BaseExpandableListAdapter {
    List<String> title;
    Map<String,List<String>> titleItem;
//    LayoutInflater inflater;
    Context context;

    public VehiclesExpandableListAdapter(List<String> title, Map<String,List<String>> titleItem, Context context){
        this.title = title;
        this.titleItem = titleItem;
//        this.inflater = inflater;
        this.context = context;
    }


    @Override
    public int getGroupCount() {
        return title.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

         return titleItem.get(title.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return title.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
//        return child1.get(groupPosition);
        return titleItem.get(title.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String str1 = (String)getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_items_parent, null);
        }
        TextView parentText = (TextView)convertView.findViewById(R.id.list_item_parent);
        parentText.setText(str1);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String str2 = (String) getChild(groupPosition,childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_items_child, null);
        }
        TextView childText = (TextView)convertView.findViewById(R.id.list_item_child1);
        childText.setText(str2);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
