package org.bytecodeandcode.java.loopobjects.service.xlsx;

import org.bytecodeandcode.java.loopobjects.domain.FidInstanceMap;
import org.bytecodeandcode.java.loopobjects.domain.xlsx.Sheet;
import org.bytecodeandcode.java.loopobjects.service.Writer;

import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "of")
@Accessors(fluent = true)
public class SheetWriter implements Writer {
	
	private final Sheet sheet;
	private final FidInstanceMap map;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write() throws Exception {
		int rowIndex = 0;
		
		// FID from the sheet metadata
		String fid  = "foo";
		
//		FidInstanceMapUtil.getLoopObjects(tradeItem, fids)
		
//		RowWriter.of(Row.of(sheet.createRow(rowIndex)), map, coordinates);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
