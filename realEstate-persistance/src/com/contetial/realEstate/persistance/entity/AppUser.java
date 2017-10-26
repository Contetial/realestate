package com.contetial.realEstate.persistance.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the app_user database table.
 * 
 */

@Entity
@Table(name="app_user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
property="userId",scope=AppUser.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AppUser implements Serializable,IEntity,UserDetails {
	
	private static final long serialVersionUID = 5110868333638273324L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Long userId;

	@Column(name="address")
	private String address;

	@Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="user_role")
	private String userRole;
	
	@Column(name="contactNo")
	private Long contactNo;
	
	@Column(name="email")
	private String email;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="manager_id")
    private AppUser manager;
	
	@OneToMany(mappedBy="manager")
	@JsonIgnore
	private List<AppUser> subordinates = new ArrayList<AppUser>();

	public AppUser() {
	}
	
	public AppUser(String userName, String hashString) {
		this.userName = userName;
		this.password = hashString;
	}

	/**
	 * @return
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AppUser getManager() {
		return manager;
	}

	public void setManager(AppUser manager) {
		this.manager = manager;
	}

	public List<AppUser> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<AppUser> subordinates) {
		this.subordinates = subordinates;
	}

	/**
	 *  Below code is implemented for Spring security
	 */
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = this.getUserRole();

		if (role == null) {
			return Collections.emptyList();
		}

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();		
		authorities.add(new SimpleGrantedAuthority(role));	

		return authorities;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return getUserName();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	public boolean isManager(){
		return !getSubordinates().isEmpty();
	}
	
	public String getManagerName(){
		String managerName = "";
		if(null!=getManager() && null!=getManager().getUserName()){
			managerName = getManager().getUserName();
		}
		return managerName;
	}
	
	public void setManagerName(String managerName){
		this.manager = new AppUser();
		manager.setUserName(managerName);
	}
	
	@Override 
	public int hashCode(){
		final int prime =3;
		int hash = 35 * prime +((null!=this.userName)?this.userName.hashCode():0);
		hash = 35 * hash + ((null!=this.userId)?this.userId.intValue():0); 
        return hash;
    }

    @Override 
    public boolean equals(Object ob){
         boolean equals = ob == this;
         if (!equals && ob != null 
        		 && ob.getClass() == this.getClass()){
              final AppUser other = (AppUser)ob;
              equals = other.userId.equals(this.userId);
         }
         return equals;
    }
}