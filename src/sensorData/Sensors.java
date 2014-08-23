/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorData;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mike
 */
@Entity
@Table(name = "sensors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sensors.findAll", query = "SELECT s FROM Sensors s"),
    @NamedQuery(name = "Sensors.findByIdsensors", query = "SELECT s FROM Sensors s WHERE s.idsensors = :idsensors"),
    @NamedQuery(name = "Sensors.findByNetworkId", query = "SELECT s FROM Sensors s WHERE s.networkId = :networkId"),
    @NamedQuery(name = "Sensors.findByActive", query = "SELECT s FROM Sensors s WHERE s.active = :active"),
    @NamedQuery(name = "Sensors.findAllTypes", query = "SELECT s FROM Sensors s WHERE s.networkId LIKE :networkPrefix ORDER BY s.networkId")})
public class Sensors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsensors")
    private Integer idsensors;
    @Basic(optional = false)
    @Column(name = "network_id")
    private String networkId;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @JoinColumn(name = "sensorTypes_idsensorTypes", referencedColumnName = "idsensorTypes")
    @ManyToOne(optional = false)
    private Sensortypes sensorTypesidsensorTypes;
    @JoinColumn(name = "locations_idlocations", referencedColumnName = "idlocations")
    @ManyToOne(optional = false)
    private Locations locationsIdlocations;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensorsIdsensors")
    private Collection<SensorData> sensorDataCollection;

    public Sensors() {
    }

    public Sensors(Integer idsensors) {
        this.idsensors = idsensors;
    }

    public Sensors(Integer idsensors, String networkId, boolean active) {
        this.idsensors = idsensors;
        this.networkId = networkId;
        this.active = active;
    }

    public Integer getIdsensors() {
        return idsensors;
    }

    public void setIdsensors(Integer idsensors) {
        this.idsensors = idsensors;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Sensortypes getSensorTypesidsensorTypes() {
        return sensorTypesidsensorTypes;
    }

    public void setSensorTypesidsensorTypes(Sensortypes sensorTypesidsensorTypes) {
        this.sensorTypesidsensorTypes = sensorTypesidsensorTypes;
    }

    public Locations getLocationsIdlocations() {
        return locationsIdlocations;
    }

    public void setLocationsIdlocations(Locations locationsIdlocations) {
        this.locationsIdlocations = locationsIdlocations;
    }

    @XmlTransient
    public Collection<SensorData> getSensorDataCollection() {
        return sensorDataCollection;
    }

    public void setSensorDataCollection(Collection<SensorData> sensorDataCollection) {
        this.sensorDataCollection = sensorDataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsensors != null ? idsensors.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sensors)) {
            return false;
        }
        Sensors other = (Sensors) object;
        if ((this.idsensors == null && other.idsensors != null) || (this.idsensors != null && !this.idsensors.equals(other.idsensors))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sensorData.Sensors[ idsensors=" + idsensors + " ]";
    }
    
}
