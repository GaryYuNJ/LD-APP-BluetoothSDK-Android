/**  
 * @Title:  TSLDoorImpl.java   
 * @Package com.example.ldsdk_xh.interfaces.impl   
 * @Description:    TODO(��һ�仰�������ļ���ʲô)   
 * @author: wangjy  
 * @date:   2017��11��8�� ����2:00:00   
 * @version V1.0 
 */  
package com.greenland.btsdk.interfaces.impl.tsl;

import com.greenland.btsdk.callback.ResultCallback;
import com.greenland.btsdk.interfaces.IDoor;
import com.greenland.btsdk.utils.Constant;
import com.greenland.btsdk.utils.OperateConstant;
import com.terminus.lock.library.CallBack;
import com.terminus.lock.library.Response;
import com.terminus.lock.library.TslBluetoothManager;
import com.terminus.lock.library.util.Utils;

import android.app.Activity;
import android.app.Service;

/**   
 * @ClassName:  TSLDoorImpl   
 * @Description:��˹��ʵ��
 * @author: wangjy
 * @date:   2017��11��8�� ����2:00:00   
 *      
 */
public class TSLDoorImpl implements IDoor{
	
	private TslBluetoothManager mTslBluetoothManager; // ��˹������������
	
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
		 * <p>Description: ʧ��</p>   
		 * @param arg0   
		 * @see com.terminus.lock.library.CallBack#onFail(int)   
		 */  
		@Override
		public void onFail(int result) {
            switch (result) {
            case Response.ERROR_CONN_TIMEOUT:// ����ʧ��
            	resultCallback.onResult(OperateConstant.TIMEOUT, null);
                break;
            case Response.ERROR_BLUETOOTH_IS_NOT_ENABLED: // ��������
            	resultCallback.onResult(OperateConstant.ERROR_BLUETOOTH_IS_NOT_ENABLED, null);
                break;
            case Response.ERROR_OPENED:// �����ɹ�
            	resultCallback.onResult(OperateConstant.OPEN_SUCCESS, null);
                break;
            case Response.ERROR_OPEN_LOCK_KEY_DISABLE:
            	resultCallback.onResult(OperateConstant.ERROR_OPEN_LOCK_KEY_DISABLE, null);
                break;
            case Response.ERROR_OPEN_LOCK_KEY_DELETED:
            	resultCallback.onResult(OperateConstant.ERROR_OPEN_LOCK_KEY_DELETED, null);
                break;
            case Response.ERROR_DEVICE_ADDRESS_ILLEGAL:
            	resultCallback.onResult(OperateConstant.ERROR_DEVICE_ADDRESS_ILLEGAL, null);
                break;
            case Response.ERROR_PASSWORD:
            	resultCallback.onResult(OperateConstant.PASSWD_ERROR, null);
                break;
            case Response.ERROR_UNSUPPORT_OPERATOR:
            	resultCallback.onResult(OperateConstant.ERROR_UNSUPPORT_OPERATOR, null);
                break;
        }
		}

		/**   
		 * <p>Title: onSuccess</p>   
		 * <p>Description: �ɹ�</p>   
		 * @param arg0   
		 * @see com.terminus.lock.library.CallBack#onSuccess(com.terminus.lock.library.Response)   
		 */  
		@Override
		public void onSuccess(Response response) {
			resultCallback.onResult(OperateConstant.OPEN_SUCCESS, null);
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
	 * @see com.greenland.btsdk.interfaces.IDoor#openDoor(java.lang.String, int, java.lang.String, int, com.greenland.btsdk.callback.ResultCallback)   
	 */  
	@Override
	public int openDoor(String mac, int time, String password, int timeout, ResultCallback resultCallback) {
		mTslBluetoothManager = TslBluetoothManager.getInstance(activity);
        String resultCipher = null;
        try {
            resultCipher = Utils.decodeCipher(password);
        } catch (Exception e) {
        	resultCallback.onResult(OperateConstant.PASSWD_ERROR, null);
        }
		mTslBluetoothManager.openRemoteDoor(resultCipher, "��������", new myCallback(resultCallback, mac));
		return 0;
	}
	

}
