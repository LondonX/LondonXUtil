package com.londonx.lutil.impl;

import com.londonx.lutil.entity.LResponse;

/**
 * Created by London on 16/4/7.
 * Response listener for {@link com.londonx.lutil.util.LRequestTool}
 */
public interface OnResponseListener {
    void onResponse(LResponse response);
}
