/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tmaf.knowledge.db.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nmsa
 */
@Entity
@Table(name = "Probe")
@NamedQueries({
    @NamedQuery(name = "Probe.findAll", query = "SELECT p FROM Probe p"),
    @NamedQuery(name = "Probe.findByProbeId", query = "SELECT p FROM Probe p WHERE p.probeId = :probeId"),
    @NamedQuery(name = "Probe.findByProbeName", query = "SELECT p FROM Probe p WHERE p.probeName = :probeName"),
    @NamedQuery(name = "Probe.findByPassword", query = "SELECT p FROM Probe p WHERE p.password = :password"),
    @NamedQuery(name = "Probe.findBySalt", query = "SELECT p FROM Probe p WHERE p.salt = :salt"),
    @NamedQuery(name = "Probe.findByToken", query = "SELECT p FROM Probe p WHERE p.token = :token"),
    @NamedQuery(name = "Probe.findByTokenExpiration", query = "SELECT p FROM Probe p WHERE p.tokenExpiration = :tokenExpiration")})
public class Probe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "probeId")
    private Integer probeId;
    @Column(name = "probeName")
    private String probeName;
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "salt")
    private String salt;
    @Column(name = "token")
    private String token;
    @Column(name = "tokenExpiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpiration;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "probe")
    private Collection<Data> dataCollection;

    public Probe() {
    }

    public Probe(Integer probeId) {
        this.probeId = probeId;
    }

    public Probe(Integer probeId, String salt) {
        this.probeId = probeId;
        this.salt = salt;
    }

    public Integer getProbeId() {
        return probeId;
    }

    public void setProbeId(Integer probeId) {
        this.probeId = probeId;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Date tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public Collection<Data> getDataCollection() {
        return dataCollection;
    }

    public void setDataCollection(Collection<Data> dataCollection) {
        this.dataCollection = dataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (probeId != null ? probeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Probe)) {
            return false;
        }
        Probe other = (Probe) object;
        if ((this.probeId == null && other.probeId != null) || (this.probeId != null && !this.probeId.equals(other.probeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Probe[ probeId=" + probeId + " ]";
    }
    
}
