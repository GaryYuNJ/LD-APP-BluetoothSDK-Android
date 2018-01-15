/**  
 * @Title:  WebReturnData.java   
 * @Package com.example.ldsdk_xh.model   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: wangjy  
 * @date:   2017年10月24日 下午5:02:56   
 * @version V1.0 
 */  
package com.example.ldsdk_xh.model;

import java.util.List;

/**   
 * @ClassName:  WebReturnData   
 * @Description:接口返回的堆数据
 * @author: wangjy
 * @date:   2017年10月24日 下午5:02:56   
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
