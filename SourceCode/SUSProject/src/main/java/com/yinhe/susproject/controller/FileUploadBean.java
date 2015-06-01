package com.yinhe.susproject.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import com.yinhe.susproject.model.Packages;
import com.yinhe.susproject.service.ImageManager;

/**
 * @author Ilya Shaikovsky
 */
//@Model
@Named
@RequestScoped
public class FileUploadBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private ImageManager imageManager;
	
	@Inject 
	private MenuBean menubean;
	
	@Produces
	@Named
	private Packages newPackage;
	
	final String tempfilepath = "E:\\temp\\update.zip";
	final String wintempFullPath = "E:\\temp\\updatefull.zip";
	final String wintempIncrementPath ="E:\\temp\\updateincrement.zip";
	
	final String linuxtempFullPath = "/home/temp/updatefull.zip";
	final String linuxtempIncrementPath ="/home/temp/updateincrement.zip";
	
	final String tempFullPath = linuxtempFullPath;
	final String tempIncrementPath =linuxtempIncrementPath;
	
	private String buttonEnable;
	

	private String CurrentVersion = null;
	@PostConstruct
	public void initnewUser() {	
		log.info("hardware id"+menubean.getNewHardware().getFactoryId());
		log.info("hardware name"+menubean.getNewHardware().getHardwareId());
		newPackage = new Packages();
		newPackage.setActive("Y");
		newPackage.setFactoryId(menubean.getNewHardware().getFactoryId());
		newPackage.setHardwareId(menubean.getNewHardware().getHardwareId());
		//newPackage.setSoftwareId("2.1.6");
		newPackage.setTrailStage("N");
		setCurrentVersion(imageManager.getCurrentVersion(menubean.getNewHardware().getFactoryId(),menubean.getNewHardware().getHardwareId()));
		newPackage.setDeversion(getCurrentVersion());
		setButtonEnable("false");
		
	}

	public void paint(OutputStream stream, Object object) throws IOException {
		log.info("call paint");
		// stream.write(getFiles().get((Integer) object).getData());
		stream.close();
	}

	public void listenerincrement(FileUploadEvent event) throws Exception {
		log.info("call listener");
		UploadedFile file = event.getUploadedFile();
		log.info("get filename:" + file.getName());
		log.info("get filesize:" + file.getSize());
		log.info("set tempincrementpath" + tempIncrementPath);
		// String tempfilepath = "E:\\temp\\update.zip";
		try {
			file.write(tempIncrementPath);
			log.info("file wirte incrementtemp OK");
		} catch (Exception e) {
			log.info("file wirte incrementtemp Error");
		}
		
	}
	
	public void listenerfull(FileUploadEvent event) throws Exception {
		log.info("call listener");
		UploadedFile file = event.getUploadedFile();
		log.info("get filename:" + file.getName());
		log.info("get filesize:" + file.getSize());
		log.info("set temppath" + tempFullPath);
		// String tempfilepath = "E:\\temp\\update.zip";
		try {
			file.write(tempFullPath);
			log.info("file wirte Fulltemp OK");
		} catch (Exception e) {
			log.info("file wirte Fulltemp Error");
		}
	
	}

	public String submit() throws Exception {

		try {
			log.info("get FactoryId:" + newPackage.getFactoryId());
			log.info("get getHardwareId:" + newPackage.getHardwareId());
			log.info("get getSoftwareId:" + newPackage.getSoftwareId());
			log.info("get Discription:" + newPackage.getDiscription());
			if(!imageManager.addImage(newPackage, tempFullPath,tempIncrementPath,tempfilepath))
			{
				
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"ERROR: 必须上传镜像!", "uploadFile fail");
				facesContext.addMessage(null, m);
				return "null";
			}
			
			return menubean.goUploadsuccess();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "uploadFile unsuccessful");
			facesContext.addMessage(null, m);
			return null;
		}
	}

	public String clearUploadData() {
		log.info("call clearUploadData");

		return null;
	}

	public int getSize() {

		return 0;

	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public Packages getNewPackage() {
		return newPackage;
	}

	public void setNewPackage(Packages newPackage) {
		this.newPackage = newPackage;
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "upload failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

	public String getButtonEnable() {
		return buttonEnable;
	}

	public void setButtonEnable(String buttonEnable) {
		this.buttonEnable = buttonEnable;
	}

	public String getCurrentVersion() {
		return CurrentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		CurrentVersion = currentVersion;
	}

}