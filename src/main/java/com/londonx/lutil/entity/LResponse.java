package com.londonx.lutil.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by london on 15/6/2.
 * LResponse
 */
public class LResponse extends LEntity {
    public int requestCode;
    public int responseCode;
    public String url;
    @NonNull
    public String body = "";
    @Nullable
    public File downloadFile;
}
