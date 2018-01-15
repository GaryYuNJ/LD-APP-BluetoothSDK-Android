package com.example.ldsdk_xh.bean;

import java.util.List;

/**
 * @ClassName:  ResourceData   
 * @Description:资源类  
 * @author: wangjy
 * @date:   2017年10月23日 下午7:19:28   
 *
 */
public class ResourceData {
	
	private Integer id;
	private String name;
	private Integer typeId;
	private String typeName;
	private String status;
	private Long createDate;
	private String createDateStr;
	private Integer createUser;
	private Integer buildingId;
	private String buildingName;
	private Integer floor;
	private Integer sequence;
	private String shareEnable;
	private String nodePath;
	private String isVirtualResource;
	private String permissionAttrId;
	private Integer deviceType;
	private String nodeId;
	private String nodeName;
	private List<ResourceKey> resourceKeys;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	public Integer getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getShareEnable() {
		return shareEnable;
	}
	public void setShareEnable(String shareEnable) {
		this.shareEnable = shareEnable;
	}
	public String getNodePath() {
		return nodePath;
	}
	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	public String getIsVirtualResource() {
		return isVirtualResource;
	}
	public void setIsVirtualResource(String isVirtualResource) {
		this.isVirtualResource = isVirtualResource;
	}
	public String getPermissionAttrId() {
		return permissionAttrId;
	}
	public void setPermissionAttrId(String permissionAttrId) {
		this.permissionAttrId = permissionAttrId;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public List<ResourceKey> getResourceKeys() {
		return resourceKeys;
	}
	public void setResourceKeys(List<ResourceKey> resourceKeys) {
		this.resourceKeys = resourceKeys;
	}
	
	
}
