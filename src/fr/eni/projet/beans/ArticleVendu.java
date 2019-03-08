package fr.eni.projet.beans;

import java.util.Date;

import fr.eni.projet.DAL.CategorieDAO;
import fr.eni.projet.DAL.DALException;

/**
 * Classe ArticleVendu
 * Un constructeur vide et un constructeur rempli
 * Comprend une enumération sur l'etat de la vente : Cree, en cours, termine
 * Comprend les getters et setters
 * @author lmonti2018
 *
 */
public class ArticleVendu {
	private Utilisateur acheteur;
	private Utilisateur vendeurO;
	private String pseudoVendeur;
	private Categorie categorie;
	private Categorie categorieDetails;
	private int noVendeur;
	private int noArticle;
	private String nomArticle;
	private String description;
	private Date dateDebutEncheres;
	private Date dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private int noCategorie;
	private Enchere enchere;
	private Retrait lieuRetrait;
	

	public ArticleVendu(Utilisateur acheteur, int noVendeur, int noArticle, String nomArticle, String description,
			Date dateDebutEncheres, Date dateFinEncheres, int miseAPrix, int prixVente, int noCategorie) {
		super();
		this.acheteur = acheteur;
		this.noVendeur = noVendeur;
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.noCategorie = noCategorie;
	}

	public ArticleVendu() {
		super();
	};
	
	/**
	 * Enumération sur l'etat de la vente : crée, en cours et terminé
	 * @author lmonti2018
	 *
	 */
	public enum etatVente {
		CREE, ENCOURS, TERMINE;
	}

	public Utilisateur getAcheteur() {
		return acheteur;
	}

	public void setAcheteur(Utilisateur acheteur) {
		this.acheteur = acheteur;
	}

	

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoVendeur(int noVendeur) {
		this.noVendeur = noVendeur;
	}

	public int getNoVendeur() {
		return noVendeur;
	}

	public String getLieuRetrait() {
		return lieuRetrait.toString();
	}

	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}


	public Enchere getEnchere() {
		return enchere;
	}

	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}

	public void setVendeurO(Utilisateur vendeur) {
		this.vendeurO = vendeur;
	}

	public Utilisateur getVendeurO() {
		return vendeurO;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	/**
	 * methode qui retourne la categorie de l'article
	 * @return
	 */
	public Categorie getCategorie() {
		try {
			categorie = CategorieDAO.selectParId(this.noCategorie);
		} catch (DALException e) {
			System.out.println("probleme dans le setCategorie");
		}
		return categorie;
	}
	
	/**
	 * methode qui retoune le noUtilisateur
	 * @return
	 */
	public String getVendeur() {
		return pseudoVendeur;
	}
	
	public void setPseudoVendeur(String pseudoVendeur) {
		this.pseudoVendeur = pseudoVendeur;
	}

	public Categorie getCategorieDetails() {
		return categorieDetails;
	}

	public void setCategorieDetails(Categorie categorieDetails) {
		this.categorieDetails = categorieDetails;
	}

	@Override
	public String toString() {
		return String.format(
				"ArticleVendu [acheteur=%s, noVendeur=%s, noArticle=%s, nomArticle=%s, description=%s, dateDebutEncheres=%s, dateFinEncheres=%s, miseAPrix=%s, prixVente=%s, noCategorie=%s]",
				acheteur, noVendeur, noArticle, nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAPrix,
				prixVente, noCategorie);
	}
	
	
	
	
	
	
}
