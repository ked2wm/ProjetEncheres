package fr.eni.projetEncheres.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projetEncheres.BusinessException;
import fr.eni.projetEncheres.bll.ArticleVenduManager;
import fr.eni.projetEncheres.bll.RetraitManager;
import fr.eni.projetEncheres.bll.UtilisateurManager;
import fr.eni.projetEncheres.bo.Categorie;
import fr.eni.projetEncheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/ServletNouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/NouvelleVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        request.setCharacterEncoding("UTF-8");
        
        
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
        UtilisateurManager um = new UtilisateurManager();
        ArticleVenduManager avm = new ArticleVenduManager();
        RetraitManager rm = new RetraitManager();
        Utilisateur user = new Utilisateur();
        
        
        
		
		
		int no_article = 0;
		String NomArticle=null;
		String description=null;
		String categorie=null;
		int  credit=0;
		String DébutEnchère=null;
		String FinEnchère=null;
		String rue=null;
		String codepostal=null;
		String ville=null;
		
		
		
		request.setCharacterEncoding("UTF-8");
		List<Integer> listeCodesErreur=new ArrayList<>();
		
		NomArticle = request.getParameter("NomArticle");
		description = request.getParameter("description");
		categorie = request.getParameter("categorie");
		credit = Integer.parseInt(request.getParameter("prix_initial"));
		DébutEnchère = request.getParameter("date_debut_encheres");
		FinEnchère = request.getParameter("date_fin_encheres");
		
		rue = request.getParameter("rue");
		codepostal = request.getParameter("codepostal");
		ville = request.getParameter("ville");
		
		Categorie categories = new Categorie(0, categorie);
		
		//From ArticleVendu
	/*	if (NomArticle == null || NomArticle.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}
		if (description == null || description.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}
		if (categorie == null || categorie.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}
		if (credit == 0 ) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}
		if (DébutEnchère == null || DébutEnchère.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}
		if (FinEnchère == null || FinEnchère.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}
		
		
		//From Retrait
		if (rue == null || rue.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}
		if (codepostal == null || codepostal.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}
		if (ville == null || ville.trim().isEmpty()) {
			listeCodesErreur.add(CodesResultatServlets.FORMAT_PSEUDO_ERREUR);
		}*/
		
		
		
		if (listeCodesErreur.size() > 0) {
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/NouvelleVente.jsp");
			rd.forward(request, response);
		} else {
			
			ArticleVenduManager articleVenduManager = new ArticleVenduManager();
			RetraitManager retraitManager = new RetraitManager();
			UtilisateurManager utilisateurManager = new UtilisateurManager();
			HttpSession session = request.getSession();
			
			try {
				
				String pseudo = (String) session.getAttribute("pseudo");
				String password = (String) session.getAttribute("password");
				
				articleVenduManager.createArticleVendu( no_article, NomArticle, description, credit, 0, user, categories  );
				
				session.setAttribute("no_article", no_article);
				session.setAttribute("NomArticle", NomArticle);
				session.setAttribute("description", description);
						
		    //    session.setAttribute("DébutEnchère", dateFormat.parse(DébutEnchère));
		    //    session.setAttribute("FinEnchère", dateFormat.parse(FinEnchère));
				session.setAttribute("credit", credit);
				session.setAttribute("prixfinal", 0);
				session.setAttribute("Utilisateur", utilisateurManager.logUtilisateur(pseudo, password) );
				session.setAttribute("categories", categories);

		//		retraitManager.ajouterRetrait( rue, codepostal, ville);
				
				session.setAttribute("rue", rue);
				session.setAttribute("codepostal", codepostal);
				session.setAttribute("ville", ville);
				
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/MesEnchères.jsp");
				rd.forward(request, response);
				
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/DejaConnecte.jsp");
				rd.forward(request, response);
			}	
		}
		
		
		
		
		
		
	}

}
