package com.example.ldsdk_xh.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ldsdk_xh.R;
import com.example.ldsdk_xh.adapter.MyBaseExpandableListAdapter;
import com.example.ldsdk_xh.callback.BindServiceCallback;
import com.example.ldsdk_xh.callback.ResultCallback;
import com.example.ldsdk_xh.callback.SearchBleCallback;
import com.example.ldsdk_xh.ctrl.BluetoothSearch;
import com.example.ldsdk_xh.ctrl.DeviceResourceDataDispose;
import com.example.ldsdk_xh.ctrl.OperateDevice;
import com.example.ldsdk_xh.model.DeviceInfo;
import com.example.ldsdk_xh.utils.Constant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	/*************主页面控件************************/
	//搜索蓝牙设备的按钮
	private Button search_ble;
	private TextView operate_status;
	
	/***********MyBaseExpandableListAdapter******/
	private ExpandableListView expandlist;
	private List<String> buildingNameList;
	private Map<Integer, List<String>> blueNameMap;//蓝牙设备名map
	private MyBaseExpandableListAdapter expandableListAdapter;
	
	/***************数据***************************/
	//设备信息的map集合  键为设备资源名称 值为设备资源信息
	Map<String, DeviceInfo> deviceInfos;
	//buildingId与ExpandableList的索引对应关系
	Map<Integer, Integer> buildingId_groupId_map;
	//厂商id
	private Integer manufacturerId;
	//不同厂商的servicemap集合 键为厂商id 值为对应service
	private Map<String,Service> serviceMap;
	//蓝牙搜索方法类
	private BluetoothSearch bluetoothSearch;
	//搜索状态
	private boolean scan = false;
	//group索引
	private Integer groupIndex;
	
	

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
		buildingNameList = new ArrayList<String>();
		blueNameMap = new HashMap<Integer, List<String>>();
		deviceInfos = new HashMap<String, DeviceInfo>();
		serviceMap = new HashMap<String, Service>();
		buildingId_groupId_map = new HashMap<Integer, Integer>();
		//获取选中楼栋的所有共有私有蓝牙设备放入缓存(加入根据条件获取) 这个应该在选择那个楼栋时候调用 
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean result = DeviceResourceDataDispose.cacheDeviceData(MainActivity.this,null,"17366001910");
				if (!result) {//表示没有请求到数据
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, "请联网之后，关掉程序后重试", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}).start();
	}
	
	/**
	 * @Title: initView   
	 * @Description: 初始化页面控件
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	private void initView() {
		expandlist = (ExpandableListView) findViewById(R.id.expandlist);
		expandlist.setGroupIndicator(null);//这里不显示系统默认的group indicator
		
		search_ble = (Button) findViewById(R.id.search_ble);
		search_ble.setOnClickListener(this);
		operate_status = (TextView) findViewById(R.id.operate_status);
		expandableListAdapter = new MyBaseExpandableListAdapter(MainActivity.this, buildingNameList, blueNameMap);
		expandlist.setAdapter(expandableListAdapter);
	}
	
	/**   
	 * @Title: initEvent   
	 * @Description: 初始化事件
	 * @param:       
	 * @return: void      
	 * @throws   
	 */
	private void initEvent() {
		expandlist.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				String blueName = (String) expandableListAdapter.getChild(groupPosition, childPosition);
				DeviceInfo deviceInfo = deviceInfos.get(blueName);
				manufacturerId = deviceInfo.getManufacturerId();
				OperateDevice invokeActivity = new OperateDevice(manufacturerId, deviceInfo.getTypeId(),serviceMap.get(manufacturerId.toString()),MainActivity.this);
				String mac = deviceInfo.getMac();
				if (scan) {
					scan = false;
					search_ble.setText(R.string.re_search);
					operate_status.setText(R.string.searched);
				}
				bluetoothSearch.stopScanBleDevice();
				//可以判断其他的该执行什么 
				if (deviceInfo.getTypeId() == Constant.DOOR) {
					int res = invokeActivity.openDoor(mac, Integer.decode("1"), deviceInfo.getPassword(), 2000, resultCallback);
					if (res == 0) {
						operate_status.setText("开门中...");
					}
				}
				return true;
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
				groupIndex = 0;
				deviceInfos.clear();
				buildingId_groupId_map.clear();
				buildingNameList.clear();
				blueNameMap.clear();
				expandableListAdapter.notifyDataSetChanged();
				
				bluetoothSearch = new BluetoothSearch(MainActivity.this,searchBleCallback,bindServiceCallback);
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
	 * 绑定所有厂商service的回调函数
	 * serviceMap 键是厂商id 值是对应的service
	 */
	private BindServiceCallback bindServiceCallback = new BindServiceCallback() {
		@Override
		public void onBind(Map<String, Service> serviceMap) {
			MainActivity.this.serviceMap = serviceMap;
		}
	};
	/**
	 * 操作设备之后的回调函数
	 * i为结果类型
	 */
	private ResultCallback resultCallback = new ResultCallback() {
		
		@Override
		public void onResult(byte[] bytes, int i) {
			 final int result = i;
			 runOnUiThread(new Runnable() {
				public void run() {
					switch (result) {
					case 0:
						Toast.makeText(MainActivity.this, "开门成功", Toast.LENGTH_SHORT).show();
						break;
					case 1:
						Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
						break;
					case 2:
						Toast.makeText(MainActivity.this, "蓝牙中断", Toast.LENGTH_SHORT).show();
						break;
					case 3:
						Toast.makeText(MainActivity.this, "超时", Toast.LENGTH_SHORT).show();
						break;
					case Constant.ERROR_BLUETOOTH_IS_NOT_ENABLED:
						Toast.makeText(MainActivity.this, "蓝牙设备异常", Toast.LENGTH_SHORT).show();
						break;
					case Constant.ERROR_OPEN_LOCK_KEY_DISABLE:
						Toast.makeText(MainActivity.this, "钥匙被禁用", Toast.LENGTH_SHORT).show();
						break;
					case Constant.ERROR_OPEN_LOCK_KEY_DELETED:
						Toast.makeText(MainActivity.this, "钥匙被清除", Toast.LENGTH_SHORT).show();
						break;
					case Constant.ERROR_DEVICE_ADDRESS_ILLEGAL:
						Toast.makeText(MainActivity.this, "mac地址错误", Toast.LENGTH_SHORT).show();
						break;
					case Constant.ERROR_UNSUPPORT_OPERATOR:
						Toast.makeText(MainActivity.this, "设备不支持4.0蓝牙", Toast.LENGTH_SHORT).show();
						break;
					}
				}
			});
		}
	};
	
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
			Integer buildingId = deviceInfo.getBuildingId();
			//buildingId_groupId_map 楼栋和group的索引对应map
			Integer index = buildingId_groupId_map.get(buildingId);
			if (index == null) {
				if (buildingId_groupId_map.size() > 0) {
					groupIndex++;
				}
				buildingId_groupId_map.put(buildingId, groupIndex);
			}
			//把设备资源名和设备信息对应，方便childitem点击的时候好找到
			String name = deviceInfo.getName();
			deviceInfos.put(name, deviceInfo);
			List<String> buildingNames = blueNameMap.get(groupIndex);
			//如果buildingIds==null 就给buildingid对应的list初始化
			if (buildingNames == null) {
				buildingNames = new ArrayList<String>();
			}
			//如果放过同样的设备资源就不放
			if (!buildingNames.contains(deviceInfo.getName())) {
				buildingNames.add(deviceInfo.getName());
			}
			//保证如果key已经在map中了，就加上去
			if (!blueNameMap.containsKey(groupIndex)) {
				buildingNameList.add(deviceInfo.getBuildingName());
			}
			blueNameMap.put(groupIndex, buildingNames);
			runOnUiThread(new Runnable() {
				public void run() {
					expandableListAdapter.notifyDataSetChanged();
					expandlist.expandGroup(groupIndex);
				}
			});
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
		if (bluetoothSearch != null) {
			bluetoothSearch.unBindService();
		}
	}
}
