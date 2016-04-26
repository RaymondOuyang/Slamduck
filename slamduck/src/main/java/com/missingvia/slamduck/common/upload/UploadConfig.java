package com.missingvia.slamduck.common.upload;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import com.missingvia.slamduck.common.util.Constants;

/**
 * 上传配置读取入口。
 * 
 */
public class UploadConfig {

	/** 上传文件分类：素材-视频 */
	public static final String TYPE_VIDEO = "video";

	/** 上传文件分类：素材-音频 */
	public static final String TYPE_AUDIO = "audio";

	/** 上传文件分类：素材-图片 */
	public static final String TYPE_IMAGE = "image";
	
	/** 上传文件分类：素材-文本 */
	public static final String TYPE_TEXT = "text";

	/** 上传文件分类：应用 */
	public static final String TYPE_APP = "app";
	
	/** 文件分类：LED图片 */
	public static final String TYPE_LEDIMAGE = "ledimage";

	/** 上传基本配置 */
	private static UploadBasicProperties basicProperties;
	
	/** 各文件上传配置 */
	private static Map<String, UploadProperties> mapProperties = new HashMap<String, UploadProperties>();
	

	static {
		Properties properties = ConfigReader
				.loadXMLProperties(new String[] { Constants.UPLOAD_CONFIG_FILE });
		load(properties);
	}

	private UploadConfig() {

	}
	
	/**
	 * 获取基本配置信息。
	 * @return 基本配置信息。
	 */
	public static UploadBasicProperties getBasicProperties() {
		return basicProperties;
	}

	/**
	 * 获取素材-视频上传配置。
	 * 
	 * @return 素材-视频上传配置。
	 */
	public static UploadProperties getVideoProperties() {
		return getProperties(TYPE_VIDEO);
	}

	/**
	 * 获取素材-音频上传配置。
	 * 
	 * @return 素材-音频上传配置。
	 */
	public static UploadProperties getAudioProperties() {
		return getProperties(TYPE_AUDIO);
	}

	/**
	 * 获取素材-图片上传配置。
	 * 
	 * @return 素材-图片上传配置。
	 */
	public static UploadProperties getImageProperties() {
		return getProperties(TYPE_IMAGE);
	}
	
	/**
	 * 获取素材-文本上传配置。
	 * 
	 * @return 素材-文本上传配置。
	 */
	public static UploadProperties getTextProperties() {
		return getProperties(TYPE_TEXT);
	}

	/**
	 * 获取应用上传配置。
	 * 
	 * @return 应用上传配置。
	 */
	public static UploadProperties getAppProperties() {
		return getProperties(TYPE_APP);
	}
	
	/**
	 * 获取LED图片存储配置。
	 * 
	 * @return LED图片存储配置。
	 */
	public static UploadProperties getLedImageProperties() {
		return getProperties(TYPE_LEDIMAGE);
	}
	

	/**
	 * 获取指定类型的配置。
	 * 
	 * @param type
	 *            video,audio...
	 * @return 配置信息。
	 */
	public static UploadProperties getProperties(String type) {
		return mapProperties.get(type);
	}

	/**
	 * 获取所有类型的配置。
	 * 
	 * @return 类型(video,audio...)与配置的对应关系。
	 */
	public static Map<String, UploadProperties> getAllProperties() {
		return mapProperties;
	}

	/**
	 * 通过文件后缀名获取素材类型。
	 * 
	 * @param ext
	 *            返回素材类型(video,audio...)，如果后缀名不是任何一种类型则返回null。
	 */
	public static String getMaterialType(String ext) {
		if (StringUtils.isBlank(ext)) {
			return null;
		}
		ext = ext.trim().toLowerCase();
		String[] meterialTypes = new String[] { TYPE_VIDEO, TYPE_AUDIO,
				TYPE_IMAGE,TYPE_TEXT };
		for (String type : meterialTypes) {
			UploadProperties up = getProperties(type);
			if (up != null && up.getAllowedExts() != null
					&& ArrayUtils.contains(up.getAllowedExts(), ext)) {
				return type;
			}
		}
		return null;
	}// end getMaterialType
	
	/**
	 * 将包含日期格式符的路径格式化，将日期格式符替换成当前日期。
	 * @param pattern 包含日期格式符的路径，如：upload/{yyyy}/{yyyy-MM-dd}
	 * @return 日期格式符替换成当前日期的路径，如：upload/2014/2014-08-08
	 */
	public static String formatPath(String pattern) {
		int fromIndex = 0;
		int index = 0;
		int i = 0; 
		StringBuffer sb = new StringBuffer();
		while( (index = pattern.indexOf("{", fromIndex)) >= 0) {
			sb.append(pattern.substring(fromIndex,index + 1));
			sb.append(i + ",date,");
			fromIndex = index + 1;
			i ++;
		}
		if(fromIndex > 0) {
			sb.append(pattern.substring(fromIndex));
			Object[] dates = new Object[i];
			for(int j = 0;j < dates.length;j ++) {
				dates[j] =  new Date();
			}
			return MessageFormat.format(sb.toString(), dates);
		} else {
			return pattern;
		}
	}//end formatPath
	
	
	/**
	 * 记载所有配置。
	 * @param properties
	 */
	private static void load(Properties properties) {
		basicProperties = extractBasicProperties(properties);
		//System.out.println("================= basicProperties  ==================");
		//System.out.println(basicProperties.toString());
		String[] types = new String[] { TYPE_VIDEO, TYPE_AUDIO, TYPE_IMAGE,TYPE_TEXT,
				TYPE_APP,TYPE_LEDIMAGE };
		for (String type : types) {
			mapProperties.put(type, extract(basicProperties,properties,type));
			//System.out.println("================= " + type + " Properties  ==================");
			//System.out.println(mapProperties.get(type).toString());
		}
	}//end load
	
	/**
	 * 提取上传的基本配置。
	 * @param properties
	 * @return
	 */
	private static UploadBasicProperties extractBasicProperties(Properties properties) {
		UploadBasicProperties bp = new UploadBasicProperties();
		bp.setContextPath(properties.getProperty("upload.contextPath"));
		bp.setWebRoot(extractWebRoot(properties));//web根目录
		//提取文件存储根目录
		if (!StringUtils.isBlank(properties.getProperty("upload.saveRoot"))) {
			// 配置指定了存储的根路径
			bp.setSaveRoot(properties.getProperty("upload.saveRoot").trim());

		} else {

			if (StringUtils.isBlank(bp.getContextPath())) {
				// 如果未指定附件的虚拟目录，则说明存储在本项目中
				bp.setSaveRoot(bp.getWebRoot());
			} else {
				// 如果存储在另外一个虚拟目录中，则认为与当前项目是平级的，需要计算绝对路径。
				File file = new File(bp.getWebRoot());
				String path = file.getParent();// web root的上一级目录
				file = new File(path,bp.getContextPath());
				path = file.getPath();
				bp.setSaveRoot(path);
			}
		}
		//提取上传临时目录
		String temp = properties.getProperty("upload.temp");
		File file = new File(bp.getSaveRoot(), temp.trim());
		bp.setTempPath(file.getPath());
		//提取截图软件的文件路径名
		temp = properties.getProperty("upload.ffmpeg");
		file = new File(temp);
		if(file.isAbsolute()) {
			//指定了绝对路径
			bp.setFfmpegPathname(temp);
		} else {
			//如果不是绝对路径，则认为是web应用的相对目录
			file = new File(bp.getWebRoot(), temp.trim());
			bp.setFfmpegPathname(file.getPath());
		}
		//提取CutyCapt程序路径名upload.cutycapt.command
		temp = properties.getProperty("upload.cutycapt.command");
		if(temp != null) {
			temp = temp.trim();
		}
		bp.setCutycaptCommand(temp);
		return bp;
	}//end extractBasicProperties
	
	/**
	 * 获取上传应用的web根路径
	 * @param properties
	 * @return
	 */
	private static String extractWebRoot(Properties properties) {
		String webAppRootKey = properties
				.getProperty("upload.webAppRootKey");
		if (StringUtils.isBlank(webAppRootKey)) {
			throw new IllegalArgumentException(
					"必须指定上传存储的根路径，"
							+ "或者通过upload.webAppRootKey指定webAppRootKey，前提是在web.xml中有相应配置");

		}
		String webRootPath = System.getProperty(webAppRootKey);
		if (StringUtils.isBlank(webRootPath)) {
			throw new IllegalArgumentException(
					"无法通过webAppRootKey("
							+ webAppRootKey
							+ ")获得项目根路径，"
							+ "请确定在web.xml中有相应配置！并且通过Spring WebAppRootListener或类似监听将该值设置到system property中！");

		}
		return webRootPath.trim();
	}//end extractWebRoot

	/**
	 * 计算指定类型的配置信息。
	 * @param basicProperties
	 * @param properties
	 * @param type
	 *            video,audio...
	 * @return
	 */
	private static UploadProperties extract(UploadBasicProperties basicProperties,Properties properties,String type) {
		UploadProperties up = new UploadProperties();
		up.setBasicProperties(basicProperties);
		String temp = properties.getProperty("upload." + type + ".allowedExts");
		if (!StringUtils.isBlank(temp)) {
			String[] a = temp.split(",");
			Arrays.sort(a);
			up.setAllowedExts(a);
		}
		temp = properties.getProperty("upload." + type + ".unallowedExts");
		if (!StringUtils.isBlank(temp)) {
			String[] a = temp.split(",");
			Arrays.sort(a);
			up.setUnallowedExts(a);
		}
		temp = properties.getProperty("upload." + type + ".maxSize");
		if (!StringUtils.isBlank(temp)) {
			up.setMaxSize(Long.parseLong(temp));
		}
		temp = properties.getProperty("upload." + type + ".relativePath");
		up.setRelativePathPattern(temp);
		// System.out.println(up.toString());
		return up;
	}// end UploadConfig

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				

	}

}
