package com.bgylde.ticket.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bgylde.ticket.R;
import com.bgylde.ticket.service.BindServiceUtils;

public class MainActivity extends AbstractActivity {

    private Button queryStart;
    private Button queryPause;
    private Button queryStop;

    private BindServiceUtils serviceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serviceUtils = new BindServiceUtils();
        serviceUtils.bindService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceUtils.unBindService(this);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        queryStart = (Button) findViewById(R.id.query_start);
        queryPause = (Button) findViewById(R.id.query_pause);
        queryStop = (Button) findViewById(R.id.query_stop);
    }

    @Override
    protected void initListener() {
        queryStart.setOnClickListener(listener);
        queryPause.setOnClickListener(listener);
        queryStop.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.query_start:
                    serviceUtils.startQuery();
                    break;
                case R.id.query_pause:
                    serviceUtils.pauseQuery();
                    break;
                case R.id.query_stop:
                    serviceUtils.stopQuery();
                    break;
            }
        }
    };
}
