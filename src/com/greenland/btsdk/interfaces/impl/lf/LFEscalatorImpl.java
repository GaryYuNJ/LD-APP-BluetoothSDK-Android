package com.greenland.btsdk.interfaces.impl.lf;

import com.greenland.btsdk.callback.ResultCallback;
import com.greenland.btsdk.interfaces.IEscalator;

import android.app.Activity;
import android.app.Service;
import cn.com.reformer.rfBleService.BleService;
import cn.com.reformer.rfBleService.BleService.RfBleKey;

public class LFEscalatorImpl implements IEscalator{

    private BleService mService;
    
    private class MyResultCallback implements cn.com.reformer.rfBleService.ResultCallback {
    	
    	private ResultCallback resultCallback;
    	
		public MyResultCallback(ResultCallback resultCallback) {
			this.resultCallback = resultCallback;
		}

		/**   
		 * <p>Title: onResult</p>   
		 * <p>Description: </p>   
		 * @param mac
		 * @param result   
		 * @see cn.com.reformer.rfBleService.ResultCallback#onResult(byte[], int)   
		 */  
		@Override
		public void onResult(byte[] mac, int result) {
			resultCallback.onResult(result, null);
		}
    }
    
    public LFEscalatorImpl(Service service,Activity activity) {
    	this.mService = (BleService) service;
	}
    
    @Override
    public int ctrlEscalator(String mac, String password, String phone, int upOrDown, int timeout,
            ResultCallback resultCallback) {
    	RfBleKey rfBleKey = mService.getRfBleKey();
    	return rfBleKey.ctrlHT(mac, password, phone, upOrDown, timeout, new MyResultCallback(resultCallback));
    }

}
