/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mike
 */
@Entity
@Table(name = "locations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locations.findAll", query = "SELECT l FROM Locations l"),
    @NamedQuery(name = "Locations.findByIdlocations", query = "SELECT l FROM Locations l WHERE l.idlocations = :idlocations"),
    @NamedQuery(name = "Locations.findByRoomName", query = "SELECT l FROM Locations l WHERE l.roomName = :roomName")})
public class Locations implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlocations")
    private Integer idlocations;
    @Basic(optional = false)
    @Column(name = "room_name")
    private String roomName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationsIdlocations")
    private Collection<Sensors> sensorsCollection;

    public Locations() {
    }

    public Locations(Integer idlocations) {
        this.idlocations = idlocations;
    }

    public Locations(Integer idlocations, String roomName) {
        this.idlocations = idlocations;
        this.roomName = roomName;
    }

    public Integer getIdlocations() {
        return idlocations;
    }

    public void setIdlocations(Integer idlocations) {
        Integer oldIdlocations = this.idlocations;
        this.idlocations = idlocations;
        changeSupport.firePropertyChange("idlocations", oldIdlocations, idlocations);
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        String oldRoomName = this.roomName;
        this.roomName = roomName;
        changeSupport.firePropertyChange("roomName", oldRoomName, roomName);
    }

    @XmlTransient
    public Collection<Sensors> getSensorsCollection() {
        return sensorsCollection;
    }

    public void setSensorsCollection(Collection<Sensors> sensorsCollection) {
        this.sensorsCollection = sensorsCollection;
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
        if (!(object instanceof Locations)) {
            return false;
        }
        Locations other = (Locations) object;
        if ((this.idlocations == null && other.idlocations != null) || (this.idlocations != null && !this.idlocations.equals(other.idlocations))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sensorData.Locations[ idlocations=" + idlocations + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
