package com.saumitra.stormy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.saumitra.stormy.R;
import com.saumitra.stormy.Weather.Day;

/**
 * Created by saumitra on 25-08-2017.
 */
// Consider each item in the list as an OBJECT. i.e days[0],days[1] etc..
//also, Adapter takes data from an object, here it is taking data from array object of type Day
public class DayAdapter extends BaseAdapter {
    private Context mContext;
    private Day[] mdays;

    public DayAdapter(Context context, Day[] days)// every adapter has a constructor which allows it use in the activity
    {
        mContext = context;
        mdays = days;
    }

    @Override
    public int getCount()// gets us the count of items in the  array that this adapter is using
    {
        return mdays.length;
    }

    @Override
    public Object getItem(int position) {
        return mdays[position];
    }

    @Override
    public long getItemId(int position) {

        return 0;// we r not going to use this
    }

//convertView is the object we want to reuse.
    // convertView is the list item view that will go up as we scroll, we need it so that we can use it again for coming items,
    @Override//instead of creating new everytime.And for the view component, we have them in holder,can be used anytime
    public View getView(int position, View convertView, ViewGroup viewGroup) // here mapping occurs
    //this method is called for everything that is initially displayed, and then it is called each time we scroll a new item
    //onto the list
    {
        ViewHolder holder;
        if(convertView==null)// that means nothing is being scrolled up, nd we need to craete new one, to hold list item layout
        {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.daily_list_item,null);//this is where u will give refrence of list item layout that u have made
            holder=new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel=(TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel=(TextView) convertView.findViewById(R.id.dayNameLabel);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }
        Day day=mdays[position];// code before here deals with reusing the view, after that we need to set them here
        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax()+"");
        if(position==0){
            holder.dayLabel.setText("Today");
        }
        holder.dayLabel.setText(day.getDaysOfWeek());

        return convertView;

    }
    private static class ViewHolder // A viewHolder class only holds the component of views(the ones that are likely to be chnaged for each item),
    {                           // otherwise u wudd need to repeated findById to everyone and that will decay microprocess speed,
        ImageView iconImageView;// friendly by default
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
