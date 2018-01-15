package com.example.ldsdk_xh.interfaces;

import com.example.ldsdk_xh.callback.ResultCallback;

/**   
 * <p>各厂商门操作接口   各厂商要实现此接口</p>
 * @author wangjy
 * @version 1.0     
 */
public interface IDoor {
	
	/**
	 * <p>各厂商门操作类   各厂商要实现此接口</p>
	 * @param mac 设备mac地址
	 * @param time 延时时间
	 * @param password 开门密码
	 * @param timeout 超时时间
	 * @param resultCallback 回调函数 
	 * @return int   开门状态   
	 */
	int openDoor(String mac,int time,String password,int timeout,ResultCallback resultCallback);
}
