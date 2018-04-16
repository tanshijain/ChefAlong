package com.example.tanshi.chefalong;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

/**
 * Created by tanshi on 4/5/18.
 */

public class MessageAdapter extends ArrayAdapter<MessageData> {
    private final ArrayList<MessageData> messages;
    private final Activity context;
    public MessageAdapter (Activity contextParam, ArrayList<MessageData> nameArrayParam) {
        super(contextParam, R.layout.grouplist_row, nameArrayParam);
        messages = nameArrayParam;
        context = contextParam;
    }
    public View getView(int position, View oldView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView;
        if (messages.get(position).user.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            rowView = inflater.inflate(R.layout.message_sent, null, true);
        }else {
            rowView = inflater.inflate(R.layout.message_received, null, true);
            TextView user = rowView.findViewById(R.id.senderName);
            user.setText(messages.get(position).user);
        }
        TextView message = rowView.findViewById(R.id.messageText);
        message.setText(messages.get(position).messageText);
        return rowView;

    }
}
