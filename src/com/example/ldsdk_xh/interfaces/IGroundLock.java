/**  
 * @Title:  IGroundLock.java   
 * @Package com.example.ldsdk_xh.interfaces   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: wangjy  
 * @date:   2017年12月29日 上午10:29:09   
 * @version V1.0 
 */  
package com.example.ldsdk_xh.interfaces;

import com.example.ldsdk_xh.callback.ResultCallback;

/**   
 * @ClassName:  IGroundLock   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: wangjy
 * @date:   2017年12月29日 上午10:29:09   
 *      
 */
public interface IGroundLock {
	/**
	 * <p>各厂商门操作类   各厂商要实现此接口</p>
	 * @param mac 设备mac地址
	 * @param time 延时时间
	 * @param password 开门密码
	 * @param timeout 超时时间
	 * @param resultCallback 回调函数 
	 * @return int   开门状态   
	 */
	int openGroundLock(String mac,int time,String password,int timeout,ResultCallback resultCallback);
}
