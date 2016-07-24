package org.bytecodeandcode.java.loopobjects.domain.xlsx;

import org.apache.poi.xssf.usermodel.XSSFCell;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

/**
 * Wrapper for excel cell
 * @author Carl
 *
 */
@Data(staticConstructor = "of")
@Accessors(fluent=true)
public class Cell {

	@Delegate
	private final XSSFCell cell;
}
