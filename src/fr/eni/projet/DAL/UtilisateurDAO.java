package fr.eni.projet.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.eni.projet.beans.Utilisateur;
import fr.eni.projet.utils.DBConnectPool;

public class UtilisateurDAO {
	private final static String LISTER = "select pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit from UTILISATEURS;";
	private final static String RECHERCHER= "select no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit from UTILISATEURS where pseudo = ? and mot_de_passe = ? OR email = ? and mot_de_passe = ?;";
	private final static String INSERER = "insert into UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values (?,?,?,?,?,?,?,?,?,?,?);";
	private final static String SUPPRIMER = "delete from UTILISATEURS where pseudo =?;";
	private final static String MODIFIER = "update UTILISATEURS set pseudo = ?, nom = ?, prenom=?, email = ?,telephone=?,rue=?, code_postal = ?,ville = ?,mot_de_passe = ? where pseudo = ?;";
	private final static String SELECTBY= "select pseudo,nom,prenom,email,telephone,rue,code_postal,ville from UTILISATEURS where pseudo = ?;";
	private final static String CREDITER = "update UTILISATEURS set credit = credit+? where pseudo = ?;";
	private final static String SELECTBYID = "select pseudo,nom,prenom,email,telephone,rue,code_postal,ville from UTILISATEURS where no_utilisateur = ?;";
	/**
	 * <b><font color=red>Methode qui permet de lister les utilisateur</font></b>
	 * @return La liste peut être vide mais jamais <b><font color=green>null</font></b>
	 * @throws DALException : propage l'exception en <b>DALException</b>
	 * @finally ferme les connexions ouvertes
	 */

	public static ArrayList<Utilisateur> lister() throws DALException{
		Connection cnx=null;
		Statement stmt=null;
		ResultSet rs=null;
		ArrayList<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();
		
		
		try {
			cnx = DBConnectPool.seConnecter();
			stmt=cnx.createStatement();
			rs=stmt.executeQuery(LISTER);
			Utilisateur utilisateur;
			while (rs.next()){
				utilisateur = new Utilisateur();
				utilisateur.getPseudo();
				if(rs.wasNull())
					utilisateur.setPseudo("inconnu");
				else
					utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.getNom();
				if(rs.wasNull())
					utilisateur.setNom("inconnu");
				else
					utilisateur.setNom(rs.getString("nom"));
				utilisateur.getPrenom();
				if(rs.wasNull())
					utilisateur.setPrenom("inconnu");
				else
					utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.getEmail();
				if(rs.wasNull())
					utilisateur.setEmail("inconnu");
				else
					utilisateur.setEmail(rs.getString("email"));
				utilisateur.getTelephone();
				if(rs.wasNull())
					utilisateur.setTelephone("inconnu");
				else
					utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.getRue();
				if(rs.wasNull())
					utilisateur.setRue("inconnu");
				else
					utilisateur.setRue(rs.getString("rue"));
				utilisateur.getCodePostal();
				if(rs.wasNull())
					utilisateur.setCodePostal("inconnu");
				else
					utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.getVille();
				if(rs.wasNull())
					utilisateur.setVille("inconnu");
				else
					utilisateur.setVille(rs.getString("ville"));
				utilisateur.setCredit(rs.getInt("credit"));
				listeUtilisateur.add(utilisateur);
			}	
		} catch (SQLException e) {
			throw new DALException ("Probleme - listerUilisateu -" + e.getMessage());
		}finally{
			try{
				if (stmt!=null) stmt.close();
				if (cnx!=null) cnx.close();
			} catch (SQLException e) {
					throw new DALException ("Probleme - FermerConnexion - " + e.getMessage());
			}

		}
		return listeUtilisateur;			
		
	}
	/**
	 * methode permettant de recherche un utilisateur
	 * @param identifiant
	 * @param password
	 * @return un utilisateur
	 * @throws DALException
	 */
	public static Utilisateur rechercher(String identifiant, String password) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Utilisateur utilisateur = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(RECHERCHER);
			pstmt.setString(1, identifiant);
			pstmt.setString(2, password);
			pstmt.setString(3, identifiant);
			pstmt.setString(4, password);
			rs=pstmt.executeQuery();
			if (rs.next()){
				utilisateur = new Utilisateur();
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
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
		return utilisateur;
	}
	/**
	 * Methode ajouter un utilisateur
	 * @param utilisateur
	 * @throws DALException propage l'exception de type DALException
	 * @finally ferme les connexions ouvertes
	 */
	public static void ajouter(Utilisateur utilisateur) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;

		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(INSERER);
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setInt(10, utilisateur.getCredit());
			pstmt.setBoolean(11, utilisateur.isAdministrateur());
			

			pstmt.executeUpdate();
		} catch (SQLException e){
			throw new DALException ("Probleme - ajouterUtilisateur - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			}catch (SQLException e){
				throw new DALException ("Probleme - fermerConnexion - " + e.getMessage());
			}		
		}
	}
	public static void supprimer(String pseudo) throws DALException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		
			try {
				cnx = DBConnectPool.seConnecter();
				pstmt=cnx.prepareStatement(SUPPRIMER);
				pstmt.setString(1, pseudo);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				throw new DALException ("Probleme - supprimerUtilisateur - " + e.getMessage());
			}finally{
				try{
					if (pstmt!=null) pstmt.close();
					if (cnx!=null) cnx.close();
				}catch (SQLException e){
					throw new DALException ("Probleme - fermerConnexion - " + e.getMessage());
				}	
			}
		
	}
	
	public static void modifier(Utilisateur utilisateur, String ancienPseudo) throws DALException, SQLException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(MODIFIER);
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setString(10, ancienPseudo);
			pstmt.executeUpdate();
		} catch (SQLException e){
			throw new DALException ("Probleme - modifierUtilisateur - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			}catch (SQLException e){
				throw new DALException ("Probleme - fermerConnexion - " + e.getMessage());
			}		
		}
	}
	
	public static Utilisateur selectParPseudo(String pseudo) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Utilisateur utilisateur = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(SELECTBY);
			pstmt.setString(1, pseudo);
			rs=pstmt.executeQuery();
			if (rs.next()){
				utilisateur = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
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
		return utilisateur;
	}
	/**
	 * 
	 * @param pseudo
	 * @return
	 * @throws DALException
	 */
	public static Utilisateur selectParId(int no_utilisateur) throws DALException{
		Connection cnx=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Utilisateur utilisateur = null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(SELECTBYID);
			pstmt.setInt(1, no_utilisateur);
			rs=pstmt.executeQuery();
			if (rs.next()){
				utilisateur = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));

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
		return utilisateur;
	}
	
	/**
	 * Méthode créditer qui sert à <strong>ajouter ou retirer des crédits</strong> à un utilisateur
	 * @param credit
	 * @param pseudo
	 * @throws DALException
	 * @throws SQLException
	 */
	public static void crediter(int credit, String pseudo) throws DALException, SQLException {
		Connection cnx=null;
		PreparedStatement pstmt=null;
		try{
			cnx = DBConnectPool.seConnecter();
			pstmt=cnx.prepareStatement(CREDITER);
			pstmt.setInt(1, credit);
			pstmt.setString(2, pseudo);
			pstmt.executeUpdate();
		} catch (SQLException e){
			throw new DALException ("Probleme - crediterUtilisateur - " + e.getMessage());
		}finally{
			try{
				if (pstmt!=null) pstmt.close();
				if (cnx!=null) cnx.close();
			}catch (SQLException e){
				throw new DALException ("Probleme - fermerConnexion - " + e.getMessage());
			}		
		}
	}
}