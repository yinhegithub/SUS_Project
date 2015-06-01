package com.yinhe.susproject.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.yinhe.susproject.model.Rulemac;
import com.yinhe.susproject.model.Rulerange;
import com.yinhe.susproject.model.Rulesn;
import com.yinhe.susproject.model.Schedule;
import com.yinhe.susproject.service.ScheduleManager;

@Named
@RequestScoped
public class ScheduleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8418665482204195464L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private ScheduleManager scheduleManager;
	@Inject 
	private MenuBean menubean;
	@Produces
	@Named
	private Schedule newSchedule;
	
	@Produces
	@Named
	private Rulesn newRulesn;
	
	@Produces
	@Named
	private Rulemac newRulemac;
	
	@Produces
	@Named
	private Rulerange newRulerange;
	

	private String scheduleExist;

	@PostConstruct
	public void initnewUser() {
		log.info("hardware id"+menubean.getNewHardware().getFactoryId());
		log.info("hardware name"+menubean.getNewHardware().getHardwareId());	
		newSchedule = new Schedule();
		if(menubean.getNewHardware().getFactoryId() != 0 && menubean.getNewHardware().getHardwareId()!= null){
			newRulemac = new Rulemac();
			newRulerange = new Rulerange();
			newRulesn = new Rulesn();
			
		if(!scheduleManager.isexistSchedule(menubean.getNewHardware().getFactoryId(),menubean.getNewHardware().getHardwareId()))
		{
		initNewSchedule();
		setScheduleExist("false");
		}else
		{
			setScheduleExist("true");
		}
		}
	}

	public String addSchedule() throws Exception {

		try {
			log.info("newSchedule get FactoryId:" + newSchedule.getFactoryId());
			log.info("newSchedule get getHardwareId:"
					+ newSchedule.getHardwareId());

			scheduleManager.addSchedule(newSchedule);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"设置默认规则成功，规则允许所有机顶盒升级到最新版本 !", "addSchedule successful");
			facesContext.addMessage(null, m);
			return "uploadsucces";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "addSchedule unsuccessful");
			facesContext.addMessage(null, m);
			return null;
		}
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
	public void initNewSchedule()
	{
	
		Schedule scheduletest = new Schedule();
		scheduletest.setFactoryId(menubean.getNewHardware().getFactoryId());
		scheduletest.setHardwareId(menubean.getNewHardware().getHardwareId());
		scheduletest.setTrailMode("Y");
		scheduletest.setDescription("Test Version");
		Date startTime = new Date();
		scheduletest.setStartTime(startTime);
		Date endTime = new Date(startTime.getYear()+20, startTime.getMonth(), startTime.getDay(), startTime.getHours(), startTime.getMinutes());
		scheduletest.setEndTime(endTime);
		scheduletest.setLevels((short) 5);	
		scheduleManager.addSchedule(scheduletest);
		
		Schedule schedulestable = new Schedule();
		schedulestable.setFactoryId(menubean.getNewHardware().getFactoryId());
		schedulestable.setHardwareId(menubean.getNewHardware().getHardwareId());
		schedulestable.setTrailMode("N");
		schedulestable.setDescription("Stable Version");	
		schedulestable.setStartTime(startTime);
		schedulestable.setEndTime(endTime);
		schedulestable.setLevels((short) 5);	
		scheduleManager.addSchedule(schedulestable);
				
	}


	public String getScheduleExist() {
		return scheduleExist;
	}

	public void setScheduleExist(String scheduleExist) {
		this.scheduleExist = scheduleExist;
	}
	
	public void updateSchedule(Schedule schedule) throws Exception {

		try {
			log.info("schedule get FactoryId:" + schedule.getFactoryId());
			log.info("schedule get getHardwareId:"
					+ schedule.getHardwareId());
			
			scheduleManager.updateSchedule(schedule);
			
		} catch (Exception e) {
		
		}
	}
	public void deleteSchedule(long id){

		try {
			log.info("schedule delete id:" + id);
			scheduleManager.deleteSchedule(id);			
		} catch (Exception e) {
		
		}
	}
	public List<Schedule> getSchedules()
	{
		try{
			return scheduleManager.getSchedules();
		}catch (Exception e)
		{
			return null;
		}
	}
	
	public List<Schedule> getSchedulesBytrailStage(int factoryId,String hardwareId,String trailMode)
	{
		try{
			return scheduleManager.getSchedulesBytrailStage(factoryId,hardwareId,trailMode);
		}catch (Exception e)
		{
			return null;
		}
	}
	
	public List<Rulesn> getRuleSns()
	{
		try{
			return scheduleManager.getRuleSns();
		}catch (Exception e)
		{
			return null;
		}
	}
	public void deleteRulesn(long stbId)
	{
		try{
			log.info("Delete stbid" + stbId);
			scheduleManager.deleteRulesn(stbId);
		}catch (Exception e)
		{
			log.info("Error delete stbid" + stbId);
		}
	}
	public void addRulesn()
	{
		
		try{
			log.info("add stbid:" +newRulesn.getSn()+" schId:"+newSchedule.getId());
			scheduleManager.addRulesn(newRulesn.getSn(),newSchedule.getId(),newRulesn.getEnable());
		}catch (Exception e)
		{
			log.info("Error add stbid" + newRulesn.getSn());
		}		
	}
	public List<Rulemac> getAllRuleMacs()
	{
		return scheduleManager.getAllRulemacs();
	}
	public void deleteRulemac(long mac)
	{
		try{
			log.info("Delete mac" + mac);
			scheduleManager.deleteRulemac(mac);
		}catch (Exception e)
		{
			log.info("Error delete mac" + mac);
		}
	}
	public void addRulemac()
	{
		
		try{
			log.info("add mac:" +newRulesn.getSn()+" schId:"+newSchedule.getId());
			scheduleManager.addRulemac(newRulemac.getMac(),newSchedule.getId(),newRulemac.getEnable());
		}catch (Exception e)
		{
			log.info("Error add rulemac");
		}		
	}
	public List<Rulerange> getAllRuleranges()
	{
		return scheduleManager.getAllRuleranges();
	}
	public void deleteRulerange(long id)
	{
		try{
			log.info("Delete Rulerange id" + id);
			scheduleManager.deleteRulerange(id);
		}catch (Exception e)
		{
			log.info("Error delete  Rulerange id" + id);
		}
	}
	public void addRulerange()
	{
		
		try{
			log.info("add mac:" +newRulesn.getSn()+" schId:"+newSchedule.getId());
			scheduleManager.addRulerange(newRulerange,newSchedule.getId());
		}catch (Exception e)
		{
			log.info("Error add rulemac");
		}		
	}
}
