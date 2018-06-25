package com.greenland.btsdk.utils;

public class OperateConstant {
	//打开成功
	public static final int OPEN_SUCCESS = 0;
	//密码错误
	public static final int PASSWD_ERROR = 1;
	//蓝牙中断
	public static final int BLE_INTERRUPT = 2;
	//超时
	public static final int TIMEOUT = 3;
	//蓝牙设备异常
	public static final int ERROR_BLUETOOTH_IS_NOT_ENABLED = 4;
	//钥匙被禁用
	public static final int ERROR_OPEN_LOCK_KEY_DISABLE = 5;
	//钥匙被清除
	public static final int ERROR_OPEN_LOCK_KEY_DELETED = 6;
	//mac地址错误
	public static final int ERROR_DEVICE_ADDRESS_ILLEGAL = 7;
	//设备不支持4.0蓝牙
	public static final int ERROR_UNSUPPORT_OPERATOR = 8;
	//地锁关闭
	public static final int GROUNDLOCK_CLOSE = 10;
	//地锁开启
	public static final int GROUNDLOCK_OPEN = 11;
	//得到地锁状态失败
	public static final int GROUNDLOCK_STATUS_FAIL = 12;
}
