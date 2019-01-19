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
    private List<QueryTicketItemModel> itemList;
    private ItemClickListener itemClickListener;

    public DrawerAdapter(Context context, List<QueryTicketItemModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public QueryTicketItemModel getItem(int position) {
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

        setTextString(view, R.id.train_date, itemList.get(position).getDate());
        setTextString(view, R.id.start_time, itemList.get(position).getStartTime());
        setTextString(view, R.id.end_time, itemList.get(position).getArrivalTime());
        setTextString(view, R.id.from_station, itemList.get(position).getFromStationName());
        setTextString(view, R.id.to_station, itemList.get(position).getToStationName());
        setTextString(view, R.id.cost_time, itemList.get(position).getDistanceTime());
        setTextString(view, R.id.train_code, itemList.get(position).getTrainCode());

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
}
