package com.contetial.realEstate.persistance.entity;


public class ImageProperties {
	
	private byte[] image;
	private byte[] imgThumb;
	private ImageDetails imageDetails;
	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public ImageDetails getImageDetails() {
		return imageDetails;
	}
	public void setImageDetails(ImageDetails imageDetails) {
		this.imageDetails = imageDetails;
	}
	public byte[] getImgThumb() {
		return imgThumb;
	}
	public void setImgThumb(byte[] imgThumb) {
		this.imgThumb = imgThumb;
	}	
}
