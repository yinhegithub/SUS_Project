package com.yinhe.susproject.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.yinhe.susproject.model.Hardware;



public class HardwareRepository {
	@Inject
	private EntityManager em;
	
	public Hardware findById(Long id) {
		return em.find(Hardware.class, id);
	}
	public List<Hardware> getAllHardware() 
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Hardware> criteria = cb.createQuery(Hardware.class);
		Root<Hardware> hards = criteria.from(Hardware.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(hards).where().orderBy(cb.desc(hards.get("id")));
		try{
			return	em.createQuery(criteria).getResultList();			 		
		}catch(NoResultException e)
		{
			return null;
		}
		
	}
	
	public void addHardware(Hardware hardware)
	{
		em.persist(hardware);
	}
	
	public void deleteHardware(long id)
	{
		em.remove(findById(id));
	}
}
