package com.yinhe.susproject.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.yinhe.susproject.data.PackagefileRepository;
import com.yinhe.susproject.data.PackagesRepository;
import com.yinhe.susproject.model.Packagefile;
import com.yinhe.susproject.model.Packages;


@Stateless
@LocalBean
public class ImageManager {
	@Inject
	private Logger log;

	@Inject
	private PackagesRepository packagesRepository;
	
	@Inject
	private PackagefileRepository packagefileRepository;
	
	private long filesize;
	public boolean addImage(Packages packages,String tempFullPath,String tempIncrementPath,String tempfilepath) {
		if (packages == null) {
			log.info("package is null ");
			return false;
		}
        
		try{
		//tempfilepath = "E:\\temp\\update.zip";
		log.info("tempFullPath: "+tempFullPath);
		log.info("tempIncrementPath: "+tempIncrementPath);
		String basepath = getImagePath("yinhe", packages.getHardwareId(),
				packages.getSoftwareId());
		//String winfilefullpath=basepath+"\\updatefull.zip";		
		//String winfileIncrementpath=basepath+"\\updateIncrement.zip";
		String linuxfilefullpath=basepath+"/updatefull.zip";		
		String linuxfileIncrementpath=basepath+"/updateIncrement.zip";
		
		String filefullpath=linuxfilefullpath;		
		String fileIncrementpath=linuxfileIncrementpath;
		
		log.info("tempFullPath: "+tempFullPath);
		log.info("tempIncrementPath: "+tempIncrementPath);
		
		File fullfile= new File(tempFullPath);
		File incrementfile= new File(tempIncrementPath);
		if(fullfile.exists() == false && incrementfile.exists() == false)
		{
			return false;
		}
		if(fullfile.exists()){
		long begin3 = System.currentTimeMillis();
		fileChannelCopy(filefullpath,tempFullPath);
		fullfile.delete();
		long end3 = System.currentTimeMillis();		
		log.info("FileWriter执行耗时:" + (end3 - begin3) + " 豪秒");
		}
		
		if(incrementfile.exists()){
		long begin3 = System.currentTimeMillis();
		fileChannelCopy(fileIncrementpath,tempIncrementPath);
		incrementfile.delete();
		long end3 = System.currentTimeMillis();		
		log.info("FileWriter执行耗时:" + (end3 - begin3) + " 豪秒");
		}
		
		log.info("package software:" + packages.getSoftwareId());
		log.info("package factoryId:" + packages.getFactoryId());
		log.info("package hardwareId:" + packages.getHardwareId());
		Packages packs = packagesRepository.getCurrentVersion(packages.getFactoryId(), packages.getHardwareId());
		if( packs != null){			
		packs.setActive("N");		
		packagesRepository.updatePackages(packs);
		}
		packagesRepository.addPackges(packages);
		
		fullfile= new File(filefullpath);
		if(fullfile.exists()){
		Packagefile packagefile = new Packagefile();
		packagefile.setFilesize(fullfile.length());
		packagefile.setLocation(filefullpath);
		packagefile.setFileType("F");
		Date time = new Date();
		packagefile.setTime(time);
		packagefile.setPackages(packages);
		packagefileRepository.addPackagefile(packagefile);
		}
		incrementfile= new File(fileIncrementpath);
		if(incrementfile.exists()){
		Packagefile packagefile = new Packagefile();
		packagefile.setFilesize(incrementfile.length());
		packagefile.setLocation(fileIncrementpath);
		packagefile.setFileType("I");
		Date time = new Date();
		packagefile.setTime(time);
		packagefile.setPackages(packages);
		packagefileRepository.addPackagefile(packagefile);
		}
		return true;
		}catch (Exception e)
		{e.printStackTrace();
			log.info("Error upload Imange");
			return false;
		}
	}
	public String getCurrentVersion(int factoryId,String hardwareId)
	{
		try{
		Packages packages = packagesRepository.getCurrentVersion(factoryId,hardwareId);
		return packages.getSoftwareId();
		}
		catch (Exception e)
		{ 	log.info("Error get CurrentVersion factoryId: "+factoryId+" hardwareId:"+hardwareId);
			return null;
		}
		
	}
	public List<Packages> getAllImagePackages() {
		
		return packagesRepository.getAllPackages();
	}
	public void deletePackages(long id)
	{
		packagesRepository.deletePackages(id);
	}
	public List<Packagefile> getAllPackagefile()
	{
		return packagefileRepository.getAllPakagefile();
	}
	public void deletePackagefile(long id)
	{
		Packagefile pf = packagefileRepository.findById(id);
		String location = pf.getLocation();
		packagefileRepository.deletePackagefile(id);
		File file = new File(location);
		log.info("delete local file:" + location);
		file.delete();	
	}
	public String getImagePath(String factoryName, String hardwareId,String Version) {
		
	//	String windir ="E:\\"+factoryName+"\\"+hardwareId+"\\"+Version;
	//	String winfilepath = windir+ "\\update.zip";
		String linuxdir ="/home/download/"+factoryName+"/"+hardwareId+"/"+Version;
		String linuxfilepath = linuxdir+ "/update.zip";

		String dir =linuxdir;
		String filepath =linuxfilepath;

		File filemkdirs = new File(filepath);
		File pf = filemkdirs.getParentFile();
		if(!pf.exists())
		{	log.info("create path");
			pf.mkdirs();
		}	
		return dir;
	}

	private void fileChannelCopy(String resultfile, String sourcefile) {
		FileInputStream fi = null;
		FileOutputStream fo = null;		
		FileChannel in = null;
		FileChannel out = null;
		
		try {
			fi = new FileInputStream(sourcefile);
			fo = new FileOutputStream (resultfile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			setFilesize(in.size());
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
}
