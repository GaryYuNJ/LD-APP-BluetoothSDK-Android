package com.example.ldsdk_xh.interfaces;

import com.example.ldsdk_xh.callback.ResultCallback;

/**   
 * <p>各厂商升降电梯操作接口   各厂商要实现此接口</p>
 * @author wangjy
 * @version 1.0     
 */
public interface IElevator {
	
	/**
	 * <p>控制升降电梯</p>
	 * @param mac 设备mac地址
	 * @param password 密码
	 * @param phone 手机号
	 * @param floor 楼层
	 * @param timeout 超时时间
	 * @param resultCallback 回调函数
	 * @return 操作状态
	 */
	int ctrlElevator(String mac, String password, String phone, int floor, int timeout,ResultCallback resultCallback);
}
