package org.plutodjava.matchmaker.wx.service;

import org.plutodjava.matchmaker.wx.utils.JwtHelper;
import org.springframework.util.StringUtils;

/**
 * 维护用户token
 */
public class UserTokenManager {
	public static String generateToken(String id) {
        JwtHelper jwtHelper = new JwtHelper();
        return jwtHelper.createToken(id);
    }
    public static String getUserId(String token) {
    	JwtHelper jwtHelper = new JwtHelper();
    	String userId = jwtHelper.verifyTokenAndGetUserId(token);
    	if(StringUtils.isEmpty(userId)){
    		return null;
    	}
        return userId;
    }
}
