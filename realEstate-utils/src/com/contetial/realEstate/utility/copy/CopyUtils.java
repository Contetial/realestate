package com.contetial.realEstate.utility.copy;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public final class CopyUtils {

	public static final void copyBean(Object sourceBean, Object destBean){		
		try {
			BeanUtils.copyProperties(destBean, sourceBean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
