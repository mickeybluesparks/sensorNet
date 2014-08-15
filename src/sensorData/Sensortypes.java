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
@Table(name = "sensortypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sensortypes.findAll", query = "SELECT s FROM Sensortypes s"),
    @NamedQuery(name = "Sensortypes.findByIdsensorTypes", query = "SELECT s FROM Sensortypes s WHERE s.idsensorTypes = :idsensorTypes"),
    @NamedQuery(name = "Sensortypes.findByType", query = "SELECT s FROM Sensortypes s WHERE s.type = :type")})
public class Sensortypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idsensorTypes")
    private Integer idsensorTypes;
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensorTypesidsensorTypes")
    private Collection<Sensors> sensorsCollection;

    public Sensortypes() {
    }

    public Sensortypes(Integer idsensorTypes) {
        this.idsensorTypes = idsensorTypes;
    }

    public Integer getIdsensorTypes() {
        return idsensorTypes;
    }

    public void setIdsensorTypes(Integer idsensorTypes) {
        this.idsensorTypes = idsensorTypes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        hash += (idsensorTypes != null ? idsensorTypes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sensortypes)) {
            return false;
        }
        Sensortypes other = (Sensortypes) object;
        if ((this.idsensorTypes == null && other.idsensorTypes != null) || (this.idsensorTypes != null && !this.idsensorTypes.equals(other.idsensorTypes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sensorData.Sensortypes[ idsensorTypes=" + idsensorTypes + " ]";
    }
    
}
