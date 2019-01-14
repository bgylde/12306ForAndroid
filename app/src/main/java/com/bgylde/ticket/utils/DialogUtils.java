package com.bgylde.ticket.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bgylde.ticket.R;
import com.bgylde.ticket.request.utils.RequestManaager;
import com.bgylde.ticket.service.RequestThread;
import com.bgylde.ticket.ui.IdentifyView;

/**
 * Created by wangyan on 2019/1/7
 */
public class DialogUtils {

    private static final String TAG = "DialogUtils";

    public static void showDialog(final Context context, final Bitmap bitmap, final RequestThread.HandleMessage handleMessage) {
        runInUiThread(new Runnable() {
            @Override
            public void run() {
                requestPermission(context, bitmap, handleMessage);
            }
        });
    }

    private static void readShowDialog(final Context context, Bitmap bitmap, final RequestThread.HandleMessage handleMessage) {
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
                handleMessage.checkIdentifyCode(imageView.getIdentifyPoint());
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

    private static void requestPermission(Context context, Bitmap bitmap, RequestThread.HandleMessage handleMessage) {
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
}
