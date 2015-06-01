package com.yinhe.susproject.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.yinhe.susproject.model.Hardware;
import com.yinhe.susproject.service.HardwareManager;

//@Model
@Named
@SessionScoped
public class MenuBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -466628826748361743L;

	@Inject
	private Logger log;
	
	@Inject
	private HardwareManager hardwareManager;

	@Produces
	@Named
	private Hardware newHardware;
	
	@Produces	
	@Named
	private Hardware addHardware;
	
	@Produces
	@Named
	private String FactoryId;
	
	public Hardware getNewHardware() {
		return newHardware;
	}

	public void setNewHardware(Hardware newHardware) {
		this.newHardware = newHardware;
	}

	@PostConstruct
	public void initNewMenuBean() 
	{	
		FactoryId=new String();
		newHardware = new Hardware();
		addHardware = new Hardware();
	}
	
	public List<Hardware>getAllHareware()
	{
		log.info("get all hardware！");
		return hardwareManager.getAllHardware();
	}
	public void addHardware()
	{		
		log.info("add hardware factoryId:"+addHardware.getFactoryId());
		log.info("add hardware factoryName:"+addHardware.getFactoryName());
		log.info("add hardware hardwreId:"+addHardware.getHardwareId());	
		//String subfactory = FactoryId.replace("0x", "");		
		//addHardware.setFactoryId(Integer.parseInt(subfactory));
		hardwareManager.addHardware(addHardware);
	}
	public void deleteHardware(long id)
	{		
		log.info("add hardware Id:"+id);	
		hardwareManager.deleteHardware(id);
	}
	public String setHardware(Hardware hardware)
	{
		this.newHardware=hardware;
		return goMenu();
	}

	public String goMenu()
	{
		return "/pages/manage/menu.jsf";
	}
	public String goManageSchedule()
	{
		return "/pages/manage/manage_schedule.jsf";
	}
	public String goManageRule()
	{
		return "/pages/manage/manage_rule.jsf";
	}
	public String goEditRulesn()
	{
		return "/pages/rule/editrulesn.jsf";
	}
	public String goEditRulemac()
	{
		return "/pages/rule/editrulemac.jsf";
	}
	public String goEditRulerange()
	{
		return "/pages/rule/editrulerange.jsf";
	}
	public String goAddhardware()
	{
		return "/pages/hardware/edithardware.jsf";
	}
	public String goSetPages()
	{
		return "/pages/manage/sethardware.jsf";
	}
	public String goManagePages()
	{
		return "/pages/manage/menu.jsf";
	}
	public String goUploadsuccess()
	{
		return "/pages/image/uploadsucces.jsf";
	}
	public String goUpload()
	{
		return "/pages/image/upload.jsf";
	}
	public String goAdminmanage()
	{
		return "/pages/admin_console/admin_console.jsf";
	}
	public String goDeleteSchedule()
	{
		return "/pages/schedule/deleteschedule.jsf";
	}

	public String goManagepackage()
	{
		return "/pages/image/managepackage.jsf";
	}
	public String goOperatorConsole()
	{
		return "/pages/operator_console/operator_console.jsf";
	}
	public String goManageFactory()
	{
		return "/pages/manage/manage_factory.jsf";
	}

	public String getFactoryId() {
		return FactoryId;
	}

	public void setFactoryId(String factoryId) {
		FactoryId = factoryId;
	}
}
