package com.example.ldsdk_xh.interfaces.impl.lf;

import com.example.ldsdk_xh.callback.ResultCallback;
import com.example.ldsdk_xh.interfaces.IElevator;

import android.app.Activity;
import android.app.Service;
import cn.com.reformer.rfBleService.BleService;
import cn.com.reformer.rfBleService.BleService.RfBleKey;

public class LFElevatorImpl implements IElevator{

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
			resultCallback.onResult(mac, result);
		}
    }
    
    public LFElevatorImpl(Service service,Activity activity) {
    	this.mService = (BleService) service;
	}
    
    @Override
    public int ctrlElevator(String mac, String password, String phone, int floor, int timeout,
            ResultCallback resultCallback) {
    	RfBleKey rfBleKey = mService.getRfBleKey();
    	return rfBleKey.ctrlTK(mac, password, phone, floor, timeout, new MyResultCallback(resultCallback));
    }

}
