package com.missingvia.slamduck.common.upload;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件上传配置属性。
 * 
 */
public class UploadProperties {

	/** 允许的文件扩展名 */
	private String[] allowedExts;
	/** 不允许的文件扩展名 */
	private String[] unallowedExts;
	/** 最大大小，单位KB */
	private Long maxSize;
	
	/** 文件存储的相对路径（匹配模式） */
	private String relativePathPattern;
	
	/**上传基本配置属性*/
	private UploadBasicProperties basicProperties;
	
	public String[] getAllowedExts() {
		return allowedExts;
	}

	public void setAllowedExts(String[] allowedExts) {
		this.allowedExts = allowedExts;
	}

	public String[] getUnallowedExts() {
		return unallowedExts;
	}

	public void setUnallowedExts(String[] unallowedExts) {
		this.unallowedExts = unallowedExts;
	}

	public Long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * 获取文件保存的真实路径，如果路径中包含日期格式符，则将被替换成当前日期。
	 * @return 文件保存的真实路径。
	 */
	public String getSavePath() {
		return getSavePath(getRelativePath());
	}
	
	/**
	 * 根据相对路径获取文件保存的真实路径，如果路径中包含日期格式符，则将被替换成当前日期。
	 * @param 相对路径。
	 * @return 文件保存的真实路径。
	 */
	public String getSavePath(String relativePath) {
		String saveRoot = basicProperties.getSaveRoot();
		if(StringUtils.isBlank(relativePath)) {
			return saveRoot;
		} else {
			relativePath = UploadConfig.formatPath(relativePath);
			File file = new File(saveRoot,relativePath);
			return file.getPath();
		}
	}

	/**
	 * 获取上传的相对路径，如果路径中包含日期格式符，则将被替换成当前日期。
	 * @return 相对路径。
	 */
	public String getRelativePath() {
		if(StringUtils.isBlank(getRelativePathPattern())) {
			return null;
		}
		return UploadConfig.formatPath(getRelativePathPattern().trim());
	}
	

	public UploadBasicProperties getBasicProperties() {
		return basicProperties;
	}

	public void setBasicProperties(UploadBasicProperties basicProperties) {
		this.basicProperties = basicProperties;
	}

	String getRelativePathPattern() {
		return relativePathPattern;
	}

	void setRelativePathPattern(String relativePathPattern) {
		this.relativePathPattern = relativePathPattern;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (getAllowedExts() != null && getAllowedExts().length > 0) {
			sb.append("allowedExts=" + StringUtils.join(getAllowedExts(), ","));
		} else {
			sb.append("allowedExts=null");
		}
		sb.append(" ,");
		if (getUnallowedExts() != null && getUnallowedExts().length > 0) {
			sb.append("unallowedExts="
					+ StringUtils.join(getUnallowedExts(), ","));
		} else {
			sb.append("unallowedExts=null");
		}
		sb.append(" ,");
		sb.append("maxSize=" + getMaxSize());
		sb.append(" ,");
		sb.append("savePath=" + getSavePath());
		sb.append(" ,");
		sb.append("relativePath=" + getRelativePath());
		return sb.toString();
	}// end toString
	
}
