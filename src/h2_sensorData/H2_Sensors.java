/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package h2_sensorData;

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
@Table(name = "SENSORS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "H2_Sensors.findAll", query = "SELECT h FROM H2_Sensors h"),
    @NamedQuery(name = "H2_Sensors.findByIdsensors", query = "SELECT h FROM H2_Sensors h WHERE h.idsensors = :idsensors"),
    @NamedQuery(name = "H2_Sensors.findByNetworkId", query = "SELECT h FROM H2_Sensors h WHERE h.networkId = :networkId"),
    @NamedQuery(name = "H2_Sensors.findByActive", query = "SELECT h FROM H2_Sensors h WHERE h.active = :active")})
public class H2_Sensors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDSENSORS")
    private Integer idsensors;
    @Basic(optional = false)
    @Column(name = "NETWORK_ID")
    private String networkId;
    @Basic(optional = false)
    @Column(name = "ACTIVE")
    private short active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensorsIdsensors")
    private Collection<H2_SensorData> h2SensorDataCollection;
    @JoinColumn(name = "SENSORTYPES_IDSENSORTYPES", referencedColumnName = "IDSENSORTYPES")
    @ManyToOne(optional = false)
    private H2_Sensortypes sensortypesIdsensortypes;
    @JoinColumn(name = "LOCATIONS_IDLOCATIONS", referencedColumnName = "IDLOCATIONS")
    @ManyToOne(optional = false)
    private H2_Locations locationsIdlocations;

    public H2_Sensors() {
    }

    public H2_Sensors(Integer idsensors) {
        this.idsensors = idsensors;
    }

    public H2_Sensors(Integer idsensors, String networkId, short active) {
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

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    @XmlTransient
    public Collection<H2_SensorData> getH2SensorDataCollection() {
        return h2SensorDataCollection;
    }

    public void setH2SensorDataCollection(Collection<H2_SensorData> h2SensorDataCollection) {
        this.h2SensorDataCollection = h2SensorDataCollection;
    }

    public H2_Sensortypes getSensortypesIdsensortypes() {
        return sensortypesIdsensortypes;
    }

    public void setSensortypesIdsensortypes(H2_Sensortypes sensortypesIdsensortypes) {
        this.sensortypesIdsensortypes = sensortypesIdsensortypes;
    }

    public H2_Locations getLocationsIdlocations() {
        return locationsIdlocations;
    }

    public void setLocationsIdlocations(H2_Locations locationsIdlocations) {
        this.locationsIdlocations = locationsIdlocations;
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
        if (!(object instanceof H2_Sensors)) {
            return false;
        }
        H2_Sensors other = (H2_Sensors) object;
        if ((this.idsensors == null && other.idsensors != null) || (this.idsensors != null && !this.idsensors.equals(other.idsensors))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "h2_sensorData.H2_Sensors[ idsensors=" + idsensors + " ]";
    }
    
}
