/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LLAP;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mike
 */
public class LLAPMessageParserTest {
    
    public LLAPMessageParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of parseMessage method, of class LLAPMessageParser.
     */
    @Test
    public void testParseMessage() {
        System.out.println("parseMessage -  current LLAP Message format");
        String msg = "a--STARTED--";
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = true;
        boolean result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);
        
        // no go tests
        
        System.out.println("parseMessage - not a LLAP Message - missing a");
        msg = "b--STARTED--";
        expResult = false;
        result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);

        System.out.println("parseMessage - not a LLAP Message - wrong length (short)");
        msg = "a--STARTED-";
        expResult = false;
        result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);

        System.out.println("parseMessage - not a LLAP Message - wrong length (long)");
        msg = "a--STARTED---";
        expResult = false;
        result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);
        
    }

     /**
     * Test of parseMessage method, - Device ID Check.
     */
    @Test
    public void testDeviceID()
    {
        System.out.println("parseMessage -  checking Device ID extracted");
        String msg = "a--STARTED--";
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = true;
        boolean result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);
        
        System.out.println("Default ID extraction");
        String expectedID = "--";
        Assert.assertArrayEquals(expectedID.getBytes(), instance.getDeviceID());
        
        msg = "aTZSTARTED--";
        result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);
        System.out.println("ID = 'TZ' extraction");
        expectedID = "TZ";
        Assert.assertArrayEquals(expectedID.getBytes(), instance.getDeviceID());      
    }
    
     /**
     * Test of parseMessage method - check for battery low message.
     */
    @Test
    public void testBatteryLowMessage()
    {
        System.out.println("parseMessage -  battery low message received");
        String msg = "a--BATTLOW--";
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = true;
        boolean result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);    // parsed ok
        System.out.println("Parsed OK");       
        assertEquals(expResult, instance.batteryLow());
        
        System.out.println("Misspelt message");
        msg = "a--BATTTOW--";
        expResult = false;      // fails to parse a correct LLAP Message
        result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);    // parsed fail
        
        expResult = false;
        assertEquals(expResult, instance.batteryLow());        
        System.out.println("Check fail on Battery Low Value Message");
        assertEquals(expResult, instance.receivedBatteryLevel());
    }
    
     /**
     * Test of parseMessage method - check for version message.
     */
    @Test
    public void testAppVersionMessage()
    {
        System.out.println("parseMessage -  Application Version received");
        String msg = "a--APVER1.05";
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = true;
        boolean result = instance.parseMessage(msg.getBytes());
        assertEquals(expResult, result);    // parsed ok
        System.out.println("Parsed OK");       
        
        result = instance.receivedVersionNumber();
        assertEquals(expResult, result);    // correct msg received
        System.out.println("Version Number Received");
        
        double expVersion = 1.05;
        double version = instance.getMessageValue();
        System.out.println("Version Number Correct?");
        assertEquals(expVersion, version, 0.01);
        
        
    }
}
