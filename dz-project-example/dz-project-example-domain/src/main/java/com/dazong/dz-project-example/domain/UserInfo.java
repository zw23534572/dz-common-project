package com.dazong.dz-project-example.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author huqichao
 */
@Data
public class UserInfo {
	/** 用户编号 出参时使用*/
	private String id;
	
	/** 用户编号 入参时使用*/
	@JSONField(name = "user_id")
	private String userId;

	/** 手机号码*/
	private String mobile;
	
	/** 性别  */
	private String sex;
	
	/** 密码强度 */
	@JSONField(name = "password_strong")
	private String passwordStrong;
	
	/** 用户名称*/
	private String realname;
	
	/** 匿名 */
	private String nickname;
	
	/** 邮箱*/
	private String email;
	
	/** 头标*/
	private String headimg;
	
	/** 角色 */
	@JSONField(name = "role_type")
	private Integer roleType;
	
	/** 公司信息*/
	@Autowired
	private Company company;
	
	public UserInfo(){
		
	}
	public UserInfo(String userId, String realname) {
		this.userId = userId;
		this.realname = realname;
	}
}
