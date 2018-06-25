package com.greenland.btsdk.interfaces;

import android.bluetooth.BluetoothDevice;

/**   
 * <p>�����̹��������Ĺ���   ������Ҫʵ�ִ˽ӿ�</p>
 * @author wangjy
 * @version 1.0     
 */
public interface IBleUtils {
	
	/**
	 * <p>�Ƿ�Ϊ���Գ��̵��豸,������Ӧʵ���ж��Լ����̵ķ���</p>
	 * @param device 
	 * @param scanRecord
	 * @return boolean      
	 */
	boolean isVenderDevice(final BluetoothDevice device,final byte[] scanRecord);
	
	/**
	 * 
	 * <p>�õ����Գ��̵�mac��ַ</p>   
	 * @param device �����豸BluetoothDevice����
	 * @param scanRecord 
	 * @return String �����Լ����̵�mac��ַ     
	 */
	String getDeviceMAC(final BluetoothDevice device,final byte[] scanRecord);
}
