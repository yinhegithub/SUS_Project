package com.yinhe.susproject.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.yinhe.susproject.model.Rulemac;

public class RulemacRepository {
	@Inject
    private EntityManager em;

 public Rulemac findById(Integer id) {
        return em.find( Rulemac.class, id);
    }
 public  Rulemac findByMac(long mac, long schId, String enable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery< Rulemac> criteria = cb.createQuery( Rulemac.class);
        Root< Rulemac> rulemac = criteria.from( Rulemac.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        criteria.select(rulemac).where(
        		cb.equal(rulemac.get("mac"), mac),
        		cb.equal(rulemac.get("schedule").get("id"), schId),
        		cb.equal(rulemac.get("enable"), enable)
        		);
        try{
        	 return em.createQuery(criteria).getSingleResult();
			}catch(NoResultException e)
			{
				return null;
			}
       
    }
	 public List<Rulemac> getAllRulemacs()
	 {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery< Rulemac> criteria = cb.createQuery( Rulemac.class);
	        Root< Rulemac> rulemac = criteria.from( Rulemac.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        criteria.select(rulemac);
	        try{
	        	 return em.createQuery(criteria).getResultList();
			}catch(NoResultException e)
			{
					return null;
			}
	 }
	public void addRulemac(Rulemac rulemac)
	{
		em.persist(rulemac);
	}
	public void deleteRulemac(long id)
	{
		em.remove(em.find(Rulemac.class, id));			
	}
	public void updateRulemac(Rulemac rulemac)
	{
		em.merge(rulemac);
	}
}
