package ro.fortech.access;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import ro.fortech.model.Image;
import ro.fortech.utils.Constants;

public class ImageAccess {

	Node node;
	Client client;
	private Properties properties = new Properties();

	public void init(Properties properties) {
		this.properties = properties;
		this.node = nodeBuilder().clusterName(
				properties.getProperty(Constants.CLUSTER)).node();
		this.client = node.client();
	}

	public void addImage(Image image) {
		client.prepareIndex(properties.getProperty(Constants.INDEX),
				properties.getProperty(Constants.IMAGE_TYPE))
				.setSource(createJsonDocument(image)).execute().actionGet();
	}

	public Map<String, Object> createJsonDocument(Image image) {

		Map<String, Object> jsonDocument = new HashMap<String, Object>();

		jsonDocument.put("name", image.getName());
		jsonDocument.put("movieId", image.getMovieId());
		return jsonDocument;

	}

	public List<Image> searchDocument(String column, String value) {

		SearchResponse response;

		if (value == null && column == null) {
			response = client.prepareSearch()
					.setTypes(properties.getProperty(Constants.IMAGE_TYPE))
					.execute().actionGet();

		} else {

			response = client.prepareSearch()
					.setTypes(properties.getProperty(Constants.IMAGE_TYPE))
					.setQuery(QueryBuilders.matchQuery(column, value))
					.execute().actionGet();
		}

		List<Image> result = new ArrayList<Image>();

		SearchHit[] results = response.getHits().getHits();
		for (SearchHit hit : results) {
			Map<String, Object> partialResult = hit.getSource();

			String localName = "";
			String localMovieId = "";
			String localId = hit.getId();
			;

			for (Map.Entry<String, Object> entry : partialResult.entrySet()) {

				if (entry.getKey().equals("name")) {
					localName = entry.getValue().toString();
				} else if (entry.getKey().equals("movieId")) {
					localMovieId = entry.getValue().toString();
				}
			}
			Image image = new Image(localName, localMovieId, localId);

			result.add(image);

		}
		client.close();
		return result;

	}

	public Image getById(String id) {
		GetResponse response = client.prepareGet()
				.setIndex(properties.getProperty(Constants.INDEX))
				.setType(properties.getProperty(Constants.IMAGE_TYPE))
				.setId(id).execute().actionGet();

		Map<String, Object> hit = response.getSource();

		String localName = hit.get("name").toString();
		String localMovieId = hit.get("movieId").toString();

		Image image = new Image(localName, localMovieId, id);

		client.close();
		return image;
	}

}
