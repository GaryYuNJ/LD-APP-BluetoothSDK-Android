package com.greenland.btsdk.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greenland.btsdk.R;
import com.greenland.btsdk.adapter.MyBaseExpandableListAdapter;
import com.greenland.btsdk.bean.ResourceKey;
import com.greenland.btsdk.callback.ResultCallback;
import com.greenland.btsdk.ctrl.LDSDKManager;
import com.greenland.btsdk.ctrl.OperateDevice;
import com.greenland.btsdk.model.DeviceInfo;
import com.greenland.btsdk.utils.DeviceConstant;
import com.greenland.btsdk.utils.OperateConstant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class KeyListActivity extends Activity {

	private ExpandableListView expandlist;
	private MyBaseExpandableListAdapter expandableListAdapter;
	private List<String> buildingNameList;
	private Map<Integer, List<DeviceInfo>> keyMap;// �����豸��map
	// buildingId��ExpandableList��������Ӧ��ϵ
	private Map<Integer, Integer> buildingId_groupId_map;

	private Map<Integer, List<DeviceInfo>> devices;
	// group����
	private Integer groupIndex = 0;

	private OperateDevice operateDevice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.key_list);
		initData();// ��ʼ������
		initView();// ��ʼ���ؼ�
		initEvent();// ��ʼ���¼�

	}

	private void initData() {
		buildingNameList = new ArrayList<String>();
		keyMap = new HashMap<Integer, List<DeviceInfo>>();
		buildingId_groupId_map = new HashMap<Integer, Integer>();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String devicesStr = bundle.getString("devices");
		Gson gson = new Gson();
		devices = gson.fromJson(devicesStr, new TypeToken<HashMap<Integer, List<DeviceInfo>>>() {
		}.getType());
		if (devices != null) {
			for (Map.Entry<Integer, List<DeviceInfo>> m : devices.entrySet()) {
				List<DeviceInfo> deviceInfos = m.getValue();
				for (DeviceInfo d : deviceInfos) {
					if (!buildingNameList.contains(d.getBuildingName())) {
						buildingNameList.add(d.getBuildingName());
						if (!buildingId_groupId_map.containsKey(d.getBuildingId())) {
							buildingId_groupId_map.put(d.getBuildingId(), groupIndex++);
							continue;
						}
					}
				}
				keyMap.put(buildingId_groupId_map.get(m.getKey()), deviceInfos);
			}
		}
	}

	private void initView() {
		expandlist = (ExpandableListView) findViewById(R.id.keylist);
		expandlist.setGroupIndicator(null);// ���ﲻ��ʾϵͳĬ�ϵ�group indicator
		expandableListAdapter = new MyBaseExpandableListAdapter(KeyListActivity.this, buildingNameList, keyMap);
		expandlist.setAdapter(expandableListAdapter);
		operateDevice = LDSDKManager.getOperateDevice(KeyListActivity.this);
		expandableListAdapter.notifyDataSetChanged();
	}

	private void initEvent() {
		expandlist.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {
				DeviceInfo deviceInfo = (DeviceInfo) expandableListAdapter.getChild(groupPosition, childPosition);
				// �����ж������ĸ�ִ��ʲô
				if (deviceInfo.getTypeId() == DeviceConstant.DOOR) {
					int res = operateDevice.openDoor(deviceInfo, Integer.decode("1"), 2000, resultCallback);
					if (res == 0) {
						// ������...
					}
				}
				return true;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.key_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * �����豸֮��Ļص����� iΪ�������
	 */
	private ResultCallback resultCallback = new ResultCallback() {

		@Override
		public void onResult(int i,ResourceKey key) {
			final int result = i;
			switch (result) {
			case OperateConstant.OPEN_SUCCESS:
				Toast.makeText(KeyListActivity.this, "���ųɹ�", Toast.LENGTH_SHORT).show();
				break;
			case OperateConstant.PASSWD_ERROR:
				Toast.makeText(KeyListActivity.this, "�������", Toast.LENGTH_SHORT).show();
				break;
			case OperateConstant.BLE_INTERRUPT:
				Toast.makeText(KeyListActivity.this, "�����ж�", Toast.LENGTH_SHORT).show();
				break;
			case OperateConstant.TIMEOUT:
				Toast.makeText(KeyListActivity.this, "��ʱ", Toast.LENGTH_SHORT).show();
				break;
			case OperateConstant.ERROR_BLUETOOTH_IS_NOT_ENABLED:
				Toast.makeText(KeyListActivity.this, "�����豸�쳣", Toast.LENGTH_SHORT).show();
				break;
			case OperateConstant.ERROR_OPEN_LOCK_KEY_DISABLE:
				Toast.makeText(KeyListActivity.this, "Կ�ױ�����", Toast.LENGTH_SHORT).show();
				break;
			case OperateConstant.ERROR_OPEN_LOCK_KEY_DELETED:
				Toast.makeText(KeyListActivity.this, "Կ�ױ����", Toast.LENGTH_SHORT).show();
				break;
			case OperateConstant.ERROR_DEVICE_ADDRESS_ILLEGAL:
				Toast.makeText(KeyListActivity.this, "mac��ַ����", Toast.LENGTH_SHORT).show();
				break;
			case OperateConstant.ERROR_UNSUPPORT_OPERATOR:
				Toast.makeText(KeyListActivity.this, "�豸��֧��4.0����", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
}
