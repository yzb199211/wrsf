package com.yyy.wrsf.enums.util;



import androidx.arch.core.util.Function;

import java.lang.reflect.Method;
import java.util.*;


/**
 *
 */
public class EnumUtils {
    /**
     * @description: 将枚举转换成map
     * @param:
     */
    public static <E extends Enum<E>, K, V> Map<K, V> GetEnumMap(E[] es, Function<E, K> enumKey, Function<E, V> enumVal) {
        try {
            Map<K, V> map = new HashMap<>();
            for (E s : es) {
                map.put(enumKey.apply(s), enumVal.apply(s));
            }
            return map;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * @param:
     */
    public static <E extends Enum<E>, K, V> List<EnumEntity> getEnumList(E[] es, Function<E, K> enumKey, Function<E, V> enumVal) {
        try {
            Map<K, V> map = GetEnumMap(es, enumKey, enumVal);
            List<EnumEntity> enumResultList = new ArrayList<>();
            if (null == map || map.size() == 0) {
                return enumResultList;
            }
            for (K key : map.keySet()) {
                EnumEntity enumResult = new EnumEntity();
                V val = map.get(key);
                enumResult.setId((Integer) key);
                enumResult.setName((String) val);
                enumResultList.add(enumResult);
            }
            return enumResultList;
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * 根据给定的属性和值获取对应的枚举类<br>
     * 没有找到指定枚举类时抛出异常
     * @param enumClz 枚举类类型
     * @param field 属性名称
     * @param value 属性参考值
     * @xuanfeng
     */
    public static <T, P> T getEnumByField(Class<T> enumClz, String field, P value) {
        try {
            if (null == value)
                throw new Exception("No found specified Enum.");
            if (!chkCharUpper(field.charAt(1)))
                field = field.substring(0, 1).toUpperCase() + field.substring(1);
            Method getter = enumClz.getDeclaredMethod("get" + field, new Class[0]);
            List<T> list = Arrays.asList(enumClz.getEnumConstants());
            P fieldValue = null;
            for (T _enum_ : list) {
                fieldValue = (P) getter.invoke(_enum_);
                if (fieldValue.equals(value))
                    return _enum_;
            }
            throw new Exception("No found specified Enum.");
        }catch (Exception e){
            return null;
        }
    }

    public static boolean chkCharUpper(char ch) {
        return (ch >= 'A' && ch <= 'Z');
    }

}
