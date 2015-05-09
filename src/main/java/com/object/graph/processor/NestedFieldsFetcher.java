package com.object.graph.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.reflections.ReflectionUtils;

import com.object.graph.test.StudentTest;

/**
 * 
 * @author Nirav.Modi
 *
 */
public class NestedFieldsFetcher {

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		NestedFieldsFetcher processor = new NestedFieldsFetcher();
		processor.getNestedFieldsWithAnnotation(null, new StudentTest());
	}

	public Map<String, Field> getNestedFieldsWithAnnotation(
			Annotation annotation, Object obj) throws InstantiationException,
			IllegalAccessException {
		if (obj != null) {
			Map<String, Field> nestedFields = new HashMap<String, Field>();
			nestedFields.putAll(getNestedFields(null, getProperties(obj), obj));
			for (String key : nestedFields.keySet()) {
				System.out.println(key + ":" + nestedFields.get(key));
			}
		}
		return null;
	}

	private Map<String, Field> getNestedFields(String path,
			Set<Field> nestedFields, Object obj) throws InstantiationException,
			IllegalAccessException {
		Map<String, Field> map = new HashMap<String, Field>();
		for (Field nestedField : nestedFields) {
			nestedField.setAccessible(true);
			String nestedPath = null;
			if (path == null) {
				nestedPath = nestedField.getName();
			} else {
				nestedPath = path + "." + nestedField.getName();
			}
			map.put(nestedPath, nestedField);
			if (!nestedField.getType().isPrimitive()
					&& nestedField.getType().isArray()
					&& !fieldTypes().contains(nestedField.getType().toString())) {
				// It's User defined Field
				Object nestedObj = nestedField.get(obj);
				map.putAll(getNestedFields(nestedPath,
						getProperties(nestedObj), nestedObj));
			}
		}
		return map;
	}

	private static Set<String> fieldTypes() {
		Set<String> fieldTypes = new HashSet<String>();
		fieldTypes.add("Collection");
		fieldTypes.add("List");
		fieldTypes.add("ArrayList");
		fieldTypes.add("Set");
		fieldTypes.add("HashSet");
		fieldTypes.add("String");
		return fieldTypes;
	}

	@SuppressWarnings("unchecked")
	public Set<Field> getProperties(Object obj) {
		Set<Field> directFields = new HashSet<Field>();
		if (obj != null) {
			directFields = ReflectionUtils.getAllFields(obj.getClass());
		}
		return directFields;
	}
}
