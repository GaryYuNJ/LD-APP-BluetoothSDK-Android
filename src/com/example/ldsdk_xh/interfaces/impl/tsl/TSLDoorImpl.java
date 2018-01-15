/**  
 * @Title:  TSLDoorImpl.java   
 * @Package com.example.ldsdk_xh.interfaces.impl   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: wangjy  
 * @date:   2017年11月8日 下午2:00:00   
 * @version V1.0 
 */  
package com.example.ldsdk_xh.interfaces.impl.tsl;

import com.example.ldsdk_xh.callback.ResultCallback;
import com.example.ldsdk_xh.interfaces.IDoor;
import com.example.ldsdk_xh.utils.Constant;
import com.terminus.lock.library.CallBack;
import com.terminus.lock.library.Response;
import com.terminus.lock.library.TslBluetoothManager;
import com.terminus.lock.library.util.Utils;

import android.app.Activity;
import android.app.Service;

/**   
 * @ClassName:  TSLDoorImpl   
 * @Description:特斯联实现
 * @author: wangjy
 * @date:   2017年11月8日 下午2:00:00   
 *      
 */
public class TSLDoorImpl implements IDoor{
	
	private TslBluetoothManager mTslBluetoothManager; // 特斯联蓝牙操作类
	
	private Activity activity;
	
	
	public TSLDoorImpl(Activity activity) {
		this.activity = activity;
	}

	private class myCallback implements CallBack{
		
		private ResultCallback resultCallback;
		private String mac;
		
		public myCallback(ResultCallback resultCallback,String mac) {
			this.resultCallback = resultCallback;
			this.mac = mac;
		}
		
		/**   
		 * <p>Title: onFail</p>   
		 * <p>Description: 失败</p>   
		 * @param arg0   
		 * @see com.terminus.lock.library.CallBack#onFail(int)   
		 */  
		@Override
		public void onFail(int result) {
            switch (result) {
            case Response.ERROR_CONN_TIMEOUT:// 连接失败
            	resultCallback.onResult(mac.getBytes(), Constant.TIMEOUT);
                break;
            case Response.ERROR_BLUETOOTH_IS_NOT_ENABLED: // 蓝牙死机
            	resultCallback.onResult(mac.getBytes(), Constant.ERROR_BLUETOOTH_IS_NOT_ENABLED);
                break;
            case Response.ERROR_OPENED:// 操作成功
            	resultCallback.onResult(mac.getBytes(), Constant.OPEN_SUCCESS);
                break;
            case Response.ERROR_OPEN_LOCK_KEY_DISABLE:
            	resultCallback.onResult(mac.getBytes(), Constant.ERROR_OPEN_LOCK_KEY_DISABLE);
                break;
            case Response.ERROR_OPEN_LOCK_KEY_DELETED:
            	resultCallback.onResult(mac.getBytes(), Constant.ERROR_OPEN_LOCK_KEY_DELETED);
                break;
            case Response.ERROR_DEVICE_ADDRESS_ILLEGAL:
            	resultCallback.onResult(mac.getBytes(), Constant.ERROR_DEVICE_ADDRESS_ILLEGAL);
                break;
            case Response.ERROR_PASSWORD:
            	resultCallback.onResult(mac.getBytes(), Constant.PASSWD_ERROR);
                break;
            case Response.ERROR_UNSUPPORT_OPERATOR:
            	resultCallback.onResult(mac.getBytes(), Constant.ERROR_UNSUPPORT_OPERATOR);
                break;
        }
		}

		/**   
		 * <p>Title: onSuccess</p>   
		 * <p>Description: 成功</p>   
		 * @param arg0   
		 * @see com.terminus.lock.library.CallBack#onSuccess(com.terminus.lock.library.Response)   
		 */  
		@Override
		public void onSuccess(Response response) {
			resultCallback.onResult(mac.getBytes(), Constant.OPEN_SUCCESS);
		}
		
	}

	/**   
	 * <p>Title: openDoor</p>   
	 * <p>Description: </p>   
	 * @param mac
	 * @param time
	 * @param password
	 * @param timeout
	 * @param resultCallback
	 * @return   
	 * @see com.example.ldsdk_xh.interfaces.IDoor#openDoor(java.lang.String, int, java.lang.String, int, com.example.ldsdk_xh.callback.ResultCallback)   
	 */  
	@Override
	public int openDoor(String mac, int time, String password, int timeout, ResultCallback resultCallback) {
		mTslBluetoothManager = TslBluetoothManager.getInstance(activity);
        String resultCipher = null;
        try {
            resultCipher = Utils.decodeCipher(password);
        } catch (Exception e) {
        	resultCallback.onResult(mac.getBytes(), Constant.PASSWD_ERROR);
        }
		mTslBluetoothManager.openRemoteDoor(resultCipher, "蓝牙开锁", new myCallback(resultCallback, mac));
		return 0;
	}
	

}
