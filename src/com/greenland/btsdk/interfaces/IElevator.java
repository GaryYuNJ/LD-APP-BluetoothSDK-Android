package com.greenland.btsdk.interfaces;

import com.greenland.btsdk.callback.ResultCallback;

/**   
 * <p>�������������ݲ����ӿ�   ������Ҫʵ�ִ˽ӿ�</p>
 * @author wangjy
 * @version 1.0     
 */
public interface IElevator {
	
	/**
	 * <p>������������</p>
	 * @param mac �豸mac��ַ
	 * @param password ����
	 * @param phone �ֻ���
	 * @param floor ¥��
	 * @param timeout ��ʱʱ��
	 * @param resultCallback �ص�����
	 * @return ����״̬
	 */
	int ctrlElevator(String mac, String password, String phone, int floor, int timeout,ResultCallback resultCallback);
}
