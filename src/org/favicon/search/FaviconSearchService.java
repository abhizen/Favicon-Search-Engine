package org.favicon.search;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.search.process.FaviconController;
import com.google.gson.Gson;

@Path("/v1/rest")
public class FaviconSearchService {
	private static FaviconController controller = new FaviconController();
	private final static String errorMessage = "Service unavailable, please try again";
	
	
	@GET
	@Path("/getUrl/{url}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFaviconUrl(@PathParam("url") String url){
		System.out.println("I am inside get method1");
		List<String> faviconUrl = null;
		try{
			
			faviconUrl = controller.
				getFaviconForUrl(url, false);
			return new Gson().toJson(faviconUrl!=null?faviconUrl:"");
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
		return errorMessage;
	}
	
	@GET
	@Path("/webUrl/{webUrl}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getWebUrl(@PathParam("webUrl") String webUrl){
		List<String> websiteUrl = null;
		try{	
			websiteUrl = controller.getWebsiteUrl(webUrl);
			
			return new Gson().toJson(websiteUrl!=null?websiteUrl:"");
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
		return "";
	}
}
