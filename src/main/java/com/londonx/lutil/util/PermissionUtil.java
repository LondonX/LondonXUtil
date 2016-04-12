package com.londonx.lutil.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by london on 15/11/16.
 * Permission checker for >api23
 */
public class PermissionUtil {
    public static final int REQUEST_PERMISSIONS = 0x52;
    private static Context context;
    private static String[] permissions;

    /**
     * init permission checker by given permissions
     * to use App permissions, plz see {@link #init(Context)}
     *
     * @param context     app context
     * @param permissions permissions in {@link Manifest.permission}
     */
    public static void init(Context context, String[] permissions) {
        PermissionUtil.context = context;
        PermissionUtil.permissions = permissions;
    }

    /**
     * init permission checker by app permissions
     * to specific the permissions, plz see {@link #init(Context, String[])}
     *
     * @param context app context
     */
    public static void init(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            throw new NullPointerException("Cannot get packageInfo !!!");
        }
        String[] requestedPermissions = packageInfo.requestedPermissions;
        init(context, requestedPermissions);
    }

    public static boolean isAllPermissionAllowed() {
        for (String p : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, p);
            boolean isAllowed = permissionCheck == PackageManager.PERMISSION_GRANTED;
            if (!isAllowed) {
                return false;
            }
        }
        return true;
    }

    public static void request(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                permissions,
                REQUEST_PERMISSIONS);
    }

    public static void request(Fragment fragment) {
        request(fragment.getActivity());
    }
}
