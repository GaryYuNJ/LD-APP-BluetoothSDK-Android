package com.greenland.btsdk.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greenland.btsdk.callback.SearchBleCallback;
import com.greenland.btsdk.model.DeviceInfo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;

public class BluetoothSearch {
	//����������
	private BluetoothAdapter bluetoothAdapter;
	private Handler handler;
	//�����豸��map
	private Map<String, DeviceInfo> deviceInfos;
	//��ֻ֤�����豸�����ѵ���һ���豸������
	private List<Integer> deviceIds;
	//���������Ļص�
	private SearchBleCallback searchBleCallback;
	//���Եõ�macutil
	private GetMacUtil macUtil;
	private Activity activity;
	private String mobile;
	/**
	 * @Title:  BluetoothSearch  
	 * @Description:    ��ʼ����������  
	 * @param:  @param activity ��ǰҳ���activity  ����ʹ�õ�ǰ��XXXActivity.this
	 * @param:  @param searchBleCallback �����Ļص� ���Եõ��豸��Դ����Ϣ
	 * @throws
	 */
	public BluetoothSearch(Activity activity,SearchBleCallback searchBleCallback,String mobile) {
		handler = new Handler();
		this.searchBleCallback = searchBleCallback;
		deviceInfos = new HashMap<String, DeviceInfo>();
		deviceIds = new ArrayList<Integer>();
		macUtil = new GetMacUtil(activity);
		this.activity = activity;
		this.mobile = mobile;
		//��ȡ����������
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {//���û��ȡ�������������͹ر�ҳ�棨��ʾ��֧��������
        	activity.finish();
        }else if (!bluetoothAdapter.isEnabled()) {//���û�д��������Ϳ�������
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(mIntent, 1);
        }
	}
	
	
	/**
	 * @Title: scanBleDevice   
	 * @Description: ��ʼ�����豸
	 * @param: @param delayMillis  ����ʱ��
	 * @return: void      
	 * @throws
	 */
    public void scanBleDevice(long delayMillis) {
	    handler.postDelayed(stopLeScanRunnable, delayMillis);
	    bluetoothAdapter.startLeScan(mLeScanCallback); //��ʼ����
    }
    
    /**
     * @Title: stopScanBleDevice   
     * @Description: ֹͣ�����豸
     * @param:       
     * @return: void      
     * @throws
     */
    public void stopScanBleDevice() {
    	handler.removeCallbacks(stopLeScanRunnable);
        bluetoothAdapter.stopLeScan(mLeScanCallback);//ֹͣ����
    }
    
    /**
     * ��ʱֹͣ����ʱ�õ��߳�
     */
    private Runnable stopLeScanRunnable = new Runnable() {
        @Override
        public void run() {
            bluetoothAdapter.stopLeScan(mLeScanCallback);
            searchBleCallback.stopSearchBle();
        }
    };
    
    /**
     * ɨ�������Ļص�
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
			final String mac = macUtil.getDeviceMAC(device, scanRecord);
			//������mac��ַ������Ϊ��������Ҫ�������жϣ�����������ظ��������ص��˿��Բ�ִ��
			if (!deviceInfos.containsKey(mac)) {
				//�ж�ÿ���ص������������Ƿ��ڱ��������������б���
				DeviceInfo deviceInfo = DeviceResourceDataDispose.isUsefulDevice(activity,mac,mobile);
				//����������������������е��б��У����ж���Դid�ڲ������е��б��У�����Ϊһ����Դ�����ж�������豸��������֤�û�ȡ�����������ѵ����豸��
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
