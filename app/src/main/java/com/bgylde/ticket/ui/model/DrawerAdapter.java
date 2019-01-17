package com.bgylde.ticket.ui.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bgylde.ticket.R;
import com.bgylde.ticket.request.model.QueryTicketItemModel;

import java.util.List;

/**
 * Created by wangyan on 2019/1/17
 */
public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private List<ViewHolderItem> itemList;
    private ItemClickListener itemClickListener;

    public DrawerAdapter(Context context, List<ViewHolderItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public ViewHolderItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.drawe_item, parent, false);
        }

        if (itemClickListener != null) {
            RelativeLayout itemView = view.findViewById(R.id.ticket_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClickItem(position);
                }
            });
        }

        setTextString(view, R.id.train_date, itemList.get(position).trainDate);
        setTextString(view, R.id.start_time, itemList.get(position).startTime);
        setTextString(view, R.id.end_time, itemList.get(position).endTime);
        setTextString(view, R.id.from_station, itemList.get(position).fromStation);
        setTextString(view, R.id.to_station, itemList.get(position).toStation);
        setTextString(view, R.id.cost_time, itemList.get(position).costTime);
        setTextString(view, R.id.train_code, itemList.get(position).trainCode);

        return view;
    }

    private void setTextString(View rootView, int resId, String text) {
        ((TextView)rootView.findViewById(resId)).setText(text);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface ItemClickListener {
        void onClickItem(int position);
    }

    public static class ViewHolderItem {
        private String trainCode;
        private String trainDate;
        private String startTime;
        private String endTime;
        private String fromStation;
        private String toStation;
        private String costTime;

        public ViewHolderItem(QueryTicketItemModel ticketInfo) {
            this.trainCode = ticketInfo.getTrainCode();
            this.trainDate = ticketInfo.getDate();
            this.startTime = ticketInfo.getStartTime();
            this.endTime = ticketInfo.getArrivalTime();
            this.fromStation = ticketInfo.getFromStation();
            this.toStation = ticketInfo.getToStation();
            this.costTime = ticketInfo.getDistanceTime();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != ViewHolderItem.class) {
                return false;
            }

            if (this.trainCode.equals(((ViewHolderItem)obj).trainCode)
                    && this.trainDate.equals(((ViewHolderItem)obj).trainDate)) {
                return true;
            }

            return false;
        }
    }
}
