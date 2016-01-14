package org.search.process;



import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FaviconUrlDao {
	private final MongoCollection<Document> faviconCollection;
	
	public FaviconUrlDao(final MongoDatabase faviconDb){
		faviconCollection = faviconDb.getCollection("UrlToFavicon");
	}
	
	public List<String> findFaviconByUrl(String url){
		List<String> favUrl = null;
		
		Bson filter =  new Document("url",new Document("$regex",url));
		Bson projection = new Document("fav_url",1)
						.append("_id", 0);
		
		List<Document> faviconLinkList = faviconCollection.find(filter)
										.projection(projection)
										.into(new ArrayList<Document>());
		
		if(faviconLinkList==null || faviconLinkList.isEmpty())
			return null;
		else{
			favUrl = new ArrayList();
			for(Document doc :faviconLinkList){
				favUrl.add(doc.getString("fav_url"));
			}
			return favUrl;
		}
	}
	
	public void insertUrltoFaviconMapping(String url,String favicon){
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		Date date = new Date(stamp.getTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Document urlToFaviconMapping = new Document("url", url)
                .append("fav_url", favicon)
                .append("created", format.format(date));
		faviconCollection.insertOne(urlToFaviconMapping);
	}
}
