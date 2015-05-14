package ro.fortech.dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import ro.fortech.model.Movie;
import ro.fortech.utils.Constants;

@ManagedBean(name = "movieImageDialog")
@SessionScoped
public class MovieImageDialog implements Serializable {

	private static final long serialVersionUID = 8621173128034519586L;
	private String imagePath;
	/*@Inject
	private ImageAccess imgAcc;
*/
	public String displayImage(Movie movie) {

		/*Image image = new Image("coffee.jpg", "AUzWgp7-vDefbL3DdFC9");
		Properties properties = loadPaths();
		imgAcc.init(properties);

		imgAcc.addImage(image);*/

		imagePath = "/rest/images/" + movie.getId();
		return "movieImage?faces-redirect=true";
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

}
