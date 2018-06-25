package com.greenland.btsdk.interfaces;

import com.greenland.btsdk.callback.ResultCallback;

public interface IEscalator{
	
	/**
	 * <p>�����Զ���������</p>
	 * @param mac �豸mac��ַ
	 * @param password ����
	 * @param phone �ֻ���
	 * @param upOrDown �ϻ����� 0:up,1:down
	 * @param timeout ��ʱʱ��
	 * @param resultCallback �ص�����
	 * @return ����״̬
	 */
	int ctrlEscalator(String mac,String password,String phone,int upOrDown,int timeout,ResultCallback resultCallback);
}
