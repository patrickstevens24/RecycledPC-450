package pws24.uw.tacoma.edu.recycledpc;



import android.util.Log;

import junit.framework.AssertionFailedError;


import org.junit.Test;


import pws24.uw.tacoma.edu.recycledpc.item.ItemContent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

/**
 * Created by Arthur on 3/9/2017.
 */

public class ItemCreateTest {

    @Test
    public void testItemConstructor(){

        assertNotNull(new ItemContent("PCname", "$5.00", "This is a test", 5, "pathway.com"));
    }
    @Test(expected=IllegalArgumentException.class)
    public void testAccountConstructorBadName() {
        new ItemContent("P", "$5.00", "This is a test", 5, "pathway.com");
    }
    @Test(expected=IllegalArgumentException.class)
    public void testAccountConstructorBadName2() {
        new ItemContent("PC", "$5.00", "This is a test", 5, "pathway.com");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAccountConstructorBadPrice() {
        new ItemContent("PcName", "$", "This is a test", 5, "pathway.com");
    }
    @Test(expected=IllegalArgumentException.class)
    public void testAccountConstructorBadPrice2() {
        new ItemContent("PcName", "Gimme yer muney", "This is a test", 5, "pathway.com");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAccountConstructorBadDesc() {
        new ItemContent("PcName", "$5.00", "noDes", 5, "pathway.com");
    }
    @Test(expected=IllegalArgumentException.class)
    public void testAccountConstructorBadDesc2() {
        new ItemContent("PcName", "$5.00", "ab", 5, "pathway.com");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAccountConstructorNull() {
        new ItemContent(null, null, null, 5, "pathway.com");
    }



}
