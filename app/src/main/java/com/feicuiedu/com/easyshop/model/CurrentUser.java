package com.feicuiedu.com.easyshop.model;

/**
 * 此类是一个用来缓存当前用户信息的极简单的实现。
 */
public class CurrentUser {

    /*此类不可实例化*/
    private CurrentUser() {
    }

    private static String userId;

    private static User user;

    public static void setUserId(String userId) {
        CurrentUser.userId = userId;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
    }

    // 清除缓存的信息
    public static void clear() {
        userId = null;
        user = null;
    }

    public static User getUser() {
        return user;
    }

    public static String getUserId() {
        return userId;
    }

    // 当前用户数据是否为空
    public static boolean isEmpty() {
        return userId == null || user == null;
    }

    // 当前是否有访问令牌
    public static boolean hasAccessToken() {
        return userId != null;
    }
}
