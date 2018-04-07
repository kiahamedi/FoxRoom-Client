package ir.kiahamedi.foxroom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ir.kiahamedi.foxroom.others.Message;

public class MessagesListAdapter extends BaseAdapter{

    private Context context;
    private List<Message> messageItems;


    public MessagesListAdapter(Context context , List<Message> nanDraweItems)
    {
        this.context=context;
        this.messageItems=nanDraweItems;

    }


    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message m=messageItems.get(position);
        LayoutInflater minflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(messageItems.get(position).isSelf())
        {
            convertView = minflater.inflate(R.layout.list_item_message_right,null);
        }else
        {
            convertView = minflater.inflate(R.layout.list_item_message_left,null);
        }

        TextView lblMsgFrom=(TextView) convertView.findViewById(R.id.lblMsgFrom);
        TextView txtMsg=(TextView) convertView.findViewById(R.id.txtMsg);


        lblMsgFrom.setText(m.getFromName());
        txtMsg.setText(m.getMessage());

        return convertView;
    }
}
