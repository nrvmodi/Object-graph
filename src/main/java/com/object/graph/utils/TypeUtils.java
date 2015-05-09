/**
 * 
 */
package com.object.graph.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Nirav.Modi
 *
 */
public class TypeUtils {


    private static Map<String, Class<?>> SUBST_MAP = new TreeMap<String, Class<?>>();
    private static Map<String, Class<?>> SIMPLE_MAP = new TreeMap<String, Class<?>>();


    static {
        SUBST_MAP.put(Byte.class.getName(), Byte.TYPE);
        SUBST_MAP.put(Short.class.getName(), Short.TYPE);
        SUBST_MAP.put(Integer.class.getName(), Integer.TYPE);
        SUBST_MAP.put(Long.class.getName(), Long.TYPE);
        SUBST_MAP.put(Float.class.getName(), Float.TYPE);
        SUBST_MAP.put(Double.class.getName(), Double.TYPE);
        SUBST_MAP.put(Boolean.class.getName(), Boolean.TYPE);
        SUBST_MAP.put(Character.class.getName(), Character.TYPE);
        SIMPLE_MAP.put(String.class.getName(), Boolean.TRUE);

    }




/**
 * Gets the the class type of the types of the argument.
 * 
 * if substPrimitiveWrapper is true,
 * then if there is argument, that represent primitive type wrapper (such as Integer),
 *      then it will be substituted to primitive type (such as int).
 * else no substitution will be done.
 *
 * @param arg object.
 * @param substPrimitiveWrapper - wheteher to do primitive type substitution.
 * @retrun class type.
 */

public static Class<?> getClassType(Object arg, boolean substPrimitiveWrapper){
    Class<?> classType = null;
    String className = null;
    Class<?> substClass = null;

    if(arg != null ){
        //making default classType
        classType = arg.getClass();
        if(substPrimitiveWrapper){
            className = classType.getName();
            substClass = (Class<?>)SUBST_MAP.get(className);
            if(substClass != null){
                classType = substClass;
            }

        }
    }
    return classType;
}

/**
 * This method consider JDK type any primitive type, wrapper class or String.
 * 
 * 
 * @param arg object
 * @return where arg is JDK type or now.
 */
public static boolean isJDKClass(Object arg){
    Class<?> classType = getClassType(arg, true);
    boolean isJDKClass = false;
    if(classType!=null){
        //if(String.class.equals(classType)){
        //  isJDKClass = true; //this is String, note that String is final
        //}
        assert classType!=null;
        String className = classType.getName();
        Boolean isFound =  SIMPLE_MAP.get(className);
        if(Boolean.TRUE.equals(isFound)){
            isJDKClass = true; //this is predefined class
        }


        boolean isPrimitiveType = classType.isPrimitive();
        if(isPrimitiveType){
            isJDKClass = true; //this is primitive type or wrapper class
        }
    }

    return isJDKClass;
 }



}




