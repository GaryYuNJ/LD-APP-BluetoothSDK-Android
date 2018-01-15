/**  
 * @Title:  TSLBleUtilsImpl.java   
 * @Package com.example.ldsdk_xh.interfaces.impl   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: wangjy  
 * @date:   2017年11月9日 下午5:54:27   
 * @version V1.0 
 */  
package com.example.ldsdk_xh.interfaces.impl.tsl;

import com.example.ldsdk_xh.interfaces.IBleUtils;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

/**   
 * @ClassName:  TSLBleUtilsImpl   
 * @Description:特斯联蓝牙的判断方法
 * @author: wangjy
 * @date:   2017年11月9日 下午5:54:27   
 *      
 */
public class TSLBleUtilsImpl implements IBleUtils{
	
    private Activity activity;
    
	public TSLBleUtilsImpl(Activity activity) {
		this.activity = activity;
	}
	/**   
	 * <p>Title: isVenderDevice</p>   
	 * <p>Description: 是否是自己厂商的设备</p>   
	 * @param device
	 * @param scanRecord
	 * @return   
	 * @see com.example.ldsdk_xh.interfaces.IBleUtils#isVenderDevice(android.bluetooth.BluetoothDevice, byte[])   
	 */  
	@Override
	public boolean isVenderDevice(BluetoothDevice device, byte[] scanRecord) {
		return false;
		//boolean result = Utils.isTerminusDevice(activity, device.getAddress());
		//return device != null && Utils.isTerminusDevice(activity, device.getAddress());
	}

	/**   
	 * <p>Title: getDeviceMAC</p>   
	 * <p>Description: 得到厂商自己的mac</p>   
	 * @param device
	 * @param scanRecord
	 * @return   
	 * @see com.example.ldsdk_xh.interfaces.IBleUtils#getDeviceMAC(android.bluetooth.BluetoothDevice, byte[])   
	 */  
	@Override
	public String getDeviceMAC(BluetoothDevice device, byte[] scanRecord) {
		//if (isVenderDevice(device, scanRecord) &&  device.getAddress() != null) {
			return device.getAddress().replaceAll(":", "");
	/*	}
		return null;*/
	}

}
