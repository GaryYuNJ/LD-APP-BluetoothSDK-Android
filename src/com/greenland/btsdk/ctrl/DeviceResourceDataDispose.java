package com.greenland.btsdk.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.greenland.btsdk.bean.ResourceData;
import com.greenland.btsdk.bean.ResourceKey;
import com.greenland.btsdk.model.Content;
import com.greenland.btsdk.model.DeviceInfo;
import com.greenland.btsdk.model.WebReturnData;
import com.greenland.btsdk.utils.Constant;
import com.greenland.btsdk.utils.DeviceConstant;
import com.greenland.btsdk.utils.OkHttpUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * ����������Դ����
 * 
 * @author wangjy
 *
 */
public class DeviceResourceDataDispose {

	private static String GET_PUBLIC_RESOURCE_URL = "http://43.254.53.219:8080/LD-PermissionSystem/appApi/queryPubResByBuildingId";
	private static String GET_PRIVATE_RESOURCE_URL = "http://43.254.53.219:8080/LD-PermissionSystem/appApi/queryPrivateResByBIdAndMobile";
	private static String GET_ALL_RESOURCE_URL = "http://auth.greenlandjs.com:8099/LD-PermissionSystem/appApi/queryAvaiableResByMobile";
	public static String TOKEN_KEY = "123qweASDzxc";

	/**
	 * @Title: queryAndSaveAllResourceData @Description:
	 * ��ѯ��Ӧ��Դ��Ϣ�б����ڻ����� @param: @param buildingId ������ѯ������Դ @param: @param
	 * mobile @param: @return @return: boolean true ��ʾ����ɹ� false ����ʧ��
	 * ��ʾ���� @throws
	 */
	public static Map<String, Object> cacheDeviceData(Context context, Integer buildingId, String mobile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isSuccess", false);
		resultMap.put("isFresh", false);
		SharedPreferences sharedPreferences = context.getSharedPreferences("resource-datas",
				Context.MODE_PRIVATE);
		SharedPreferences sharedPreferences2 = context.getSharedPreferences("resource-datas-all",
				Context.MODE_PRIVATE);
		String devicesStr = sharedPreferences2.getString(mobile+"alldata", Constant.UN_USEFUL);

		String buildingIdStr = "";
		if (buildingId != null) {
			buildingIdStr = buildingId.toString();
		}
		/*
		 * String pubJson = getPubResourceData(buildingIdStr); String priJson =
		 * getPriResourceData(mobile, buildingIdStr);
		 */
		String allJson = getAllResourceData(mobile, buildingIdStr);
		Gson gson = new Gson();
		WebReturnData webReturnDatas = gson.fromJson(allJson, WebReturnData.class);
		if (webReturnDatas == null || webReturnDatas.getStatus() != 1) {// �����������������粻��
			// ��ʾ�Ѿ���������Ͳ��û����˻�����������
			if (!sharedPreferences.getAll().isEmpty()) {
				resultMap.put("isSuccess", true);
				resultMap.put("devices", devicesStr);
				return resultMap;
			}
			if (webReturnDatas != null) {
				Log.e("sdk�������ݴ���״̬", webReturnDatas.getStatus().toString());
			}
			return resultMap;
		}

		Editor edit2 = sharedPreferences2.edit();
		edit2.putString(mobile+"alldata", gson.toJson(webReturnDatas));
		edit2.commit();
		List<Content> contents = webReturnDatas.getContent();

		Editor edit = sharedPreferences.edit();
		for (Content content : contents) {
			List<ResourceData> resourceDatas = content.getResourceDatas();
			if (resourceDatas != null && resourceDatas.size() > 0) {
				for (ResourceData resourceData : resourceDatas) {
					List<ResourceKey> resourceKeys = resourceData.getResourceKeys();
					for (ResourceKey resourceKey : resourceKeys) {
						String json = gson.toJson(resourceData);
						edit.putString(mobile+resourceKey.getMac(), json);
					}
				}
			}
		}
		boolean commit = edit.commit();
		if (!commit) {
			Log.e("sdk�������ݴ���״̬", "�������ݵ�����ʧ��");
		}
		resultMap.put("isFresh", true);
		resultMap.put("devices", sharedPreferences2.getString(mobile+"alldata", Constant.UN_USEFUL));
		resultMap.put("isSuccess", true);
		return resultMap;
	}

	/**
	 * �鿴�豸ɨ�������mac�Ƿ��ڻ�����
	 * 
	 * @param device
	 * @return ��������õķ�����Դ���ݣ����ǵķ���null
	 */
	public static DeviceInfo isUsefulDevice(final Context context, String mac, String mobile) {
		Gson gson = new Gson();
		SharedPreferences preferences = context.getSharedPreferences("resource-datas", Context.MODE_PRIVATE);
		String result = preferences.getString(mobile+mac, Constant.UN_USEFUL);
		if (!Constant.UN_USEFUL.equals(result)) {
			ResourceData resourceData = gson.fromJson(result, ResourceData.class);
			DeviceInfo deviceInfo = new DeviceInfo();
			Integer deviceId = resourceData.getId();
			deviceInfo.setId(deviceId);
			deviceInfo.setName(resourceData.getName());
			deviceInfo.setTypeId(resourceData.getDeviceType());
			deviceInfo.setTypeName(resourceData.getTypeName());
			deviceInfo.setBuildingId(resourceData.getBuildingId());
			deviceInfo.setBuildingName(resourceData.getBuildingName());
			List<ResourceKey> resourceKeys = resourceData.getResourceKeys();
			for (ResourceKey resourceKey : resourceKeys) {
				if (resourceKey.getMac().equals(mac)) {
					List<ResourceKey> keys = new ArrayList<ResourceKey>();
					keys.add(resourceKey);
					deviceInfo.setKeys(keys);
					return deviceInfo;
				}
			}
		}
		return null;
	}

	/**
	 * �õ������豸��json
	 * 
	 * @param buildingId
	 * @param token
	 * @return
	 */
	private static String getPubResourceData(String buildingId) {
		String token = DigestUtils.md5Hex("buildingId=" + buildingId + "&" + TOKEN_KEY);
		GET_PUBLIC_RESOURCE_URL += "?buildingId=" + buildingId + "&token=" + token;
		String json = "";
		try {
			json = OkHttpUtil.doGet(GET_PUBLIC_RESOURCE_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	private static String getPriResourceData(String mobile, String buildingId) {
		SortedMap<String, String> sortParams = new TreeMap<String, String>();
		StringBuffer encodeStr = new StringBuffer();
		sortParams.put("mobile", mobile);
		// ����
		sortParams.put("buildingId", buildingId);
		// ��ȡҪ���ܵ�string
		Set es = sortParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {

			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v)) {
				encodeStr.append(k + "=" + v + "&");
			}
		}
		encodeStr.append(TOKEN_KEY);
		String token = DigestUtils.md5Hex(encodeStr.toString());
		GET_PRIVATE_RESOURCE_URL += "?mobile=" + mobile + "&buildingId=" + buildingId + "&token=" + token;
		String json = "";
		try {
			json = OkHttpUtil.doGet(GET_PRIVATE_RESOURCE_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * @Title: getAllResourceData @Description: �õ�����˽�к͹��е��豸 @param: @param
	 * mobile @param: @param buildingId @param: @return @return: String @throws
	 */
	private static String getAllResourceData(String mobile, String buildingId) {
		SortedMap<String, String> sortParams = new TreeMap<String, String>();
		StringBuffer encodeStr = new StringBuffer();
		sortParams.put("mobile", mobile);
		// ����
		sortParams.put("buildingId", buildingId);
		// ��ȡҪ���ܵ�string
		Set es = sortParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {

			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v)) {
				encodeStr.append(k + "=" + v + "&");
			}
		}
		encodeStr.append(TOKEN_KEY);
		String token = DigestUtils.md5Hex(encodeStr.toString());
		String url = GET_ALL_RESOURCE_URL;
		url += "?mobile=" + mobile + "&buildingId=" + buildingId + "&token=" + token;
		String json = "";
		try {
			json = OkHttpUtil.doGet(url);
		} catch (IOException e) {
			// ��ʾ����ʧ��
			Log.e("getAllResourceData", "��ȡweb�ӿ�����ʧ�ܣ���������");
			return null;
		}
		return json;
	}

	/**
	 * �����ֻ���ɾ���ļ�
	 * 
	 * @param mobile
	 */
	public static boolean cleanCacheDeviceData(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("resource-datas",
				Context.MODE_PRIVATE);
		SharedPreferences sharedPreferences2 = context.getSharedPreferences("resource-datas-all",
				Context.MODE_PRIVATE);
		boolean commit1 = sharedPreferences.edit().clear().commit();
		boolean commit2 = sharedPreferences2.edit().clear().commit();
		return commit2;
	}

}
