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
import android.widget.ImageView;

import com.bgylde.ticket.R;

/**
 * Created by wangyan on 2019/1/7
 */
public class DialogUtils {

    public static void showDialog(final Context context, final Bitmap bitmap) {
        runInUiThread(new Runnable() {
            @Override
            public void run() {
                requestPermission(context, bitmap);
            }
        });
    }

    private static void readShowDialog(Context context, Bitmap bitmap) {
        View view = View.inflate(context, R.layout.identify_code, null);
        ImageView imageView = view.findViewById(R.id.dialog_identify_code);
        imageView.setImageBitmap(bitmap);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        //dialog.setCancelable(false);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    private static void requestPermission(Context context, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
            } else {
                readShowDialog(context, bitmap);
            }
        } else {
            readShowDialog(context, bitmap);
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
}
