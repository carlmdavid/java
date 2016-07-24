/**
 * 
 */
package org.bytecodeandcode.java.loopobjects.domain.xlsx;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

/**
 * Wrapper for excel workbook
 * 
 * @author Carl
 *
 */
@Data(staticConstructor = "of")
@Accessors(fluent = true)
public class Workbook {
	@Delegate
	private XSSFWorkbook workbook;

	
}
