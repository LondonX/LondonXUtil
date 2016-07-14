package com.londonx.lutil.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.londonx.lutil.R;
import com.londonx.lutil.entity.LResponse;

/**
 * Created by london on 15/6/2.
 * ToastUtil, init() before use.
 * 2016-07-14 17:38:55 add #showInCenter() method
 */
public class ToastUtil {
    private static Context context;
    private static Toast toast;

    public static void init(Context context) {
        ToastUtil.context = context;
        toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        toast.show();
        toast.cancel();
    }

    public static void showInCenter() {
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    public static void show(int resId) {
        show(context.getString(resId));
    }

    public static void show(String str) {
        if (toast == null) {
            throw new NullPointerException(
                    "Call Lutil.init(Context) or ToastUtil.init(Context) first !!!");
        }
        toast.setText(str);
        toast.show();
    }

    /**
     * show server error code
     *
     * @param responseCode response code
     * @deprecated avoid using is directly, use {@code serverErr(LResponse response)} instead
     */
    @Deprecated
    public static void serverErr(int responseCode) {
        show(context.getString(R.string.server_error_) + responseCode);
    }

    public static void serverErr(LResponse response) {
        if (response.responseCode == 0) {
            show(context.getString(R.string.server_err_0));
            return;
        }
        show(context.getString(R.string.server_error_) + response.responseCode);
    }
}
