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
import com.bgylde.ticket.utils.ConfigPreference;
import com.bgylde.ticket.utils.DialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AbstractActivity {

    private Button login;
    private Button noLogin;

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

        ConfigPreference configPreference = new ConfigPreference(this);
        username.setText(configPreference.getUsername());
        userpasswd.setText(configPreference.getUserPasswd());
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
        login = (Button) findViewById(R.id.login);
        noLogin = (Button) findViewById(R.id.no_login);
        identifyView = (IdentifyView) findViewById(R.id.identify_code);

        username = (EditText) findViewById(R.id.username);
        userpasswd = (EditText) findViewById(R.id.password);
    }

    @Override
    protected void initListener() {
        login.setOnClickListener(listener);
        noLogin.setOnClickListener(listener);

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusCarrier event) {
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

    private void startQueryActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainQueryActivity.class);
        startActivity(intent);
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.login:
                    serviceUtils.login(username.getText().toString(), userpasswd.getText().toString());
                    break;
                case R.id.no_login:
                    startQueryActivity();
                    break;
            }
        }
    };
}