package com.yinhe.susproject.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.yinhe.susproject.model.Rulerange;

public class RulerangeRepository {
	@Inject
	private EntityManager em;

	public Rulerange findById(Integer id) {
		return em.find(Rulerange.class, id);
	}

	public Rulerange findBySn(long sn, long schId,String Include) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Rulerange> criteria = cb.createQuery(Rulerange.class);
		Root<Rulerange> rulerange = criteria.from(Rulerange.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0

		criteria.select(rulerange).where(
				cb.equal(rulerange.get("schedule").get("id"), schId),
				cb.equal(rulerange.get("snInclude"), Include)
				);
		
		List<Rulerange> rlueranges = null;
		Rulerange rluerange = null;
		
		try {
			rlueranges = em.createQuery(criteria).getResultList();
			for(Rulerange rs:rlueranges)
			{
				if(sn >= rs.getStartSn() && sn <= rs.getEndSn())
				{
					rluerange = rs;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("not found Rulerange ");
			return rluerange;
		}
		return rluerange;
	}

	public Rulerange findByMac(long mac, long schId ,String Include) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Rulerange> criteria = cb.createQuery(Rulerange.class);
		Root<Rulerange> rulerange = criteria.from(Rulerange.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0

		criteria.select(rulerange).where(
				cb.equal(rulerange.get("schedule").get("id"), schId),
				cb.equal(rulerange.get("snInclude"), Include)
				);
		
		List<Rulerange> rlueranges = null;
		Rulerange rluerange = null;
		try {
			rlueranges = em.createQuery(criteria).getResultList();
			for(Rulerange rs:rlueranges)
			{
				if(mac >= rs.getStartMac() && mac <= rs.getEndMac())
				{
					rluerange = rs;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("not found Rulerange ");
			return rluerange;
		}
		return rluerange;
	}
	public List<Rulerange> getAllRuleranges()
	 {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery< Rulerange> criteria = cb.createQuery( Rulerange.class);
	        Root< Rulerange> rulerange = criteria.from( Rulerange.class);
	        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	        // feature in JPA 2.0
	        criteria.select(rulerange);
	        try{
	        	 return em.createQuery(criteria).getResultList();
			}catch(NoResultException e)
			{
					return null;
			}
	 }
	public void addRulerange(Rulerange rulerange)
	{
		em.persist(rulerange);
	}
	
	public void updateRulerange(Rulerange rulerange)
	{
		em.merge(rulerange);
	}
	public void deleteRulerange(long id)
	{
		em.remove(id);
	}
	
	
}
