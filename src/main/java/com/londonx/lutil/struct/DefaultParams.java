package com.londonx.lutil.struct;

import java.util.HashMap;

/**
 * Created by London on 16/4/7.
 * default params of HashMap
 */
public class DefaultParams extends HashMap<String, Object> {
    @Override
    public Object put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
