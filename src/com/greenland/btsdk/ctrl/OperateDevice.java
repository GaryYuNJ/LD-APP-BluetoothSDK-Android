package com.greenland.btsdk.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greenland.btsdk.bean.ResourceKey;
import com.greenland.btsdk.callback.BindServiceCallback;
import com.greenland.btsdk.callback.ResultCallback;
import com.greenland.btsdk.factory.DeviceFactory;
import com.greenland.btsdk.interfaces.IDoor;
import com.greenland.btsdk.interfaces.IElevator;
import com.greenland.btsdk.interfaces.IEscalator;
import com.greenland.btsdk.interfaces.IGroundLock;
import com.greenland.btsdk.model.DeviceInfo;
import com.greenland.btsdk.utils.Constant;
import com.greenland.btsdk.utils.DeviceConstant;
import com.greenland.btsdk.utils.OperateConstant;

import android.app.Activity;
import android.app.Service;
import android.os.Handler;

/**
 * @ClassName: OperateDevice
 * @Description:操作设备
 * @author: wangjy
 * @date: 2017年10月23日 下午11:32:24
 *
 */
public class OperateDevice {

	private IDoor door;
	private IElevator elevator;
	private IEscalator escalator;
	private IGroundLock groundLock;
	private ManufacturerServiceDispose serviceDispose;
	// 不同厂商的servicemap集合 键为厂商id 值为对应service
	private Map<String, Service> serviceMap = new HashMap<String, Service>();
	private Activity activity;
	private ResultCallback callback;
	private DeviceInfo deviceInfo = new DeviceInfo();
	
	private int time;
	private int timeout;
	private int floor;
	private String phone;
	private int upOrDown;
	protected OperateDevice(Activity activity) {
		this.activity = activity;
		// 绑定lf service
		serviceDispose = new ManufacturerServiceDispose(activity);
		serviceDispose.bindService(bindServiceCallback);
	}

	/**
	 * @Title: openDoor @Description: 开门 @param: @param mac
	 *         设备mac地址 @param: @param time 延时时间 @param: @param password
	 *         设备密码 @param: @param timeout 超时时间 @param: @param resultCallback
	 *         回调 @param: @return @return: int @throws
	 */
	public int openDoor(DeviceInfo d, int time, int timeout,ResultCallback resultCallback) {
		this.callback = resultCallback;
		copyDeviceInfo(d);
		ResourceKey resourceKey = deviceInfo.getKeys().get(0);
		initObject(deviceInfo.getTypeId(), resourceKey.getManufacturerId());
		return door.openDoor(resourceKey.getMac(), time, resourceKey.getPassword(), timeout, resultCallbackThis);
	}


	/**
	 * @Title: ctrlElevator @Description: 控制电梯 @param: @param mac
	 *         设备mac地址 @param: @param password 设备密码 @param: @param phone
	 *         手机号码 @param: @param floor 楼层 @param: @param timeout
	 *         超时时间 @param: @param resultCallback 回调 @param: @return @return:
	 *         int @throws
	 */
	public int ctrlElevator(DeviceInfo d, String phone, int floor, int timeout,ResultCallback resultCallback) {
		this.callback = resultCallback;
		copyDeviceInfo(d);
		ResourceKey resourceKey = deviceInfo.getKeys().get(0);
		initObject(deviceInfo.getTypeId(), resourceKey.getManufacturerId());
		return elevator.ctrlElevator(resourceKey.getMac(), resourceKey.getPassword(), phone, floor, timeout, resultCallbackThis);
	}

	/**
	 * @Title: ctrlEscalator @Description: 呼梯 @param: @param mac
	 *         设备mac地址 @param: @param password 设备密码 @param: @param phone
	 *         手机号码 @param: @param upOrDown 上或者下 0:up,1:down @param: @param
	 *         timeout 超时时间 @param: @param resultCallback
	 *         回调 @param: @return @return: int @throws
	 */
	public int ctrlEscalator(DeviceInfo d, String phone, int upOrDown, int timeout,ResultCallback resultCallback) {
		this.callback = resultCallback;
		copyDeviceInfo(d);
		ResourceKey resourceKey = deviceInfo.getKeys().get(0);
		initObject(deviceInfo.getTypeId(), resourceKey.getManufacturerId());
		return escalator.ctrlEscalator(resourceKey.getMac(), resourceKey.getPassword(), phone, upOrDown, timeout, resultCallbackThis);
	}

	/**
	 * @Title: ctrlEscalator @Description: 地锁 @param: @param mac
	 *         设备mac地址 @param: @param password 设备密码 @param: @param phone
	 *         手机号码 @param: @param upOrDown 上或者下 0:up,1:down @param: @param
	 *         timeout 超时时间 @param: @param resultCallback
	 *         回调 @param: @return @return: int @throws
	 */
	public int ctrlGroundLock(DeviceInfo d, int time, int timeout,ResultCallback resultCallback) {
		this.callback = resultCallback;
		copyDeviceInfo(d);
		ResourceKey resourceKey = deviceInfo.getKeys().get(0);
		initObject(deviceInfo.getTypeId(), resourceKey.getManufacturerId());
		return groundLock.openGroundLock(resourceKey.getMac(), time, resourceKey.getPassword(), timeout, resultCallbackThis);
	}

	/**
	 * @Title: unBindService @Description:解绑service @param: @return:
	 *         void @throws
	 */
	public void unBindService() {
		serviceDispose.unBindService();
	}

	/**
	 * 绑定所有厂商service的回调函数 serviceMap 键是厂商id 值是对应的service
	 */
	private BindServiceCallback bindServiceCallback = new BindServiceCallback() {
		@Override
		public void onBind(Map<String, Service> serviceMap) {
			OperateDevice.this.serviceMap = serviceMap;
		}
	};

	private void initObject(int typeId, Integer manufacturerId) {
		// 调用工厂类，根据manufacturerId和typeId通过反射判断厂商和调用类型并得到相应的接口
		Service service = serviceMap.get(manufacturerId.toString());
		switch (typeId) {
		case DeviceConstant.DOOR:
			door = DeviceFactory.getDoor(manufacturerId, typeId, service, activity);
			break;
		case DeviceConstant.ELEVATOR:
			elevator = DeviceFactory.getEscalator(manufacturerId, typeId, service, activity);
			break;
		case DeviceConstant.ESCALATOR:
			escalator = DeviceFactory.getElevator(manufacturerId, typeId, service, activity);
			break;
		case DeviceConstant.GROUND_LOCK:
			groundLock = DeviceFactory.getGroundLock(manufacturerId, typeId, service, activity);
			break;
		default:
			break;
		}
	}

	/**
	 * 操作设备之后的回调函数 i为结果类型
	 */
	private ResultCallback resultCallbackThis = new ResultCallback() {

		@Override
		public void onResult(int result,ResourceKey resourceKey) {
			final List<ResourceKey> keys = deviceInfo.getKeys();
			Integer typeId = deviceInfo.getTypeId();
			final int r = result;
			//地锁单独处理
			if (result == OperateConstant.GROUNDLOCK_CLOSE || result == OperateConstant.GROUNDLOCK_OPEN || result == OperateConstant.GROUNDLOCK_STATUS_FAIL) {
				activity.runOnUiThread(new Runnable() {
					public void run() {
						callback.onResult(r, null);
					}
				});
			} else {
				if (result != OperateConstant.OPEN_SUCCESS && keys.size() > 1) {
					keys.remove(0);
					switch (typeId) {
					case DeviceConstant.DOOR:
						openDoor(deviceInfo, time, timeout,callback);
						break;
					case DeviceConstant.ELEVATOR:
						ctrlElevator(deviceInfo, phone, floor, timeout,callback);
						break;
					case DeviceConstant.ESCALATOR:
						ctrlEscalator(deviceInfo, phone, upOrDown, timeout,callback);
						break;
					case DeviceConstant.GROUND_LOCK:
						ctrlGroundLock(deviceInfo, time, timeout,callback);
						break;
					}
				} else {
					activity.runOnUiThread(new Runnable() {
						public void run() {
							callback.onResult(r, keys.get(0));
						}
					});
				}
			}
		}
	};
	private void copyDeviceInfo(DeviceInfo d) {
		deviceInfo.setBuildingId(d.getBuildingId());
		deviceInfo.setBuildingName(d.getBuildingName());
		deviceInfo.setId(d.getId());
		deviceInfo.setKeys(d.getKeys());
		deviceInfo.setName(d.getName());
		deviceInfo.setTypeId(d.getTypeId());
		deviceInfo.setTypeName(d.getTypeName());
	}
}
