package server;

public class SpeciesStats {
	public SpeciesStats(String descriptif, float odorat, float vue,
			float attaquer, int ageMin, int ageMax, float energie,
			float distanceDeplacement, float seReproduire, float gestation,
			float poidsMin, float poidsMax) {

		this.descriptif = descriptif;
		this.odorat = odorat;
		this.vue = vue;
		this.attaquer = attaquer;
		this.ageMin = ageMin;
		this.ageMax = ageMax;
		this.energie = energie;
		this.distanceDeplacement = distanceDeplacement;
		this.seReproduire = seReproduire;
		this.gestation = gestation;
		this.poidsMin = poidsMin;
		this.poidsMax = poidsMax;
	}
	
	public SpeciesStats() {
		// TODO Auto-generated constructor stub
	}
	private String descriptif;
    private float odorat; //pour se déplacer vers la nourriture
    private float vue; //permet d'attaquer si un lapin est en vue
    private float attaquer; //coefficient de réussite (quand le lapin survit, il se cache pendant une journée)
    private int ageMin; //age à partir duquel l'espèce commence à décliner
    private int ageMax; //age à partir duquel l'espèce est très vieille (et doit mourir)
    private float energie; //poids de viande à manger par jour pour être à 100%
    private float distanceDeplacement;
    private float seReproduire; //coefficient de réussite
    private float gestation; //durée de gestation
    private float poidsMin; //poids minimum pour les plus maigres
    private float poidsMax; //poids maximum pour les plus gros
	
    public String getDescriptif() {
		return descriptif;
	}
	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}
	public float getOdorat() {
		return odorat;
	}
	public void setOdorat(float odorat) {
		this.odorat = odorat;
	}
	public float getVue() {
		return vue;
	}
	public void setVue(float vue) {
		this.vue = vue;
	}
	public float getAttaquer() {
		return attaquer;
	}
	public void setAttaquer(float attaquer) {
		this.attaquer = attaquer;
	}
	public int getAgeMin() {
		return ageMin;
	}
	public void setAgeMin(int ageMin) {
		this.ageMin = ageMin;
	}
	public int getAgeMax() {
		return ageMax;
	}
	public void setAgeMax(int ageMax) {
		this.ageMax = ageMax;
	}
	public float getEnergie() {
		return energie;
	}
	public void setEnergie(float energie) {
		this.energie = energie;
	}
	public float getDistanceDeplacement() {
		return distanceDeplacement;
	}
	public void setDistanceDeplacement(float distanceDeplacement) {
		this.distanceDeplacement = distanceDeplacement;
	}
	public float getSeReproduire() {
		return seReproduire;
	}
	public void setSeReproduire(float seReproduire) {
		this.seReproduire = seReproduire;
	}
	public float getGestation() {
		return gestation;
	}
	public void setGestation(float gestation) {
		this.gestation = gestation;
	}
	public float getPoidsMin() {
		return poidsMin;
	}
	public void setPoidsMin(float poidsMin) {
		this.poidsMin = poidsMin;
	}
	public float getPoidsMax() {
		return poidsMax;
	}
	public void setPoidsMax(float poidsMax) {
		this.poidsMax = poidsMax;
	}
    

	
}
