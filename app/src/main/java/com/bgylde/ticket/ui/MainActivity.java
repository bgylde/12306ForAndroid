package com.bgylde.ticket.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bgylde.ticket.R;
import com.bgylde.ticket.request.model.UserInfoResponse;
import com.bgylde.ticket.service.ServiceManager;
import com.bgylde.ticket.ui.model.EventBusCarrier;
import com.bgylde.ticket.utils.ConfigureManager;
import com.bgylde.ticket.utils.DialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AbstractActivity {

    private Button queryStart;
    private Button queryPause;
    private Button queryStop;

    private IdentifyView identifyView;

    private EditText username;

    private EditText userpasswd;

    private ServiceManager serviceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serviceUtils = new ServiceManager();
        serviceUtils.bindService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceUtils.unBindService(this);
        EventBus.getDefault().unregister(this);
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
        identifyView = (IdentifyView) findViewById(R.id.identify_code);

        username = (EditText) findViewById(R.id.username);
        userpasswd = (EditText) findViewById(R.id.password);
    }

    @Override
    protected void initListener() {
        queryStart.setOnClickListener(listener);
        queryPause.setOnClickListener(listener);
        queryStop.setOnClickListener(listener);

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusCarrier event){
        byte type = event.getEventType();
        Object object = event.getObject();
        switch (type) {
            case EventBusCarrier.IDENTIFY_CODE:
                if (object != null && object instanceof Bitmap) {
                    this.identifyView.setImageBitmap((Bitmap)object);
                }
                break;
            case EventBusCarrier.LOGING_SUCCESSFUL_CODE:
                if (object != null && object instanceof UserInfoResponse) {
                    UserInfoResponse userInfoResponse = (UserInfoResponse) object;
                    DialogUtils.showToast(this, "Welcome user[" + userInfoResponse.getUsername() + "] login.");
                    Intent intent = new Intent();
                    intent.setClass(this, MainQueryActivity.class);
                    intent.putExtra("username", userInfoResponse.getUsername());
                    startActivity(intent);
                }
                break;
        }
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.query_start:
                    ConfigureManager.getInstance().setUsername(username.getText().toString());
                    ConfigureManager.getInstance().setAccountPwd(userpasswd.getText().toString());

                    // serviceUtils.startQuery();   该场景下使用startService更好
                    serviceUtils.startService(MainActivity.this);
                    break;
                case R.id.query_pause:
                    //serviceUtils.pauseQuery();
                    break;
                case R.id.query_stop:
                    serviceUtils.stopService(MainActivity.this);
                    //serviceUtils.stopQuery();
                    break;
            }
        }
    };
}
