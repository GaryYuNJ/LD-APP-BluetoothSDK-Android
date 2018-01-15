package com.example.ldsdk_xh.interfaces.impl.lf;
import com.example.ldsdk_xh.interfaces.IBleUtils;
import com.example.ldsdk_xh.utils.ByteUtil;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

/**   
 * @ClassName:  LFBleUtils   
 * @Description: 
 * @author: wangjy
 * @date:   2017�?10�?25�? 上午10:03:45   
 *      
 */
public class LFBleUtilsImpl implements IBleUtils{
	
    private static final int RAWDATALEN = 27;
    private Activity activity;
    
	public LFBleUtilsImpl(Activity activity) {
		super();
		this.activity = activity;
	}

	/**   
	 * <p>Title: isVenderDevice</p>   
	 * <p>Description: 是否为立方的设备</p>   
	 * @param device
	 * @param scanRecord
	 * @return   
	 * @see com.example.ldsdk_xh.interfaces.IBleUtils#isVenderDevice(android.bluetooth.BluetoothDevice, byte[])   
	 */  
	@Override
	public boolean isVenderDevice(BluetoothDevice device, byte[] scanRecord) {
        if( device == null || scanRecord.length < RAWDATALEN ||
                (scanRecord[12] != (byte)0xE6 && scanRecord[12] != (byte)0xEB) || scanRecord[13] != (byte)0xFD) {
            return false;
        }else{
            return true;
        }
	}

	/**   
	 * <p>Title: getDeviceMAC</p>   
	 * <p>Description: 得到立方的mac</p>   
	 * @param device
	 * @param scanRecord
	 * @return   
	 * @see com.example.ldsdk_xh.interfaces.IBleUtils#getDeviceMAC(android.bluetooth.BluetoothDevice, byte[])   
	 */  
	@Override
	public String getDeviceMAC(BluetoothDevice device, byte[] scanRecord) {
        try {
            if (isVenderDevice(device, scanRecord)) {
                byte[] mac = new byte[9];
                System.arraycopy(scanRecord, 18, mac, 0, 9);
                return ByteUtil.byte2HexString(mac);
            }
        }catch (Exception e){
            return null;
        }
        return null;
	}

}
