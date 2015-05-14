package ro.fortech.model;

public class Image {
	
	private String name;
	private String movieId;
	private String id;

	public Image(String name, String movieId, String id) {
		
		this.name = name;
		this.movieId = movieId;
		this.id = id;
	}

	public Image(){
		
	}
	
	public Image(String name, String movieId) {
		
		this.name = name;
		this.movieId = movieId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
