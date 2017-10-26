package com.contetial.realEstate.utility.fileIO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileOpps {

	public static void saveFileOnDisk(byte[] fileData, String path) throws IOException{
		OutputStream os = new FileOutputStream(path);
	    os.write(fileData); // writes the bytes
	    os.flush();
	    os.close();
	}
	
	public static byte[] readFileFromDisk(String path) throws IOException{
		InputStream is = new FileInputStream(path);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];

		for (int readNum; (readNum = is.read(buf)) != -1;) {
			bos.write(buf, 0, readNum); //no doubt here is 0			
		}

		byte[] fileData = bos.toByteArray();
		bos.close();
		is.close();
		return fileData;
	}
	
	public static String getGenericFilePath(){
		String path="d:\temp";
		
		return path;
	}
	
	public static void checkOrCreateDirectory(String path){
		//path = getGenericFilePath()+path;
		File directory  = new File(path);
		if(!directory.isDirectory()){
			directory.mkdirs();
		}
	}
	
	public static void deleteImage(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}
	
	public static void deleteVideo(String path){
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
	}
}
