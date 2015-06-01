package com.yinhe.susproject.service;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.yinhe.susproject.model.Users;

/**
 * Session Bean implementation class UserManager
 */
@Stateless
public class UserManager {
	
	public static int USER_ROLE_NONE = 0;
	public static int USER_ROLE_ADMIN = 0;
	public static int USER_ROLE_OPERATOR = 0;

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Resource
	private SessionContext ctx;

	/**
	 * Default constructor.
	 */
	public UserManager() {
		// TODO Auto-generated constructor stub
	}
	
	public String login(String username, String password) {
		// 
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		try {
			request.login(username, password);
			return where2Go();
		} catch (ServletException e) {
			context.addMessage("login", new FacesMessage("Login failed, please check your username & password!"));
			return "void";
		}
	}
	
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		try {
			request.logout();
		} catch (ServletException e) {
			context.addMessage(null, new FacesMessage("Logout failed."));
		}
		
		return "index";
	}
	
	
	public String where2Go() {
		log.info("where 2 go? ==>>");
		if (ctx.isCallerInRole("admin")) {
			return "admin_console";
		}
		
		if (ctx.isCallerInRole("operator")) {
			return "operator_console";
		}
		
		return "void";
	}

	public int whoIsUser(Users currentUser) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Users> criteria = cb.createQuery(Users.class);
		Root<Users> page = criteria.from(Users.class);
		
		// 
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(page).where(
				cb.equal(page.get("username"), currentUser.getUsername()));
		List<Users> users = em.createQuery(criteria).getResultList();

		if (0 == users.size()) {
			log.info(currentUser.getUsername() + " don't exit.");
			return USER_ROLE_NONE;
		} else {
			for (Users user:users)
			{
				user.getEmail();
				return USER_ROLE_OPERATOR;
			}
			return USER_ROLE_NONE;
		}
	}

	public boolean isUserAdmin(Users currentUser, String passwd) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Users> criteria = cb.createQuery(Users.class);
		Root<Users> page = criteria.from(Users.class);
		
		// 
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(page)
				.where(cb.equal(page.get("username"), currentUser.getUsername()))
				.where(cb.equal(page.get("isadmin"), "Y"));
		List<Users> users = em.createQuery(criteria).getResultList();

		if (0 == users.size()) {
			return false;
		}
		/*
		for (Users user : users) {
			// 以下代码用与预置管理员用户名密码
			String salt = PasswdHelperCryptoUtils.getSalt();
			user.setPasswordsalt(salt);
			user.setPasswordhash(PasswdHelperCryptoUtils.getHash(passwd, salt));
						
			if (PasswdHelperCryptoUtils.verify(user.getPasswordhash(),
					passwd, user.getPasswordsalt())) {
				log.info(currentUser.getUsername()
						+ " is admin, passwd correct.");
				return true;
			} else {
				log.info(currentUser.getUsername()
						+ " is admin, passwd NOT correct.");
				continue;
			}
		}*/

		return true;
	}

	public boolean isUserOperator(Users currentUser, String passwd) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Users> criteria = cb.createQuery(Users.class);
		Root<Users> page = criteria.from(Users.class);
		
		// 
		// Swap criteria statements if you would like to try out type-safe
		// criteria queries, a new
		// feature in JPA 2.0
		// criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		criteria.select(page)
				.where(cb.equal(page.get("username"), currentUser.getUsername()))
				.where(cb.equal(page.get("isadmin"), "N"));
		List<Users> users = em.createQuery(criteria).getResultList();

		if (0 == users.size()) {
			return false;
		}

		/*
		for (Users user : users) {
			if (PasswdHelperCryptoUtils.verify(user.getPasswordhash(),
				passwd, user.getPasswordsalt())) {
				log.info(currentUser.getUsername()
						+ " is operator, passwd correct.");
				return true;
			} else {
				log.info(currentUser.getUsername()
						+ " is operator, passwd NOT correct.");
				continue;
			}
		}*/

		return true;
	}

	public List<Users> getAllUsers() {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Users> criteria = cb.createQuery(Users.class);
		Root<Users> usr = criteria.from(Users.class);
		criteria.select(usr).orderBy(cb.asc(usr.get("name")));
		return em.createQuery(criteria).getResultList();
	}

	public void deleteUser(String name) {
		// TODO Auto-generated method stub
		log.info("Delete user:" + name);
		em.remove(em.find(Users.class, name));
	}

}
