package com.bgylde.ticket.ui.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bgylde.ticket.R;
import com.bgylde.ticket.request.model.QueryTicketItemModel;
import com.bgylde.ticket.ui.MainQueryActivity;
import com.bgylde.ticket.utils.LogUtils;
import com.bgylde.ticket.utils.StringUtils;

import java.util.List;

/**
 * Created by wangyan on 2019/1/14
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<QueryTicketItemModel> ticketList;

    private MainQueryActivity.ItemClickListener itemClickListener;

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        QueryTicketItemModel model = ticketList.get(viewHolder.getAdapterPosition());

        viewHolder.stationId.setText(model.getTrainCode());
        viewHolder.fromStation.setText(StringUtils.formatString("始发站：%s[%s]", model.getFromStation(), model.getStartTime()));
        viewHolder.toStation.setText(StringUtils.formatString("终到站：%s[%s]", model.getToStation(), model.getArrivalTime()));
        if ("IS_TIME_NOT_BUY".equals(model.getResult())) {
            changeViewVisible(viewHolder, View.GONE);
            viewHolder.bussinessSeat.setTextColor(0xFFAA0000);
            viewHolder.bussinessSeat.setText(model.getStatus());
            viewHolder.layout.setOnTouchListener(null);
        } else {
            changeViewVisible(viewHolder, View.VISIBLE);
            viewHolder.bussinessSeat.setTextColor(0xFF000000);
            viewHolder.duration.setText(StringUtils.formatString(viewHolder.duration.getContext().
                    getString(R.string.dutaion), model.getDistanceTime()));
            setSeatInfo(viewHolder.bussinessSeat, R.string.bussiness_seat, model.getBussinessSeat());
            setSeatInfo(viewHolder.superSeat, R.string.super_seat, model.getSuperSeat());
            setSeatInfo(viewHolder.firstSeat, R.string.first_seat, model.getFirstSeat());
            setSeatInfo(viewHolder.secondSeat, R.string.second_seat,model.getSecondSeat());
            setSeatInfo(viewHolder.softSeat, R.string.soft_seat, model.getSoftSeat());
            setSeatInfo(viewHolder.hardSeat, R.string.hard_seat, model.getHardSeat2());
            setSeatInfo(viewHolder.hardSeat2, R.string.hard_seat2, model.getHardSeat2());
            setSeatInfo(viewHolder.noSeat, R.string.no_seat, model.getVoidSeat());
            LogUtils.d("wy", "isSelect: " + model.isSelect());
            if (model.isSelect()) {
                viewHolder.layout.setBackgroundColor(0x66AA9988);
                viewHolder.layout.setOnTouchListener(null);
            } else {
                viewHolder.layout.setBackgroundColor(0xFFFFFFFF);
                viewHolder.layout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_UP:
                                if (itemClickListener != null) {
                                    itemClickListener.onClick(viewHolder.getAdapterPosition(), event.getRawX(), event.getRawY());
                                }
                                break;
                        }

                        return true;
                    }
                });
            }
        }
    }

    private void setSeatInfo(TextView view, int stringId, String info) {
        view.setText(StringUtils.formatString(view.getContext().getString(stringId), info));
        if (StringUtils.isNotBlank(info) && !"无".equals(info)) {
            view.setTextColor(0xFFFF0000);
        } else {
            view.setTextColor(0xFF000000);
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

    public void setOnItemClickListener(MainQueryActivity.ItemClickListener clickListener) {
        this.itemClickListener = clickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View layout;
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
            layout = itemView.findViewById(R.id.ticket_layout);
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
