package com.greenland.btsdk.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.greenland.btsdk.bean.ResourceKey;

/**
 * <p>蓝牙设备类</p>  
 * @author wangjy
 * @version 1.0
 */
public class DeviceInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3871416881825129251L;
	private Integer id; //设备id
	private String name;//设备名
	private Integer typeId;// 类型id
	private String typeName; //类型名
	private Integer buildingId; //楼栋id
	private String buildingName; //楼栋名称
	//设备mackey
	private List<ResourceKey> keys;
	
	/**
	 * <p>得到设备资源主键id</p>   
	 * @return Integer      
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * <p>设置资源主键id</p>   
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * <p>得到设备资源名称，可用来显示</p>   
	 * @return String      
	 */
	public String getName() {
		return name;
	}
	/**
	 * <p>设置设备资源名称</p>   
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * <p>得到设备资源类型id</p>   
	 * @return Integer      
	 */
	public Integer getTypeId() {
		return typeId;
	}
	/**
	 * <p>设置设备资源类型id</p>   
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/**
	 * <p>得到设备资源类型名称</p>   
	 * @return String      
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * <p>设置设备资源类型名称</p>   
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * <p>得到楼栋名称</p>   
	 * @return String      
	 */
	public String getBuildingName() {
		return buildingName;
	}
	/**
	 * <p>设置楼栋名称</p>   
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	/**
	 * <p>得到楼栋id</p>   
	 * @return Integer      
	 */
	public Integer getBuildingId() {
		return buildingId;
	}
	/**
	 * <p>设置楼栋id</p>   
	 */
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	public List<ResourceKey> getKeys() {
		return keys;
	}
	public void setKeys(List<ResourceKey> keys) {
		this.keys = keys;
	}
}
