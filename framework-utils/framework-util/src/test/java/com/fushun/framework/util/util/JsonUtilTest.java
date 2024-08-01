package com.fushun.framework.util.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fushun.framework.util.util.JsonUtil.jsonToMap;

public class JsonUtilTest {


    public void test(String[] args) {

        Map<String,Object> map2=new HashMap<>();
        map2.put("ddd",new Date());
        String str= JsonUtil.classToJson(map2, DateUtil.FORMAT_ST2);
        System.out.println(str);

//		String json="{\"0\":{\"orderObj\":{\"amount\":24,\"deliveryTime\":\"0\",\"remark\":\"\",\"isViewed\":1,\"orderNo\":\"2015102700000333\",\"appointmentTime\":\"\",\"userAttribute\":{\"score\":1,\"userId\":\"5628e62d60b28da5c9ac2eb1\",\"username\":\"18523827123\"},\"shopId\":[{\"__type\":\"Pointer\",\"className\":\"Shop\",\"objectId\":\"5625b7b660b2d1400144bd4e\"}],\"activityInfo\":{\"activityId\":{\"__type\":\"Pointer\",\"className\":\"Activity\",\"objectId\":\"55f63a5a60b2ad8a0ae1a96a\"},\"status\":1},\"type\":3,\"tip\":0,\"deliveryAddress\":{\"address\":\"重庆市渝北区空港大道656号\",\"updatedAt\":\"2015-10-24T12:20:17.195Z\",\"isDefault\":\"1\",\"name\":\"晓\",\"objectId\":\"5628e62d00b07d3622f0d307\",\"createdAt\":\"2015-10-22T13:35:41.922Z\",\"title\":\"公司\",\"userId\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"5628e62d60b28da5c9ac2eb1\"},\"detailAddress\":\"2栋1-2\",\"phoneNumber\":\"18523827123\",\"geoPoint\":{\"__type\":\"GeoPoint\",\"latitude\":29.7460444,\"longitude\":106.6450168}},\"systemCancelOrder\":0,\"isCommented\":1,\"taskStatus\":1,\"userId\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"5628e62d60b28da5c9ac2eb1\"},\"taskTime\":60,\"productAttribute\":[{\"productName\":\"红牛维生素功能饮料250ml/罐\",\"productLibraryId\":\"5625c23500b0cfba0787c3f9\",\"imageUrl\":\"http://ac-zibyemqg.clouddn.com/Gbghum6BMhzrIuxbHzbKAlR961tHBhvzUaU7ltLc.png\",\"productNumber\":4,\"productPrice\":6}],\"shopAttribute\":[{\"shopName\":\"一站便利店尚阳康城店\",\"shopId\":\"5625b7b660b2d1400144bd4e\",\"score\":0,\"shopAdminMobilePhone\":\"18623106325\"}],\"geoPoint\":{\"__type\":\"GeoPoint\",\"latitude\":29.7460444,\"longitude\":106.6450168},\"systemCancelComment\":1,\"orderStatus\":4,\"objectId\":\"562f815d00b0ee7fdadcb625\",\"createdAt\":\"2015-10-27T13:51:25.769Z\",\"updatedAt\":\"2015-10-27T15:14:20.598Z\"},\"shopObj\":{\"objectId\":\"5625b7b660b2d1400144bd4e\"},\"queueKey\":\"completeCouponQueue562f815d00b0ee7fdadcb625\"},\"1\":null}";
//		jsonToHashMap(json);
//		HashMap<String, Object> str= jsonToHashMap("[{\"name\":\"净含量\",\"value\":\"275ml/瓶\"}]");
//		String[] str= jsonToHashMap("[{\"name\":\"净含量\",\"value\":\"275ml/瓶\"}]");
//		List<HashMap<String, Object>> str=jsonToList("[{\"name\":\"净含量\",\"value\":\"275ml/瓶\"}]");
//		System.out.println("");
        String json = "{\"x1:4028813d554439710155443992ee0002\":{\"id\":\"4028813d554439710155443992ee0002\",\"count\":1,\"firstOrderConfigId\":\"4028813d554439710155443992e00001\",\"createdAt\":1465728471000,\"money\":1,\"probability\":1,\"status\":2,\"updatedAt\":1465728471000,\"useCount\":0,\"minProbability\":1,\"maxProbability\":1},\"x5:4028813d554439710155443992f40003\":{\"id\":\"4028813d554439710155443992f40003\",\"count\":1,\"firstOrderConfigId\":\"4028813d554439710155443992e00001\",\"createdAt\":1465728471000,\"money\":5,\"probability\":1,\"status\":2,\"updatedAt\":1465728471000,\"useCount\":0,\"minProbability\":2,\"maxProbability\":2}}";
        HashMap<String, Object> map = jsonToMap(json, HashMap.class, String.class, Object.class);

//		BaseDTO baseDTO=new BaseDTO();
//		baseDTO.setCreatedAt(new Date());
//		baseDTO.setUpdatedAt(new Date());
//		json=classToJson(baseDTO);
//		baseDTO=jsonToClass(json, BaseDTO.class);
//		 json="["+json+"]";
//		 json="[{\"id\":\"\",\"createdAt\":1465728471000,\"updatedAt\":1465728471000}]";
//		 List<BaseDTO> list= jsonToList(json, BaseDTO.class);
//		 List<HashMap<String, Object>> list2=jsonToList(json);
        System.out.println("");
    }
}