package org.bytecodeandcode.java.loopobjects.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent=true)
public class FidInstance {

	private final String fid;
	private final int[] indices;
	private final String profile;
}
