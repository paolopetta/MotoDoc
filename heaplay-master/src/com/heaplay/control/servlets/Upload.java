package com.heaplay.control.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.PurchasableTrackBean;
import com.heaplay.model.beans.TagBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PurchasableTrackDao;
import com.heaplay.model.dao.TrackDao;
import com.mysql.cj.jdbc.exceptions.PacketTooBigException;

@WebServlet("/upload")
@MultipartConfig(
		fileSizeThreshold = 2 * 1024 * 1024,
		maxFileSize = 30 * 1024 * 1024,
		maxRequestSize = 60 * 1024 * 1024 )

public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Lettura della sessione
    	HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		if(userBean != null) {
			//Loggato
			request.setAttribute("jspPath", response.encodeURL("/upload.jsp"));
			request.setAttribute("pageTitle", "Upload");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
			rd.forward(request, response);
		} else {
			//Non loggato
			request.setAttribute("jspPath", "/login.jsp");
			request.setAttribute("pageTitle", "Login");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");
			rd.forward(request, response);
		}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Lettura dei parametri
		String trackName = request.getParameter("songName");
		String radioBox = request.getParameter("purchasable");
		String id = request.getParameter("authorId");
		String duration = request.getParameter("duration");
		String username = ((UserBean)request.getSession().getAttribute("user")).getUsername();
		
		String[] tags = request.getParameterValues("tag");
		ArrayList<TagBean> listTags = new ArrayList<TagBean>();
		
		for(String name : tags) {
			TagBean tagBean = new TagBean();
			tagBean.setName(name);
			listTags.add(tagBean);
		}
		//Audio
		Part audio = request.getPart("audio");
		String audioFileName = audio.getSubmittedFileName();
		String audioExt=audioFileName.substring(audioFileName.lastIndexOf('.'),audioFileName.length()).toLowerCase();
		InputStream audioStream =audio.getInputStream();
		byte[] audioBytes = audioStream.readAllBytes();
		audioStream.close();

		//Immagine
		Part image = request.getPart("image");
		String imageFileName = image.getSubmittedFileName();
		String imageExt=imageFileName.substring(imageFileName.lastIndexOf('.'),imageFileName.length()).toLowerCase();
		InputStream imageStream =image.getInputStream();
		byte[] imageBytes = imageStream.readAllBytes();
		imageStream.close();
		
		TrackBean trackBean = new TrackBean();
		PurchasableTrackBean purchasableTrack = null;
		
		trackBean.setAuthor(Long.parseLong(id));
		trackBean.setIndexable(true);
		trackBean.setName(trackName);
		trackBean.setTrack(audioBytes);
		trackBean.setTrackExt(audioExt);
		trackBean.setImage(imageBytes);
		trackBean.setImageExt(imageExt);
		trackBean.setUploadDate(new Timestamp(System.currentTimeMillis()));
		trackBean.setTags(listTags);
		trackBean.setDuration(Integer.parseInt(duration));
		trackBean.setAuthorName(username);
		
		if(radioBox.equalsIgnoreCase("Gratis"))
			trackBean.setType("free");
		else {
			trackBean.setType("pagamento");
			String p = request.getParameter("price").replaceAll(",", ".");
			double price = Double.parseDouble(p);	
			purchasableTrack = new PurchasableTrackBean(trackBean);
			purchasableTrack.setPrice(price);
		}
		
		try {
			//Creazione della track
			if(purchasableTrack != null) {
				PurchasableTrackDao purchasableTrackdao = new PurchasableTrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
				purchasableTrackdao.doSave(purchasableTrack);
			}else {
				TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
				trackDao.doSave(trackBean);
			}
			response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));	
	
		} catch (PacketTooBigException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "La grandezza del brano dev'essere di massimo 20MB");
			request.setAttribute("jspPath", response.encodeURL("/upload.jsp"));
			request.setAttribute("pageTitle", "Upload");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
			rd.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "C'è stato un problema con il caricamento, riprova");
			request.setAttribute("jspPath", response.encodeURL("/upload.jsp"));
			request.setAttribute("pageTitle", "Upload");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
			rd.forward(request, response);
		}
	}
}
