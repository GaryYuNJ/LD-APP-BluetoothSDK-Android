package com.example.ldsdk_xh.utils;

public class Constant {

	public static final String salt = "lvsdk";
	public static final String UN_USEFUL = "unuseful";
	
	//门
	public static final int DOOR = 1;
	//电梯
	public static final int ELEVATOR = 2;
	//扶梯
	public static final int ESCALATOR = 3;
	//地锁
	public static final int GROUND_LOCK = 4;
	
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
	
	

}
