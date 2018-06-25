package com.greenland.btsdk.interfaces;

import com.greenland.btsdk.callback.ResultCallback;

public interface IEscalator{
	
	/**
	 * <p>控制自动扶梯上下</p>
	 * @param mac 设备mac地址
	 * @param password 密码
	 * @param phone 手机号
	 * @param upOrDown 上或者下 0:up,1:down
	 * @param timeout 超时时间
	 * @param resultCallback 回调函数
	 * @return 操作状态
	 */
	int ctrlEscalator(String mac,String password,String phone,int upOrDown,int timeout,ResultCallback resultCallback);
}
