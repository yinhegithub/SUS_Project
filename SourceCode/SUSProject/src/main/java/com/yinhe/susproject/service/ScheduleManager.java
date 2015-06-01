package com.yinhe.susproject.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.yinhe.susproject.data.HardwareRepository;
import com.yinhe.susproject.data.RulemacRepository;
import com.yinhe.susproject.data.RulerangeRepository;
import com.yinhe.susproject.data.RulesnRepository;
import com.yinhe.susproject.data.ScheduleRepository;
import com.yinhe.susproject.model.Hardware;
import com.yinhe.susproject.model.Rulemac;
import com.yinhe.susproject.model.Rulerange;
import com.yinhe.susproject.model.Rulesn;
import com.yinhe.susproject.model.Schedule;

@Stateless
@LocalBean
public class ScheduleManager {
	@Inject
	private Logger log;

	@Inject
	private ScheduleRepository scheduleRepository;

	@Inject
	private RulesnRepository rulesnRepository;
	@Inject
	private RulemacRepository rulemacRepository;
	@Inject
	private RulerangeRepository rulerangeRepository;

	@Inject
	private HardwareRepository hardwareRepository;
	
	public boolean addSchedule(Schedule schedule) {

		scheduleRepository.addSchedule(schedule);
		return true;
	}

	public void updateSchedule(Schedule schedule) {
		try {
			scheduleRepository.updateSchedule(schedule);
			log.info("updateSchedule OK ");
		} catch (Exception e) {
			log.info("Error updateSchedule");
		}
	}
	public void deleteSchedule(long id) {
		try {
			scheduleRepository.deleteSchedule(id);
			log.info("delete Schedule OK ");
		} catch (Exception e) {
			log.info("Error delete Schedule");
		}
	}
	public boolean isexistSchedule(int factoryId, String hardwareId) {
		try {
			List<Schedule> schs = scheduleRepository.findByfIdAndHid(factoryId,
					hardwareId);
			if (!schs.isEmpty()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public List<Schedule> getSchedules() {
		try {
			return scheduleRepository.getSchedules();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Schedule> getSchedulesBytrailStage(int factoryId,String hardwareId,String trailMode) {
		try {
			return scheduleRepository.getSchedulesBytrailStage(factoryId,hardwareId,trailMode);
		} catch (Exception e) {
			return null;
		}
	}

	public void deleteRulesn(long stbId) {
		rulesnRepository.deleteRulesn(stbId);

	}

	public List<Rulesn> getRuleSns() {
		return rulesnRepository.getRuleSns();
	}

	public void addRulesn(String stbId, long schId, String enable) {
		try {
			Schedule sch = scheduleRepository.findById(schId);
			Rulesn rulesn = new Rulesn(sch, stbId, enable);
			rulesnRepository.addRulesn(rulesn);
		} catch (Exception e) {
			log.info("Error addRulesn" + " stbid:" + stbId + " schId: " + schId);
		}
	}

	public void deleteRulemac(long id) {
		rulemacRepository.deleteRulemac(id);
	}

	public List<Rulemac> getAllRulemacs() {
		return rulemacRepository.getAllRulemacs();
	}

	public void addRulemac(long mac, long schId, String enable) {
		try {
			Schedule sch = scheduleRepository.findById(schId);
			Rulemac rulemac = new Rulemac(sch, mac, enable);
			rulemacRepository.addRulemac(rulemac);
		} catch (Exception e) {
			log.info("Error addRulemac" + " mac:" + mac + " schId: " + schId);
		}
	}
	public void deleteRulerange(long id) {
		rulerangeRepository.deleteRulerange(id);
	}
	public List<Rulerange> getAllRuleranges() {
		return rulerangeRepository.getAllRuleranges();
	}
	
	public void addRulerange(Rulerange rulerange, long schId) {
		
			Schedule sch = scheduleRepository.findById(schId);
			rulerange.setSchedule(sch);
			rulerangeRepository.addRulerange( rulerange);
		
	}
	public List<Hardware> getAllHardware()
	{
		return hardwareRepository.getAllHardware();
	}
}
