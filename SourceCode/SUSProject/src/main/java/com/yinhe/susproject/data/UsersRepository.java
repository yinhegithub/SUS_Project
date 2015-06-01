package com.yinhe.susproject.data;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.yinhe.susproject.model.Users;

@Stateless
public class UsersRepository {
	 @Inject
	    private EntityManager em;
	 
	    public Users findById(Integer id) {
	        return em.find(Users.class, id);
	    }
	    
	    public void addUsers(Users user)throws Exception
		  {
				em.persist(user);
		  }
		  
}
