package com.saumitra.stormy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saumitra.stormy.R;
import com.saumitra.stormy.Weather.Hour;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saumitra on 28-08-2017.
 */
//recycler view works in a different way as compared to ListAdapter, there is no separate getView and Holder methods
  //  i.e viewholder now is also reponsible for mapping and as a container

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

    private Hour[] mHours;
    private Context mContext;

    public HourAdapter(Hour[] hours,Context mContext) {
        mHours = hours;
        this.mContext=mContext;
    }

    @Override// this method is called when we are about to scroll, it will creat a previously generated list item for us
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list_item, parent, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override// and this code will bind the view/components of that List item
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }// CODE UPTO HERE IS ADAPTER

    /* So Adapter did 3 things
    1- connected the recycler view to data source via construction having contect and mDays
    2- It created new items using View Holder
    3-It populated those data using bindHour
     */


    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);


            itemView.setOnClickListener(this);// very important
        }
// this is a helper method which will we used by adapter to bind/populate the list items
        public void bindHour(Hour hour) {
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperature() + "");
            mIconImageView.setImageResource(hour.getIconId());
        }

        @Override
        public void onClick(View view) {
            String time=mTimeLabel.getText().toString();
            String temperature=mTemperatureLabel.getText().toString();// since u r using info from Xml
            String summary=mSummaryLabel.getText().toString();
            String message=String.format("On %s the high will be %s and it will be %s",time,temperature,summary);

            Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();

        }
    }
}
