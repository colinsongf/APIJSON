/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package apijson.demo.server;

import static zuo.biao.apijson.RequestMethod.DELETE;
import static zuo.biao.apijson.RequestMethod.GET;
import static zuo.biao.apijson.RequestMethod.HEAD;
import static zuo.biao.apijson.RequestMethod.POST;
import static zuo.biao.apijson.RequestMethod.POST_GET;
import static zuo.biao.apijson.RequestMethod.POST_HEAD;
import static zuo.biao.apijson.RequestMethod.PUT;

import java.util.Random;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import apijson.demo.server.model.BaseModel;
import apijson.demo.server.model.Comment;
import apijson.demo.server.model.Login;
import apijson.demo.server.model.Moment;
import apijson.demo.server.model.Password;
import apijson.demo.server.model.User;
import apijson.demo.server.model.UserPrivacy;
import apijson.demo.server.model.Verify;
import apijson.demo.server.model.Wallet;
import zuo.biao.apijson.JSON;
import zuo.biao.apijson.JSONResponse;
import zuo.biao.apijson.Log;
import zuo.biao.apijson.RequestMethod;
import zuo.biao.apijson.StringUtil;
import zuo.biao.apijson.server.JSONRequest;
import zuo.biao.apijson.server.Parser;
import zuo.biao.apijson.server.exception.ConditionErrorException;
import zuo.biao.apijson.server.exception.ConflictException;
import zuo.biao.apijson.server.exception.NotExistException;
import zuo.biao.apijson.server.exception.OutOfRangeException;

/**request receiver and controller
 * <br > 如果用在金融等对安全要求很高的领域，get和head可以测试期间使用明文的HTTP GET，上线版改用非明文的HTTP POST，兼顾系统安全与开发效率。
 * <br > get,head等接口都用HTTP GET方法请求，post,put,delete等接口都用HTTP POST方法请求。
 * <br > 这样做是为了前端和客户端方便，只需要做GET和POST请求。也可以改用实际对应的方法。
 * @author Lemon
 */
@RestController
@RequestMapping("")
public class Controller {
	private static final String TAG = "Controller";

	//通用接口，非事务型操作 和 简单事务型操作 都可通过这些接口自动化实现<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**获取
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#GET}
	 */
	@RequestMapping("get/{request}")
	public String get(@PathVariable String request, HttpSession session) {
		return new Parser(GET).setSession(session).parse(request);
	}

	/**计数
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#HEAD}
	 */
	@RequestMapping("head/{request}")
	public String head(@PathVariable String request, HttpSession session) {
		return new Parser(HEAD).setSession(session).parse(request);
	}

	/**用POST方法GET，request和response都非明文，浏览器看不到，用于对安全性要求高的GET请求
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#POST_GET}
	 */
	@RequestMapping(value = "post_get", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public String post_get(@RequestBody String request, HttpSession session) {
		return new Parser(POST_GET).setSession(session).parse(request);
	}

	/**用POST方法HEAD，request和response都非明文，浏览器看不到，用于对安全性要求高的HEAD请求
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#POST_HEAD}
	 */
	@RequestMapping(value = "post_head", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public String post_head(@RequestBody String request, HttpSession session) {
		return new Parser(POST_HEAD).setSession(session).parse(request);
	}

	/**新增
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#POST}
	 */
	@RequestMapping(value = "post", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public String post(@RequestBody String request, HttpSession session) {
		return new Parser(POST).setSession(session).parse(request);
	}

	/**修改
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#PUT}
	 */
	@RequestMapping(value = "put", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public String put(@RequestBody String request, HttpSession session) {
		return new Parser(PUT).setSession(session).parse(request);
	}

	/**删除
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 * @see {@link RequestMethod#DELETE}
	 */
	@RequestMapping(value = "delete", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public String delete(@RequestBody String request, HttpSession session) {
		return new Parser(DELETE).setSession(session).parse(request);
	}

	//通用接口，非事务型操作 和 简单事务型操作 都可通过这些接口自动化实现>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>













	public static final String USER_;
	public static final String MOMENT_;
	public static final String COMMENT_;
	public static final String WALLET_;
	public static final String PASSWORD_;
	public static final String USER_PRIVACY_;
	public static final String VERIFY_;
	static {
		USER_ = User.class.getSimpleName();
		MOMENT_ = Moment.class.getSimpleName();
		COMMENT_ = Comment.class.getSimpleName();
		WALLET_ = Wallet.class.getSimpleName();
		PASSWORD_ = Password.class.getSimpleName();
		USER_PRIVACY_ = UserPrivacy.class.getSimpleName();
		VERIFY_ = Verify.class.getSimpleName();
	}

	public static final String TOTAL = JSONResponse.KEY_TOTAL;

	public static final String RANGE = "range";

	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String CURRENT_USER_ID = "currentUserId";

	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String PASSWORD = "password";
	public static final String LOGIN_PASSWORD = "loginPassword";
	public static final String PAY_PASSWORD = "payPassword";
	public static final String OLD_PASSWORD = "oldPassword";
	public static final String VERIFY = "verify";

	public static final String SEX = "sex";
	public static final String TYPE = "type";
	public static final String CONTENT = "content";




	public static final String DATE_UP = "date+";//同 "date ASC"
	public static final String DATE_DOWN = "date-";//同 "date DESC"

	public static final String ID_AT = ID + "@";
	public static final String USER_ID_AT = USER_ID + "@";
	public static final String MOMENT_ID_AT = "momentId@";
	public static final String COMMENT_ID_AT = "commentId@";

	public static final String ID_IN = ID + "{}";
	public static final String USER_ID_IN = USER_ID + "{}";
	public static final String MOMENT_ID_IN = "momentId{}";
	public static final String COMMENT_ID_IN = "commentId{}";

	public static final String NAME_SEARCH = NAME + "$";
	public static final String PHONE_SEARCH = PHONE + "$";
	public static final String CONTENT_SEARCH = CONTENT + "$";



	public static final String COLUMNS_USER_SIMPLE = "id,name";
	public static final String COLUMNS_USER = "id,sex,name,head";




	/**生成验证码,修改为post请求
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "post/verify", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public JSONObject postVerify(@RequestBody String request) {
		JSONObject requestObject;
		String phone;
		try {
			requestObject = Parser.parseRequest(request, POST);
			phone = requestObject.getString(PHONE);
		} catch (Exception e) {
			return Parser.newErrorResult(e);
		}

		new Parser(DELETE, true).parse(newVerifyRequest(phone, null));

		JSONObject response = new Parser(POST, true).parseResponse(
				newVerifyRequest(phone, "" + (new Random().nextInt(9999) + 1000)));

		JSONObject verify = null;
		try {
			verify = response.getJSONObject(VERIFY_);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (verify == null || JSONResponse.isSucceed(verify.getIntValue(JSONResponse.KEY_CODE)) == false) {
			new Parser(DELETE, true).parseResponse(new JSONRequest(new Verify(phone)));
			return response;
		}

		//TODO 这里直接返回验证码，方便测试。实际上应该只返回成功信息，验证码通过短信发送
		JSONObject object = new JSONObject();
		object.put(PHONE, phone);
		return getVerify(JSON.toJSONString(object));
	}

	/**获取验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "post_get/verify", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public JSONObject getVerify(@RequestBody String request) {
		JSONObject requestObject;
		String phone;
		try {
			requestObject = Parser.parseRequest(request, POST_GET);
			phone = requestObject.getString(PHONE);
		} catch (Exception e) {
			return Parser.newErrorResult(e);
		}
		return new Parser(POST_GET, true).parseResponse(newVerifyRequest(phone, null));
	}

	/**校验验证码
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "post_head/verify", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public JSONObject headVerify(@RequestBody String request) {
		String phone;
		String verify;
		try {
			JSONObject requestObject = Parser.parseRequest(request, POST_HEAD);
			phone = requestObject.getString(PHONE);
			verify = requestObject.getString(VERIFY);
		} catch (Exception e) {
			return Parser.newErrorResult(e);
		}
		return headVerify(phone, verify);
	}

	/**校验验证码
	 * @param phone
	 * @param vfy
	 * @return
	 */
	public JSONObject headVerify(String phone, String vfy) {
		JSONResponse response = new JSONResponse(
				new Parser(POST_GET, true).parseResponse(
						new JSONRequest(new Verify(phone)).setTag(VERIFY_)
						)
				);
		Verify verify = response.getObject(Verify.class);
		if (verify == null) {
			return Parser.newErrorResult(new NullPointerException("验证码为空！"));
		}

		//验证码过期
		if (System.currentTimeMillis() > (60000 + BaseModel.value(verify.getDate()))) {
			new Parser(DELETE, true).parseResponse(
					new JSONRequest(new Verify(phone)).setTag(VERIFY_)
					);
			return Parser.newErrorResult(new TimeoutException("验证码已过期！"));
		}

		return new JSONResponse(
				new Parser(POST_HEAD, true).parseResponse(
						new JSONRequest(new Verify(phone, vfy))
						)
				);
	}


	/**新建一个验证码请求
	 * @param phone
	 * @param verify
	 * @return
	 */
	private JSONObject newVerifyRequest(String phone, String verify) {
		return new JSONRequest(new Verify(phone, verify)).setTag(VERIFY_);
	}



	/**用户登录
	 * @param request 只用String，避免encode后未decode
	 * @return
	 */
	@RequestMapping(value = "login", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public JSONObject login(@RequestBody String request, HttpSession session) {
		JSONObject requestObject = null;
		try {
			requestObject = Parser.parseRequest(request, POST);
		} catch (Exception e) {
			return Parser.newErrorResult(e);
		}

		String typeString = requestObject.getString(TYPE);//登录类型
		String phone = requestObject.getString(PHONE);//手机
		String password = requestObject.getString(PASSWORD);//密码

		//判断手机号是否合法
		if (StringUtil.isPhone(phone) == false) {
			return Parser.newErrorResult(new IllegalArgumentException("手机号不合法！"));
		}

		//判断密码是否合法
		if ("1".equals(typeString)) {
			if (StringUtil.isVerify(password) == false) {
				return Parser.newErrorResult(new IllegalArgumentException("验证码不合法！"));
			}
		} else {
			if (StringUtil.isPassword(password) == false) {
				return Parser.newErrorResult(new IllegalArgumentException("密码不合法！"));
			}
		}

		//手机号是否已注册
		JSONObject phoneResponse = new Parser(POST_HEAD, true).parseResponse(
				new JSONRequest(
						new UserPrivacy().setPhone(phone)
						)
				);
		JSONResponse response = new JSONResponse(phoneResponse).getJSONResponse(USER_PRIVACY_);
		if (JSONResponse.isSucceed(response) == false) {
			return response;
		}
		if(JSONResponse.isExist(response) == false) {
			return Parser.newErrorResult(new NotExistException("手机号未注册"));
		}

		//根据phone获取User
		JSONObject privacyResponse = new Parser(POST_GET, true).parseResponse(
				new JSONRequest(
						new UserPrivacy().setPhone(phone)
						)
				);
		response = new JSONResponse(privacyResponse);

		UserPrivacy userPrivacy = response == null ? null : response.getObject(UserPrivacy.class);
		long userId = userPrivacy == null ? 0 : BaseModel.value(userPrivacy.getId());
		if (userId <= 0) {
			return privacyResponse;
		}

		//校验凭证 
		int type = Integer.valueOf(0 + StringUtil.getNumber(typeString));
		if (type == Login.TYPE_PASSWORD) {//password密码登录
			response = new JSONResponse(
					new Parser(POST_HEAD, true).parseResponse(
							new JSONRequest(new UserPrivacy(userId).setPassword(password))
							)
					);
		} else {//verify手机验证码登录
			response = new JSONResponse(headVerify(phone, password));
		}
		if (JSONResponse.isSucceed(response) == false) {
			return response;
		}
		response = response.getJSONResponse(type == Login.TYPE_PASSWORD ? USER_PRIVACY_ : VERIFY_);
		if (JSONResponse.isExist(response) == false) {
			return Parser.newErrorResult(new ConditionErrorException("账号或密码错误"));
		}

		response = new JSONResponse(
				new Parser(POST_GET, true).parseResponse(
						new JSONRequest(new User(userId))
						)
				);
		User user = response.getObject(User.class);
		if (user == null || BaseModel.value(user.getId()) != userId) {
			return Parser.newErrorResult(new NullPointerException("服务器内部错误"));
		}

		//登录状态保存至session
		session.setAttribute(USER_ID, userId);//用户id
		session.setAttribute(TYPE, type);//登录方式
		session.setAttribute(USER_, user);//用户
		session.setAttribute(USER_PRIVACY_, userPrivacy);//用户隐私信息
		session.setMaxInactiveInterval(1*60);//设置session过期时间

		return response;
	}

	/**退出登录，清空session
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logout", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public JSONObject logout(HttpSession session) {
		long userId = AccessVerifier.getUserId(session);//必须在session.invalidate();前！
		session.invalidate();

		JSONObject result = Parser.newSuccessResult();
		JSONObject user = Parser.newSuccessResult();
		user.put(ID, userId);
		result.put(USER_, user);

		return result;
	}


	/**注册
	 * @param request 只用String，避免encode后未decode
	 * @return
	 */
	@RequestMapping(value = "register", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public JSONObject register(@RequestBody String request) {
		JSONObject requestObject = null;
		try {
			requestObject = Parser.getCorrectRequest(POST, Parser.parseRequest(request, POST));
		} catch (Exception e) {
			return Parser.newErrorResult(e);
		}

		String phone = requestObject.getString(PHONE);
		String password = StringUtil.getString(requestObject.getString(PASSWORD));
		String verify = StringUtil.getString(requestObject.getString(VERIFY));
		requestObject.remove(PHONE);
		requestObject.remove(PASSWORD);
		requestObject.remove(VERIFY);

		if (StringUtil.isPhone(phone) == false) {
			return Parser.extendErrorResult(requestObject
					, new IllegalArgumentException("User.phone: " + phone + " 不合法！"));
		}
		if (StringUtil.isPassword(password) == false) {
			return Parser.extendErrorResult(requestObject
					, new IllegalArgumentException("User.password: " + password + " 不合法！不能小于6个字符！"));
		}
		if (StringUtil.isVerify(verify) == false) {
			return Parser.extendErrorResult(requestObject
					, new IllegalArgumentException("User.verify: " + verify + " 不合法！不能小于6个字符！"));
		}


		JSONResponse response = new JSONResponse(headVerify(phone, verify));
		if (JSONResponse.isSucceed(response) == false) {
			return response;
		}

		//手机号或验证码错误
		if (JSONResponse.isExist(response.getJSONResponse(VERIFY_)) == false) {
			return Parser.extendErrorResult(response, new ConditionErrorException("手机号或验证码错误！"));
		}

		//验证手机号是否已经注册
		JSONObject check = new Parser(POST_HEAD, true).parseResponse(
				new JSONRequest(
						new UserPrivacy().setPhone(phone)
						)
				);
		JSONObject checkUser = check == null ? null : check.getJSONObject(USER_PRIVACY_);
		if (checkUser == null || checkUser.getIntValue(JSONResponse.KEY_COUNT) > 0) {
			return Parser.newErrorResult(new ConflictException("手机号" + phone + "已经注册"));
		}

		//生成User
		JSONObject result = new Parser(POST, true).parseResponse(requestObject);
		response = new JSONResponse(result);
		if (JSONResponse.isSucceed(response) == false) {
			return result;
		}

		response = response.getJSONResponse(USER_);
		long userId = response == null ? 0 : response.getId();

		//生成UserPrivacy
		response = new JSONResponse(
				new Parser(POST, true).parseResponse(
						new JSONRequest(
								new UserPrivacy(userId).setPhone(phone).setPassword(password)
								)
						)
				);
		if (JSONResponse.isSucceed(response.getJSONResponse(USER_PRIVACY_)) == false) {//创建失败，删除新增的无效User和userPrivacy

			new Parser(DELETE, true).parseResponse(
					new JSONRequest(
							new User(userId)
							)
					);

			new Parser(DELETE, true).parseResponse(
					new JSONRequest(
							new UserPrivacy().setPhone(phone)
							)
					);

			return Parser.extendErrorResult(result, new Exception("服务器内部错误"));
		}

		return result;
	}


	/**设置密码
	 * @param request 只用String，避免encode后未decode
	 * @return
	 */
	@RequestMapping(value = "put/password", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public JSONObject putPassword(@RequestBody String request){
		JSONObject requestObject = null;
		try {
			requestObject = Parser.parseRequest(request, POST);
		} catch (Exception e) {
			return Parser.newErrorResult(e);
		}
		String phone = requestObject.getString(PHONE);
		String password = StringUtil.getString(requestObject.getString(PASSWORD));
		String verify = StringUtil.getString(requestObject.getString(VERIFY));

		if (StringUtil.isPhone(phone) == false) {
			return Parser.extendErrorResult(requestObject
					, new IllegalArgumentException("User.phone: " + phone + " 不合法！"));
		}
		if (StringUtil.isPassword(password) == false) {
			return Parser.extendErrorResult(requestObject
					, new IllegalArgumentException("User.password: " + password + " 不合法！不能小于6个字符！"));
		}
		if (StringUtil.isVerify(verify) == false) {
			return Parser.extendErrorResult(requestObject
					, new IllegalArgumentException("User.verify: " + verify + " 不合法！不能小于6个字符！"));
		}

		//校验验证码
		JSONResponse response = new JSONResponse(headVerify(phone, requestObject.getString(VERIFY)));
		if (JSONResponse.isSucceed(response) == false) {
			return response;
		}
		//手机号或验证码错误
		if (JSONResponse.isExist(response.getJSONResponse(VERIFY_)) == false) {
			return Parser.extendErrorResult(response, new ConditionErrorException("手机号或验证码错误！"));
		}
		
		response = new JSONResponse(
				new Parser(GET, true).parseResponse(
						new JSONRequest(
								new UserPrivacy().setPhone(phone)
								)
						)
				);
		UserPrivacy privacy = response.getObject(UserPrivacy.class);
		long userId = privacy == null ? 0 : privacy.getId();
		//修改密码
		return new Parser(PUT, true).parseResponse(
				new JSONRequest(
						new UserPrivacy(userId).setPassword(password)
						)
				);
	}



	/**充值/提现
	 * @param request 只用String，避免encode后未decode
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "put/balance", method = org.springframework.web.bind.annotation.RequestMethod.POST)
	public JSONObject putBalance(@RequestBody String request, HttpSession session) {
		JSONObject requestObject = null;
		try {
			AccessVerifier.verifyLogin(session);
			requestObject = Parser.getCorrectRequest(PUT, Parser.parseRequest(request, PUT));
		} catch (Exception e) {
			return Parser.newErrorResult(e);
		}

		//验证密码<<<<<<<<<<<<<<<<<<<<<<<

		JSONObject pwdObj = requestObject.getJSONObject(PASSWORD_);
		requestObject.remove(PASSWORD_);
		if (pwdObj == null || pwdObj.getIntValue(TYPE) != Password.TYPE_PAY) {
			return Parser.extendErrorResult(requestObject, new ConditionErrorException("Password type必须是支付类型！"));
		}

		JSONResponse response = new JSONResponse(
				new Parser(POST_HEAD, true).setSession(session).parseResponse(
						new JSONRequest(PASSWORD_, pwdObj)
						)
				);
		response = response.getJSONResponse(PASSWORD_);
		if (response == null || response.isExist() == false) {
			return Parser.extendErrorResult(requestObject, new ConditionErrorException("支付密码错误！"));
		}

		//验证密码>>>>>>>>>>>>>>>>>>>>>>>>


		//验证金额范围<<<<<<<<<<<<<<<<<<<<<<<

		JSONObject wallet = requestObject.getJSONObject(WALLET_);
		long id = wallet == null ? null : wallet.getLong(ID);
		if (id <= 0) {
			return Parser.extendErrorResult(requestObject, new ConditionErrorException("请设置Wallet及内部的id！"));
		}

		double change = wallet.getDoubleValue("balance+");
		if (change == 0) {
			return Parser.extendErrorResult(requestObject, new OutOfRangeException("balance+的值不能为0！"));
		}
		if (Math.abs(change) > 10000) {
			return Parser.extendErrorResult(requestObject, new OutOfRangeException("单次 充值/提现 的金额不能超过10000元！"));
		}

		//验证金额范围>>>>>>>>>>>>>>>>>>>>>>>>

		if (change < 0) {//提现
			response = new JSONResponse(
					new Parser(POST_GET, true).parseResponse(
							new JSONRequest(
									new Wallet(id).setUserId(AccessVerifier.getUserId(session))
									)
							)
					);
			Wallet w = response == null ? null : response.getObject(Wallet.class);
			if (w == null) {
				return Parser.extendErrorResult(requestObject, new Exception("服务器内部错误！"));
			}

			if (w.getBalance() == null || w.getBalance().doubleValue() < -change) {
				return Parser.extendErrorResult(requestObject, new OutOfRangeException("余额不足！"));
			}
		}

		//不免验证，里面会验证身份
		return new Parser(PUT).setSession(session).parseResponse(requestObject);
	}


}
