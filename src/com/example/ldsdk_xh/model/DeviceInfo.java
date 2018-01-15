package com.example.ldsdk_xh.model;

/**
 * <p>蓝牙设备类</p>  
 * @author wangjy
 * @version 1.0
 */
public class DeviceInfo {
	private Integer id;
	private String name;
	private Integer typeId;
	private String typeName;
	private String mac;
	private Integer manufacturerId;
	private String password;
	private Integer buildingId;
	private String buildingName;
	
	/**
	 * <p>得到设备厂商id</p>   
	 * @return Integer      
	 */
	public Integer getManufacturerId() {
		return manufacturerId;
	}
	/**
	 * <p>设置设备厂商id</p>   
	 */
	public void setManufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
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
	 * <p>得到设备资源mac地址</p>   
	 * @return String      
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * <p>设置设备资源mac地址</p>   
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * <p>得到设备资源操作密码</p>   
	 * @return String      
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * <p>设置设备资源操作密码</p>   
	 */
	public void setPassword(String password) {
		this.password = password;
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
	
}
