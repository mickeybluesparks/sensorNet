/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorData;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mike
 */
@Entity
@Table(name = "sensor_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SensorData.findAll", query = "SELECT s FROM SensorData s"),
    @NamedQuery(name = "SensorData.findByIdsensorData", query = "SELECT s FROM SensorData s WHERE s.idsensorData = :idsensorData"),
    @NamedQuery(name = "SensorData.findByTimeStamp", query = "SELECT s FROM SensorData s WHERE s.timeStamp = :timeStamp"),
    @NamedQuery(name = "SensorData.findByValue1", query = "SELECT s FROM SensorData s WHERE s.value1 = :value1"),
    @NamedQuery(name = "SensorData.findByValue2", query = "SELECT s FROM SensorData s WHERE s.value2 = :value2"),
    @NamedQuery(name = "SensorData.findByValue3", query = "SELECT s FROM SensorData s WHERE s.value3 = :value3"),
    @NamedQuery(name = "SensorData.findByValue4", query = "SELECT s FROM SensorData s WHERE s.value4 = :value4"),
    @NamedQuery(name = "SensorData.findByValue5", query = "SELECT s FROM SensorData s WHERE s.value5 = :value5"),
    @NamedQuery(name = "SensorData.findByState1", query = "SELECT s FROM SensorData s WHERE s.state1 = :state1"),
    @NamedQuery(name = "SensorData.findByState2", query = "SELECT s FROM SensorData s WHERE s.state2 = :state2")})
public class SensorData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsensor_data")
    private Integer idsensorData;
    @Basic(optional = false)
    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value_1")
    private Float value1;
    @Column(name = "value_2")
    private Float value2;
    @Column(name = "value_3")
    private Float value3;
    @Column(name = "value_4")
    private Integer value4;
    @Column(name = "value_5")
    private Integer value5;
    @Column(name = "state_1")
    private Boolean state1;
    @Column(name = "state_2")
    private Boolean state2;
    @JoinColumn(name = "sensors_idsensors", referencedColumnName = "idsensors")
    @ManyToOne(optional = false)
    private Sensors sensorsIdsensors;

    public SensorData() {
    }

    public SensorData(Integer idsensorData) {
        this.idsensorData = idsensorData;
    }

    public SensorData(Integer idsensorData, Date timeStamp) {
        this.idsensorData = idsensorData;
        this.timeStamp = timeStamp;
    }

    public Integer getIdsensorData() {
        return idsensorData;
    }

    public void setIdsensorData(Integer idsensorData) {
        this.idsensorData = idsensorData;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Float getValue1() {
        return value1;
    }

    public void setValue1(Float value1) {
        this.value1 = value1;
    }

    public Float getValue2() {
        return value2;
    }

    public void setValue2(Float value2) {
        this.value2 = value2;
    }

    public Float getValue3() {
        return value3;
    }

    public void setValue3(Float value3) {
        this.value3 = value3;
    }

    public Integer getValue4() {
        return value4;
    }

    public void setValue4(Integer value4) {
        this.value4 = value4;
    }

    public Integer getValue5() {
        return value5;
    }

    public void setValue5(Integer value5) {
        this.value5 = value5;
    }

    public Boolean getState1() {
        return state1;
    }

    public void setState1(Boolean state1) {
        this.state1 = state1;
    }

    public Boolean getState2() {
        return state2;
    }

    public void setState2(Boolean state2) {
        this.state2 = state2;
    }

    public Sensors getSensorsIdsensors() {
        return sensorsIdsensors;
    }

    public void setSensorsIdsensors(Sensors sensorsIdsensors) {
        this.sensorsIdsensors = sensorsIdsensors;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsensorData != null ? idsensorData.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SensorData)) {
            return false;
        }
        SensorData other = (SensorData) object;
        if ((this.idsensorData == null && other.idsensorData != null) || (this.idsensorData != null && !this.idsensorData.equals(other.idsensorData))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sensorData.SensorData[ idsensorData=" + idsensorData + " ]";
    }
    
}
