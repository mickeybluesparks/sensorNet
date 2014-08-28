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
@Table(name = "LOCATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "H2_Locations.findAll", query = "SELECT h FROM H2_Locations h"),
    @NamedQuery(name = "H2_Locations.findByIdlocations", query = "SELECT h FROM H2_Locations h WHERE h.idlocations = :idlocations"),
    @NamedQuery(name = "H2_Locations.findByRoomName", query = "SELECT h FROM H2_Locations h WHERE h.roomName = :roomName")})
public class H2_Locations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDLOCATIONS")
    private Integer idlocations;
    @Basic(optional = false)
    @Column(name = "ROOM_NAME")
    private String roomName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationsIdlocations")
    private Collection<H2_Sensors> h2SensorsCollection;

    public H2_Locations() {
    }

    public H2_Locations(Integer idlocations) {
        this.idlocations = idlocations;
    }

    public H2_Locations(Integer idlocations, String roomName) {
        this.idlocations = idlocations;
        this.roomName = roomName;
    }

    public Integer getIdlocations() {
        return idlocations;
    }

    public void setIdlocations(Integer idlocations) {
        this.idlocations = idlocations;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @XmlTransient
    public Collection<H2_Sensors> getH2SensorsCollection() {
        return h2SensorsCollection;
    }

    public void setH2SensorsCollection(Collection<H2_Sensors> h2SensorsCollection) {
        this.h2SensorsCollection = h2SensorsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlocations != null ? idlocations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof H2_Locations)) {
            return false;
        }
        H2_Locations other = (H2_Locations) object;
        if ((this.idlocations == null && other.idlocations != null) || (this.idlocations != null && !this.idlocations.equals(other.idlocations))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "h2_sensorData.H2_Locations[ idlocations=" + idlocations + " ]";
    }
    
}
