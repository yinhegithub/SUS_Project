package com.yinhe.susproject.data;



import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.yinhe.susproject.model.Rulesn;


public class RulesnRepository {
	 @Inject
	    private EntityManager em;
	
	 
	 public Rulesn findById(Long id) {
	        return em.find(Rulesn.class, id);
	    }
	 
	 public Rulesn findBySn(long sn,Long schId,String enable) {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Rulesn> criteria = cb.createQuery(Rulesn.class);
	        Root<Rulesn> rulesn = criteria.from(Rulesn.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
	       
	       criteria.select(rulesn).where(
	    		   cb.equal(rulesn.get("sn"), sn), 
	    		   cb.equal(rulesn.get("schedule").get("id"), schId),
	    		    cb.equal(rulesn.get("enable"), enable)
	    		   );
	      
	       try{
	    	   return em.createQuery(criteria).getSingleResult();
	       }catch(NoResultException e)
	       {
	    	   return null;
	       }
	    
	    }
		public void addRulesn(Rulesn rulesn)
		{
			em.persist(rulesn);
		}
		
		public void updateRulesn(Rulesn rulesn)
		{
			em.refresh(rulesn);
		}
		public void deleteRulesn(long StbId)
		{
			em.remove(em.find(Rulesn.class, StbId));			
		}
		public List<Rulesn> getRuleSns()
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Rulesn> criteria = cb.createQuery(Rulesn.class);
	        Root<Rulesn> rulesn = criteria.from(Rulesn.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
	       criteria.select(rulesn);
	       try{
	        return em.createQuery(criteria).getResultList();
	        
	       }catch(NoResultException e)
	       {
	    	  return  null;
	       }
	     		
		}
	
}
