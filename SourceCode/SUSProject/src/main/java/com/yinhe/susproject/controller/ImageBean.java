package com.yinhe.susproject.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.yinhe.susproject.model.Packagefile;
import com.yinhe.susproject.model.Packages;
import com.yinhe.susproject.service.ImageManager;

/**
 * @author Ilya Shaikovsky
 */
//@Model
@Named
@RequestScoped
public class ImageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private Logger log;

	@Inject
	private ImageManager imageManager;
	

	
	public List<Packages> getAllPackages()
	{
		return imageManager.getAllImagePackages();
	}
	
	public List<Packagefile> getAllPackagefile()
	{
		return imageManager.getAllPackagefile();
	}
	
	public void deletePackagefile(long id)
	{
		imageManager.deletePackagefile(id);
	}
	public void deletePackage(long id)
	{
		log.info("deletePackage"+id);
		imageManager.deletePackages(id);
	}

}