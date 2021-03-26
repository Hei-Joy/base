package com.yuanlrc.base.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 系统运行时的常量
 * @author Administrator
 *
 */
public class RuntimeConstant {

	/**
	 * 系统统一返回视图
	 */
	public static final String RUNTIME_ERROR_VIEW = "error/runtime_error";

	//登录拦截器无需拦截的url
	public static List<String> loginExcludePathPatterns = Arrays.asList(
			"/system/login",
			"/admin/css/**",
			"/admin/fonts/**",
			"/admin/js/**",
			"/admin/images/**",
			"/error",
			"/pay/alipay_notify",
			"/cpacha/generate_cpacha"
		);
	//权限拦截器无需拦截的url
	public static List<String> authorityExcludePathPatterns = Arrays.asList(
			"/system/login",
			"/system/index",
			"/system/no_right",
			"/admin/css/**",
			"/admin/fonts/**",
			"/admin/js/**",
			"/admin/images/**",
			"/error",
			"/pay/alipay_notify",
			"/cpacha/generate_cpacha",
			"/system/logout",
			"/system/update_userinfo",
			"/system/update_pwd",
			"/photo/view"
		);
}
