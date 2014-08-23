/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LLAP;

import org.junit.AfterClass;
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
        System.out.println("parseMessage");
        byte[] msg = null;
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.parseMessage(msg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receivedStartMsg method, of class LLAPMessageParser.
     */
    @Test
    public void testReceivedStartMsg() {
        System.out.println("receivedStartMsg");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.receivedStartMsg();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDeviceID method, of class LLAPMessageParser.
     */
    @Test
    public void testGetDeviceID() {
        System.out.println("getDeviceID");
        LLAPMessageParser instance = new LLAPMessageParser();
        byte[] expResult = null;
        byte[] result = instance.getDeviceID();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receivedVersionNumber method, of class LLAPMessageParser.
     */
    @Test
    public void testReceivedVersionNumber() {
        System.out.println("receivedVersionNumber");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.receivedVersionNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receivedTemperature method, of class LLAPMessageParser.
     */
    @Test
    public void testReceivedTemperature() {
        System.out.println("receivedTemperature");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.receivedTemperature();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageValue method, of class LLAPMessageParser.
     */
    @Test
    public void testGetMessageValue() {
        System.out.println("getMessageValue");
        LLAPMessageParser instance = new LLAPMessageParser();
        double expResult = 0.0;
        double result = instance.getMessageValue();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageText method, of class LLAPMessageParser.
     */
    @Test
    public void testGetMessageText() {
        System.out.println("getMessageText");
        LLAPMessageParser instance = new LLAPMessageParser();
        String expResult = "";
        String result = instance.getMessageText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deviceAwaken method, of class LLAPMessageParser.
     */
    @Test
    public void testDeviceAwaken() {
        System.out.println("deviceAwaken");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.deviceAwaken();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of batteryLow method, of class LLAPMessageParser.
     */
    @Test
    public void testBatteryLow() {
        System.out.println("batteryLow");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.batteryLow();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receivedBatteryLevel method, of class LLAPMessageParser.
     */
    @Test
    public void testReceivedBatteryLevel() {
        System.out.println("receivedBatteryLevel");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.receivedBatteryLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of confirmDeviceIDChange method, of class LLAPMessageParser.
     */
    @Test
    public void testConfirmDeviceIDChange() {
        System.out.println("confirmDeviceIDChange");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.confirmDeviceIDChange();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receivedErrorCode method, of class LLAPMessageParser.
     */
    @Test
    public void testReceivedErrorCode() {
        System.out.println("receivedErrorCode");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.receivedErrorCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receivedFirmwareVersion method, of class LLAPMessageParser.
     */
    @Test
    public void testReceivedFirmwareVersion() {
        System.out.println("receivedFirmwareVersion");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.receivedFirmwareVersion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receivedHelloMsg method, of class LLAPMessageParser.
     */
    @Test
    public void testReceivedHelloMsg() {
        System.out.println("receivedHelloMsg");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.receivedHelloMsg();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isRebootConfirmed method, of class LLAPMessageParser.
     */
    @Test
    public void testIsRebootConfirmed() {
        System.out.println("isRebootConfirmed");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.isRebootConfirmed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receivedSerialNumber method, of class LLAPMessageParser.
     */
    @Test
    public void testReceivedSerialNumber() {
        System.out.println("receivedSerialNumber");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.receivedSerialNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deviceGoingToSleep method, of class LLAPMessageParser.
     */
    @Test
    public void testDeviceGoingToSleep() {
        System.out.println("deviceGoingToSleep");
        LLAPMessageParser instance = new LLAPMessageParser();
        boolean expResult = false;
        boolean result = instance.deviceGoingToSleep();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
