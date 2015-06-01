package com.yinhe.susproject.rest;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.yinhe.susproject.model.Results;
import com.yinhe.susproject.service.UpdateCheck;

@Path("/queryUpdateInfo")
public class UpdateRESTService {
	
	 @Inject 
	 private UpdateCheck updateCheck;
		@Inject
		private Logger log;
	 @GET
		@Produces(MediaType.APPLICATION_JSON)
	    public Results lookupMemberById(@QueryParam("stbId") String stbId,@QueryParam("mac") String mac,
	    		@QueryParam("factoryId") String factoryId,@QueryParam("hardware") String hardware,@QueryParam("software") String software) 
	 {	      
	     return getUpdateUrl(stbId, mac, factoryId,hardware,software); 
	  }
	 
	
	 private Results getUpdateUrl( String stbId,String mac, String factoryId,  String hardware, String software)
		{
			 Results hr = new Results();
			 log.info("stbid="+stbId); 
			 log.info("mac="+mac); 
			 log.info("factoryId="+factoryId); 
			 log.info("hardware="+hardware); 
			 log.info("software="+software); 
			 
			 String subfac = factoryId.replaceAll("0x", "");
			 int fId  = Integer.parseInt(subfac);			 
			 long sn = Long.parseLong(stbId,16);
			 String submac = mac.replaceAll(":", "");
			 long macs = Long.parseLong(submac,16);
			 log.info("sn="+sn); 
			 log.info("macs="+macs); 
			 log.info("fId="+fId); 
			 if(updateCheck.checkRule(sn, macs, fId, hardware))
			 {
				 hr = updateCheck.getResult(fId, hardware, software);
			 }else
			 {
				 log.info("checkRule can not allow");
			 }

			 return hr;
		}

}
