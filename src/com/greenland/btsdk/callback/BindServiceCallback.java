package com.greenland.btsdk.callback;

import java.util.Map;

import android.app.Service;
/**
 * <p>绑定所有厂商service的回调函数  </p> 
 * @author wangjy
 * @version 1.0
 */
public interface BindServiceCallback {
	
	/**
	 * <p>绑定一个调用此方法 </p> 
	 * @param serviceMap  键是厂商id 值是对应的service    
	 */
	void onBind(Map<String, Service> serviceMap);
}
