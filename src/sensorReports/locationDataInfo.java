/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorReports;

import java.util.Date;

/**
 *
 * @author Mike
 */
public class locationDataInfo {
    
    private String location_name;
    private String sensorType;
    private String sensorID;
    private boolean sensor_state;
    private Date startDate;
    private Date endDate;
    private int numOfRecords;
    
    
    public locationDataInfo()
    {
        sensor_state = false;
        numOfRecords = 0;
    }
    
    public void setLocationName(String name)
    {
        location_name = name;
    }
    
    public String getLocationName()
    {
        return location_name;
    }
    
    public void setSensorType(String type)
    {
        sensorType = type;
    }
    
    public String getSensorType()
    {
        return sensorType;
    }
    
    public void setSensorID(String id)
    {
        sensorID = id;
    }
    
    public String getSensorID()
    {
        return sensorID;
    }
    
    public void setSensorState(boolean state)
    {
        sensor_state = state;
    }
    
    public boolean getSensorState()
    {
        return sensor_state;
    }
    
    public void setStartDate(Date start)
    {
        startDate = start;
    }
    
    public Date getStartDate()
    {
        return startDate;
    }
    
    public void setEndDate(Date finishDate)
    {
        endDate = finishDate;
    }
 
    public Date getEndDate()
    {
        return endDate;
    }
    
    public void setNumOfRecords(int count)
    {
        numOfRecords = count;
    }
    
    public int getNumOfRecords()
    {
        return numOfRecords;
    }
}
