package com.dazong.common.util;

import com.dazong.common.util.entity.StudentBase;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author: zisong.wang
 * @date: 2018/1/11
 */
public class CommonUtilsTest {

    @Test
    public void test() {
        //1. 为null则返回空字符串
        assertThat(CommonUtils.toString(null, "")).isEqualTo("");

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        //2. 返回list第一个元素
        assertThat(CommonUtils.getFirst(list)).isEqualTo("1");

        //3. 字符串转Map
        Map<String, String> map = CommonUtils.string2Map("a->test1;b->test2;gggg");
        System.out.println("字符串转map,以指定字符串分隔,结果为:b,test2 a,test1");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(key + "," + val);
        }

        Map<String, String> map2 = CommonUtils.string2Map("1&test1。", "&", "。");
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            assertThat(key).isEqualTo("1");
            assertThat(val).isEqualTo("test1");
        }

        //4. 数组生成map
        String[] arr1 = {"1", "2"};//,"3","4","5","6"

        Map<String, String> map3 = CommonUtils.map(arr1);
        for (Map.Entry<String, String> entry : map3.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            assertThat(key).isEqualTo("1");
            assertThat(val).isEqualTo("2");
        }

        Map<Object, Object> map4 = CommonUtils.mapByAarray();
        for (Map.Entry<Object, Object> entry : map4.entrySet()) {
            Object key = entry.getKey();
            Object val = entry.getValue();
            assertThat(key).isEqualTo("1");
            assertThat(val).isEqualTo("2");
        }

        //5. 以list方式读取map的值
        Map<String, String> map5 = new HashMap<>();
        map5.put("m1", "mm1");
        map5.put("m2", "mm2");

        List list5 = CommonUtils.valuesOfMap(map5);
        assertThat(list5.get(0)).isEqualTo("mm1");
        assertThat(list5.get(1)).isEqualTo("mm2");

        //6. 判断某个map是否为空
        Map<String, String> map6 = null;
        assertThat(CommonUtils.isEmpty(map5)).isFalse();
        assertThat(CommonUtils.isEmpty(map6)).isTrue();

        //7. 判断某个集合是否为空
        List list6 = null;
        assertThat(CommonUtils.isEmpty(list5)).isFalse();
        assertThat(CommonUtils.isEmpty(list6)).isTrue();
        assertThat(CommonUtils.isNotEmpty(list5)).isTrue();
        assertThat(CommonUtils.isNotEmpty(list6)).isFalse();

        //8. 判断某个数组是否为空
        String[] arr2 = {};
        assertThat(CommonUtils.isEmpty(arr1)).isFalse();
        assertThat(CommonUtils.isEmpty(arr2)).isTrue();
        assertThat(CommonUtils.isNotEmpty(arr1)).isTrue();
        assertThat(CommonUtils.isNotEmpty(arr2)).isFalse();

        //9. 判断某个对象是否为空，字符串、集合、映射
        Integer inte = null;
        String str = "";
        Object obj = null;
        assertThat(CommonUtils.isEmpty(inte)).isTrue();
        assertThat(CommonUtils.isEmpty(str)).isTrue();
        assertThat(CommonUtils.isEmpty(obj)).isTrue();
        assertThat(CommonUtils.isNotEmpty(inte)).isFalse();
        assertThat(CommonUtils.isNotEmpty(str)).isFalse();
        assertThat(CommonUtils.isNotEmpty(obj)).isFalse();

        //10. 删除map中的空值
        Map<String,Object> map7 = new HashMap<>();
        map7.put("1",1);
        map7.put("2",inte);
        map7.put("3",str);
        map7.put("4",obj);
        Map<String,Object> map8 = CommonUtils.removeEmptyFromMap(map7);
        for(Map.Entry<String,Object> m : map8.entrySet()){
            assertThat(m.getKey()).isEqualTo("1");
            assertThat(m.getValue()).isEqualTo(1);
        }

        //11. 删除数组中的空值
        Object[] obj7 = {1,inte,str,obj};
        Object[] obj8 = CommonUtils.removeIfEmpty(obj7);
        for(Object o : obj8){
            assertThat(o).isEqualTo(1);
        }

        //12. 将某个Collection转成数组
        List<String> list7 = new ArrayList<>();
        list7.add("1");
        list7.add("2");
        list7.add("3");
        String[] str7 =  CommonUtils.toArray(list7);
        assertThat(str7[0]).isEqualTo("1");
        assertThat(str7[1]).isEqualTo("2");
        assertThat(str7[2]).isEqualTo("3");

        //13. 删除list中的空项
        List<Object> list8 = new ArrayList<>();
        list8.add("1");
        list8.add(inte);
        list8.add(str);
        list8.add(obj);
        List<Object> list9 = CommonUtils.removeIfEmpty(list8);
        for(Object o : list9){
            assertThat(o).isEqualTo("1");
        }

        //14. List<VO>转map，输入分组字段
        StudentBase sb1 = new StudentBase();
        sb1.setStuName("100");
        sb1.setStuAge("11");
        sb1.setStuAddress("white");
        StudentBase sb2 = new StudentBase();
        sb2.setStuName("100");
        sb2.setStuAge("12");
        sb2.setStuAddress("black");
        StudentBase sb3 = new StudentBase();
        sb3.setStuName("300");
        sb3.setStuAge("22");
        sb3.setStuAddress("green");
        StudentBase sb4 = new StudentBase();
        sb4.setStuName("300");
        sb4.setStuAge("22");
        sb4.setStuAddress("red");
        List<StudentBase> list10 = CommonUtils.asArrayList(sb1,sb2,sb3,sb4);
        Map<String,StudentBase> map10 =  CommonUtils.list2MapByGroup(list10,"stuName");
        assertThat(map10).isNotEmpty();
        assertThat(map10.size()).isEqualTo(2);
        map10 =  CommonUtils.list2MapByGroup(list10,"stuName,stuAge");
        assertThat(map10.size()).isEqualTo(3);
        map10 =  CommonUtils.list2MapByGroup(list10,"stuAddress");
        assertThat(map10.size()).isEqualTo(4);
        /* for(Map.Entry<String,StudentBase> m : map10.entrySet()){
            System.out.println(m.getKey() + " - " + m.getValue().getStuName() + " - " + m.getValue().getStuAge() + " - " + m.getValue().getStuAddress());
        }*/

        //System.out.print(CommonUtils.isEmpty(map5)+" - " + CommonUtils.isEmpty(map6));
    }

}
