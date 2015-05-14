package ro.fortech.dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import ro.fortech.business.DisplayMoviesController;
import ro.fortech.business.ImageController;
import ro.fortech.model.Image;
import ro.fortech.model.Movie;
import ro.fortech.utils.Constants;

//@ManagedBean(name = "editMovieDialog")
@Named("editMovieDialog")
//@SessionScoped
@javax.enterprise.context.SessionScoped
public class EditMovieDialog implements Serializable {

	private static final long serialVersionUID = 4128776498153336119L;

	private Movie movie;

	@Inject
	private DisplayMoviesController displayCtrl;
	@Inject
	private ImageController imgCtrl;

	private boolean isEditMode;

	private String imagePath;
	private List<Image> imagesForMovie;

	public EditMovieDialog() {
		
	}
	
	@PostConstruct
	public void init3(){
		Movie editedMovie = displayCtrl.getAddedMovie() ;
		if (editedMovie != null) {
			this.setMovie(editedMovie);
		} else {
			this.setMovie(new Movie());
		}
	}

	public String editMovie(Movie movie) {

		Properties properties = loadPaths();
		imgCtrl.init(properties);
		imagesForMovie = imgCtrl.displayImages(movie);

		/*
		 * for (Image image : imagesForMovie) {
		 * 
		 * imagePath = "/rest/images/" + image.getId();
		 * 
		 * }
		 */

		setMovie(displayCtrl.editMovie(movie.getId()));
		return "editMovie?faces-redirect=true";
	}

	public String updateMovie(Movie movie) {

		displayCtrl.updateMovie(movie);
		return "index?faces-redirect=true";
	}

	public String checkEditMode(ActionEvent event) {

		String buttonId = event.getComponent().getId();

		if (buttonId.equals("edit")) {
			isEditMode = true;
		}

		else {
			isEditMode = false;
		}

		return String.valueOf(isEditMode);
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public boolean getIsEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Properties loadPaths() {

		Properties result = null;
		try {

			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			String path = servletContext.getRealPath(Constants.XML_PATH);
			File file = new File(path);

			FileInputStream fileInput = new FileInputStream(file);

			Properties properties = new Properties();
			properties.loadFromXML(fileInput);
			fileInput.close();
			result = properties;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public List<Image> getImagesForMovie() {
		return imagesForMovie;
	}

	public void setImagesPath(List<Image> imagesForMovie) {
		this.imagesForMovie = imagesForMovie;
	}

}
