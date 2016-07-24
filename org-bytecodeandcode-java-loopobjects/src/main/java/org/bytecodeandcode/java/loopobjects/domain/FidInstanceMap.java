package org.bytecodeandcode.java.loopobjects.domain;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.experimental.Delegate;

public class FidInstanceMap {
	
	@Delegate
	private Map<FidInstance, Object> internalFidInstanceMap = Maps.newHashMap();
	
	
}
