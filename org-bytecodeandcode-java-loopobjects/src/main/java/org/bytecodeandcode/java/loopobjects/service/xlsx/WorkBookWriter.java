package org.bytecodeandcode.java.loopobjects.service.xlsx;

import java.io.IOException;

import org.bytecodeandcode.java.loopobjects.domain.FidInstanceMap;
import org.bytecodeandcode.java.loopobjects.domain.xlsx.Sheet;
import org.bytecodeandcode.java.loopobjects.domain.xlsx.Workbook;
import org.bytecodeandcode.java.loopobjects.service.Writer;

import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "of")
@Accessors(fluent = true)
public class WorkBookWriter implements Writer {

	private final Workbook workbook;
	private final FidInstanceMap map;

	@Override
	public void init() {

	}

	@Override
	public void write() throws Exception {
		SheetWriter sheetWriter = SheetWriter.of(Sheet.of(workbook.createSheet("TestSheetName")), map);
		sheetWriter.write();
	}

	@Override
	public void destroy() {
		try {
			this.workbook.close();
		} catch (IOException e) {
		}
	}

}
