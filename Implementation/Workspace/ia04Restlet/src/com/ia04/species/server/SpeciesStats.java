package com.ia04.species.server;


import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class SpeciesStats {

   @PrimaryKey
   @Persistent
	private String nom;
   
   @Persistent
	private String descriptif;
   
   @Persistent
	private Boolean isHerbivorious;
	
	// Animal
   @Persistent
	private String type;
   
   @Persistent
	private Double smellPoint; // by meter
   
   @Persistent
	private Double visionPoint; // by meter
   
   @Persistent
	private Double movePoint; // by meter per day
   
   @Persistent
	private Double maxLifetime;
   
   @Persistent
	private Double minimumWeightToDeath;
   
   @Persistent
	private Double weightConsumeByDay;
   
   @Persistent
	private Double maxNbDaySafe;
   
   @Persistent
	private Double attackPoint;
   
   @Persistent
	private Double defendPoint;
   
   @Persistent
	private Boolean isUseHiddenDefense;

	// Init animal
   @Persistent
	private Double initWeight;
   
   @Persistent
	private Double initAge;

	// Carnivorious
   @Persistent
	private ArrayList<String> eatableFoodList;
	
	// Reproduction
   @Persistent
	private Double birthRateByDay;

	
	public SpeciesStats() {
		eatableFoodList = new ArrayList<String>();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescriptif() {
		return descriptif;
	}

	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}

	public Boolean getIsHerbivorious() {
		return isHerbivorious;
	}

	public void setIsHerbivorious(Boolean isHerbivorious) {
		this.isHerbivorious = isHerbivorious;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getSmellPoint() {
		return smellPoint;
	}

	public void setSmellPoint(Double smellPoint) {
		this.smellPoint = smellPoint;
	}

	public Double getVisionPoint() {
		return visionPoint;
	}

	public void setVisionPoint(Double visionPoint) {
		this.visionPoint = visionPoint;
	}

	public Double getMovePoint() {
		return movePoint;
	}

	public void setMovePoint(Double movePoint) {
		this.movePoint = movePoint;
	}

	public Double getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(Double maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public Double getMinimumWeightToDeath() {
		return minimumWeightToDeath;
	}

	public void setMinimumWeightToDeath(Double minimumWeightToDeath) {
		this.minimumWeightToDeath = minimumWeightToDeath;
	}

	public Double getWeightConsumeByDay() {
		return weightConsumeByDay;
	}

	public void setWeightConsumeByDay(Double weightConsumeByDay) {
		this.weightConsumeByDay = weightConsumeByDay;
	}

	public Double getMaxNbDaySafe() {
		return maxNbDaySafe;
	}

	public void setMaxNbDaySafe(Double maxNbDaySafe) {
		this.maxNbDaySafe = maxNbDaySafe;
	}

	public Double getAttackPoint() {
		return attackPoint;
	}

	public void setAttackPoint(Double attackPoint) {
		this.attackPoint = attackPoint;
	}

	public Double getDefendPoint() {
		return defendPoint;
	}

	public void setDefendPoint(Double defendPoint) {
		this.defendPoint = defendPoint;
	}

	public Boolean getIsUseHiddenDefense() {
		return isUseHiddenDefense;
	}

	public void setIsUseHiddenDefense(Boolean isUseHiddenDefense) {
		this.isUseHiddenDefense = isUseHiddenDefense;
	}

	public Double getInitWeight() {
		return initWeight;
	}

	public void setInitWeight(Double initWeight) {
		this.initWeight = initWeight;
	}

	public Double getInitAge() {
		return initAge;
	}

	public void setInitAge(Double initAge) {
		this.initAge = initAge;
	}

	public ArrayList<String> getEatableFoodList() {
		return eatableFoodList;
	}

	public void setEatableFoodList(ArrayList<String> eatableFoodList) {
		this.eatableFoodList = eatableFoodList;
	}

	public Double getBirthRateByDay() {
		return birthRateByDay;
	}

	public void setBirthRateByDay(Double birthRateByDay) {
		this.birthRateByDay = birthRateByDay;
	}
	
}
