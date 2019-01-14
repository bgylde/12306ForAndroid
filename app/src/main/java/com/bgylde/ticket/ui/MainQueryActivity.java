package com.bgylde.ticket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bgylde.ticket.MainApplication;
import com.bgylde.ticket.R;
import com.bgylde.ticket.request.model.QueryTicketItemModel;
import com.bgylde.ticket.request.model.QueryTicketsResponse;
import com.bgylde.ticket.request.utils.RequestManaager;
import com.bgylde.ticket.ui.model.RecyclerViewAdapter;
import com.bgylde.ticket.utils.DialogUtils;
import com.bgylde.ticket.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangyan on 2019/1/14
 */
public class MainQueryActivity extends AbstractActivity {

    private static final String TAG = "MainQueryActivity";
    private String username;

    private TextView userText;

    private TextView process_info;

    private EditText from_station_input;
    private EditText to_station_input;
    private EditText order_date_input;

    private Button query;
    private Button query_start;

    private RecyclerView ticket_list;

    private RecyclerViewAdapter adapter;

    private List<QueryTicketItemModel> ticketItemModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    @Override
    protected void onResume() {
        super.onResume();
        userText.setText("当前用户：" + username);

        from_station_input.setText("北京");
        to_station_input.setText("太原");

        order_date_input.setText("2019-02-01");
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activyt_query;
    }

    @Override
    protected void initView() {
        userText = (TextView) findViewById(R.id.user);
        from_station_input = (EditText) findViewById(R.id.from_station_input);
        to_station_input = (EditText) findViewById(R.id.to_station_input);
        order_date_input = (EditText) findViewById(R.id.order_date_input);
        query = (Button) findViewById(R.id.query);
        query_start = (Button) findViewById(R.id.quert_start);
        process_info = (TextView) findViewById(R.id.process_info);
        ticket_list = (RecyclerView) findViewById(R.id.ticket_list);

        ticket_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ticketItemModels = new ArrayList<>();
        adapter = new RecyclerViewAdapter(ticketItemModels);
        ticket_list.setItemAnimator(new DefaultItemAnimator());
        ticket_list.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        query_start.setOnClickListener(listener);
        query.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.query:
                    queryTickets("2019-02-01", "BXP", "TNV");
                    break;
                case R.id.quert_start:
                    break;
            }
        }
    };

    private void queryTickets(final String data, String fromStation, String toStation) {
        RequestManaager.getInstance().sendQueryTickets(
            this, data, fromStation, toStation, new Callback<QueryTicketsResponse>() {
                @Override
                public void onResponse(@NonNull Call<QueryTicketsResponse> call, @NonNull Response<QueryTicketsResponse> response) {
                    QueryTicketsResponse ticketsResponse = response.body();
                    if (ticketsResponse == null || ticketsResponse.getData() == null) {
                        DialogUtils.showToast(MainApplication.getApplication(), "查询为空，可能IP被封，请重试！");
                        return;
                    }

                    List<String> list = ticketsResponse.getData().getResult();
                    if (list == null || list.size() == 0) {
                        process_info.setText("查询为空，可能IP被封，请重试！");
                        DialogUtils.showToast(MainApplication.getApplication(), "查询为空，可能IP被封，请重试！");
                        return;
                    }

                    for (String itemStr : list) {
                        QueryTicketItemModel model = new QueryTicketItemModel(itemStr, data);
                        ticketItemModels.add(model);
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<QueryTicketsResponse> call, @NonNull Throwable t) {
                    LogUtils.e(TAG, t);
                }
            });
    }
}
