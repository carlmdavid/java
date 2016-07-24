/**
 * 
 */
package org.bytecodeandcode.java.loopobjects.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.bytecodeandcode.java.loopobjects.Application;
import org.bytecodeandcode.java.loopobjects.domain.FidInstanceMap;
import org.gs1ca.tradeitem.TradeItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Carl
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class FidInstanceMapUtilTest {

	/**
	 * Test method for {@link org.bytecodeandcode.java.loopobjects.service.FidInstanceMapUtil#convertToFidInstanceMap(org.gs1ca.tradeitem.TradeItem, java.lang.String)}.
	 */
	@Test
	public void testConvertToFidInstanceMap() throws Exception {
		
		TradeItem tradeItem = new TradeItem();
		tradeItem.setAttributeValue("19190", new String[] {"1245678","23456789"});
		tradeItem.setAttributeValue("19210", new String[][] {{"abcd","efgh","ijkl"},{"mnop","qrst","uvwx"}});
		
		FidInstanceMap map = FidInstanceMapUtil.convertToFidInstanceMap(tradeItem, "19210");;
		assertThat(map.size(), equalTo(12));
	}

}
