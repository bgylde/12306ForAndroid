package com.bgylde.ticket.utils;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bgylde.ticket.R;
import com.bgylde.ticket.service.RequestThread;
import com.bgylde.ticket.ui.IdentifyView;
import com.bgylde.ticket.ui.MainQueryActivity;

/**
 * Created by wangyan on 2019/1/7
 */
public class DialogUtils {

    private static final String TAG = "DialogUtils";

    public static void showDialog(final Context context, final Bitmap bitmap, final Handler handler) {
        runInUiThread(new Runnable() {
            @Override
            public void run() {
                requestPermission(context, bitmap, handler);
            }
        });
    }

    private static void readShowDialog(final Context context, Bitmap bitmap, final Handler handleMessage) {
        View view = View.inflate(context, R.layout.identify_code, null);
        final IdentifyView imageView = view.findViewById(R.id.dialog_identify_code);
        imageView.setImageBitmap(bitmap);

        Button okButton = view.findViewById(R.id.ok);
        Button cancel = view.findViewById(R.id.cancel);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        //dialog.setCancelable(false);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = handleMessage.obtainMessage();
                msg.what = RequestThread.IDENTIFY_CHECK;
                msg.obj = imageView.getIdentifyPoint();
                handleMessage.sendMessage(msg);
                // handleMessage.checkIdentifyCode(imageView.getIdentifyPoint());
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private static void requestPermission(Context context, Bitmap bitmap, Handler handleMessage) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            } else {
                readShowDialog(context, bitmap, handleMessage);
            }
        } else {
            readShowDialog(context, bitmap, handleMessage);
        }
    }

    private static void runInUiThread(Runnable runnable) {
        if (runnable == null) {
            return;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    /**
     * 将字节数组转换为ImageView可调用的Bitmap对象
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null) {
            if (opts != null) {
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            } else {
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }

        return null;
    }

    public static void showToast(final Context context, final String showStr) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            Toast.makeText(context, showStr, Toast.LENGTH_SHORT).show();
        } else {
            runInUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, showStr, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void showNotification(Context context, String state, int progress) {
        Intent  intent = new Intent(context, MainQueryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, PendingIntent.FLAG_UPDATE_CURRENT, intent,0);
        NotificationManager manger = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(state)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setProgress(100, progress, true)
                .build();

        manger.notify(1, notification);
    }
}
