package com.missingvia.slamduck.common.service;

/**
 * 错误码。
 *
 */
public enum ErrorCode {
	
	/**参数不合法。如必须传递的参数没有传递、参数值超过范围等。*/
	ILLEGAL_ARGUMENT(1400),
	
	/**没有验证。用户没有登录状态，可能是尚未登录，也可能是登录已失效。*/
	UNAUTHORIZED(1401),
	
	/**禁止访问错误*/
	FORBIDDEN(1403),
	
	/**找不到请求的数据*/
	NOT_FOUND(1404),
	
	/**系统内部错误*/
	INTERNAL_SERVER_ERROR(1500),
	
	
	// ------------------ 媒体模板类错误 ------------------ //
	/**模板名称错误*/
	MEDIA_TEMPLATE_NAME_EXIST(5010),
	
	/**该模板不存在*/
	MEDIA_TEMPLATE_NOT_FOUND(5011),
	
	/**未设置发布设备*/
	MEDIA_DEVICES_NOT_SETTING(5020),
	
	// ------------------ 系统管理类错误 ------------------ //
	/**用户名重复*/
	SYSTEM_USER_NAME_REPEAT(3000),
	
	/**用户名不存在*/
	SYSTEM_USER_NAME_NOTFOUND(3001),
	
	/**角色名重复*/
	SYSTEM_ROLE_NAME_REPEAT(3002),
	
	/**角色有关联的用户不允许删除**/
	SYSTEM_USER_ROLE_EXIST(3003),
	
	/**用户是超户不允许删除**/
	SYSTEM_USER_CANT_DELETE(3004),
	
	/**用户名或密码错误*/
	SYSTEM_USER_PASSWORD_WRONG(6011),
	
	/**尚未登录或者登录信息过期*/
	UN_LOGIN_OR_LOGIN_EXPIRED(6012),
	
	/**获取上传文件失败*/
	GET_UPLOAD_FILE_FAILED(6015),
	
	/**当前已是最新版本*/
	IS_NEWEST_VERSION(6081),
	
	/**网点柜员工号重复*/
	SYSTEM_TELLER_CODE_REPEAT(6090),
	
	/**业务类型编号重复*/
	SYSTEM_BIZ_TYPE_CODE_REPEAT(6091),
	
	/**业务类型编号正在使用*/
	SYSTEM_BIZ_TYPE_CODE_IN_USE(6093),
	
	/**网点排队机压力指标重复*/
	SYSTEM_QUEUE_PRESSURE_REPEAT(6092);
	
	
	
	
	

	/**错误码值*/
	private int code = 0;
	
	private ErrorCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	/**
	 * 通过错误码值创建对象。
	 * @param code 错误码值。
	 * @return 错误码对象。
	 */
	public static ErrorCode fromCode(int code) {
		for(ErrorCode errorCode : ErrorCode.values()) {
			if(errorCode.getCode() == code) {
				return errorCode;
			}
		}
		throw new IllegalArgumentException("没有定义错误码(" + code + ")");
	}
	
	public static void main(String[] args) {
		System.out.println(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
	}
	
}
