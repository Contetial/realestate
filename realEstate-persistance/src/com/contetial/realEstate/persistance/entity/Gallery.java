package com.contetial.realEstate.persistance.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="gallery")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
property="galleryid",scope=Gallery.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Gallery implements java.io.Serializable,IEntity {

	private static final long serialVersionUID = -5814578523283972303L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="gallery_id")
	private Long galleryid;

	@OneToOne
	@JoinColumn(name = "property_id", nullable = false)
	@JsonIgnore
	private Property property;
	
	@OneToMany(mappedBy = "gallery")
	private List<ImageDetails> images = new ArrayList<ImageDetails>();
	
	@OneToMany(mappedBy = "gallery")
	private List<VideoDetails> videos = new ArrayList<VideoDetails>();

	public Long getGalleryid() {
		return galleryid;
	}

	public void setGalleryid(Long galleryid) {
		this.galleryid = galleryid;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public List<ImageDetails> getImages() {
		return images;
	}

	public void setImages(List<ImageDetails> images) {
		this.images = images;
	}

	public List<VideoDetails> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoDetails> videos) {
		this.videos = videos;
	}
	
	public Long getPropertyId(){
		return getProperty().getPropertyId();
	}
	
	public void setPropertyId(Long propertyId){
		this.property = new Property();
		property.setPropertyId(propertyId);
	}
	
	public String getPropertyName(){
		return getProperty().getPropName();
	}
}
