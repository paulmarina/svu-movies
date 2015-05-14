package ro.fortech.business;

import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ro.fortech.access.MovieAccess;
import ro.fortech.model.Movie;

@Stateless
public class DisplayMoviesController {

	@Inject
	private MovieAccess movieAccess;

	private List<Movie> moviesList;
	private Movie addedMovie;
	
	public Movie getAddedMovie() {
		return addedMovie;
	}

	public void setAddedMovie(Movie addedMovie) {
		this.addedMovie = addedMovie;
	}

	public DisplayMoviesController() {

	}

	public void init(Properties properties) {
		movieAccess.init(properties);
	}

	public List<Movie> displayMovies() {

		/*
		 * Movie mov = new Movie("Pearl Harbor", "Michael Bay", 2001, 16,
		 * "coffee.jpg"); Movie mov1 = new Movie("Fast and furious",
		 * "Francis Ford Coppola", 1972, 34,"coffee.jpg"); Movie mov2 = new
		 * Movie("12 angry men", "Sidney Lumet", 1957, 18, "coffee.jpg"); Movie
		 * mov3 = new Movie("Pulp Fiction", "Quentin Tarantino", 1994, 19,
		 * "coffee.jpg"); Movie mov4 = new Movie("Inception",
		 * "Christopher Nolan", 2010, 20, "coffee.jpg"); Movie mov5 = new
		 * Movie("The Silence of the Lambs", "Jonathan Demme", 1991, 21,
		 * "coffee.jpg");
		 * 
		 * movieAccess.upsertDocument(mov); movieAccess.upsertDocument(mov1);
		 * movieAccess.upsertDocument(mov2); movieAccess.upsertDocument(mov3);
		 * movieAccess.upsertDocument(mov4); movieAccess.upsertDocument(mov5);
		 */
		return movieAccess.searchDocument();
	}

	public void deleteMovie(Movie movie) {

		Boolean deleteSucceeded = movieAccess.deleteDocument(movie.getId());
		if (deleteSucceeded) {
			moviesList.remove(movie);
		}

	}

	public void addMovie(Movie movie) {
		String newMovieId = movieAccess.insertDocument(movie);
		if (!newMovieId.equals("")) {
			movie.setId(newMovieId);
			moviesList.add(movie);
			this.addedMovie = movie;
		}
	}

	public Movie editMovie(String id) {

		return movieAccess.getById(id);
	}

	public void updateMovie(Movie movie) {

		movieAccess.updateDocument(movie);

		for (Movie movieElem : moviesList) {
			if (movieElem.getId().equals(movie.getId())) {
				movieElem.setTitle(movie.getTitle());
				movieElem.setDirector(movie.getDirector());
				movieElem.setYear(movie.getYear());

			}
		}

	}

	public List<Movie> getMoviesList() {
		return moviesList;
	}

	public void setMoviesList(List<Movie> moviesList) {
		this.moviesList = moviesList;
	}

}
