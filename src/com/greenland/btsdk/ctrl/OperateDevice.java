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
 * @Description:�����豸
 * @author: wangjy
 * @date: 2017��10��23�� ����11:32:24
 *
 */
public class OperateDevice {

	private IDoor door;
	private IElevator elevator;
	private IEscalator escalator;
	private IGroundLock groundLock;
	private ManufacturerServiceDispose serviceDispose;
	// ��ͬ���̵�servicemap���� ��Ϊ����id ֵΪ��Ӧservice
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
		// ��lf service
		serviceDispose = new ManufacturerServiceDispose(activity);
		serviceDispose.bindService(bindServiceCallback);
	}

	/**
	 * @Title: openDoor @Description: ���� @param: @param mac
	 *         �豸mac��ַ @param: @param time ��ʱʱ�� @param: @param password
	 *         �豸���� @param: @param timeout ��ʱʱ�� @param: @param resultCallback
	 *         �ص� @param: @return @return: int @throws
	 */
	public int openDoor(DeviceInfo d, int time, int timeout,ResultCallback resultCallback) {
		this.callback = resultCallback;
		copyDeviceInfo(d);
		ResourceKey resourceKey = deviceInfo.getKeys().get(0);
		initObject(deviceInfo.getTypeId(), resourceKey.getManufacturerId());
		return door.openDoor(resourceKey.getMac(), time, resourceKey.getPassword(), timeout, resultCallbackThis);
	}


	/**
	 * @Title: ctrlElevator @Description: ���Ƶ��� @param: @param mac
	 *         �豸mac��ַ @param: @param password �豸���� @param: @param phone
	 *         �ֻ����� @param: @param floor ¥�� @param: @param timeout
	 *         ��ʱʱ�� @param: @param resultCallback �ص� @param: @return @return:
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
	 * @Title: ctrlEscalator @Description: ���� @param: @param mac
	 *         �豸mac��ַ @param: @param password �豸���� @param: @param phone
	 *         �ֻ����� @param: @param upOrDown �ϻ����� 0:up,1:down @param: @param
	 *         timeout ��ʱʱ�� @param: @param resultCallback
	 *         �ص� @param: @return @return: int @throws
	 */
	public int ctrlEscalator(DeviceInfo d, String phone, int upOrDown, int timeout,ResultCallback resultCallback) {
		this.callback = resultCallback;
		copyDeviceInfo(d);
		ResourceKey resourceKey = deviceInfo.getKeys().get(0);
		initObject(deviceInfo.getTypeId(), resourceKey.getManufacturerId());
		return escalator.ctrlEscalator(resourceKey.getMac(), resourceKey.getPassword(), phone, upOrDown, timeout, resultCallbackThis);
	}

	/**
	 * @Title: ctrlEscalator @Description: ���� @param: @param mac
	 *         �豸mac��ַ @param: @param password �豸���� @param: @param phone
	 *         �ֻ����� @param: @param upOrDown �ϻ����� 0:up,1:down @param: @param
	 *         timeout ��ʱʱ�� @param: @param resultCallback
	 *         �ص� @param: @return @return: int @throws
	 */
	public int ctrlGroundLock(DeviceInfo d, int time, int timeout,ResultCallback resultCallback) {
		this.callback = resultCallback;
		copyDeviceInfo(d);
		ResourceKey resourceKey = deviceInfo.getKeys().get(0);
		initObject(deviceInfo.getTypeId(), resourceKey.getManufacturerId());
		return groundLock.openGroundLock(resourceKey.getMac(), time, resourceKey.getPassword(), timeout, resultCallbackThis);
	}

	/**
	 * @Title: unBindService @Description:���service @param: @return:
	 *         void @throws
	 */
	public void unBindService() {
		serviceDispose.unBindService();
	}

	/**
	 * �����г���service�Ļص����� serviceMap ���ǳ���id ֵ�Ƕ�Ӧ��service
	 */
	private BindServiceCallback bindServiceCallback = new BindServiceCallback() {
		@Override
		public void onBind(Map<String, Service> serviceMap) {
			OperateDevice.this.serviceMap = serviceMap;
		}
	};

	private void initObject(int typeId, Integer manufacturerId) {
		// ���ù����࣬����manufacturerId��typeIdͨ�������жϳ��̺͵������Ͳ��õ���Ӧ�Ľӿ�
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
	 * �����豸֮��Ļص����� iΪ�������
	 */
	private ResultCallback resultCallbackThis = new ResultCallback() {

		@Override
		public void onResult(int result,ResourceKey resourceKey) {
			final List<ResourceKey> keys = deviceInfo.getKeys();
			Integer typeId = deviceInfo.getTypeId();
			final int r = result;
			//������������
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
