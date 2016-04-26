package com.missingvia.slamduck.common.upload;

/**
 * 上传配置的基本属性。
 * 
 */
public class UploadBasicProperties {
	
	/**上传的虚拟目录，如果不指定，则认为是提供上传应用的虚拟目录。*/
	private String contextPath;
	
	/**提供上传应用的web根目录，真实路径，如：c:/web*/
	private String webRoot;
	
	/**文件存储的根目录，真实路径，如：c:/upload*/
	private String saveRoot;
	
	/**截图软件的路径文件名，真实路径，如：c:/ffmpeg.exe*/
	private String ffmpegPathname;
	
	/**CutyCapt软件的执行命令，提供两个占位符，分别表示输入和输出文件*/
	private String cutycaptCommand;
	
	/**上传用临时目录，真实路径，如：c:/temp*/
	private String tempPath;

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getWebRoot() {
		return webRoot;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

	public String getSaveRoot() {
		return saveRoot;
	}

	public void setSaveRoot(String saveRoot) {
		this.saveRoot = saveRoot;
	}

	public String getFfmpegPathname() {
		return ffmpegPathname;
	}

	public void setFfmpegPathname(String ffmpegPathname) {
		this.ffmpegPathname = ffmpegPathname;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	
	
	public String getCutycaptCommand() {
		return cutycaptCommand;
	}

	public void setCutycaptCommand(String cutycaptCommand) {
		this.cutycaptCommand = cutycaptCommand;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("contextPath=" + getContextPath());
		sb.append(",webRoot=" + getWebRoot());
		sb.append(",saveRoot=" + getSaveRoot());
		sb.append(",ffmpegPathname=" + getFfmpegPathname());
		sb.append(",cutycaptCommand=" + getCutycaptCommand());
		sb.append(",tempPath=" + getTempPath());
		return sb.toString();
	}
	
	
	
	

}
