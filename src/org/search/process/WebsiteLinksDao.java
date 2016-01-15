package org.search.process;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class WebsiteLinksDao {
private final MongoCollection<Document> webUrlCollection;
private final static String urlCol = "url";
	
	public WebsiteLinksDao(final MongoDatabase faviconDb){
		webUrlCollection = faviconDb.getCollection("UrlToFavicon");
	}
	
	public List<String> findWebsiteUrl(String url){
		List<String> webUrl = null;
		
		Bson filter =  new Document(urlCol,new Document("$regex",url));
		Bson projection = new Document(urlCol,1)
						.append("_id", 0);
		
		List<Document> webUrlList = webUrlCollection.find(filter)
										.projection(projection)
										.into(new ArrayList<Document>());
		
		if(webUrlList==null || webUrlList.isEmpty())
			return null;
		else{
			webUrl = new ArrayList();
			for(Document doc :webUrlList){
				webUrl.add(doc.getString(urlCol));
			}
			return webUrl;
		}
	}
	
}
