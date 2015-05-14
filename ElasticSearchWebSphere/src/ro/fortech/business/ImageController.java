package ro.fortech.business;

import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ro.fortech.access.ImageAccess;
import ro.fortech.model.Image;
import ro.fortech.model.Movie;

@Stateless
public class ImageController {
	
	@Inject
	private ImageAccess imgAcc;
	
	public void init(Properties properties) {
		imgAcc.init(properties);
	}
	
	public void addImage(Image image){
		imgAcc.addImage(image);
	}
	
	public List<Image> displayImages(Movie movie){
		
		return imgAcc.searchDocument("movieId", movie.getId());
	}
	

}
