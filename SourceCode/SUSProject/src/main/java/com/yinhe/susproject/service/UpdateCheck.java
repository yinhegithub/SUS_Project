package com.yinhe.susproject.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.yinhe.susproject.data.PackagefileRepository;
import com.yinhe.susproject.data.PackagesRepository;
import com.yinhe.susproject.data.RulemacRepository;
import com.yinhe.susproject.data.RulerangeRepository;
import com.yinhe.susproject.data.RulesnRepository;
import com.yinhe.susproject.data.ScheduleRepository;
import com.yinhe.susproject.model.Packagefile;
import com.yinhe.susproject.model.Packages;
import com.yinhe.susproject.model.Results;
import com.yinhe.susproject.model.Schedule;

@Stateless
public class UpdateCheck {
	  @Inject
	    private Logger log;
	  @Inject
	  private ScheduleRepository schrepository;  
	  @Inject
	  private RulerangeRepository rulerangerepository;
	  @Inject
	  private RulesnRepository rsnrepository; 
	  @Inject
	  private RulemacRepository rmacrepository;
	  
	  @Inject
	  private PackagesRepository packpository;
	  @Inject 
	  private  PackagefileRepository pfpository;
	  
	  final private String ip="116.210.255.60";
	  
	  public Boolean checkRule(  long sn, long macs, int factoryId,  String hardware)
		{
					
			long schId =(long) -1;
			 
			List<Schedule> schds= schrepository.findByfIdAndHid(factoryId,hardware);
			if(schds == null)
			{
				return false;
			}
			 log.info("find Schedules size:"+schds.size());
			 
			for(Schedule sch:schds)
			{
				log.info("Schedules id:"+sch.getId());
				if(sch.getTrailMode().equals("Y"))
				{
					Date date = new Date();
					log.info("StartTime:"+sch.getStartTime().toString());
					log.info("endTime:"+sch.getEndTime().toString());
					if(date.after(sch.getStartTime()) && date.before(sch.getEndTime()))
					{
						schId = sch.getId();
					}
					break;
				}
			}
			if(schId == -1){
				for(Schedule sch:schds)
				{
					if(sch.getTrailMode().equals("N"))
					{	
						Date date = new Date();
						log.info("StartTime:"+sch.getStartTime().toString());
						log.info("endTime:"+sch.getEndTime().toString());
						if(date.after(sch.getStartTime())&&date.before(sch.getEndTime()))
						{
							schId = sch.getId();
						}
					break;
					}
				}
			}
			
			if(schId == -1){
				log.info("Find Schedule but time is Out");
				return false;
				}//not found Schedule
			 log.info("Find schId = "+schId);
			 //规则判断  range -> sn -> mac 
			if( null != rulerangerepository.findBySn(sn, schId, "N") 
			   ||null != rulerangerepository.findByMac(macs, schId, "N"))
			{//机顶盒在不包含区间内
				log.info("sn | mac not include rulerange");
				return false;
			}
			if( null != rulerangerepository.findBySn(sn, schId, "Y") 
				||null != rulerangerepository.findByMac(macs, schId, "Y"))
			{//机顶盒在包含区间内,可以升级
				log.info("sn | mac in include rulerange");
				return true;
			}
			
			if(null != rsnrepository.findBySn(sn, schId,"N"))
			{//某个机顶盒不允许被升级
				log.info("stbId"+sn+" not allow update");
				return false;
			}
			
			if(null != rsnrepository.findBySn(sn, schId,"Y"))
			{//某个机顶盒不允许被升级
				log.info("stbId:"+sn+" can update");
				return true;
			}
			if(null != rmacrepository.findByMac(macs, schId,"N"))
			{//某个机顶盒不允许被升级
				log.info("macs:"+macs+" not allow update");
				return false;
			}
			if(null != rmacrepository.findByMac(macs, schId,"Y"))
			{//某个机顶盒不允许被升级
				log.info("macs:"+macs+" can update");
				return true;
			}
			 log.info("Find schId = "+schId);
			return true;
		}
	  
	  
	  public Results getResult( int fId,  String hardware, String software)
		{				
			Long packId = (long) 0;
			Results hr = new Results();
					
			Packages pak = packpository.getCurrentVersion(fId, hardware);	
			packId = pak.getId();
			log.info("getResult packageid= "+packId);
			if((pak == null) || (software.equals(pak.getSoftwareId()) ))
			{return hr;}
			
			
			List<Packagefile> pfs = pfpository.findByPackageId(packId);
			log.info("getResult pfs size= "+pfs.size());
			int packagefilecount = pfs.size() ;
						
			if(packagefilecount == 2 )//包里有两个文件
			{
				
				if(software.equals(pak.getDeversion()))//使用增量包
				{	log.info("useing increment");
					for(Packagefile pf:pfs)
					{log.info("useing increment");
						if(pf.getFileType().equals("I"))
						{
							String update ="increment";
							hr.setUpdate(update);
							hr.setUrl("http://"+ip+":8080/SUSProject/ServletDownload?id="+pf.getId());
							break;
						}
					}
				}else
				{
					for(Packagefile pf:pfs)
					{
						if(pf.getFileType().equals("F"))
						{
							String update ="full";
							hr.setUpdate(update);
							
							hr.setUrl("http://"+ip+":8080/SUSProject/ServletDownload?id="+pf.getId());
							break;
						}
					}
		
				}
			}
				
			if((packagefilecount == 1))//包里只有一个文件
			{
				Packagefile pf = pfs.get(0);
				String update = null;
				System.out.println("getResult pf_Id= "+pf.getId());
				switch(pf.getFileType())
				{
				case "I": update ="increment";
					break;
				case "F":  update ="full";
					break;
				}
				hr.setUpdate(update);
				hr.setUrl("http://"+ip+":8080/SUSProject/ServletDownload?id="+pf.getId());
				return hr;
			}
			
				return hr;
		}
}
