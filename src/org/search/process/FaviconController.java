package org.search.process;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientURI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FaviconController {
	private static final String url = "mongodb://localhost";
	private final FaviconUrlDao faviconUrlDao;
	private final WebsiteLinksDao websiteLinkDao;
	private final FaviconProcessor processorInst = FaviconProcessor.getInstance();
	
	public FaviconController(){
		final MongoClient mongoClient = new MongoClient(new MongoClientURI(url));
        final MongoDatabase faviconDatabase = mongoClient.getDatabase("UrltoFavicon");
        
        faviconUrlDao = new FaviconUrlDao(faviconDatabase);
        websiteLinkDao = new WebsiteLinksDao(faviconDatabase);
	}
	
	public List<String> getFaviconForUrl(String url, Boolean getFresh) throws IOException {
		List<String> faviconUrl = null;
	   	
		if(getFresh){
			return getNewUrl(url);
		}
		else{
			
			faviconUrl = faviconUrlDao.findFaviconByUrl(url);
			if(faviconUrl!=null){
				return faviconUrl;
			}
			else{
				return getNewUrl(url);
			}
		}
	}
	
	public List<String> getWebsiteUrl(String url) throws IOException {
		List<String> websiteUrlList = websiteLinkDao.findWebsiteUrl(url);
		return websiteUrlList;
	}
	
	private List<String> getNewUrl(String url) throws IOException{
		List<String> faviconUrl = new ArrayList();
		String favUrl = processorInst.getFaviconURL(url);
		
		if(favUrl!=null /*&& isValidUrl(favUrl)*/){
			faviconUrlDao.insertUrltoFaviconMapping(url, favUrl);
			faviconUrl.add(favUrl);
			return faviconUrl;
		}
		else{
			return null;
		}
	}

	private boolean isValidUrl(String url) throws IOException{
		URL u = new URL ( url);
		HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
		huc.setRequestMethod("HEAD");   
		huc.connect () ; 
		int code = huc.getResponseCode() ;
		
		if(huc.getResponseCode() == HttpURLConnection.HTTP_OK)
			return true;
		else
			return false;
	}
}
