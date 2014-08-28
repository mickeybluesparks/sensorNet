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
@Table(name = "SENSORTYPES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "H2_Sensortypes.findAll", query = "SELECT h FROM H2_Sensortypes h"),
    @NamedQuery(name = "H2_Sensortypes.findByIdsensortypes", query = "SELECT h FROM H2_Sensortypes h WHERE h.idsensortypes = :idsensortypes"),
    @NamedQuery(name = "H2_Sensortypes.findByType", query = "SELECT h FROM H2_Sensortypes h WHERE h.type = :type"),
    @NamedQuery(name = "H2_Sensortypes.findByNetworkprefix", query = "SELECT h FROM H2_Sensortypes h WHERE h.networkprefix = :networkprefix")})
public class H2_Sensortypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDSENSORTYPES")
    private Integer idsensortypes;
    @Basic(optional = false)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @Column(name = "NETWORKPREFIX")
    private String networkprefix;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensortypesIdsensortypes")
    private Collection<H2_Sensors> h2SensorsCollection;

    public H2_Sensortypes() {
    }

    public H2_Sensortypes(Integer idsensortypes) {
        this.idsensortypes = idsensortypes;
    }

    public H2_Sensortypes(Integer idsensortypes, String type, String networkprefix) {
        this.idsensortypes = idsensortypes;
        this.type = type;
        this.networkprefix = networkprefix;
    }

    public Integer getIdsensortypes() {
        return idsensortypes;
    }

    public void setIdsensortypes(Integer idsensortypes) {
        this.idsensortypes = idsensortypes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNetworkprefix() {
        return networkprefix;
    }

    public void setNetworkprefix(String networkprefix) {
        this.networkprefix = networkprefix;
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
        hash += (idsensortypes != null ? idsensortypes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof H2_Sensortypes)) {
            return false;
        }
        H2_Sensortypes other = (H2_Sensortypes) object;
        if ((this.idsensortypes == null && other.idsensortypes != null) || (this.idsensortypes != null && !this.idsensortypes.equals(other.idsensortypes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "h2_sensorData.H2_Sensortypes[ idsensortypes=" + idsensortypes + " ]";
    }
    
}
