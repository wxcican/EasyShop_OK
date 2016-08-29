package com.feicuiedu.com.easyshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * 商品展示详情类
 */
@SuppressWarnings("unused")
public class GoodsDetail implements Serializable {

    private String name;
    private String type;
    private String price;
    private String description;
    private String master;
    private List<ImageUri> pages;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getMaster() {
        return master;
    }

    public List<ImageUri> getPages() {
        return pages;
    }

    public class ImageUri {
        private String uri;

        public String getUri() {
            return uri;
        }
    }
}
