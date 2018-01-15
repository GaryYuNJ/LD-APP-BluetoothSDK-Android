package com.example.ldsdk_xh.callback;

import com.example.ldsdk_xh.model.DeviceInfo;

/**
 * <p>搜索蓝牙的回调函数  </p> 
 * @author wangjy
 * @version 1.0
 */
public interface SearchBleCallback {
	
	/**
	 * <p>每搜索到一个蓝牙都会调用此方法 </p> 
	 * @param deviceInfo  设备信息
	 */
	void onSearchBle(DeviceInfo deviceInfo);
	
	/**
	 * <p>停止搜索时调用   </p> 
	 */
	void stopSearchBle();
}
