package com.greenland.btsdk.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.greenland.btsdk.bean.ResourceKey;

/**
 * <p>�����豸��</p>  
 * @author wangjy
 * @version 1.0
 */
public class DeviceInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3871416881825129251L;
	private Integer id; //�豸id
	private String name;//�豸��
	private Integer typeId;// ����id
	private String typeName; //������
	private Integer buildingId; //¥��id
	private String buildingName; //¥������
	//�豸mackey
	private List<ResourceKey> keys;
	
	/**
	 * <p>�õ��豸��Դ����id</p>   
	 * @return Integer      
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * <p>������Դ����id</p>   
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * <p>�õ��豸��Դ���ƣ���������ʾ</p>   
	 * @return String      
	 */
	public String getName() {
		return name;
	}
	/**
	 * <p>�����豸��Դ����</p>   
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * <p>�õ��豸��Դ����id</p>   
	 * @return Integer      
	 */
	public Integer getTypeId() {
		return typeId;
	}
	/**
	 * <p>�����豸��Դ����id</p>   
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/**
	 * <p>�õ��豸��Դ��������</p>   
	 * @return String      
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * <p>�����豸��Դ��������</p>   
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * <p>�õ�¥������</p>   
	 * @return String      
	 */
	public String getBuildingName() {
		return buildingName;
	}
	/**
	 * <p>����¥������</p>   
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	/**
	 * <p>�õ�¥��id</p>   
	 * @return Integer      
	 */
	public Integer getBuildingId() {
		return buildingId;
	}
	/**
	 * <p>����¥��id</p>   
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
