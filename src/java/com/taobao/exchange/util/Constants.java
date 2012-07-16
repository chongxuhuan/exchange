/**
 * 
 */
package com.taobao.exchange.util;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9
 *
 */
public class Constants {
	
	public final static String PLATFORM_ID_TAOBAO = "taobao";
	public final static String PLATFORM_ID_SINA = "sina";
	
	public final static String EXCEPTION_AUTH_USER_NOT_EXIST = "authUserNotExist"; 
	public final static String EXCEPTION_APINAME_IS_NULL = "apiNameIsNull"; 
	public final static String EXCEPTION_PLATFORMENTRY_NOT_EXIST = "platformEntryNotExist";
	public final static String EXCEPTION_APPCLIENT_NOT_EXIST = "appClientNotExist";
	public final static String EXCEPTION_RELATIONMANAGER_NOT_EXIST = "relationManagerNotExist";
	public final static String EXCEPTION_SECONDHANDMANAGER_NOT_EXIST = "secondhandManagerNotExist";
	public final static String EXCEPTION_CONDITION_IS_NULL = "conditionIsNull";
	public final static String EXCEPTION_SECONDHANDPLATFORM_IS_NULL = "secondhandPlatformIsNull";
	
	public final static String EXCEPTION_SERVICE_ERROR = "error_response";
	
	public final static String SYS_PARAMETER_ACCESS_TOKEN = "access_token";
	public final static String SYS_PARAMETER_VERSION = "v";
	public final static String SYS_PARAMETER_FORMAT = "format";
	public final static String SYS_PARAMETER_APINAME = "method";
	public final static String SYS_PARAMETER_TIMESTAMP = "timestamp";
	public final static String SYS_PARAMETER_APPKEY = "app_key";
	public final static String SYS_PARAMETER_SIGNMETHOD = "sign_method";
	public final static String SYS_PARAMETER_SIGN = "sign";
	public final static String SYS_PARAMETER_UID = "uid";
	
	public final static String SYS_AUTH_PARAMETER_CLIENTID = "client_id";
	public final static String SYS_AUTH_PARAMETER_SECRETCODE = "client_secret";
	public final static String SYS_AUTH_PARAMETER_GRANT = "grant_type";
	public final static String SYS_AUTH_PARAMETER_CODE = "code";
	public final static String SYS_AUTH_PARAMETER_REDIRECT_URI = "redirect_uri";
	public final static String SYS_AUTH_PARAMETER_SCOPE = "scope";
	public final static String SYS_AUTH_PARAMETER_STATE = "state";
	public final static String SYS_AUTH_PARAMETER_VIEW = "view";
	
	
	public final static String CONTEXT_USER = "context:user:";
	public final static String CONTEXT_SECONDHAND = "context:secondhand:";
	public final static String CONTEXT_SPLIT = ":ct:";

}
