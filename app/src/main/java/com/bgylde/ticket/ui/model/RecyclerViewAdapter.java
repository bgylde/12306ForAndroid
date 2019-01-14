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
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        QueryTicketItemModel model = ticketList.get(position);

        viewHolder.stationId.setText(model.getTrainCode());
        viewHolder.fromStation.setText(model.getFromStation() + "[" + model.getStartTime() + "]");
        viewHolder.toStation.setText(model.getToStation() + "[" + model.getArrivalTime() + "]");
        viewHolder.duration.setText(model.getDistanceTime());
        viewHolder.bussinessSeat.setText(model.getBussinessSeat());
        viewHolder.superSeat.setText(model.getSuperSeat());
        viewHolder.firstSeat.setText(model.getFirstSeat());
        viewHolder.secondSeat.setText(model.getSecondSeat());
        viewHolder.softSeat.setText(model.getSoftSeat());
        viewHolder.hardSeat.setText(model.getHardSeat2());
        viewHolder.hardSeat2.setText(model.getHardSeat2());
        viewHolder.noSeat.setText(model.getVoidSeat());
        viewHolder.date.setText(model.getDate());
        viewHolder.remark.setText("预定");
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
        private TextView date;
        private Button remark;

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
            date = itemView.findViewById(R.id.station_date);
            remark = itemView.findViewById(R.id.remark);
        }
    }
}
