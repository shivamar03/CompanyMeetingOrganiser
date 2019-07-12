package com.example.companymeetingorganiser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MeetingAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MeetingModel> meetingModelArrayList;

    public MeetingAdapter(Context context, ArrayList<MeetingModel> meetingModelArrayList) {

        this.context = context;
        this.meetingModelArrayList = meetingModelArrayList;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getCount() {
        return meetingModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return meetingModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_meeting, null, true);

            //holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.stime = (TextView) convertView.findViewById(R.id.startimeview);
            holder.etime = (TextView) convertView.findViewById(R.id.endtime);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.participants = (TextView) convertView.findViewById(R.id.participants);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.stime.setText("Start Time: "+meetingModelArrayList.get(position).getStartTime());
        holder.etime.setText("End Time: "+meetingModelArrayList.get(position).getEndTime());
        holder.description.setText("Description: "+meetingModelArrayList.get(position).getDescription());
        holder.participants.setText("Participants: "+meetingModelArrayList.get(position).getParticipants());

        return convertView;
    }

    private class ViewHolder {

        protected TextView stime, etime, description, participants;
    }
}
