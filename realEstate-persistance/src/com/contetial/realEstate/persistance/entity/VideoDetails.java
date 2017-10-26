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
@Table(name="video_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VideoDetails implements java.io.Serializable,IEntity {

	private static final long serialVersionUID = 1842562815134680009L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="video_id")
	private Long videoid;
	
	@Column(name="video_name")
	private String videoname;
		
	
	@Column(name="video_path")
	private String videopath;
	
	@Column(name="vthumb_address")
	private String vthumbadd;
	
	@ManyToOne
    @JoinColumn(name = "gallery_id", nullable = false)
	private Gallery gallery;
	
	public VideoDetails() {
		super();
	}

	public Long getVideoid() {
		return videoid;
	}

	public void setVideoid(Long videoid) {
		this.videoid = videoid;
	}

	public String getVideoname() {
		return videoname;
	}

	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}


	public String getVideopath() {
		return videopath;
	}

	public void setVideopath(String videopath) {
		this.videopath = videopath;
	}

	public String getVthumbadd() {
		return vthumbadd;
	}

	public void setVthumbadd(String vthumbadd) {
		this.vthumbadd = vthumbadd;
	}

	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
		this.gallery.getVideos().add(this);
	}
	
	public Long getPropertyId(){		
		return gallery.getPropertyId();
	}
	
	public void setPropertyId(Long propertyId){
		/*if(null==gallery){
			gallery = new Gallery();
		}*/
		gallery.setPropertyId(propertyId);
	}

}