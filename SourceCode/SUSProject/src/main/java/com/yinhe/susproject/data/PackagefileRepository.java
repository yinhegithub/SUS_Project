package com.yinhe.susproject.data;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.yinhe.susproject.model.Packagefile;

public class PackagefileRepository {
	 @Inject
	    private EntityManager em;
	    
	 public Packagefile findById(Long id) {
		 	try{
		 		return em.find(Packagefile.class, id);
		 	}catch(NoResultException e)
			{
				return null;
			}
	    }
		 
		
	 public List<Packagefile> findByPackageId(Long PackageId) {
	       CriteriaBuilder cb = em.getCriteriaBuilder();
	       CriteriaQuery<Packagefile> criteria = cb.createQuery(Packagefile.class);
	       Root<Packagefile> pagefile = criteria.from(Packagefile.class);
	      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	      // feature in JPA 2.0 	   	 
		   criteria.select(pagefile).where(cb.equal(pagefile.get("packages").get("id"), PackageId));
		   try{
			   List<Packagefile>  pagefiles =  em.createQuery(criteria).getResultList();
			   return pagefiles;	
			}catch(NoResultException e)
			{
				return null;
			}
		   
		  }  
	 public void addPackagefile(Packagefile packagefile)
	 {
		 em.persist(packagefile);		 
	 }
	 public List<Packagefile> getAllPakagefile()
	 {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	       CriteriaQuery<Packagefile> criteria = cb.createQuery(Packagefile.class);
	       Root<Packagefile> pagefile = criteria.from(Packagefile.class);
	      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
	      // feature in JPA 2.0 	   	 
		   criteria.select(pagefile).orderBy(cb.desc(pagefile.get("id")));;
		   try{
			   List<Packagefile>  pagefiles =  em.createQuery(criteria).getResultList();
			   return pagefiles;	
			}catch(NoResultException e)
			{
				return null;
			}
		   
	 }
		public void deletePackagefile(long id)
		{
			em.remove(findById(id));
		}
}
