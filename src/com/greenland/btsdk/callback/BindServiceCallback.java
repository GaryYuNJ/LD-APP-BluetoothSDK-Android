package com.greenland.btsdk.callback;

import java.util.Map;

import android.app.Service;
/**
 * <p>�����г���service�Ļص�����  </p> 
 * @author wangjy
 * @version 1.0
 */
public interface BindServiceCallback {
	
	/**
	 * <p>��һ�����ô˷��� </p> 
	 * @param serviceMap  ���ǳ���id ֵ�Ƕ�Ӧ��service    
	 */
	void onBind(Map<String, Service> serviceMap);
}
