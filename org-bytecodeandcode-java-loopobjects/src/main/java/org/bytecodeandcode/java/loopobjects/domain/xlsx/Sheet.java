package org.bytecodeandcode.java.loopobjects.domain.xlsx;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

/**
 * Wrapper for excel sheet
 * 
 * @author Carl
 *
 */

@Data(staticConstructor = "of")
@Accessors(fluent = true)
public class Sheet {
	@Delegate
	private final XSSFSheet sheet;
	
	
}
