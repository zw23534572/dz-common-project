package com.dazong.common.cache.redis;

import com.dazong.common.cache.core.ICacheHandler;
import com.dazong.common.cache.manager.CacheFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("/META-INF/dz-common-cache.xml")
public class RedisCacheHandlerTests implements ICacheHandler{

    @Autowired
    CacheFactory cacheFactory;

    @Override
    public void saveString(String key, String str) {
        
    }

    @Override
    public void saveObject(String key, Object object) {

    }

    @Override
    public void saveMap(String key, Map<String, ?> data) {

    }

    @Override
    public void saveMapItem(String key, String itemKey, Object value) {

    }

    @Override
    public void saveList(String key, List data) {

    }

    @Override
    public void saveListItem(String key, Object object) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void deleteMapItem(String key, String itemKey) {

    }

    @Test
    public String getString(String key) {
        return null;
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getMapItem(String key, String itemKey, Class<T> type) {
        return null;
    }

    @Override
    public <T> Map<String, T> getMap(String key, Class<T> type) {
        return null;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> type) {
        return null;
    }


//
//    @Test
//    CacheHandler cacheHandler;
//
//    @Test
//    public void saveList() {
//        List<String> strs2 = new ArrayList<String>();
//        strs2.add("1");
//        strs2.add("2");
//        cacheHandler.delete("test:list:string");
//        cacheHandler.saveList("test:list:string", strs2);
//        List<String> strs = cacheHandler.getList("test:list:string",String.class);
//        equals(strs,strs2);
//    }
//
//    private void equals(List list,List list2) {
//        assertNotNull(list);
//        assertEquals(list.size(),list2.size());
//        for (int i =0,len = list.size();i < len;i++) {
//            Object obj1 = list.get(i);
//            Object obj2 = list.get(i);
//            assertEquals(obj1,obj2);
//        }
//    }
//
//    @Test
//    public void testSaveString() {
//        cacheHandler.saveString("test:string","string");
//        String str = cacheHandler.getString("test:string");
//        assertNotNull(str);
//        assertEquals(str,"string");
//    }
//
//    @Test
//    public void testSaveMap() {
//        String mapkey = "test:map";
//        String itemkey = "str";
//        String itemValue = "test001";
//        cacheHandler.saveMapItem(mapkey,itemkey,itemValue);
//        String value = cacheHandler.getMapItem(mapkey,itemkey,String.class);
//        assertNotNull(value);
//        assertEquals(value,itemValue);
//
//        cacheHandler.saveMapItem(mapkey,"int",new Integer(100));
//        Integer intValue = cacheHandler.getMapItem(mapkey,"int",Integer.class);
//        assertNotNull(intValue);
//        assertEquals(intValue,new Integer(100));
//
//        cacheHandler.saveMapItem(mapkey,"long",new Long(100));
//        Long longValue = cacheHandler.getMapItem(mapkey,"long",Long.class);
//        assertNotNull(longValue);
//        assertEquals(longValue,new Long(100));
//
//        cacheHandler.saveMapItem(mapkey,"double",new Double(1000.222));
//        Double doubleValue = cacheHandler.getMapItem(mapkey,"double",Double.class);
//        assertNotNull(doubleValue);
//        assertEquals(doubleValue,new Double(1000.222));
//
//        cacheHandler.saveMapItem(mapkey,"bigdecimal",new BigDecimal(1000.222));
//        BigDecimal decimal = cacheHandler.getMapItem(mapkey,"bigdecimal",BigDecimal.class);
//        assertNotNull(decimal);
//        assertEquals(decimal,new BigDecimal(1000.222));
//
//        cacheHandler.saveMapItem("amap","one","1");
//        cacheHandler.saveMapItem("amap","two","2");
//        cacheHandler.saveMapItem("amap","three","3");
//        cacheHandler.saveMapItem("amap","four","4");
//        Map<String,String> allvalue = cacheHandler.getMap("amap",String.class);
//        assertNotNull(allvalue);
//        assertTrue(allvalue.size() == 4);
//        assertEquals("1",allvalue.get("one"));
//        assertEquals("2",allvalue.get("two"));
//        assertEquals("3",allvalue.get("three"));
//        assertEquals("4",allvalue.get("four"));
//
//    }
}
