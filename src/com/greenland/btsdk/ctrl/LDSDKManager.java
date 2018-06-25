package com.greenland.btsdk.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.greenland.btsdk.bean.ResourceData;
import com.greenland.btsdk.bean.ResourceKey;
import com.greenland.btsdk.callback.FetchKeysCallback;
import com.greenland.btsdk.model.Content;
import com.greenland.btsdk.model.DeviceInfo;
import com.greenland.btsdk.model.WebReturnData;
import com.greenland.btsdk.utils.DeviceConstant;

import android.app.Activity;
import android.content.Context;

public class LDSDKManager {
	
	private Activity activity;
	private static OperateDevice operateDevice;
	public LDSDKManager(Activity activity) {
		this.activity = activity;
	}
	
	public void getAllKeys(Integer buildingId, final String mobile,final FetchKeysCallback callback){
		final Integer bid = buildingId;
		final String m = mobile;
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, Object> datas = DeviceResourceDataDispose.cacheDeviceData(activity, bid, m);
				boolean isSuccess = (Boolean) datas.get("isSuccess");
				boolean isFresh = (Boolean) datas.get("isFresh");
				String json = (String) datas.get("devices");
				Gson gson = new Gson();
				WebReturnData data = gson.fromJson(json, WebReturnData.class);
				if (data == null) {
					callback.onFetched(false, false, null);
					return;
				}
				List<Content> content = data.getContent();
				Map<Integer, List<DeviceInfo>> map = new HashMap<Integer, List<DeviceInfo>>();
				for (Content c : content) {
					List<ResourceData> resourceDatas = c.getResourceDatas();
					for (ResourceData resourceData : resourceDatas) {
						DeviceInfo deviceInfo = new DeviceInfo();
						Integer deviceId = resourceData.getId();
						deviceInfo.setId(deviceId);
						deviceInfo.setName(resourceData.getName());
						deviceInfo.setTypeId(resourceData.getDeviceType());
						deviceInfo.setTypeName(resourceData.getTypeName());
						deviceInfo.setBuildingId(resourceData.getBuildingId());
						deviceInfo.setBuildingName(resourceData.getBuildingName());
						deviceInfo.setKeys(resourceData.getResourceKeys());
						List<DeviceInfo> deviceInfos = map.get(deviceInfo.getBuildingId());
						if (deviceInfos == null) {
							deviceInfos = new ArrayList<DeviceInfo>();
						}
						deviceInfos.add(deviceInfo);
						map.put(deviceInfo.getBuildingId(), deviceInfos);
					}
				}
				final boolean isS = isSuccess;
				final boolean isF = isFresh;
				final Map<Integer, List<DeviceInfo>> m = map;
				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						callback.onFetched(isS, isF, m);
					}
				});
			}
		}).start();
	}
	/**
	 * 根据手机号删除
	 * @param mobile
	 * @return
	 */
	public static boolean cleanCacheDeviceData(Context context){
		return DeviceResourceDataDispose.cleanCacheDeviceData(context);
	}
	public static OperateDevice getOperateDevice (Activity activity) {
		if (operateDevice == null) {
			synchronized(LDSDKManager.class) {
				if (operateDevice == null) {
					operateDevice = new OperateDevice(activity);
				}
			}
		}
		return operateDevice;
	}
}
