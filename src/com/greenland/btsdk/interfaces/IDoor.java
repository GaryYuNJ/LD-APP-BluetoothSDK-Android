package com.greenland.btsdk.interfaces;

import com.greenland.btsdk.callback.ResultCallback;

/**   
 * <p>�������Ų����ӿ�   ������Ҫʵ�ִ˽ӿ�</p>
 * @author wangjy
 * @version 1.0     
 */
public interface IDoor {
	
	/**
	 * <p>�������Ų�����   ������Ҫʵ�ִ˽ӿ�</p>
	 * @param mac �豸mac��ַ
	 * @param time ��ʱʱ��
	 * @param password ��������
	 * @param timeout ��ʱʱ��
	 * @param resultCallback �ص����� 
	 * @return int   ����״̬   
	 */
	int openDoor(String mac,int time,String password,int timeout,ResultCallback resultCallback);
}
