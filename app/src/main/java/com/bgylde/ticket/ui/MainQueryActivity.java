package com.bgylde.ticket.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bgylde.ticket.MainApplication;
import com.bgylde.ticket.R;
import com.bgylde.ticket.request.model.QueryTicketItemModel;
import com.bgylde.ticket.request.model.QueryTicketsResponse;
import com.bgylde.ticket.request.utils.RequestManaager;
import com.bgylde.ticket.ui.model.DrawerAdapter;
import com.bgylde.ticket.ui.model.RecyclerViewAdapter;
import com.bgylde.ticket.utils.ConfigPreference;
import com.bgylde.ticket.utils.DialogUtils;
import com.bgylde.ticket.utils.LogUtils;
import com.bgylde.ticket.utils.StringUtils;

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
    private TextView processInfo;

    private EditText fromStationInput;
    private EditText toStationInput;
    private EditText orderDateInput;

    private Button query;
    private Button queryStart;
    private Button ticketsMarket;

    private RecyclerView ticket_list;
    private RecyclerViewAdapter adapter;
    private List<QueryTicketItemModel> ticketItemModels;

    private ListView drawerListView;
    private DrawerAdapter drawerAdapter;
    private DrawerLayout drawerLayout;
    private List<QueryTicketItemModel> drawerList;

    private int pointCount = 0;
    private FrameLayout viewGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        viewGroup = createAnimationLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isNotBlank(username)) {
            userText.setText("未登录");
        } else {
            userText.setText(username);
        }

        ConfigPreference configPreference = new ConfigPreference(this);
        fromStationInput.setText(configPreference.getFromStation());
        toStationInput.setText(configPreference.getToStation());
        orderDateInput.setText(configPreference.getQueryDate());

        query.requestFocus();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activyt_query;
    }

    @Override
    protected void initView() {
        userText = (TextView) findViewById(R.id.user);
        fromStationInput = (EditText) findViewById(R.id.from_station_input);
        toStationInput = (EditText) findViewById(R.id.to_station_input);
        orderDateInput = (EditText) findViewById(R.id.order_date_input);
        query = (Button) findViewById(R.id.query);
        queryStart = (Button) findViewById(R.id.quert_start);
        ticketsMarket = (Button) findViewById(R.id.market);
        processInfo = (TextView) findViewById(R.id.process_info);
        ticket_list = (RecyclerView) findViewById(R.id.ticket_list);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.market_tickets);

        drawerList = new ArrayList<>();
        drawerAdapter = new DrawerAdapter(this, drawerList);
        drawerAdapter.setItemClickListener(new DrawerAdapter.ItemClickListener() {
            @Override
            public void onClickItem(int position) {
                LogUtils.d("wy", "click item: " + position);
                drawerList.remove(position);
                ticketItemModels.get(position).setSelect(false);
                drawerAdapter.notifyDataSetChanged();
            }
        });
        drawerListView.setAdapter(drawerAdapter);
        drawerLayout.setDrawerListener(drawerListener);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, manager.getOrientation());
        ticket_list.setLayoutManager(manager);
        ticket_list.addItemDecoration(itemDecoration);
        ticketItemModels = new ArrayList<>();
        adapter = new RecyclerViewAdapter(ticketItemModels);
        adapter.setOnItemClickListener(itemClickListener);
        ticket_list.setItemAnimator(new DefaultItemAnimator());
        ticket_list.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        queryStart.setOnClickListener(listener);
        query.setOnClickListener(listener);
        ticketsMarket.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.query:
                    String date = orderDateInput.getText().toString();
                    String fromStation = fromStationInput.getText().toString();
                    String toStation = toStationInput.getText().toString();
                    if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(fromStation) && StringUtils.isNotBlank(toStation)) {
                        ConfigPreference.updateQueryInfo(MainQueryActivity.this, date, fromStation, toStation);
                        queryTickets("2019-02-01", "BXP", "TNV");
                    }
                    break;
                case R.id.quert_start:
                    break;
                case R.id.market:
                    if (drawerList.size() > 0) {
                        drawerAdapter.notifyDataSetChanged();
                        drawerLayout.openDrawer(drawerListView);
                    }
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
                        DialogUtils.showToast(MainApplication.getApplication(), getResources().getString(R.string.query_fail_info));
                        return;
                    }

                    List<String> list = ticketsResponse.getData().getResult();
                    if (list == null || list.size() == 0) {
                        processInfo.setText(getResources().getString(R.string.query_fail_info));
                        DialogUtils.showToast(MainApplication.getApplication(), getResources().getString(R.string.query_fail_info));
                        return;
                    }

                    ticketItemModels.clear();
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

    public interface ItemClickListener {
        void onClick(int position, float rawX, float rawY);
    }

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onClick(int position, float rawX, float rawY) {
            ticketItemModels.get(position).setSelect(true);
            adapter.notifyItemChanged(position);
            if (!drawerList.contains(ticketItemModels.get(position))) {
                drawerList.add(ticketItemModels.get(position));
                startClickAnimation((int)rawX, (int)rawY);
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void startClickAnimation(int startX, int startY) {
        ImageView viewImage = new ImageView(this);
        viewImage.setImageResource(R.drawable.shape_point);
        View view = addViewToAnimationLayout(viewGroup, viewImage, startX, startY);
        view.setAlpha(0.6f);

        Animation scaleAnimation = new ScaleAnimation(1.5f, 0.0f, 1.5f, 0.0f, Animation.RELATIVE_TO_SELF, 0.1f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        float endX = ticketsMarket.getX() - startX + ticketsMarket.getWidth() / 2;
        float endY = ticketsMarket.getY() - startY + ticketsMarket.getHeight() / 2;
        Animation translateAnimation = new TranslateAnimation(0, endX, 0, endY);
        Animation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
        rotateAnimation.setDuration(1000);
        translateAnimation.setDuration(1000);

        AnimationSet animatorSet = new AnimationSet(true);
        animatorSet.setFillAfter(true);
        animatorSet.addAnimation(rotateAnimation);
        animatorSet.addAnimation(scaleAnimation);
        animatorSet.addAnimation(translateAnimation);
        animatorSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                pointCount++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (--pointCount <= 0) {
                    viewGroup.removeAllViews();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animatorSet);
    }

    private FrameLayout createAnimationLayout() {
        ViewGroup rootView = (ViewGroup) window.getDecorView();
        FrameLayout animationLayout = new FrameLayout(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        animationLayout.setLayoutParams(lp);
        animationLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animationLayout);

        return animationLayout;
    }

    private View addViewToAnimationLayout(ViewGroup viewGroup, View view, int x, int y) {
        viewGroup.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(90, 90);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }

    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View view, float v) {

        }

        @Override
        public void onDrawerOpened(@NonNull View view) {
        }

        @Override
        public void onDrawerClosed(@NonNull View view) {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onDrawerStateChanged(int i) {

        }
    };
}
