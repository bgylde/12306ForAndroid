package com.bgylde.ticket.ui.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bgylde.ticket.R;
import com.bgylde.ticket.request.model.QueryTicketItemModel;
import com.bgylde.ticket.utils.StringUtils;

import java.util.List;

/**
 * Created by wangyan on 2019/1/14
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<QueryTicketItemModel> ticketList;

    public RecyclerViewAdapter(List<QueryTicketItemModel> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tickets_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        QueryTicketItemModel model = ticketList.get(position);

        viewHolder.stationId.setText(model.getTrainCode());
        viewHolder.fromStation.setText(StringUtils.formatString("始发站：%s[%s]", model.getFromStation(), model.getStartTime()));
        viewHolder.toStation.setText(StringUtils.formatString("终到站：%s[%s]", model.getToStation(), model.getArrivalTime()));
        if ("IS_TIME_NOT_BUY".equals(model.getResult())) {
            changeViewVisible(viewHolder, View.GONE);
            viewHolder.bussinessSeat.setTextColor(0xFFAA0000);
            viewHolder.bussinessSeat.setText("列车运行图调整，暂停发售");
        } else {
            changeViewVisible(viewHolder, View.VISIBLE);
            viewHolder.bussinessSeat.setTextColor(0xFF000000);
            viewHolder.duration.setText(StringUtils.formatString("%sh", model.getDistanceTime()));
            viewHolder.bussinessSeat.setText(StringUtils.formatString("商务座：%s", model.getBussinessSeat()));
            viewHolder.superSeat.setText(StringUtils.formatString("特等座：%s", model.getSuperSeat()));
            viewHolder.firstSeat.setText(StringUtils.formatString("一等座：%s", model.getFirstSeat()));
            viewHolder.secondSeat.setText(StringUtils.formatString("二等座：%s" ,model.getSecondSeat()));
            viewHolder.softSeat.setText(StringUtils.formatString("软卧：%s", model.getSoftSeat()));
            viewHolder.hardSeat.setText(StringUtils.formatString("硬卧：%s", model.getHardSeat2()));
            viewHolder.hardSeat2.setText(StringUtils.formatString("硬座：%s", model.getHardSeat2()));
            viewHolder.noSeat.setText(StringUtils.formatString("无座：%s", model.getVoidSeat()));
        }
    }

    private void changeViewVisible(ViewHolder viewHolder, int viewState) {
        viewHolder.duration.setVisibility(viewState);
        viewHolder.superSeat.setVisibility(viewState);
        viewHolder.firstSeat.setVisibility(viewState);
        viewHolder.secondSeat.setVisibility(viewState);
        viewHolder.softSeat.setVisibility(viewState);
        viewHolder.hardSeat.setVisibility(viewState);
        viewHolder.hardSeat2.setVisibility(viewState);
        viewHolder.noSeat.setVisibility(viewState);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView stationId;
        private TextView fromStation;
        private TextView toStation;
        private TextView duration;
        private TextView bussinessSeat;
        private TextView superSeat;
        private TextView firstSeat;
        private TextView secondSeat;
        private TextView softSeat;
        private TextView hardSeat;
        private TextView hardSeat2;
        private TextView noSeat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stationId = itemView.findViewById(R.id.station_id);
            fromStation = itemView.findViewById(R.id.from_station);
            toStation = itemView.findViewById(R.id.to_station);
            duration = itemView.findViewById(R.id.duration);
            bussinessSeat = itemView.findViewById(R.id.bussiness_seat);
            superSeat = itemView.findViewById(R.id.super_seat);
            firstSeat = itemView.findViewById(R.id.first_seat);
            secondSeat = itemView.findViewById(R.id.second_seat);
            softSeat = itemView.findViewById(R.id.soft_seat);
            hardSeat = itemView.findViewById(R.id.hard_seat);
            hardSeat2 = itemView.findViewById(R.id.hard_seat2);
            noSeat = itemView.findViewById(R.id.no_seat);
        }
    }
}
