package com.skyfilm.owner;

import java.util.HashMap;

import android.content.Intent;

public class Const {
	public static final boolean DEBUG = false;
	private static final String URL_R = "http://owner.yjy.csq365.com";
	private static final String URL_T = "http://zhsqdev.loganwy.com";
	private static final String URL_T_CJ = "http://dylw.test.csq365.com";
	public static final String URL_T_CJ_ = "http://dylw.test.csq365.com/";
	private static final String URL = URL_T_CJ;
	public static final String BASE_URL = URL;
	public static final String EXEC_URL = BASE_URL+"/app/index";
	public static final String MOBILE_REG = "[1][34578]\\d{9}";
	public static final int LIST_REFRESH = 0x112;
	public static final int LIST_PAGE_SIZE = 20;
	public static final int LIST_LOADMORE = 0x111;
	public static final int LIST_DELETE=0x113;
	public static final String ADD_ADDRESS_REFRESH = "com.lg.actioninit.ADD_ADDRESS_REFRESH";
	
/** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
	public static final String ACTION_SET_DEFAULT_ADDRESS = "com.cinema.actionACTION_SET_DEFAULT_ADDRESS";
	public static final String PWD_REG = "[a-zA-Z0-9]{6,16}";
	/** 新浪微博：当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
    public static final String SINA_APP_KEY = "3260836599";
    /** 微信：APP_ID 替换为你的应用从官方网站申请到的合法appId */
    public static final String WECHAT_APP_ID = "wx32377228dac3cb59";
    /** 
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.sina.com";
    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * 
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * 
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * 
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    
    /**
	 *开发者需要填一个服务端URL 该URL是用来请求支付需要的charge。务必确保，URL能返回json格式的charge对象。
	 *服务端生成charge 的方式可以参考ping++官方文档，地址 https://pingxx.com/guidance/server/import 
	 *
	 *【 http://218.244.151.190/demo/charge 】是 ping++ 为了方便开发者体验 sdk 而提供的一个临时 url 。
	 * 该 url 仅能调用【模拟支付控件】，开发者需要改为自己服务端的 url 。
	 */
    private static String YOUR_URL ="http://218.244.151.190/demo/charge";
    public static final String PINGPP_URL = YOUR_URL;
    
}
