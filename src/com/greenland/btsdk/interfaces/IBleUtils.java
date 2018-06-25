package com.greenland.btsdk.interfaces;

import android.bluetooth.BluetoothDevice;

/**   
 * <p>各厂商关于蓝牙的工具   各厂商要实现此接口</p>
 * @author wangjy
 * @version 1.0     
 */
public interface IBleUtils {
	
	/**
	 * <p>是否为各自厂商的设备,各厂商应实现判断自己厂商的方法</p>
	 * @param device 
	 * @param scanRecord
	 * @return boolean      
	 */
	boolean isVenderDevice(final BluetoothDevice device,final byte[] scanRecord);
	
	/**
	 * 
	 * <p>得到各自厂商的mac地址</p>   
	 * @param device 蓝牙设备BluetoothDevice对象
	 * @param scanRecord 
	 * @return String 返回自己厂商的mac地址     
	 */
	String getDeviceMAC(final BluetoothDevice device,final byte[] scanRecord);
}
