/**  
 * @Title:  TSLGroundLockImpl.java   
 * @Package com.example.ldsdk_xh.interfaces.impl.tsl   
 * @Description:    TODO(��һ�仰�������ļ���ʲô)   
 * @author: wangjy  
 * @date:   2017��12��29�� ����10:28:38   
 * @version V1.0 
 */  
package com.greenland.btsdk.interfaces.impl.tsl;

import com.greenland.btsdk.callback.ResultCallback;
import com.greenland.btsdk.interfaces.IGroundLock;
import com.greenland.btsdk.utils.OperateConstant;
import com.terminus.lock.library.CallBack;
import com.terminus.lock.library.Response;
import com.terminus.lock.library.TslBluetoothManager;
import com.terminus.lock.library.response.GarateStatusResponse;
import com.terminus.lock.library.util.Utils;

import android.app.Activity;
import android.util.Log;

/**   
 * @ClassName:  TSLGroundLockImpl   
 * @Description:TODO(������һ�仰��������������)   
 * @author: wangjy
 * @date:   2017��12��29�� ����10:28:38   
 *      
 */
public class TSLGroundLockImpl implements IGroundLock{
	
	TslBluetoothManager manager; // ��˹������������
	
	private Activity activity;
	
	
	public TSLGroundLockImpl(Activity activity) {
		this.activity = activity;
        //SDK��ʼ��
		manager = TslBluetoothManager.getInstance(activity);
	}
	
	private class myCallBack implements CallBack {
		
		private ResultCallback resultCallback;
		private String mac;
		private String decodeCipher;
		
		public myCallBack(ResultCallback resultCallback,String mac, String decodeCipher) {
			this.resultCallback = resultCallback;
			this.mac = mac;
			this.decodeCipher = decodeCipher;
		}
		
		/**   
		 * <p>Title: onFail</p>   
		 * <p>Description: </p>   
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
		 * <p>Description: </p>   
		 * @param arg0   
		 * @see com.terminus.lock.library.CallBack#onSuccess(com.terminus.lock.library.Response)   
		 */  
		@Override
		public void onSuccess(Response response) {
			resultCallback.onResult(OperateConstant.OPEN_SUCCESS, null);
		}
		
	}
	
	/**   
	 * <p>Title: openGroundLock</p>   
	 * <p>Description: </p>   
	 * @param mac
	 * @param time
	 * @param password
	 * @param timeout
	 * @param resultCallback
	 * @return   
	 * @see com.greenland.btsdk.interfaces.IGroundLock#openGroundLock(java.lang.String, int, java.lang.String, int, com.greenland.btsdk.callback.ResultCallback)   
	 */  
	@Override
	public int openGroundLock(final String mac, int time, final String password, int timeout, final ResultCallback resultCallback) {
		manager.getGarateStatusWithCipher(password, new CallBack(){

			@Override
			public void onFail(int arg0) {
				resultCallback.onResult(OperateConstant.GROUNDLOCK_STATUS_FAIL, null);//��ȡ����״̬ʧ��
			}

			@Override
			public void onSuccess(Response response) {
				if (response instanceof GarateStatusResponse) {
					boolean currentOpenStatus = !((GarateStatusResponse)response).isOpened();
					Log.d("��������ѯ״̬����ǰ״̬�Ƿ�����=>" + currentOpenStatus,"openGroundStatus");
					if (currentOpenStatus) {
						resultCallback.onResult(OperateConstant.GROUNDLOCK_CLOSE, null);//�������ڹر�
					} else {
						resultCallback.onResult(OperateConstant.GROUNDLOCK_OPEN, null);//�������ڿ���
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					manager.openRemoteDoor(password, "��������", new myCallBack(resultCallback, mac, password));
				}
			}
			
		}); 
		return 0;
	}

}
