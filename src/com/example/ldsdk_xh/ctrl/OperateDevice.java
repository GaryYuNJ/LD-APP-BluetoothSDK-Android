package com.example.ldsdk_xh.ctrl;

import com.example.ldsdk_xh.callback.ResultCallback;
import com.example.ldsdk_xh.factory.DeviceFactory;
import com.example.ldsdk_xh.interfaces.IDoor;
import com.example.ldsdk_xh.interfaces.IElevator;
import com.example.ldsdk_xh.interfaces.IEscalator;
import com.example.ldsdk_xh.interfaces.IGroundLock;
import com.example.ldsdk_xh.utils.Constant;

import android.app.Activity;
import android.app.Service;

/**
 * @ClassName:  OperateDevice   
 * @Description:操作设备
 * @author: wangjy
 * @date:   2017年10月23日 下午11:32:24   
 *
 */
public class OperateDevice{

    private IDoor door;
    private IElevator elevator;
    private IEscalator escalator;
    private IGroundLock groundLock;
	/**
	 * @Title:  OperateDevice   
	 * @Description:    初始化设备操作类
	 * @param:  @param manufacturerId 厂商id
	 * @param:  @param typeId 资源类型id
	 * @param:  @param service  厂商service
	 * @throws
	 */
	public OperateDevice(Integer manufacturerId,Integer typeId,Service service,Activity activity){
		//调用工厂类，根据manufacturerId和typeId通过反射判断厂商和调用类型并得到相应的接口
		switch (typeId) {
		case Constant.DOOR:
			door = DeviceFactory.getDoor(manufacturerId, typeId,service,activity);
			break;
		case Constant.ELEVATOR:
			elevator = DeviceFactory.getEscalator(manufacturerId, typeId,service,activity);
			break;
		case Constant.ESCALATOR:
			escalator = DeviceFactory.getElevator(manufacturerId, typeId,service,activity);
			break;
		case Constant.GROUND_LOCK:
			groundLock = DeviceFactory.getGroundLock(manufacturerId, typeId,service,activity);
			break;

		default:
			break;
		}
	}


	/**
	 * @Title: openDoor   
	 * @Description: 开门
	 * @param: @param mac 设备mac地址
	 * @param: @param time 延时时间
	 * @param: @param password 设备密码
	 * @param: @param timeout 超时时间
	 * @param: @param resultCallback 回调
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int  openDoor(String mac,int time,String password,int timeout,ResultCallback resultCallback){
	   return door.openDoor(mac, time, password, timeout, resultCallback);
	}
	
	
	/**
	 * @Title: ctrlElevator   
	 * @Description: 控制电梯
	 * @param: @param mac 设备mac地址
	 * @param: @param password 设备密码
	 * @param: @param phone 手机号码
	 * @param: @param floor 楼层
	 * @param: @param timeout 超时时间
	 * @param: @param resultCallback 回调
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int ctrlElevator(String mac, String password, String phone, int floor, int timeout,ResultCallback resultCallback){
		return elevator.ctrlElevator(mac, password, phone, floor, timeout, resultCallback);
	}
	
	
	/**
	 * @Title: ctrlEscalator   
	 * @Description: 呼梯 
	 * @param: @param mac 设备mac地址
	 * @param: @param password 设备密码
	 * @param: @param phone 手机号码
	 * @param: @param upOrDown 上或者下 0:up,1:down
	 * @param: @param timeout 超时时间
	 * @param: @param resultCallback 回调
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int ctrlEscalator(String mac,String password,String phone,int upOrDown,int timeout,ResultCallback resultCallback){
		return escalator.ctrlEscalator(mac, password, phone, upOrDown, timeout, resultCallback);
    }
	/**
	 * @Title: ctrlEscalator   
	 * @Description: 地锁
	 * @param: @param mac 设备mac地址
	 * @param: @param password 设备密码
	 * @param: @param phone 手机号码
	 * @param: @param upOrDown 上或者下 0:up,1:down
	 * @param: @param timeout 超时时间
	 * @param: @param resultCallback 回调
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int ctrlGroundLock(String mac,int time,String password,int timeout,ResultCallback resultCallback){
		return groundLock.openGroundLock(mac, time, password, timeout, resultCallback);
	}
}
