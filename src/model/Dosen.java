/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author raka
 */
@Entity
@Table(name = "dosen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dosen.findAll", query = "SELECT d FROM Dosen d")
    , @NamedQuery(name = "Dosen.findByNidn", query = "SELECT d FROM Dosen d WHERE d.nidn = :nidn")
    , @NamedQuery(name = "Dosen.findByNama", query = "SELECT d FROM Dosen d WHERE d.nama = :nama")})
public class Dosen implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    @OneToMany(mappedBy = "dosenPembimbing")
    private Collection<Mahasiswa> mahasiswaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nidn")
    private Integer nidn;
    @Column(name = "nama")
    private String nama;

    public Dosen() {
    }

    public Dosen(Integer nidn) {
        this.nidn = nidn;
    }

    public Integer getNidn() {
        return nidn;
    }

    public void setNidn(Integer nidn) {
        Integer oldNidn = this.nidn;
        this.nidn = nidn;
        changeSupport.firePropertyChange("nidn", oldNidn, nidn);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        String oldNama = this.nama;
        this.nama = nama;
        changeSupport.firePropertyChange("nama", oldNama, nama);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nidn != null ? nidn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dosen)) {
            return false;
        }
        Dosen other = (Dosen) object;
        if ((this.nidn == null && other.nidn != null) || (this.nidn != null && !this.nidn.equals(other.nidn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nidn + " - " + nama;
    }

    @XmlTransient
    public Collection<Mahasiswa> getMahasiswaCollection() {
        return mahasiswaCollection;
    }

    public void setMahasiswaCollection(Collection<Mahasiswa> mahasiswaCollection) {
        this.mahasiswaCollection = mahasiswaCollection;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
