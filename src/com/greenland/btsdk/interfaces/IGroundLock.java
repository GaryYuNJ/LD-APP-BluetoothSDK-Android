/**  
 * @Title:  IGroundLock.java   
 * @Package com.example.ldsdk_xh.interfaces   
 * @Description:    TODO(��һ�仰�������ļ���ʲô)   
 * @author: wangjy  
 * @date:   2017��12��29�� ����10:29:09   
 * @version V1.0 
 */  
package com.greenland.btsdk.interfaces;

import com.greenland.btsdk.callback.ResultCallback;

/**   
 * @ClassName:  IGroundLock   
 * @Description:TODO(������һ�仰��������������)   
 * @author: wangjy
 * @date:   2017��12��29�� ����10:29:09   
 *      
 */
public interface IGroundLock {
	/**
	 * <p>�������Ų�����   ������Ҫʵ�ִ˽ӿ�</p>
	 * @param mac �豸mac��ַ
	 * @param time ��ʱʱ��
	 * @param password ��������
	 * @param timeout ��ʱʱ��
	 * @param resultCallback �ص����� 
	 * @return int   ����״̬   
	 */
	int openGroundLock(String mac,int time,String password,int timeout,ResultCallback resultCallback);
}
