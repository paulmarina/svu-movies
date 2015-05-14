package ro.fortech.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.activation.MimetypesFileTypeMap;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import ro.fortech.access.ImageAccess;
import ro.fortech.model.Image;
import ro.fortech.utils.Constants;

@Path("images")
public class ImageService {

	@Inject
	private ImageAccess imgAcc;

	@GET
	@Path("{id}")
	@Produces("image/jpg")
	public Response getImage(@PathParam("id") String id,
			@Context ServletContext ctx) {

		Properties properties;
		try {
			String urlEncoded = ctx.getResource(Constants.XML_PATH_JAX)
					.getPath();
			String decoded = URLDecoder.decode(urlEncoded, "UTF-8");
			properties = loadPaths(decoded);
			imgAcc.init(properties);

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		Image image = imgAcc.getById(id);

		File f = null;
		String mt = "";

		try {
			String myPath = ctx.getResource("/").toURI()
					.resolve(Constants.IMAGE_PATH_JAX).getPath()
					+ image.getName();
			f = new File(myPath);
			mt = new MimetypesFileTypeMap().getContentType(f);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.ok(f, mt).build();

	}

	public Properties loadPaths(String preferencesPath) {

		Properties result = null;
		try {

			File file = new File(preferencesPath);
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
