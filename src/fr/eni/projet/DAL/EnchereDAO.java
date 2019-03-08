package fr.eni.projet.DAL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jackson.annotate.JacksonAnnotation;

import fr.eni.projet.beans.ArticleVendu;
import fr.eni.projet.beans.Categorie;
import fr.eni.projet.beans.Retrait;
import fr.eni.projet.beans.Utilisateur;
import fr.eni.projet.utils.DBConnectPool;
import fr.eni.projet.utils.Outils;

public class EnchereDAO {

	private final static String EXECUTER = "{call enchere (?,?,?,?) }";
	private final static String DETAIL = "SELECT av.no_article, nom_article, description, prix_vente, prix_initial, date_fin_encheres, libelle, acheteur.pseudo as acheteur, vendeur.pseudo as vendeur, r.rue, r.code_postal, r.ville	 \r\n" + 
			"	FROM ENCHERES e INNER JOIN UTILISATEURS acheteur \r\n" + 
			"		ON e.no_utilisateur = acheteur.no_utilisateur\r\n" + 
			"		INNER JOIN ARTICLES_VENDUS av ON e.no_article = av.no_article\r\n" + 
			"		INNER JOIN RETRAITS r ON av.no_article = r.no_article \r\n" + 
			"		INNER JOIN CATEGORIES c ON av.no_categorie = c.no_categorie\r\n" + 
			"		INNER JOIN UTILISATEURS vendeur ON vendeur.no_utilisateur = av.no_utilisateur \r\n" + 
			"	WHERE av.no_article = ?;";
	private final static String AFFICHERACQUEREUR = "select en.no_article,pseudo,date_fin_encheres,prix_vente,r.rue,r.code_postal,r.ville,u.telephone  \r\n" + 
			"FROM ENCHERES en\r\n" + 
			"INNER JOIN (SELECT no_article, MAX(montant_enchere) AS maxMontant FROM ENCHERES GROUP BY no_article) enMax ON enMax.no_article = en.no_article\r\n" + 
			"	AND en.montant_enchere = enMax.maxMontant\r\n" + 
			"INNER JOIN ARTICLES_VENDUS av ON en.no_article = av.no_article\r\n" + 
			"INNER JOIN UTILISATEURS u ON u.no_utilisateur = en.no_utilisateur\r\n" + 
			"LEFT OUTER JOIN RETRAITS r ON en.no_article = r.no_article\r\n" + 
			"WHERE en.no_utilisateur = ?;";
	private final static String AFFICHERVENTEFINIE = "SELECT nom_article, av.description, prix_vente, prix_initial, date_fin_encheres, acheteur.pseudo as acheteur, vendeur.pseudo as vendeur, r.rue, r.code_postal, r.ville	 \r\n" + 
			"	FROM ARTICLES_VENDUS av INNER JOIN UTILISATEURS vendeur\r\n" + 
			"		ON av.no_utilisateur = vendeur.no_utilisateur\r\n" + 
			"		INNER JOIN RETRAITS r ON av.no_article = r.no_article \r\n" + 
			"		INNER JOIN ENCHERES e ON av.no_article = e.no_article\r\n" + 
			"		INNER JOIN UTILISATEURS acheteur ON acheteur.no_utilisateur = e.no_utilisateur \r\n" + 
			"	WHERE av.no_utilisateur = ?;";
	
	public static String encherir(int noNouveauEncheisseur, int noArticle, int nouveauMontant) throws DALException{
		Connection cnx=null;
		CallableStatement cstmt=null;
		String message ="";
		try{
			cnx = DBConnectPool.seConnecter();
			cstmt=cnx.prepareCall(EXECUTER);
			cstmt.setInt(1, noNouveauEncheisseur);
			cstmt.setInt(2, nouveauMontant);
			cstmt.setInt(3, noArticle);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			message = cstmt.getString(4);
		} catch (SQLException e){
			throw new DALException ("Probleme - ajouterEnchere - " + e.getMessage());
		}finally{
			try{
				if (cstmt!=null) cstmt.close();
				if (cnx!=null) cnx.close();
			}catch (SQLException e){
				throw new DALException ("Probleme - fermerConnexion - " + e.getMessage());
			}		
		}return message;
	}
	
	/**
	 * Méthode qui importe une enchère de la base de données et la charge dans un <b>Objet vente de type ArticleVendu</b> 
	 * @param noArticle
	 * @return <b>ArticleVendu vente</b>
	 * @throws DALException
	 */
	public static ArticleVendu detailVente(int noArticle) throws DALException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Retrait lieuRetrait = null;
		ArticleVendu vente = null;
		Utilisateur acheteur = null;
		Utilisateur vendeur = null;
		Categorie categorieDetails = null;
		Outils o = new Outils();
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(DETAIL);
			pstmt.setInt(1, noArticle);
			rs=pstmt.executeQuery();
			if (rs.next()){
				//instantiation des objets à charger
				lieuRetrait = new Retrait();
				vente = new ArticleVendu();
				acheteur = new Utilisateur();
				categorieDetails = new Categorie();
				vendeur = new Utilisateur();
				//chargement de l'objet retrait & envoi de l'objet dans ArticleVendu
				lieuRetrait.setRue(rs.getString("rue"));
				lieuRetrait.setCode_postal(rs.getString("code_postal"));
				lieuRetrait.setVille(rs.getString("ville"));
				vente.setLieuRetrait(lieuRetrait);
				//chargement de l'objet categorie & envoi de l'objet dans ArticleVendu
				categorieDetails.setLibelle(rs.getString("libelle"));
				vente.setCategorieDetails(categorieDetails);
				//chargement de l'objet acheteur & envoi de l'objet dans ArticleVendu
				acheteur.setPseudo(rs.getString("acheteur"));
				vente.setAcheteur(acheteur);
				//chargement de l'objet vendeur & envoi de l'objet dans ArticleVendu
				vendeur.setPseudo(rs.getString("vendeur"));
				vente.setVendeurO(vendeur);	
				//fin de chargement de l'objet ArticleVendu
				vente.setNoArticle(rs.getInt("no_article"));
				vente.setNomArticle(rs.getString("nom_article"));
				vente.setDescription(rs.getString("description"));
				vente.setPrixVente(rs.getInt("prix_vente"));
				vente.setMiseAPrix(rs.getInt("prix_initial"));
				vente.setDateFinEncheres(o.dateSQLEnJavaUtil(rs.getDate("date_fin_encheres")));
			}
		}catch (SQLException e){
			throw new DALException ("Probleme - rechercherUtilisateur - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			} catch (SQLException e){
				throw new DALException ("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return vente;
	}
	
	public static ArticleVendu AFFICHERACQUEREUR(int noUtilisateur) throws DALException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Retrait lieuRetrait = null;
		ArticleVendu vente = null;
		Utilisateur vendeur = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(AFFICHERACQUEREUR);
			pstmt.setInt(1, noUtilisateur);
			rs=pstmt.executeQuery();
			if (rs.next()){
				//chargement de l'objet retrait & envoi de l'objet dans ArticleVendu
				lieuRetrait = new Retrait();
				vente = new ArticleVendu();
				vendeur = new Utilisateur();
				lieuRetrait.setRue(rs.getString("r.rue"));
				lieuRetrait.setCode_postal(rs.getString("r.code_postal"));
				lieuRetrait.setVille(rs.getString("r.ville"));
				vente.setLieuRetrait(lieuRetrait);
				//chargement de l'objet vendeur & envoi de l'objet dans ArticleVendu
				vendeur.setPseudo(rs.getString("vendeur"));
				vendeur.setTelephone(rs.getString("telephone"));
				vente.setVendeurO(vendeur);
				//fin de chargement de l'objet ArticleVendu
				vente.setNomArticle(rs.getString("nom_article"));
				vente.setDescription(rs.getString("description"));
				vente.setPrixVente(rs.getInt("prix_vente"));
				vente.setMiseAPrix(rs.getInt("prix_initial"));
			
			}
		}catch (SQLException e){
			throw new DALException ("Probleme - rechercherUtilisateur - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			} catch (SQLException e){
				throw new DALException ("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return vente;
	}
	public static ArticleVendu VenteFinie(int noArticle) throws DALException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Retrait lieuRetrait = null;
		ArticleVendu vente = null;
		Utilisateur acheteur = null;
		Utilisateur vendeur = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(AFFICHERVENTEFINIE);
			pstmt.setInt(1, noArticle);
			rs=pstmt.executeQuery();
			if (rs.next()){
				//instantiation des objets à charger
				lieuRetrait = new Retrait();
				vente = new ArticleVendu();
				acheteur = new Utilisateur();
				vendeur = new Utilisateur();
				//chargement de l'objet retrait & envoi de l'objet dans ArticleVendu
				lieuRetrait.setRue(rs.getString("r.rue"));
				lieuRetrait.setCode_postal(rs.getString("r.code_postal"));
				lieuRetrait.setVille(rs.getString("r.ville"));
				vente.setLieuRetrait(lieuRetrait);
				//chargement de l'objet acheteur & envoi de l'objet dans ArticleVendu
				acheteur.setPseudo(rs.getString("acheteur"));
				vente.setAcheteur(acheteur);
				//chargement de l'objet vendeur & envoi de l'objet dans ArticleVendu
				vendeur.setPseudo(rs.getString("vendeur"));
				vente.setVendeurO(vendeur);	
				//fin de chargement de l'objet ArticleVendu
				vente.setNomArticle(rs.getString("nom_article"));
				vente.setDescription(rs.getString("description"));
				vente.setPrixVente(rs.getInt("prix_vente"));
				vente.setMiseAPrix(rs.getInt("prix_initial"));
				vente.setDateFinEncheres(rs.getDate("date_fin_encheres"));
			}
		}catch (SQLException e){
			throw new DALException ("Probleme - rechercherUtilisateur - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			} catch (SQLException e){
				throw new DALException ("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return vente;
	}
}
