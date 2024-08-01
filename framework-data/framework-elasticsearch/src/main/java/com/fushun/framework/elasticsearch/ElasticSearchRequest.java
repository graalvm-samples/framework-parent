package com.fushun.framework.elasticsearch;

import com.fushun.framework.elasticsearch.enumeration.IBaseElasticSearchIndexEnum;
import com.fushun.framework.elasticsearch.enumeration.IBaseElasticSearchIndexTypeEnum;

/**
 * ES请求
 *
 * @author zhoup
 * @date 2016年5月21日
 */
public class ElasticSearchRequest {

    IBaseElasticSearchIndexEnum index;
    IBaseElasticSearchIndexTypeEnum type;
    String id;

    public String getIndex() {
        return index.getCode();
    }

    public void setIndex(IBaseElasticSearchIndexEnum index) {
        this.index = index;
    }

    public String getType() {
        return type.getCode();
    }

    public void setType(IBaseElasticSearchIndexTypeEnum type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
