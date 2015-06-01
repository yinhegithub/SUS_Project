package com.yinhe.susproject.model;

// Generated 2015-5-11 19:44:16 by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Schedule generated by hbm2java
 */
@Entity
@Table(name = "schedule", catalog = "db3")
public class Schedule implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6221815056643131512L;
	private Long id;
	private Users users;
	private int factoryId;
	private String hardwareId;
	private Short levels;
	private String description;
	private String trailMode;
	private Date startTime;
	private Date endTime;
	private Set<Rulemac> rulemacs = new HashSet<Rulemac>(0);
	private Set<Rulesn> rulesns = new HashSet<Rulesn>(0);
	private Set<Rulerange> ruleranges = new HashSet<Rulerange>(0);

	public Schedule() {
	}

	public Schedule(int factoryId) {
		this.factoryId = factoryId;
	}

	public Schedule(Users users, int factoryId, String hardwareId,
			Short levels, String description,
			String trailMode, Date startTime, Date endTime,
			Set<Rulemac> rulemacs, Set<Rulesn> rulesns,
			Set<Rulerange> ruleranges) {
		this.users = users;
		this.factoryId = factoryId;
		this.hardwareId = hardwareId;	
		this.levels = levels;
		this.description = description;
		this.trailMode = trailMode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.rulemacs = rulemacs;
		this.rulesns = rulesns;
		this.ruleranges = ruleranges;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	@Column(name = "factoryId", nullable = false)
	public int getFactoryId() {
		return this.factoryId;
	}

	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}

	@Column(name = "hardwareId", length = 64)
	public String getHardwareId() {
		return this.hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}


	@Column(name = "levels")
	public Short getLevels() {
		return this.levels;
	}

	public void setLevels(Short levels) {
		this.levels = levels;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "trailMode", length = 2)
	public String getTrailMode() {
		return this.trailMode;
	}

	public void setTrailMode(String trailMode) {
		this.trailMode = trailMode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startTime")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endTime")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
	public Set<Rulemac> getRulemacs() {
		return this.rulemacs;
	}

	public void setRulemacs(Set<Rulemac> rulemacs) {
		this.rulemacs = rulemacs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
	public Set<Rulesn> getRulesns() {
		return this.rulesns;
	}

	public void setRulesns(Set<Rulesn> rulesns) {
		this.rulesns = rulesns;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
	public Set<Rulerange> getRuleranges() {
		return this.ruleranges;
	}

	public void setRuleranges(Set<Rulerange> ruleranges) {
		this.ruleranges = ruleranges;
	}

}