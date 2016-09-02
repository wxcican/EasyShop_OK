package com.feicuiedu.com.easyshop.network;

/**
 * 此接口中包含了易淘的所有网络连接路径
 */
public interface EasyShopApi {

    String BASE_URL = "http://192.168.1.37:8080/yitao/";
    String IMAGE_URL = "http://192.168.1.37:8080";

    String LOGIN = "UserWeb?method=login";
    String REGISTER = "UserWeb?method=register";
    String UPDATE = "UserWeb?method=update";

    String ALL_GOODS = "GoodsServlet?method=getAll";
    String ADD = "GoodsServlet?method=add";
    String DETAIL = "GoodsServlet?method=view";
    String DELETE = "GoodsServlet?method=delete";

}

