package ro.fortech.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ro.fortech.access.MovieAccess;
import ro.fortech.business.DisplayMoviesController;
import ro.fortech.model.Movie;

@RunWith(MockitoJUnitRunner.class)
public class DisplayMoviesControllerTest {

	// Note the variable name must be exactly the same as used in
	// DisplayMoviesController
	@Mock
	private MovieAccess movieAccess;
	@InjectMocks
	private final DisplayMoviesController controller = new DisplayMoviesController();

	@Test
	public void displayMoviesWithNoParamsReturns3Movies() {

		// MockitoAnnotations.initMocks(this); either this or
		// @RunWith(MockitoJUnitRunner.class)

		// create mock list
		List<Movie> mockMovieList = new ArrayList<Movie>();
		mockMovieList.add(new Movie());
		mockMovieList.add(new Movie());
		mockMovieList.add(new Movie());
		mockMovieList.add(new Movie());

		// mock
		Mockito.when(movieAccess.searchDocument()).thenReturn(mockMovieList);

		// DisplayMoviesController is tested
		List<Movie> list = controller.displayMovies();

		// Tests
		assertEquals("There should be 3 movies", 4, list.size());
	}
}
