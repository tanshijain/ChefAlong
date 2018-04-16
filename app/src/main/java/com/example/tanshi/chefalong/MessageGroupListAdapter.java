package com.example.tanshi.chefalong;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tanshi on 4/5/18.
 */

public class MessageGroupListAdapter extends ArrayAdapter<MessageGroupListItemInfo> {
    private final ArrayList<MessageGroupListItemInfo> nameArray;
    private final Activity context;

    public MessageGroupListAdapter (Activity contextParam, ArrayList<MessageGroupListItemInfo> nameArrayParam) {
        super(contextParam, R.layout.grouplist_row, nameArrayParam);
        nameArray = nameArrayParam;
        context = contextParam;
    }
    public View getView(int position, View oldView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.grouplist_row, null, true);
        TextView topic = rowView.findViewById(R.id.group_topic_label);
        topic.setText(nameArray.get(position).topic);
        TextView description = rowView.findViewById(R.id.group_description_label);
        description.setText(nameArray.get(position).description);
        return rowView;
    }
}

