package com.greenland.btsdk.callback;

import java.util.List;
import java.util.Map;

import com.greenland.btsdk.model.DeviceInfo;

public interface FetchKeysCallback {
	void onFetched(boolean isSuccess,boolean isFresh,Map<Integer, List<DeviceInfo>> devices);
}
