package fr.eni.projet.DAL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni.projet.beans.ArticleVendu;
import fr.eni.projet.beans.Categorie;
import fr.eni.projet.beans.Utilisateur;
import fr.eni.projet.utils.DBConnectPool;
import fr.eni.projet.utils.Outils;

public class ArticleVenduDAO {
	
	private final static String INSERER = "insert into ARTICLES_VENDUS(nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,prix_vente,no_utilisateur,no_categorie,) values (?,?,?,?,?,?,?,?,?);";
	private final static String LISTER = "select no_article,nom_article,description,date_fin_encheres,prix_initial, prix_vente, pseudo from ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur;";
	private final static String SELECTBYID = "select no_categorie, libelle from CATEGORIES where no_categorie = ?;";
	private final static String RECHERCHEPARIDCATEGORIE ="select no_article,nom_article,description,date_fin_encheres,prix_initial, prix_vente, pseudo from ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur where ARTICLES_VENDUS.no_categorie = ?;";
	private final static String RECHERCHEPARMOTCLE ="select no_article,nom_article,description,date_fin_encheres,prix_initial, prix_vente, pseudo from ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur where nom_article LIKE ?;";
	private final static String RECHERCHEPARFILTRES ="select no_article,nom_article,description,date_fin_encheres,prix_initial, prix_vente, pseudo from ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur where nom_article LIKE ?  AND no_categorie = ?;";
	public static void ajouter(ArticleVendu articleVendu) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;

		try{
			cnx = DBConnectPool.seConnecter();
			Outils o = new Outils();
			pstmt=cnx.prepareStatement(INSERER);
			pstmt.setString(1, articleVendu.getNomArticle());
			pstmt.setString(2, articleVendu.getDescription());
			pstmt.setDate(3, o.dateJavaUtilEnDateSQL(articleVendu.getDateDebutEncheres()));
			pstmt.setDate(4, o.dateJavaUtilEnDateSQL(articleVendu.getDateFinEncheres()));
			pstmt.setInt(5, articleVendu.getMiseAPrix());
			pstmt.setInt(6, articleVendu.getPrixVente());
			pstmt.setInt(7, articleVendu.getNoVendeur());
			pstmt.setInt(8, articleVendu.getNoCategorie());

			pstmt.executeUpdate();
		} catch (SQLException e){
			throw new DALException ("Probleme - ajouterEnchere - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			}catch (SQLException e){
				throw new DALException ("Probleme - fermerConnexion - " + e.getMessage());
			}		
		}
	}
	public static ArrayList<ArticleVendu> lister() throws DALException {
		Connection cnx = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ArticleVendu> listeArticle = new ArrayList<ArticleVendu>();
		try {
			cnx = DBConnectPool.seConnecter();
			stmt = cnx.createStatement();
			rs = stmt.executeQuery(LISTER);
			Outils o = new Outils();
			ArticleVendu articleVendu;
			while (rs.next()) {
				articleVendu = new ArticleVendu();
				articleVendu.setNoArticle(rs.getInt("no_article"));
				articleVendu.getNomArticle();
				if (rs.wasNull())
					articleVendu.setNomArticle("inconnu");
				else
					articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.getDescription();
				if (rs.wasNull())
					articleVendu.setDescription("inconnu");
				else
					articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateFinEncheres(o.dateSQLEnJavaUtil(rs.getDate("date_fin_encheres")));
				articleVendu.getMiseAPrix();
				if (rs.wasNull())
					articleVendu.setMiseAPrix(0);
				else
					articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.getPrixVente();
				if (rs.wasNull())
					articleVendu.setPrixVente(0);
				else
					articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setPseudoVendeur(rs.getString("pseudo"));
				listeArticle.add(articleVendu);
			}
			
		} catch (SQLException e) {
			throw new DALException("Probleme - listeCategorie -" + e.getMessage());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return listeArticle;
	}
	public static ArticleVendu selectParId(int noCategorie) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArticleVendu articleVendu = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(SELECTBYID);
			pstmt.setInt(1, noCategorie);
			rs=pstmt.executeQuery();
			if (rs.next()){
				articleVendu = new ArticleVendu();
				articleVendu.setNoCategorie(rs.getInt("no_categorie"));
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
		return articleVendu;
	}
	public static ArrayList<ArticleVendu> rechercheParIdCategories(int noCategorie) throws DALException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<ArticleVendu> listeArticle = new ArrayList<ArticleVendu>();
		try {
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(RECHERCHEPARIDCATEGORIE);
			pstmt.setInt(1, noCategorie);
			rs=pstmt.executeQuery();
			Outils o = new Outils();
			ArticleVendu articleVendu;
			while (rs.next()) {
				articleVendu = new ArticleVendu();
				articleVendu.setNoArticle(rs.getInt("no_article"));

				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateFinEncheres(o.dateSQLEnJavaUtil(rs.getDate("date_fin_encheres")));
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setPseudoVendeur(rs.getString("pseudo"));
				listeArticle.add(articleVendu);
			}
			
		} catch (SQLException e) {
			throw new DALException("Probleme - listeCategorie -" + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return listeArticle;
	}
	public static ArrayList<ArticleVendu> rechercheParMotCle(String nomArticle) throws DALException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<ArticleVendu> listeArticle = new ArrayList<ArticleVendu>();
		try {
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(RECHERCHEPARMOTCLE);
			pstmt.setString(1, "%" + nomArticle + "%");
			rs=pstmt.executeQuery();
			Outils o = new Outils();
			ArticleVendu articleVendu;
			while (rs.next()) {
				articleVendu = new ArticleVendu();
				articleVendu.setNoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateFinEncheres(o.dateSQLEnJavaUtil(rs.getDate("date_fin_encheres")));
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setPseudoVendeur(rs.getString("pseudo"));
				listeArticle.add(articleVendu);
			}
			
		} catch (SQLException e) {
			throw new DALException("Probleme - listeCategorie -" + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return listeArticle;
	}
	public static ArrayList<ArticleVendu> rechercheParDeuxFiltres(String nomArticle, int noCategorie) throws DALException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<ArticleVendu> listeArticle = new ArrayList<ArticleVendu>();
		try {
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(RECHERCHEPARFILTRES);
			pstmt.setString(1, "%" + nomArticle + "%");
			pstmt.setInt(2, noCategorie);
			rs=pstmt.executeQuery();
			Outils o = new Outils();
			ArticleVendu articleVendu;
			while (rs.next()) {
				articleVendu = new ArticleVendu();
				articleVendu.setNoArticle(rs.getInt("no_article"));
				articleVendu.setNomArticle(rs.getString("nom_article"));
				articleVendu.setDescription(rs.getString("description"));
				articleVendu.setDateFinEncheres(o.dateSQLEnJavaUtil(rs.getDate("date_fin_encheres")));
				articleVendu.setMiseAPrix(rs.getInt("prix_initial"));
				articleVendu.setPrixVente(rs.getInt("prix_vente"));
				articleVendu.setPseudoVendeur(rs.getString("pseudo"));
				listeArticle.add(articleVendu);
			}
			
		} catch (SQLException e) {
			throw new DALException("Probleme - listeCategorie -" + e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (cnx != null)
					cnx.close();
			} catch (SQLException e) {
				throw new DALException("Probleme - FermerConnexion - " + e.getMessage());
			}
		}
		return listeArticle;
	}

	
}
