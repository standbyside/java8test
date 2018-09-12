package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 此帮助类严格限定为有typeName和typeCode的枚举类，如定义枚举类型为 ADMIN(typeName,typeCode)这种
 */
public class EnumHelperUtil {

  /**
   * indexOf,传入的参数ordinal指的是需要的枚举值在设定的枚举类中的顺序，以0开始
   * T
   *
   * @param clazz
   * @param ordinal
   * @return
   */
  public static <T extends Enum<T>> T indexOf(Class<T> clazz, int ordinal) {
    return clazz.getEnumConstants()[ordinal];
  }

  /**
   * nameOf,传入的参数name指的是枚举值的名称，一般是大写加下划线的
   * T
   *
   * @param clazz
   * @param name
   * @return Enum T
   */
  public static <T extends Enum<T>> T nameOf(Class<T> clazz, String name) {

    return Enum.valueOf(clazz, name);
  }


  /**
   * 使用枚举类型对应的typeCode获取枚举类型
   * T
   *
   * @param clazz         枚举类的class
   * @param getMethodName 传入的typeCode的get方法
   * @param integer       传入的typeCode值，这个方法为Integer类型
   * @return
   * @author xiehao
   */
  public static <T extends Enum<T>> T getByInteger(Class<T> clazz, String getMethodName, Integer integer) {
    try {
      T[] arr = clazz.getEnumConstants();
      Method method = clazz.getDeclaredMethod(getMethodName);
      Integer val;
      for (T entity : arr) {
        val = Integer.valueOf(method.invoke(entity).toString());
        if (val.equals(integer)) {
          return entity;
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 使用枚举类型对应的typeName获取枚举类型
   * T
   *
   * @param clazz         枚举类的class
   * @param getMethodName 传入的typeName的get方法
   * @param s             传入的typeName值，这个方法为String类型
   * @return
   */
  public static <T extends Enum<T>> T getByString(Class<T> clazz, String getMethodName, String s) {
    try {
      T[] arr = clazz.getEnumConstants();
      Method method = clazz.getDeclaredMethod(getMethodName);
      String val;
      for (T entity : arr) {
        val = method.invoke(entity).toString();
        if (val.contentEquals(s)) {
          return entity;
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    return null;
  }
}