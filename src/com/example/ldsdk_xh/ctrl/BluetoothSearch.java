package com.example.ldsdk_xh.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ldsdk_xh.callback.BindServiceCallback;
import com.example.ldsdk_xh.callback.SearchBleCallback;
import com.example.ldsdk_xh.model.DeviceInfo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;

public class BluetoothSearch {
	//蓝牙适配器
	private BluetoothAdapter bluetoothAdapter;
	private Handler handler;
	//蓝牙设备的map
	private Map<String, DeviceInfo> deviceInfos;
	//保证只保存设备最先搜到的一个设备的蓝牙
	private List<Integer> deviceIds;
	//搜索蓝牙的回调
	private SearchBleCallback searchBleCallback;
	//处理厂商的对象
	private ManufacturerServiceDispose serviceDispose;
	//可以得到macutil
	private GetMacUtil macUtil;
	private Activity activity;
	/**
	 * @Title:  BluetoothSearch  
	 * @Description:    初始化蓝牙搜索  
	 * @param:  @param activity 当前页面的activity  可以使用当前的XXXActivity.this
	 * @param:  @param searchBleCallback 蓝牙的回调 可以得到设备资源的信息
	 * @param:  @param bindServiceCallback  在这里传入绑定蓝牙service的回调这个是为了确保手机蓝牙打开了之后在进行各厂商蓝牙的绑定
	 * @throws
	 */
	public BluetoothSearch(Activity activity,SearchBleCallback searchBleCallback,BindServiceCallback bindServiceCallback) {
		handler = new Handler();
		this.searchBleCallback = searchBleCallback;
		deviceInfos = new HashMap<String, DeviceInfo>();
		deviceIds = new ArrayList<Integer>();
		macUtil = new GetMacUtil(activity);
		this.activity = activity;
		//获取蓝牙适配器
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {//如果没获取到蓝牙适配器就关闭页面（表示不支持蓝牙）
        	activity.finish();
        }else if (!bluetoothAdapter.isEnabled()) {//如果没有打开蓝牙，就开启蓝牙
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(mIntent, 1);
        }else{//在设备打开蓝牙后绑定service
        	serviceDispose = new ManufacturerServiceDispose(activity);
        	serviceDispose.bindService(bindServiceCallback);
        }
	}
	
	
	/**
	 * @Title: scanBleDevice   
	 * @Description: 开始搜索设备
	 * @param: @param delayMillis  搜索时间
	 * @return: void      
	 * @throws
	 */
    public void scanBleDevice(long delayMillis) {
	    handler.postDelayed(stopLeScanRunnable, delayMillis);
	    bluetoothAdapter.startLeScan(mLeScanCallback); //开始搜索
    }
    
    /**
     * @Title: stopScanBleDevice   
     * @Description: 停止搜索设备
     * @param:       
     * @return: void      
     * @throws
     */
    public void stopScanBleDevice() {
    	handler.removeCallbacks(stopLeScanRunnable);
        bluetoothAdapter.stopLeScan(mLeScanCallback);//停止搜索
    }
    
    /**
     * 延时停止搜索时用的线程
     */
    private Runnable stopLeScanRunnable = new Runnable() {
        @Override
        public void run() {
            bluetoothAdapter.stopLeScan(mLeScanCallback);
            searchBleCallback.stopSearchBle();
        }
    };
    
    /**
     * @Title: unBindService   
     * @Description:解绑service
     * @param:       
     * @return: void      
     * @throws
     */
    public void unBindService(){
    	serviceDispose.unBindService();
    }
    
    /**
     * 扫描蓝牙的回调
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
			final String mac = macUtil.getDeviceMAC(device, scanRecord);
			//对蓝牙mac地址加密作为主键，主要是用来判断，如果搜索到重复的蓝牙回调了可以不执行
			if (!deviceInfos.containsKey(mac)) {
				//判断每个回调进来的蓝牙是否在本地搜索出来的列表中
				DeviceInfo deviceInfo = DeviceResourceDataDispose.isUsefulDevice(activity,mac);
				//如果传进来的蓝牙不在已有的列表中，再判断资源id在不在已有的列表中，（因为一个资源可能有多个蓝牙设备，这样保证用户取到的是最先搜到的设备）
				if (deviceInfo != null && (!deviceIds.contains(deviceInfo.getId()))) {
					if (deviceInfo != null) {
						deviceInfos.put(mac, deviceInfo);
						deviceIds.add(deviceInfo.getId());
						searchBleCallback.onSearchBle(deviceInfo);
					}
				}
			}
        }
    };
}
