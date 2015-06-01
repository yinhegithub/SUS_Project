package com.yinhe.susproject.data;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.yinhe.susproject.model.Schedule;


public class ScheduleRepository {
	  @Inject
	    private Logger log;
	 @Inject
	    private EntityManager em;
	    
	 public Schedule findById(Long id) {
	        return em.find(Schedule.class, id);
	    }
	 
	  public List<Schedule> findByfIdAndHid(int factoryId,String hardwareId) {
	       CriteriaBuilder cb = em.getCriteriaBuilder();
	       CriteriaQuery<Schedule> criteria = cb.createQuery(Schedule.class);
	       Root<Schedule> schd = criteria.from(Schedule.class);
	      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	      // feature in JPA 2.0
	      // criteria.select(member).orderBy(cb.asc(member.get(Member_.name))); 
	       log.info("findByfIdAndHid factoryId"+factoryId);
	       log.info("findByfIdAndHid hardwareId:"+hardwareId);
	       criteria.select(schd).where(
	    		  cb.equal(schd.get("hardwareId"), hardwareId),
	 			 cb.equal(schd.get("factoryId"), factoryId)
	 			);
	       try{
		 		return em.createQuery(criteria).getResultList();
		 	}catch(NoResultException e)
			{	log.info("get schedule is null");
				return null;
			}
	       
	  }
	  public void addSchedule(Schedule schedule)
	  {
		  em.persist(schedule);
	  }
	  
	  public void updateSchedule(Schedule schedule)
	  {
		  em.merge(schedule);
	  }
	  public void deleteSchedule(long id)
	  {
		  em.remove(findById(id));;
	  }
	  public List<Schedule> getSchedules()
	  {
		  CriteriaBuilder cb = em.getCriteriaBuilder();
	       CriteriaQuery<Schedule> criteria = cb.createQuery(Schedule.class);
	       Root<Schedule> schd = criteria.from(Schedule.class);
	      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	      // feature in JPA 2.0
	      // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));  	   	 
	 	 
	       criteria.select(schd).orderBy(cb.desc(schd.get("id")));
	       try{
		 		return em.createQuery(criteria).getResultList();
		 	}catch(NoResultException e)
			{
				return null;
			}
	      
	  }
	  public List<Schedule> getSchedulesBytrailStage(int factoryId,String hardwareId,String trailMode)
		{
		  CriteriaBuilder cb = em.getCriteriaBuilder();
	       CriteriaQuery<Schedule> criteria = cb.createQuery(Schedule.class);
	       Root<Schedule> schd = criteria.from(Schedule.class);
	      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	      // feature in JPA 2.0
	      // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));  	   	 
	 	 
	       criteria.select(schd).where(cb.equal(schd.get("hardwareId"), hardwareId),
	    		   cb.equal(schd.get("factoryId"), factoryId),
	    		   cb.equal(schd.get("trailMode"), trailMode)).orderBy(cb.desc(schd.get("id")));
	       try{
		 		return em.createQuery(criteria).getResultList();
		 	}catch(NoResultException e)
			{
				return null;
			}
	      
		}
}
