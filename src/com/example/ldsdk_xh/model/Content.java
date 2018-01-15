package com.example.ldsdk_xh.model;

import java.util.List;

import com.example.ldsdk_xh.bean.ResourceData;

public class Content {
	
	private List<ResourceData> resourceDatas;
	private String name;
	private Integer id;
	
	public List<ResourceData> getResourceDatas() {
		return resourceDatas;
	}
	public void setResourceDatas(List<ResourceData> resourceDatas) {
		this.resourceDatas = resourceDatas;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
