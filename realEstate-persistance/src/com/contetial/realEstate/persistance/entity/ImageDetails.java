package com.contetial.realEstate.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="image_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ImageDetails implements java.io.Serializable,IEntity {

	private static final long serialVersionUID = -4389615961761439987L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="img_id")
	private Long imageid;
	
	@Column(name="img_name")
	private String imagename;
		
	
	@Column(name="img_path")
	private String imagepath;
	

	@Column(name="ithumb_address")
	private String ithumbadd;
	
	@ManyToOne//(fetch = FetchType.LAZY)	
	@JoinColumn(name="gallery_id", nullable = false)
	private Gallery gallery;
	
	public ImageDetails() {
		super();
	}

	public Long getImageid() {
		return imageid;
	}

	public void setImageid(Long imageid) {
		this.imageid = imageid;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getIthumbadd() {
		return ithumbadd;
	}

	public void setIthumbadd(String ithumbadd) {
		this.ithumbadd = ithumbadd;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
		this.gallery.getImages().add(this);
	}
}