package com.greenland.btsdk.callback;

import com.greenland.btsdk.bean.ResourceKey;

/**
 * <p>�����豸�Ļص����������̶�Ӧ��ʹ�ô˻ص� </p> 
 * @author wangjy
 * @version 1.0
 */
public interface ResultCallback {
	
	/**
	 * <p>�����豸֮����ô˷���</p> 
	 * @param mac mac��ַ
	 * @param result   �������     
	 */
    void onResult(int result,ResourceKey resourceKey);
}
