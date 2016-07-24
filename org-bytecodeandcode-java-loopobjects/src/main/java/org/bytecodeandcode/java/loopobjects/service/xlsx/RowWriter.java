package org.bytecodeandcode.java.loopobjects.service.xlsx;

import java.util.Stack;

import org.bytecodeandcode.java.loopobjects.domain.FidInstanceMap;
import org.bytecodeandcode.java.loopobjects.domain.xlsx.Row;
import org.bytecodeandcode.java.loopobjects.service.Writer;

import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "of")
@Accessors(fluent = true)
public class RowWriter implements Writer {

	private final Row row;
	private final FidInstanceMap map;
	private final Stack<Integer> coordinates;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void write() throws Exception {
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
