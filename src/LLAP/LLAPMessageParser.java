/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LLAP;

/**
 * This class is responsible for parsing the LLAP Messages.
 * 
 * LLAP message structure aDDxxxxxxxxx
 * where DD is a two character device ID followed by up to 9 characters
 * padded out with '-'.
 * LLAP messages are taken from the following list:
 *  a--APVERX.XX - application version number X.XX
 *  a--AWAKE---- - Device has just woken up in order to send data
 *  a--BATTLOW-- - Battery powered device indicates that battery power is low
 *  a--BATTx.xx- - Battery powered device sending current battery voltage x.xx
 *  a--CHDEVIDBB - Device has confirmed change of ID to BB. Follow this with a 
 *                  Reboot message to save the new device ID.
 * <p>
 * The message is first checked for length (12 bytes) and
 * the first character has to be 'a'. The message is then stripped
 * of any trailing '-' padding characters before being parsed and the
 * appropriate flags and data values being set
 * @author Mike
 */
public class LLAPMessageParser {
    
    private byte[] m_deviceID;
    private String m_messageBody;
    private double m_messageValue;
    private String m_messageString;
    private boolean m_parseOk;
    
            // received message types
        
    private boolean m_version;
    private boolean m_awake;
    private boolean m_batteryLow;
    private boolean m_batteryValue;
    private boolean m_idChanged;
    private boolean m_errorCode;
    private boolean m_firmwareVersion;
    private boolean m_hello;
    private boolean m_reboot;
    private boolean m_serialNum;
    private boolean m_sleeping;
    private boolean m_started;
    private boolean m_temperature;

    
    public LLAPMessageParser()
    {
        m_deviceID = new byte[2];
        
        m_messageBody = new String();
        
        m_parseOk = false;
        
        resetFlags();
        
    }
    
    private void resetFlags()
    {
        m_deviceID[0] = '-';
        m_deviceID[1] = '-';
        m_messageValue = 0;
        m_messageBody = "";
        
        m_version = false;
        m_awake = false;
        m_batteryLow = false;
        m_batteryValue = false;
        m_idChanged = false;
        m_errorCode = false;
        m_firmwareVersion = false;
        m_hello = false;
        m_reboot = false;
        m_serialNum = false;
        m_sleeping = false;
        m_started = false;
        m_temperature = false;
                
    }
    
    public boolean parseMessage(byte[] msg)
    {
        m_parseOk = false;
        
        resetFlags();
        
        // check the first byte for 'a' to indicate a LLAP m_messageBody protocol
        // also length of m_messageBody is always 12
        
        if ((msg[0] != 'a') || (msg.length != 12))
            return false;
        
        // next two bytes are the id of the device
        
        m_deviceID[0] = msg[1];
        m_deviceID[1] = msg[2];
        
        // the next 9 bytes represent the m_messageBody which can be padded
        // out with the character '-'
        
        for (int i = 0; i < 9; i++)
        {
           if (msg[i+3] != '-')
               m_messageBody += (char)msg[i+3];
        }
        
        // now parse the body of the message
        
        switch (m_messageBody.charAt(0))
        {
            case 'A' :      // either APVER or AWAKE
            {
                if (m_messageBody.startsWith("APVER"))
                {
                    // Recieved version number from device
                    // check that the message value is indeed a number

                    if (Character.isDigit(m_messageBody.charAt(5)))
                    {
                    
                        m_version = true;
                        m_parseOk = true;

                        // now extract the version number

                        m_messageValue = Double.valueOf(m_messageBody.substring(5));
                    }
                    
                    break;                   
                }
                
                if (m_messageBody.equals("AWAKE"))
                {
                
                    // Device has just woken up

                    m_awake = true;
                    m_parseOk = true;
                }
                break;
            }
            
            case 'B' :      // either BATT or BATTLOW
            {
                if (m_messageBody.equals("BATTLOW"))
                {
                    m_batteryLow = true;
                    m_parseOk = true;
                    break;
                }
                
                if (m_messageBody.startsWith("BATT"))
                {
                    // check that the message value is indeed a number

                    if (Character.isDigit(m_messageBody.charAt(5)))
                    {
                        m_batteryValue = true;
                        m_parseOk = true;

                        // extract the battery value

                        m_messageValue = Double.valueOf(m_messageBody.substring(5));
                   }
                }
                    
                break;                                   
            }
            
            case 'C' :      // CHDEVIDxx - reponse to confirm change
            {
                if (m_messageBody.startsWith("CHDEVID"))
                {
                
                    m_idChanged = true;

                    m_messageString = new String(m_messageBody.substring(7));
                    m_parseOk = true;
                }
                
                break;
            }
            
            case 'E' :      // ERRORnnnn - four character error code
            {
                if (m_messageBody.startsWith("ERROR"))
                {
                    m_errorCode = true;
                    m_messageString = new String(m_messageBody.substring(5));
                    m_parseOk = true;
                }
                
                break;
            }
            
            case 'F' :      // FVER
            {
             
                if (m_messageBody.startsWith("FVER"))
                {
                    if (Character.isDigit(m_messageBody.charAt(4)))
                    {               
                        m_firmwareVersion = true;
                        m_messageValue = Double.valueOf(m_messageBody.substring(4));
                        m_parseOk = true;
                    }
                }
                
                break;
            }
            
            case 'H' :      // HELLO
            {
                
                if (m_messageBody.equals("HELLO"))
                {
                    m_hello = true;
                    m_parseOk = true;
                }
                break;
            }
            
            case 'R' :      // REBOOT Confirmed
            {
                if (m_messageBody.equals("REBOOT"))
                {
                    m_reboot = true;
                    m_parseOk = true;
                }
                break;
            }
            
            case 'S' :      // SER or SLEEPING or STARTED
            {
                if (m_messageBody.startsWith("SER"))
                {
                    if (Character.isDigit(m_messageBody.charAt(3)))
                    {
                    
                        m_serialNum = true;
                        m_messageValue = Double.valueOf(m_messageBody.substring(3));
                        m_parseOk = true;
                    }
                    break;
                }
                
                if (m_messageBody.equals("SLEEPING"))
                {
                    m_sleeping = true;
                    m_parseOk = true;
                    break;
                }
                
                if (m_messageBody.equals("STARTED"))
                {
                
                    // Must be started

                    m_started = true;
                    m_parseOk = true;
                }
                    
                break;
            }
            
            case 'T' :          // TEMP or TMPA
            {
                if ((m_messageBody.startsWith("TEMP")) ||
                        (m_messageBody.startsWith("TMPA")))
                {

                    if (Character.isDigit(m_messageBody.charAt(4)))
                    {
                        m_temperature = true;
                        m_messageValue = Double.valueOf(m_messageBody.substring(4));
                        m_parseOk = true;
                    }
                }
                
                break;
            }
 
            default :
                // failed to parse
                break;
        }
        
        return m_parseOk;
    }
    
    public boolean receivedStartMsg()
    {
        return m_started;
    }
    
    public byte[] getDeviceID()
    {
        return m_deviceID;
    }
    
    public boolean receivedVersionNumber()
    {
        return m_version;
    }
    
    public boolean receivedTemperature()
    {
        return m_temperature;
    }
    
    public double getMessageValue()
    {
        return m_messageValue;
    }
    
    public String getMessageText()
    {
        return m_messageString;
    }
    
    public boolean deviceAwaken()
    {
        return m_awake;
    }
    
    public boolean batteryLow()
    {
        return m_batteryLow;
    }
    
    public boolean receivedBatteryLevel()
    {
        return m_batteryValue;
    }
    
    public boolean confirmDeviceIDChange()
    {
        return m_idChanged;
    }
    
    public boolean receivedErrorCode()
    {
        return m_errorCode;
    }
    
    public boolean receivedFirmwareVersion()
    {
        return m_firmwareVersion;
    }
    
    public boolean receivedHelloMsg()
    {
        return m_hello;
    }
    
    public boolean isRebootConfirmed()
    {
        return m_reboot;
    }
    
    public boolean receivedSerialNumber()
    {
        return m_serialNum;
    }
    
    public boolean deviceGoingToSleep()
    {
        return m_sleeping;
    }
}
