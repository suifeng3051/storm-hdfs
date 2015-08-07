package com.fsti.flmnt.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * 类描述： 字典映射工具(用于数据转换)
 * 
 */
public class DictionaryMappingUtil {

	/**
	 * 省份属性映射文件
	 */
	private static final String PROVINCE_FILE_PATH = "/province_mapping.properties";
	/**
	 * 业务类型映射文件
	 */
	private static final String BUSINESS_FILE_PATH = "/business_type_mapping.properties";
	/**
	 * 业务类型Key
	 */
	public static final String BUSINESSTYPE_KEY = "BUSINESSTYPE_KEY";
	/**
	 * 省份编码Key
	 */
	public static final String PROVINCECODE_KEY = "PROVINCECODE_KEY";
	/**
	 * 结果数据结合（暂时只包括业务类型：bussinessType和省份编码）
	 */
	private static Map<String, Object> resultMap;

	/**
	 * 字典转换
	 * 
	 * @param savaPath
	 *            文件保存路径
	 */
	public static Map<String, Object> transfer(String savaPath) {

		try {
			if (resultMap == null) {
				resultMap = new HashMap<String, Object>();
			}

			if (savaPath != null) {
				/******************* 匹配省份开始 *******************/
				int tempIndex = savaPath.lastIndexOf('/');
				String tempStr = savaPath.substring(0, tempIndex);
				int provinceIndex = tempStr.lastIndexOf('/');
				// 保存省份编码
				String provinceCode = null;
				// 保存业务类型代码
				String businessCode = null;
				if (provinceIndex != -1) {
					// 省份对应子串
					String provinceStr = savaPath.substring(provinceIndex + 1,
							tempIndex);
					provinceCode = matchProvince(provinceStr);
					if (provinceCode == null
							|| provinceCode.trim().length() == 0) {
						provinceCode = "998";
					}

					// 保存省份编码
					resultMap.put(PROVINCECODE_KEY, provinceCode);
					System.out.println("省份编码为：" + provinceCode);

					/*********************** 匹配省份结束 ******************/

					/**************** 匹配业务类型开始 *********************/
					// 业务编码对应子串
					String businessTypeStr = null;
					// ②匹配业务类型business_type
					// 判断是否为全国,是则直接按最后一个'/'进行业务编码截取
					if ("998".equals(provinceCode.trim())) {
						businessTypeStr = provinceStr;
						businessCode = matchBusinessType(businessTypeStr);
					} else {// 否则按第二个'/'到最后一个'/'之间进行截取业务子串
							// 获得业务编码首地址
						int startIndex = savaPath.indexOf('/', 1);
						int endIndex = provinceIndex;
						// 截取业务类型
						businessTypeStr = savaPath.substring(startIndex + 1,
								endIndex);
						businessCode = matchBusinessType(businessTypeStr);
					}

					// 保存业务
					resultMap.put(BUSINESSTYPE_KEY, businessCode);
					System.out.println("业务类型编码为：" + businessCode);

					/**************** 匹配业务类型结束 *********************/
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	private static Properties provincePool;
	private static Properties businessTypePool;

	/**
	 * 匹配业务类型编码
	 * 
	 * @param businessTypeStr
	 */
	private static String matchBusinessType(String businessTypeStr) {
		String businessCode = null;
		if (businessTypePool == null) {
			businessTypePool = PropertiesUtil.getProperties(BUSINESS_FILE_PATH);
		}
		if (businessTypeStr != null && businessTypeStr.trim().length() > 0) {
			businessCode = businessTypePool.getProperty(businessTypeStr);
		}
		return businessCode;
	}

	/**
	 * 匹配省份编码
	 * 
	 * @param provinceStr
	 * @return
	 */
	private static String matchProvince(String provinceStr) {
		String provinceCode = null;
		if (provincePool == null) {
			provincePool = PropertiesUtil.getProperties(PROVINCE_FILE_PATH);
		}
		if (provinceStr != null) {
			provinceCode = provincePool.getProperty(provinceStr.trim());
		}
		return provinceCode;
	}

	/**
	 * 根据物理文件名获取资产名称
	 * 
	 * @return
	 */
	public static String getName(String physicalName, String businessType) {
		// 资产名称
		String assetName = "";
		if (businessType != null && businessType.trim().length() > 0) {
			if ("A0009".equals(businessType) || "A0005".equals(businessType)
					|| "A0010".equals(businessType)
					|| "A0009".equals(businessType)
					|| "B0005".equals(businessType)) {
				assetName = businessType;
			} else if ("A0007".equals(businessType)) {
				assetName = "LDAPD_YUN_VSOP_PHONE_NBR_MODIFY";
			} else if ("A0002".equals(businessType)) {
				assetName = "http_get";
			} else if ("A0001".equals(businessType)) {
				// 从A0001获取资产名
				assetName = getNameFromA001(physicalName);
			} else if ("A0003".equals(businessType)) {
				// 从A0003获取资产名
				assetName = getNameFromA003(physicalName);
			} else if ("A0004".equals(businessType)
					|| "A0006".equals(businessType)) {
				// 从A0004或A0006获取资产名
				assetName = getNameFromA004(physicalName);
			} else if ("A0008".equals(businessType)) {
				// 从A0008获取资产名
				assetName = getNameFromA008(physicalName);
			}
		}

		return assetName;
	}

	/**
	 * 从A0008获取资产名
	 * 
	 * @param physicalName
	 * @return
	 */
	private static String getNameFromA008(String physicalName) {
		// QAS_ACTIVITY_1_20150620_00_151.dat.gz QAS_开头，后面单个单词为资产英文名称
		// QAS_IOS_DETAIL_1_20150620_00_157.dat.gz QAS_IOS_开头，后面单个单词为资产英文名称
		// UserLogon20150529.tar.gz 时间前为资产英文名称
		String assetName = "";
		String[] splitArray = null;
		String regex = "[a-zA-Z]+[\\w]*[\\d]+\\.tar\\.gz";
		// 判断是否以QAS_IOS_开头
		if (physicalName.startsWith("QAS_IOS_")) {
			splitArray = physicalName.split("_");
			if (splitArray.length > 0) {
				assetName = splitArray[2];
			}
			// 判断是否以QAS_开头
		} else if (physicalName.startsWith("QAS_")) {
			splitArray = physicalName.split("_");
			if (splitArray.length > 0) {
				assetName = splitArray[1];
			}
			// 判断是否以UserLogon20150529.tar.gz存在
		} else if (physicalName.matches(regex)) {
			splitArray = physicalName.split("[\\d]{6,}");
			if (splitArray.length > 0) {
				assetName = splitArray[0];
			}
		}
		return assetName;
	}

	/**
	 * 从A0004获取资产名
	 * 
	 * @param physicalName
	 * @return
	 */
	private static String getNameFromA004(String physicalName) {
		// 统一为第二个_前为资产名称
		String assetName = "";
		// 获取首个"_"的索引
		int firstIndex = physicalName.indexOf("_");
		int secondIndex = -1;
		if (firstIndex != -1) {
			secondIndex = physicalName.indexOf("_", firstIndex + 1);
			// 获取资产名
			assetName = physicalName.substring(0, secondIndex);
		}
		return assetName;
	}

	/**
	 * 获取第一个"_"和第二个"_"之间的子串; 如果为：01 call_scene 02 sms_scene 03 position_scene
	 * 
	 * @param physicalName
	 * @return
	 */
	private static String getNameFromA003(String physicalName) {
		String assetName = "";
		int startIndex = physicalName.indexOf("_");
		int endIndex = -1;
		if (startIndex != -1) {
			endIndex = physicalName.indexOf("_", startIndex + 1);
			if (endIndex != -1) {
				// 获取第一个"_"和第二个"_"子串
				assetName = physicalName.substring(startIndex + 1, endIndex);
				if (assetName != null && assetName.trim().length() > 0) {
					if ("01".equals(assetName)) {
						assetName = "call_scene";
					} else if ("02".equals(assetName)) {
						assetName = "sms_scene";
					} else if ("03".equals(assetName)) {
						assetName = "position_scene";
					}
				}
			}
		}
		return assetName;
	}

	/**
	 * 从A001获取中获取资产名
	 * 
	 * @param physicalName
	 * @return
	 */
	private static String getNameFromA001(String physicalName) {
		// physicalName的第一个.前为资产英文名称
		String assetName = "";
		int assetIndex = physicalName.indexOf(".");
		// 判断索引是否为空
		if (assetIndex != -1) {
			assetName = physicalName.substring(0, assetIndex);
		}
		return assetName;
	}

	public static void main(String[] args) {
		String str = "/itf/dpi/qixin/fujian/200_IF1_02_201506291141_475.gz";
		String str1 = "/itf/qixin/term/dest_work/~AWS_TF_TML_PROD_INFO_FULL.20150627.20150626.00.001.001.000.VAL@2193420150627012421010001002";
		String str2 = "/itf/oidd/original/02/Notice_03_201506281959_000.txt";

		Map<String, Object> transfer = DictionaryMappingUtil.transfer(str2);

		String businessType = null;
		try {
			businessType = transfer.get(DictionaryMappingUtil.BUSINESSTYPE_KEY)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("业务类型：" + businessType);
		// String physicalName = "LDAPD_YUN_VSOP_PHONE_NBR_MODIFY";
		// String name = DictionaryMappingUtil.getName(physicalName, "A0007");
		// if (name != null) {
		// System.out.println("资产名为：" + name);
		// }
	}
}
