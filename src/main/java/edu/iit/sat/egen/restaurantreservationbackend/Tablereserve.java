/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.egen.restaurantreservationbackend;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thanusha
 */
@Entity
@Table(name = "tablereserve")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tablereserve.findAll", query = "SELECT t FROM Tablereserve t"),
    @NamedQuery(name = "Tablereserve.findByTableId", query = "SELECT t FROM Tablereserve t WHERE t.tableId = :tableId")})
public class Tablereserve implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tableId")
    private Integer tableId;
    @JoinColumn(name = "customerId", referencedColumnName = "customerId")
    @ManyToOne
    private Customer customerId;

    public Tablereserve() {
    }

    public Tablereserve(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tableId != null ? tableId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tablereserve)) {
            return false;
        }
        Tablereserve other = (Tablereserve) object;
        if ((this.tableId == null && other.tableId != null) || (this.tableId != null && !this.tableId.equals(other.tableId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.iit.sat.egen.restaurantreservationbackend.Tablereserve[ tableId=" + tableId + " ]";
    }
    
}
