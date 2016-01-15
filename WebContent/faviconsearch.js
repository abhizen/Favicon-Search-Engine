/**
 * 
 */

$(document).ready(function(){
	
	//var url = "http://localhost:8080/FaviconSearchEngine/favicon/v1/rest/getUrl";
	var url = "https://cryptic-shelf-6360.herokuapp.com/favicon/v1/rest/getUrl/";
	//var webLinksUrl = "http://localhost:8080/FaviconSearchEngine/favicon/v1/rest/webUrl";
	var webLinksUrl = "https://cryptic-shelf-6360.herokuapp.com/favicon/v1/rest/webUrl";
	var submitbutton = $("#submit");
	var textbox = $("#link");
	var link = "";
	var faviconlist = null;
	var dispUrlLinks = null;
	
	textbox.autocomplete({
		source: function( request, response ) {
			getLink(response,showWebUrl(webLinksUrl+"/"+textbox.val(),ajaxcall));
		}
	});
	
	function getLink(response,callback){
		response(dispUrlLinks);
		
	}
	//declaration
	function createLink(callback){
		link = url+"/"+textbox.val();
		return callback(link,ajaxcall);
	}
	
	function showWebUrl(url,callback){
		return callback(url,"");
	}
	
	function showfav(url,callback){
		//textbox.autocomplete('disable');
		return callback(url,flow);
	}
	
	function ajaxcall(url,callback){
		var result = //null;
		$.ajax({
			type: 'get',
			url: url,
			dataType: 'text',
			async: false,
			success:
		      function (response) {
			
				return response.responseText;
		        }
		});
		
		if ('function' === typeof callback){
			return callback(result.responseText,hideHeader);
		}
		else{
			return toArray(result.responseText,assignValues);
		}
	}
	
	function flow(list,callback){
		
		callback($("#resultheader"),$("#imgList"),list,addImage);
	}
	
	function hideHeader(resultheader,imgList,list,callback){
		resultheader.show();
		imgList.empty();
    	callback(list,function(){$("#results").show();});
	}
	
	function addImage(list, callback){
	
		if(list==null || $.trim(list)==="\"\""){
			$("#imgList").
	    	append("<li>'No search results'</li>");
			return;
		}
		
		var elements = list.toString().split(",");
		var arrayLength = elements.length;
		
		for (var i = 0; i < arrayLength; i++) {
			if(elements[i].indexOf("[")>-1)
			   elements[i] = elements[i].replace(/[\[\]]+/g, '');
			if(elements[i].indexOf("]")>-1)
				   elements[i] = elements[i].replace(/[\[\]]+/g, '');
			if(elements[i].indexOf("\"")>-1)
				elements[i] = elements[i].replace(/[\"\"]+/g, '');
			
			$("#imgList").
			append("<tr>" +
					"<td>"+elements[i]+"</td>"+
					"<td><img src='"+elements[i]+"'></td><tr>");
	    	
		}
		//textbox.autocomplete('enable');
		return callback;
	}
	
	function assignValues(elements){
		dispUrlLinks = elements;
	}
	
	function toArray(list,callback){
		var elements = list.split(",");
		var arrayLength = elements.length;
		
		if(list==null || list.trim()==="\"\""){
			return null;
		}
		
		for (var i = 0; i < arrayLength; i++) {
			if(elements[i].indexOf("[")>-1)
			   elements[i] = elements[i].replace(/[\[\]]+/g, '');
			if(elements[i].indexOf("]")>-1)
				   elements[i] = elements[i].replace(/[\[\]]+/g, '');
			if(elements[i].indexOf("\"")>-1)
				elements[i] = elements[i].replace(/[\"\"]+/g, '');
		}
		return callback(elements);
	}

	submitbutton.click(function(){
		createLink(showfav);
	});
});