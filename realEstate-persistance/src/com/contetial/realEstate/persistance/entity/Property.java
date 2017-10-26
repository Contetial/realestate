package com.contetial.realEstate.persistance.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="property")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
property="propertyId", scope=Property.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Property implements Serializable,IEntity, Comparable<Property>{

	private static final long serialVersionUID = 3176565975109616649L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="property_id")
	private Long propertyId;
	
	@Column(name="property_name")
	private String propName;
	
	@Column(name="property_address")
	private String propAddress;
	
	@Column(name="property_detailes")
	private String propDetails;
	
	@Column(name="status")
	private String propStatus;
	
	@OneToMany(mappedBy = "property",cascade = {CascadeType.ALL})	
    private Set<Deal> onGoingDeals;
	
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropAddress() {
		return propAddress;
	}
	public void setPropAddress(String propAddress) {
		this.propAddress = propAddress;
	}
	public String getPropDetails() {
		return propDetails;
	}
	public void setPropDetails(String propDetails) {
		this.propDetails = propDetails;
	}
	public String getPropStatus() {
		return propStatus;
	}
	public void setPropStatus(String propStatus) {
		this.propStatus = propStatus;
	}
	public Set<Deal> getOnGoingDeals() {
		return onGoingDeals;
	}
	public void setOnGoingDeals(Set<Deal> onGoingDeals) {
		this.onGoingDeals = onGoingDeals;
	}
	
	public int compareTo(Property p) { 
		return this.propertyId.compareTo(p.propertyId); 
	}
	
	@Override 
	public int hashCode(){
		final int prime =3;
		int hash = 35 * prime +((null!=this.propName)?this.propName.hashCode():0);
		hash = 35 * hash + ((null!=this.propertyId)?this.propertyId.intValue():0); 
        return hash;
    }

    @Override 
    public boolean equals(Object ob){
         boolean equals = ob == this;
         if (!equals && ob != null 
        		 && ob.getClass() == this.getClass()){
              final Property other = (Property)ob;
              equals = other.propertyId.equals(this.propertyId);
         }
         return equals;
    }
}
