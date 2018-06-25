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
	/*************主页面控件************************/
	//搜索蓝牙设备的按钮
	private Button search_ble;
	private TextView operate_status;
	
	/***********ListAdapter******/
	private ListView listView;
	private MyListViewAdapter listViewAdapter;
	private Button checkAllKeyBtn;
	private Button clearCacheBtn;
	
	/***************数据***************************/
	//蓝牙搜索到的key
	List<DeviceInfo> deviceInfos;
	//搜索状态
	private boolean scan = false;
	//存放用户所有的可用的钥匙
	private Map<Integer, List<DeviceInfo>> devices;
	
	//蓝牙搜索方法类
	private BluetoothSearch bluetoothSearch;
	//操作设备的类
	private OperateDevice operateDevice;
	private LDSDKManager manager;
	private String mobile = "17366010645";
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();//初始化数据
		initView();//初始化控件
		initEvent();//初始化事件
		buildEnv();
	}
	
	/**
	 * @Title: initData   
	 * @Description: 初始化数据
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	private void initData() {
		deviceInfos = new ArrayList<DeviceInfo>();
		devices = new HashMap<Integer, List<DeviceInfo>>();
		//调用缓存数据(已经另开线程)
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
			Toast.makeText(MainActivity.this, "刷新"+isFresh, Toast.LENGTH_SHORT).show();
		}
	};
	
	/**
	 * @Title: initView   
	 * @Description: 初始化页面控件
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
	 * @Description: 初始化事件
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
					operate_status.setText("开门中...");
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
				//模拟切换用户
				mobile = "17366001920";
				manager.getAllKeys(null, mobile, callback);
				if (result) {
					Toast.makeText(MainActivity.this, "清除缓存数据成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "清除缓存数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	/**
	 * 页面的点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_ble:
			if (!scan) {
				//搜索之前把清理数据
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
	 * 自定义蓝牙工具类回调函数参数
	 * deviceInfo 为搜索到一个蓝牙设备 返回一个设备信息
	 */
	private SearchBleCallback searchBleCallback = new SearchBleCallback() {
		/**
		 * 每搜索到一个就会调用一次
		 */
		@Override
		public void onSearchBle(DeviceInfo deviceInfo) {
			deviceInfos.add(deviceInfo);
			listView.setAdapter(listViewAdapter);
		}
		/**
		 * 搜索蓝牙结束
		 */
		@Override
		public void stopSearchBle() {
			scan = false;
			operate_status.setText(R.string.search_complete);
			search_ble.setText(R.string.re_search);
		}
	};
	/**
	 * 操作设备之后的回调函数
	 * i为结果类型
	 */
	private ResultCallback resultCallback = new ResultCallback() {
		
		@Override
		public void onResult(int i,ResourceKey key) {
			 final int result = i;
			 switch (result) {
				case OperateConstant.OPEN_SUCCESS:
					//operate_status.setText("开门成功");
					Toast.makeText(MainActivity.this, "开门成功", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.PASSWD_ERROR:
					Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.BLE_INTERRUPT:
					Toast.makeText(MainActivity.this, "蓝牙中断", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.TIMEOUT:
					Toast.makeText(MainActivity.this, "超时", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_BLUETOOTH_IS_NOT_ENABLED:
					Toast.makeText(MainActivity.this, "蓝牙设备异常", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_OPEN_LOCK_KEY_DISABLE:
					Toast.makeText(MainActivity.this, "钥匙被禁用", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_OPEN_LOCK_KEY_DELETED:
					Toast.makeText(MainActivity.this, "钥匙被清除", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_DEVICE_ADDRESS_ILLEGAL:
					Toast.makeText(MainActivity.this, "mac地址错误", Toast.LENGTH_SHORT).show();
					break;
				case OperateConstant.ERROR_UNSUPPORT_OPERATOR:
					Toast.makeText(MainActivity.this, "设备不支持4.0蓝牙", Toast.LENGTH_SHORT).show();
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
	 * @Description: 由于安卓6.0是动态请求位置信息权限，这里要判断，动态询问权限
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	@SuppressLint("NewApi")
	private void buildEnv() {
		// 要申请的权限 数组 可以同时申请多个权限
	    String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};
		if (Build.VERSION.SDK_INT >= 23) {
			  //如果超过6.0才需要动态权限，否则不需要动态权限
	        //如果同时申请多个权限，可以for循环遍历
	        int check = checkSelfPermission(permissions[0]);
	        if (check == PackageManager.PERMISSION_DENIED) {
	        	//手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
	            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
			}
		}
	}
	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
	    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	    //回调，判断用户到底点击是还是否。
	    //如果同时申请多个权限，可以for循环遍历
	    if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	    	
	    } else {
	        // 没有获取 到权限，从新请求，或者关闭app
	        Toast.makeText(this,"需要获得位置权限，请手动给当前应用赋予位置权限",Toast.LENGTH_LONG).show();
	        finish();
	    }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//退出 销毁service
		operateDevice.unBindService();
	}
	
}
