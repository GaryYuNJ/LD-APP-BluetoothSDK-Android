/**  
 * @Title:  WebReturnData.java   
 * @Package com.example.ldsdk_xh.model   
 * @Description:    TODO(��һ�仰�������ļ���ʲô)   
 * @author: wangjy  
 * @date:   2017��10��24�� ����5:02:56   
 * @version V1.0 
 */  
package com.greenland.btsdk.model;

import java.util.List;

/**   
 * @ClassName:  WebReturnData   
 * @Description:�ӿڷ��صĶ�����
 * @author: wangjy
 * @date:   2017��10��24�� ����5:02:56   
 *      
 */
public class WebReturnData {
	
	private List<Content> content;
	private String message;
	private Integer status;
	public List<Content> getContent() {
		return content;
	}
	public void setContent(List<Content> content) {
		this.content = content;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	} 
	
}
