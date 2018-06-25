/**  
 * @Title:  TSLBleUtilsImpl.java   
 * @Package com.example.ldsdk_xh.interfaces.impl   
 * @Description:    TODO(��һ�仰�������ļ���ʲô)   
 * @author: wangjy  
 * @date:   2017��11��9�� ����5:54:27   
 * @version V1.0 
 */  
package com.greenland.btsdk.interfaces.impl.tsl;

import com.greenland.btsdk.interfaces.IBleUtils;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

/**   
 * @ClassName:  TSLBleUtilsImpl   
 * @Description:��˹���������жϷ���
 * @author: wangjy
 * @date:   2017��11��9�� ����5:54:27   
 *      
 */
public class TSLBleUtilsImpl implements IBleUtils{
	
    private Activity activity;
    
	public TSLBleUtilsImpl(Activity activity) {
		this.activity = activity;
	}
	/**   
	 * <p>Title: isVenderDevice</p>   
	 * <p>Description: �Ƿ����Լ����̵��豸</p>   
	 * @param device
	 * @param scanRecord
	 * @return   
	 * @see com.greenland.btsdk.interfaces.IBleUtils#isVenderDevice(android.bluetooth.BluetoothDevice, byte[])   
	 */  
	@Override
	public boolean isVenderDevice(BluetoothDevice device, byte[] scanRecord) {
		return false;
		//boolean result = Utils.isTerminusDevice(activity, device.getAddress());
		//return device != null && Utils.isTerminusDevice(activity, device.getAddress());
	}

	/**   
	 * <p>Title: getDeviceMAC</p>   
	 * <p>Description: �õ������Լ���mac</p>   
	 * @param device
	 * @param scanRecord
	 * @return   
	 * @see com.greenland.btsdk.interfaces.IBleUtils#getDeviceMAC(android.bluetooth.BluetoothDevice, byte[])   
	 */  
	@Override
	public String getDeviceMAC(BluetoothDevice device, byte[] scanRecord) {
		//if (isVenderDevice(device, scanRecord) &&  device.getAddress() != null) {
			return device.getAddress().replaceAll(":", "");
	/*	}
		return null;*/
	}

}
