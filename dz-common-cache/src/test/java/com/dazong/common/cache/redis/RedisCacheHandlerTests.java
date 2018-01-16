package com.dazong.common.cache.redis;

import com.dazong.common.cache.constants.IExpire;
import com.dazong.common.cache.core.ICacheHandler;
import com.dazong.common.cache.core.impl.RedisCacheHandler;
import com.dazong.common.cache.manager.CacheFactory;
import com.dazong.common.cache.redis.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * @author: DanielLi
 * @Date: 2018/1/10
 * @Description:
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("/META-INF/dz-common-cache-test.xml")
public class RedisCacheHandlerTests{

    @Autowired
    CacheFactory cacheFactory;

    @Test
    public void saveString() {
        String keyTemp = "name";
        String valueOri = "daniel";

        cacheFactory.getDefaultCacheHandler().saveString(keyTemp, valueOri, IExpire.FIVE_MIN);
        String valueResult = cacheFactory.getDefaultCacheHandler().getString(keyTemp);

        assert (valueOri.equals(valueResult));
    }
    @Test
    public void saveStringExpire() {
        String keyTemp = "name";
        String valueOri = "daniel";

        cacheFactory.getDefaultCacheHandler().saveString(keyTemp, valueOri, IExpire.ONE_SEC);
        try {
            Thread.sleep(IExpire.FIVE_SEC);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String valueResult = cacheFactory.getDefaultCacheHandler().getString(keyTemp);

        assert ("".equals(valueResult));
    }

    @Test
    public void saveObject() {
        String keyTemp = "name";

        Person personOri = new Person();
        personOri.setName("daniel");
        personOri.setAge(18);

        cacheFactory.getDefaultCacheHandler().saveObject(keyTemp, personOri, IExpire.FIVE_MIN);
        Person valueTemp = cacheFactory.getDefaultCacheHandler().getObject(keyTemp, Person.class);

        assert (personOri.equals(valueTemp));
    }
    @Test
    public void saveObjectExpire() throws InterruptedException {
        String keyTemp = "name";

        Person personOri = new Person();
        personOri.setName("daniel");
        personOri.setAge(18);

        cacheFactory.getDefaultCacheHandler().saveObject(keyTemp, personOri, IExpire.ONE_SEC);
        Thread.sleep(IExpire.FIVE_SEC);
        Person valueTemp = cacheFactory.getDefaultCacheHandler().getObject(keyTemp, Person.class);

        assertEquals (valueTemp, null);
    }

    @Test
    public void saveMap() {
        ICacheHandler cacheHandler = cacheFactory.getDefaultCacheHandler();
        String mapkey = "test:map";
        String itemkey = "str";
        String itemValue = "test001";
        cacheHandler.saveMapItem(mapkey,itemkey,itemValue, IExpire.FIVE_MIN);
        String value = cacheHandler.getMapItem(mapkey,itemkey,String.class);
        assertNotNull(value);
        assertEquals(value,itemValue);

        cacheHandler.saveMapItem(mapkey,"int",new Integer(100), IExpire.FIVE_MIN);
        Integer intValue = cacheHandler.getMapItem(mapkey,"int",Integer.class);
        assertNotNull(intValue);
        assertEquals(intValue,new Integer(100));

        cacheHandler.saveMapItem(mapkey,"long",new Long(100), IExpire.FIVE_MIN);
        Long longValue = cacheHandler.getMapItem(mapkey,"long",Long.class);
        assertNotNull(longValue);
        assertEquals(longValue,new Long(100));

        cacheHandler.saveMapItem(mapkey,"double",new Double(1000.222), IExpire.FIVE_MIN);
        Double doubleValue = cacheHandler.getMapItem(mapkey,"double",Double.class);
        assertNotNull(doubleValue);
        assertEquals(doubleValue,new Double(1000.222));

        cacheHandler.saveMapItem(mapkey,"bigdecimal",new BigDecimal(1000.222), IExpire.FIVE_MIN);
        BigDecimal decimal = cacheHandler.getMapItem(mapkey,"bigdecimal",BigDecimal.class);
        assertNotNull(decimal);
        assertEquals(decimal,new BigDecimal(1000.222));

        cacheHandler.saveMapItem("amap","one","1", IExpire.FIVE_MIN);
        cacheHandler.saveMapItem("amap","two","2", IExpire.FIVE_MIN);
        cacheHandler.saveMapItem("amap","three","3", IExpire.FIVE_MIN);
        cacheHandler.saveMapItem("amap","four","4", IExpire.FIVE_MIN);
        Map<String,String> allvalue = cacheHandler.getMap("amap",String.class);
        assertNotNull(allvalue);
        assertTrue(allvalue.size() == 4);
        assertEquals("1",allvalue.get("one"));
        assertEquals("2",allvalue.get("two"));
        assertEquals("3",allvalue.get("three"));
        assertEquals("4",allvalue.get("four"));
    }


    @Test
    public void saveList() {
        ICacheHandler cacheHandler = cacheFactory.getDefaultCacheHandler();
        List<String> strs2 = new ArrayList<String>();
        strs2.add("1");
        strs2.add("2");
        cacheHandler.delete("test:list:string");
        cacheHandler.saveList("test:list:string", strs2, IExpire.FIVE_SEC);
        List<String> strs = cacheHandler.getList("test:list:string",String.class);
        equals(strs,strs2);
    }
    private void equals(List list,List list2) {
        assertNotNull(list);
        assertEquals(list.size(),list2.size());
        for (int i =0,len = list.size();i < len;i++) {
            Object obj1 = list.get(i);
            Object obj2 = list.get(i);
            assertEquals(obj1,obj2);
        }
    }

    @Test
    public void delete() {
        String keyTemp = "name";

        Person personOri = new Person();
        personOri.setName("danielDelete");
        personOri.setAge(18);

        cacheFactory.getDefaultCacheHandler().saveObject(keyTemp, personOri, IExpire.FIVE_MIN);
        cacheFactory.getDefaultCacheHandler().delete(keyTemp);
        Person valueTemp = cacheFactory.getDefaultCacheHandler().getObject(keyTemp, Person.class);

        assertEquals (valueTemp, null);
    }

}
