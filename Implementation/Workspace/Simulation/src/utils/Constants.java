package utils;

public class Constants {

	/** Parametrage de la simulation **/
	public static final int PRE_SIM_WDW_WIDTH = 600;
	public static final int PRE_SIM_WDW_HEIGHT = 400;
	public static final String[] PREDEFINED_SIZES = {"100x100", "500x500", "500x700", "700x700", "Personnalise..."}; // choix de tailles de grille
	public static final int MAX_POP = 500; // population maximale 
	
	/** Donnees de la simulation **/
	public static final float VEGETATION_MAX_WEIGHT_PER_CELL=0.2f; //200 gramms
	public static final float VEGETATION_WEIGHT_GAINED_PER_STEP=0.005f; //5 gramms

	/** Gestion du service Rest **/
	public static final String[] REST_MANAGEMENT_ACTIONS = {"Choisir une action...", "Ajouter une espèce", "Supprimer une espèce", "Mettre à jour une espèce existante"};
}
