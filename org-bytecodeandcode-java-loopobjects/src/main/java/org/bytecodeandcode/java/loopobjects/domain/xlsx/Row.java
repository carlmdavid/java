package org.bytecodeandcode.java.loopobjects.domain.xlsx;

import org.apache.poi.xssf.usermodel.XSSFRow;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

/**
 * Wrapper for excel row
 */

@Data(staticConstructor = "of")
@Accessors(fluent = true)
public class Row {
	@Delegate
	private final XSSFRow row;

}
