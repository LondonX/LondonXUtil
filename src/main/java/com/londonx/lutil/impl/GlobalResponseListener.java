package com.londonx.lutil.impl;

import com.londonx.lutil.entity.LResponse;

/**
 * Created by London on 16/4/7.
 * this interface will be called every times when
 * {@link com.londonx.lutil.util.LRequestTool} gets a response
 */
public interface GlobalResponseListener {
    /**
     * called every times when
     * {@link com.londonx.lutil.util.LRequestTool} gets a response
     *
     * and the local {@link OnResponseListener#onResponse(LResponse)} will not be called
     */
    void onGlobalResponse(LResponse response);
}
