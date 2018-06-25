package com.greenland.btsdk.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.greenland.btsdk.R;
import com.greenland.btsdk.adapter.MyListViewAdapter;
import com.greenland.btsdk.bean.ResourceKey;
import com.greenland.btsdk.callback.FetchKeysCallback;
import com.greenland.btsdk.callback.ResultCallback;
import com.greenland.btsdk.callback.SearchBleCallback;
import com.greenland.btsdk.ctrl.BluetoothSearch;
import com.greenland.btsdk.ctrl.LDSDKManager;
import com.greenland.btsdk.ctrl.OperateDevice;
import com.greenland.btsdk.model.DeviceInfo;
import com.greenland.btsdk.utils.OperateConstant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	/*************��ҳ��ؼ�************************/
	//���������豸�İ�ť
	private Button search_ble;
	private TextView operate_status;
	
	/***********ListAdapter******/
	private ListView listView;
	private MyListViewAdapter listViewAdapter;
	private Button checkAllKeyBtn;
	private Button clearCacheBtn;
	
	/***************����***************************/
	//������������key
	List<DeviceInfo> deviceInfos;
	//����״̬
	private boolean scan = false;
	//����û����еĿ��õ�Կ��
	private Map<Integer, List<DeviceInfo>> devices;
	
	//��������������
	private BluetoothSearch bluetoothSearch;
	//�����豸����
	private OperateDevice operateDevice;
	private LDSDKManager manager;
	private String mobile = "17366010645";
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();//��ʼ������
		initView();//��ʼ���ؼ�
		initEvent();//��ʼ���¼�
		buildEnv();
	}
	
	/**
	 * @Title: initData   
	 * @Description: ��ʼ������
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	private void initData() {
		deviceInfos = new ArrayList<DeviceInfo>();
		devices = new HashMap<Integer, List<DeviceInfo>>();
		//���û�������(�Ѿ����߳�)
		manager = new LDSDKManager(MainActivity.this);
		manager.getAllKeys(null, mobile,callback);
		//operateDevice = new OperateDevice(MainActivity.this, resultCallback);
		operateDevice = LDSDKManager.getOperateDevice(MainActivity.this);
	}
	private FetchKeysCallback callback = new FetchKeysCallback() {
		
		@Override
		public void onFetched(boolean isSuccess, boolean isFresh, Map<Integer, List<DeviceInfo>> devices) {
			System.out.println(isSuccess);
			System.out.println(isFresh);
			MainActivity.this.devices = devices;
			Toast.makeText(MainActivity.this, "ˢ��"+isFresh, Toast.LENGTH_SHORT).show();
		}
	};
	
	/**
	 * @Title: initView   
	 * @Description: ��ʼ��ҳ��ؼ�
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	private void initView() {
		listView = (ListView) findViewById(R.id.search_key_list);
		listViewAdapter = new MyListViewAdapter(deviceInfos, MainActivity.this);
		listView.setAdapter(listViewAdapter);
		checkAllKeyBtn = (Button) findViewById(R.id.deviceList_btn);
		clearCacheBtn = (Button) findViewById(R.id.clearCacheBtn);
		search_ble = (Button) findViewById(R.id.search_ble);
		search_ble.setOnClickListener(this);
		operate_status = (TextView) findViewById(R.id.operate_status);
	}
	
	/**   
	 * @Title: initEvent   
	 * @Description: ��ʼ���¼�
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	private void initEvent() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DeviceInfo deviceInfo = (DeviceInfo) listViewAdapter.getItem(position);
				int result = operateDevice.openDoor(deviceInfo, Integer.decode("1"), 2000, resultCallback);
				if (result == 0) {
					operate_status.setText("������...");
				}
			}
		});
		checkAllKeyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,KeyListActivity.class);
				Bundle bundle = new Bundle();
				String devicesStr = new Gson().toJson(devices);
				bundle.putString("devices", devicesStr );
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		clearCacheBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean result = LDSDKManager.cleanCacheDeviceData(getApplicationContext());
				//ģ���л��û�
				mobile = "17366001920";
				manager.getAllKeys(null, mobile, callback);
				if (result) {
					Toast.makeText(MainActivity.this, "����������ݳɹ�", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "�����������ʧ��", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * ҳ��ĵ���¼�
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_ble:
			if (!scan) {
				//����֮ǰ����������
				deviceInfos.clear();
				bluetoothSearch = new BluetoothSearch(MainActivity.this,searchBleCallback,mobile);
				bluetoothSearch.scanBleDevice(10000);
				search_ble.setText(R.string.stop_search);
				operate_status.setText(R.string.searching);
				scan = true;
			} else {
				bluetoothSearch.stopScanBleDevice();
				search_ble.setText(R.string.re_search);
				operate_status.setText(R.string.searched);
				scan = false;
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * �Զ�������������ص���������
	 * deviceInfo Ϊ������һ�������豸 ����һ���豸��Ϣ
	 */
	private SearchBleCallback searchBleCallback = new SearchBleCallback() {
		/**
		 * ÿ������һ���ͻ����һ��
		 */
		@Override
		public void onSearchBle(DeviceInfo deviceInfo) {
			deviceInfos.add(deviceInfo);
			listView.setAdapter(listViewAdapter);
		}
		/**
		 * ������������
		 */
		@Override
		public void stopSearchBle() {
			scan = false;
			operate_status.setText(R.string.search_complete);
			search_ble.setText(R.string.re_search);
		}
	};
	/**
	 * �����豸֮��Ļص�����
	 * iΪ�������
	 */
	private ResultCallback resultCallback = new ResultCallback() {
		
		@Override
		public void onResult(int i,ResourceKey key) {
			 final int result = i;
			 switch (result) {
				case OperateConstant.OPEN_SUCCESS:
					//operate_status.setText("���ųɹ�");
					Toast.makeText(MainActivity.this, "���ųɹ�", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.PASSWD_ERROR:
					Toast.makeText(MainActivity.this, "�������", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.BLE_INTERRUPT:
					Toast.makeText(MainActivity.this, "�����ж�", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.TIMEOUT:
					Toast.makeText(MainActivity.this, "��ʱ", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_BLUETOOTH_IS_NOT_ENABLED:
					Toast.makeText(MainActivity.this, "�����豸�쳣", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_OPEN_LOCK_KEY_DISABLE:
					Toast.makeText(MainActivity.this, "Կ�ױ�����", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_OPEN_LOCK_KEY_DELETED:
					Toast.makeText(MainActivity.this, "Կ�ױ����", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_DEVICE_ADDRESS_ILLEGAL:
					Toast.makeText(MainActivity.this, "mac��ַ����", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_UNSUPPORT_OPERATOR:
					Toast.makeText(MainActivity.this, "�豸��֧��4.0����", Toast.LENGTH_SHORT).show();
					break;
				}
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**   
	 * @Title: buildEnv   
	 * @Description: ���ڰ�׿6.0�Ƕ�̬����λ����ϢȨ�ޣ�����Ҫ�жϣ���̬ѯ��Ȩ��
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	@SuppressLint("NewApi")
	private void buildEnv() {
		// Ҫ�����Ȩ�� ���� ����ͬʱ������Ȩ��
	    String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
		if (Build.VERSION.SDK_INT >= 23) {
			  //�������6.0����Ҫ��̬Ȩ�ޣ�������Ҫ��̬Ȩ��
	        //���ͬʱ������Ȩ�ޣ�����forѭ������
	        int check = checkSelfPermission(permissions[0]);
	        if (check == PackageManager.PERMISSION_DENIED) {
	        	//�ֶ�ȥ�����û���Ȩ��(��������������Ӷ��Ȩ��) 1 Ϊ������ һ������Ϊfinal��̬����
	            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
			}
		}
	}
	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
	    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	    //�ص����ж��û����׵���ǻ��Ƿ�
	    //���ͬʱ������Ȩ�ޣ�����forѭ������
	    if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	    	
	    } else {
	        // û�л�ȡ ��Ȩ�ޣ��������󣬻��߹ر�app
	        Toast.makeText(this,"��Ҫ���λ��Ȩ�ޣ����ֶ�����ǰӦ�ø���λ��Ȩ��",Toast.LENGTH_LONG).show();
	        finish();
	    }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//�˳� ����service
		operateDevice.unBindService();
	}
	
}
