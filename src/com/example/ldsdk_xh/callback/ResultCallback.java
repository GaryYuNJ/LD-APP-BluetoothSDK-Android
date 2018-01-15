package com.example.ldsdk_xh.callback;

/**
 * <p>操作设备的回调函数各厂商都应该使用此回调 </p> 
 * @author wangjy
 * @version 1.0
 */
public interface ResultCallback {
	
	/**
	 * <p>操作设备之后调用此方法</p> 
	 * @param mac mac地址
	 * @param result   操作结果     
	 */
    void onResult(byte[] mac, int result);
}
