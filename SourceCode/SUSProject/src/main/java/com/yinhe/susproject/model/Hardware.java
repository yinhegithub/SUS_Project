package com.yinhe.susproject.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity implementation class for Entity: Hardware
 *
 */
@Entity
@Table(name = "hardware", catalog = "db3", uniqueConstraints = @UniqueConstraint(columnNames = {
		"hardwareId", "factoryId" }))
public class Hardware implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Long id;
	private int factoryId;
	private String hardwareId;
	private String factoryName;


	public Hardware() {
		super();
	}
	
	public Hardware(int factoryId, String hardwareId) {
		
		this.factoryId = factoryId;
		this.hardwareId = hardwareId;
		
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
	@Column(name = "factoryId", nullable = false)
	
	public int getFactoryId() {
		return this.factoryId;
	}

	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}

	@Column(name = "hardwareId", length = 64, nullable = false)
	public String getHardwareId() {
		return this.hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}
	@Column(name = "factoryName", length = 128)
	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

}
