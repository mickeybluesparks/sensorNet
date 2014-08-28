/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package h2_sensorData;

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
@Table(name = "SENSOR_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "H2_SensorData.findAll", query = "SELECT h FROM H2_SensorData h"),
    @NamedQuery(name = "H2_SensorData.findByIdsensorData", query = "SELECT h FROM H2_SensorData h WHERE h.idsensorData = :idsensorData"),
    @NamedQuery(name = "H2_SensorData.findByTimeStamp", query = "SELECT h FROM H2_SensorData h WHERE h.timeStamp = :timeStamp"),
    @NamedQuery(name = "H2_SensorData.findByValue1", query = "SELECT h FROM H2_SensorData h WHERE h.value1 = :value1"),
    @NamedQuery(name = "H2_SensorData.findByValue2", query = "SELECT h FROM H2_SensorData h WHERE h.value2 = :value2"),
    @NamedQuery(name = "H2_SensorData.findByValue3", query = "SELECT h FROM H2_SensorData h WHERE h.value3 = :value3"),
    @NamedQuery(name = "H2_SensorData.findByValue4", query = "SELECT h FROM H2_SensorData h WHERE h.value4 = :value4"),
    @NamedQuery(name = "H2_SensorData.findByValue5", query = "SELECT h FROM H2_SensorData h WHERE h.value5 = :value5"),
    @NamedQuery(name = "H2_SensorData.findByState1", query = "SELECT h FROM H2_SensorData h WHERE h.state1 = :state1"),
    @NamedQuery(name = "H2_SensorData.findByState2", query = "SELECT h FROM H2_SensorData h WHERE h.state2 = :state2")})
public class H2_SensorData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDSENSOR_DATA")
    private Integer idsensorData;
    @Basic(optional = false)
    @Column(name = "TIME_STAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALUE_1")
    private Double value1;
    @Column(name = "VALUE_2")
    private Double value2;
    @Column(name = "VALUE_3")
    private Double value3;
    @Column(name = "VALUE_4")
    private Integer value4;
    @Column(name = "VALUE_5")
    private Integer value5;
    @Column(name = "STATE_1")
    private Short state1;
    @Column(name = "STATE_2")
    private Short state2;
    @JoinColumn(name = "SENSORS_IDSENSORS", referencedColumnName = "IDSENSORS")
    @ManyToOne(optional = false)
    private H2_Sensors sensorsIdsensors;

    public H2_SensorData() {
    }

    public H2_SensorData(Integer idsensorData) {
        this.idsensorData = idsensorData;
    }

    public H2_SensorData(Integer idsensorData, Date timeStamp) {
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

    public Double getValue1() {
        return value1;
    }

    public void setValue1(Double value1) {
        this.value1 = value1;
    }

    public Double getValue2() {
        return value2;
    }

    public void setValue2(Double value2) {
        this.value2 = value2;
    }

    public Double getValue3() {
        return value3;
    }

    public void setValue3(Double value3) {
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

    public Short getState1() {
        return state1;
    }

    public void setState1(Short state1) {
        this.state1 = state1;
    }

    public Short getState2() {
        return state2;
    }

    public void setState2(Short state2) {
        this.state2 = state2;
    }

    public H2_Sensors getSensorsIdsensors() {
        return sensorsIdsensors;
    }

    public void setSensorsIdsensors(H2_Sensors sensorsIdsensors) {
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
        if (!(object instanceof H2_SensorData)) {
            return false;
        }
        H2_SensorData other = (H2_SensorData) object;
        if ((this.idsensorData == null && other.idsensorData != null) || (this.idsensorData != null && !this.idsensorData.equals(other.idsensorData))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "h2_sensorData.H2_SensorData[ idsensorData=" + idsensorData + " ]";
    }
    
}
