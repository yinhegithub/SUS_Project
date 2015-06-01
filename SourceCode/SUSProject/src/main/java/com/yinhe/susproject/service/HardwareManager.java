package com.yinhe.susproject.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.yinhe.susproject.data.HardwareRepository;
import com.yinhe.susproject.model.Hardware;

@Stateless
@LocalBean
public class HardwareManager {
	@Inject
	private Logger log;

	
	@Inject
	private HardwareRepository hardwareRepository;
	
	
	public List<Hardware> getAllHardware()
	{
		return hardwareRepository.getAllHardware();
	}
	public void addHardware(Hardware hardware)
	{
		hardwareRepository.addHardware(hardware);
	}
	public void deleteHardware(long id)
	{
		hardwareRepository.deleteHardware(id);
	}
}
