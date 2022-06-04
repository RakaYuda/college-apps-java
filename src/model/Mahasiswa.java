/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author raka
 */
@Entity
@Table(name = "mahasiswa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mahasiswa.findAll", query = "SELECT m FROM Mahasiswa m")
    , @NamedQuery(name = "Mahasiswa.findByNim", query = "SELECT m FROM Mahasiswa m WHERE m.nim = :nim")
    , @NamedQuery(name = "Mahasiswa.findByNama", query = "SELECT m FROM Mahasiswa m WHERE m.nama = :nama")
    , @NamedQuery(name = "Mahasiswa.findByAngkatan", query = "SELECT m FROM Mahasiswa m WHERE m.angkatan = :angkatan")
    , @NamedQuery(name = "Mahasiswa.findBySekolahAsal", query = "SELECT m FROM Mahasiswa m WHERE m.sekolahAsal = :sekolahAsal")})
public class Mahasiswa implements Serializable {

    @JoinColumn(name = "dosen_pembimbing", referencedColumnName = "nidn")
    @ManyToOne
    private Dosen dosenPembimbing;
    @JoinColumn(name = "fakultas", referencedColumnName = "id")
    @ManyToOne
    private Fakultas fakultas;

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nim")
    private Long nim;
    @Column(name = "nama")
    private String nama;
    @Column(name = "angkatan")
    private Integer angkatan;
    @Column(name = "sekolah_asal")
    private String sekolahAsal;

    public Mahasiswa() {
    }

    public Mahasiswa(Long nim, String nama, Integer angkatan, String sekolahAsal) {
        this.nim = nim;
        this.nama = nama;
        this.angkatan = angkatan;
        this.sekolahAsal = sekolahAsal;
    }

    public Long getNim() {
        return nim;
    }

    public void setNim(Long nim) {
        Long oldNim = this.nim;
        this.nim = nim;
        changeSupport.firePropertyChange("nim", oldNim, nim);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        String oldNama = this.nama;
        this.nama = nama;
        changeSupport.firePropertyChange("nama", oldNama, nama);
    }

    public Integer getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(Integer angkatan) {
        Integer oldAngkatan = this.angkatan;
        this.angkatan = angkatan;
        changeSupport.firePropertyChange("angkatan", oldAngkatan, angkatan);
    }

    public String getSekolahAsal() {
        return sekolahAsal;
    }

    public void setSekolahAsal(String sekolahAsal) {
        String oldSekolahAsal = this.sekolahAsal;
        this.sekolahAsal = sekolahAsal;
        changeSupport.firePropertyChange("sekolahAsal", oldSekolahAsal, sekolahAsal);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nim != null ? nim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mahasiswa)) {
            return false;
        }
        Mahasiswa other = (Mahasiswa) object;
        if ((this.nim == null && other.nim != null) || (this.nim != null && !this.nim.equals(other.nim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nim.toString() + "," + nama + "," + angkatan + "," + sekolahAsal;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public Dosen getDosenPembimbing() {
        return dosenPembimbing;
    }

    public void setDosenPembimbing(Dosen dosenPembimbing) {
        this.dosenPembimbing = dosenPembimbing;
    }

    public Fakultas getFakultas() {
        return fakultas;
    }

    public void setFakultas(Fakultas fakultas) {
        this.fakultas = fakultas;
    }
    
}
