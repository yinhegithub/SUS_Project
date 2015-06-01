package com.yinhe.susproject.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.yinhe.susproject.model.Packages;

public class PackagesRepository {
	@Inject
	private EntityManager em;

	public Packages findById(Long id) {
		return em.find(Packages.class, id);
	}

	public List<Packages> findByfIdAndHid(int factoryId, String hardwareId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Packages> criteria = cb.createQuery(Packages.class);
		Root<Packages> page = criteria.from(Packages.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(page)
				.where(cb.equal(page.get("factoryId"), factoryId),
						cb.equal(page.get("hardwareId"), hardwareId))
				.orderBy(cb.desc(page.get("softwareId")));
		;
		
		try{
			List<Packages> pages = em.createQuery(criteria).getResultList();
			return pages;		
		}catch(NoResultException e)
		{
			return null;
		}
	}

	public void addPackges(Packages packages) {
		em.persist(packages);
	}

	public List<Packages> getAllPackages() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Packages> criteria = cb.createQuery(Packages.class);
		Root<Packages> page = criteria.from(Packages.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(page).where().orderBy(cb.desc(page.get("id")));
		try{
			List<Packages> pages = em.createQuery(criteria).getResultList();
			return pages;		
		}catch(NoResultException e)
		{
			return null;
		}
		
	}

	public Packages getCurrentVersion(int factoryId, String hardwareId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Packages> criteria = cb.createQuery(Packages.class);
		Root<Packages> page = criteria.from(Packages.class);
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(page).where(
				cb.equal(page.get("factoryId"), factoryId),
				cb.equal(page.get("hardwareId"), hardwareId),
				cb.equal(page.get("active"), "Y")
				);
		
		try{			
			return 	em.createQuery(criteria).getSingleResult();			
		}catch(NoResultException e)
		{
			return null;
		}
	}
	public void updatePackages(Packages packages)
	{
		em.merge(packages);
	}
	public void deletePackages(long id)
	{
		em.remove(findById(id));
	}

}
