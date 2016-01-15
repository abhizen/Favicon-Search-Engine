package org.search.process;

import org.apache.commons.validator.routines.UrlValidator;

/*Favicon Processor creates new favicon url and validates it.
 * */
public class FaviconProcessor {
	private static FaviconProcessor instance = null;
	private final static String faviconURL = "/favicon.ico";
	private final static String webPrefix = "www.";
	private final static String protocol = "http://";
	
	private FaviconProcessor(){}
	
	public static FaviconProcessor getInstance(){
		if(instance==null)
			instance = new FaviconProcessor();
		
		return instance;
	}
	
	public static String getFaviconURL(String url){
		String newfaviconUrl = null;
		if(url==null)
			return null;
		else{
			newfaviconUrl = addProtocol(addWorldWideWeb(url))+faviconURL;
			
			if(isValid(newfaviconUrl)){
				return newfaviconUrl;
			}
			else{
				System.out.println("Invalid url");
				return null;
			}
		}
	}
	
	private static String addWorldWideWeb(String url){
		
		if(url!=null && !url.toLowerCase().contains(webPrefix))
			return  webPrefix+url;
		return url;
	}
	private static String addProtocol(String url){
		
		if(url!=null && !url.toLowerCase().contains(protocol))
			return  protocol+url;
		return url;
	}
		
	@SuppressWarnings("deprecation")
	private static boolean isValid(String url){
		@SuppressWarnings("deprecation")
		UrlValidator defaultValidator = new UrlValidator();
		if (defaultValidator.isValid(url)) {
		    return true;
		}
		else
			return false;
	}
	
}
