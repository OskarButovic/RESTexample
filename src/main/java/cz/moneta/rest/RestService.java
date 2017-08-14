package cz.moneta.rest;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
 
@Path("/text-transform")
public class RestService {
	
	public static final List<String> WOVELS = Arrays.asList("a", "e", "i", "o", "u", 
															"á", "é", "í", "ó", "ú",
																 "ě",           "ů",
															"A", "E", "I", "O", "U",
															"Á", "É", "Í", "Ó", "Ú",
															     "Ě",           "Ů");
	
	@POST
	@Consumes("text/plain; charset=UTF-8")
	@Produces("text/plain; charset=UTF-8")
	public Response getMsgPost(String msg) {
		String output = transformText(msg);
		return Response.status(200).entity(output).build();
	}
	
	private String transformText(String text){
		if(text == null || text.isEmpty() || text.trim().isEmpty()){
			return "";
		}
		
		text = squashWhitespaces(text);
		List<Integer> wovelPlaces = computeWovelPlaces(text);
		text = turnText(text);
		text = text.toLowerCase();
		text = toUpperByList(text, wovelPlaces);
		return text;
	}
	
	private boolean isWovel(char candidate){
		for(String wovel : WOVELS){
			if(wovel.charAt(0) == candidate){
				return true;
			}
		}
		return false;
	} 
	
	private List<Integer> computeWovelPlaces(String msg){
		List<Integer> wowelPlaces = new ArrayList<Integer>();
		
		for(int i = 0; i < msg.length(); i++){
			char currentChar = msg.charAt(i);
			if(isWovel(currentChar)){
				Integer wovelPlace = new Integer(1);
				wowelPlaces.add(wovelPlace);
			}else{
				Integer wovelPlace = new Integer(0);
				wowelPlaces.add(wovelPlace);
			}
		}
		
		return wowelPlaces;
	}
	
	private String squashWhitespaces(String msg){
		String result = msg.replaceAll("\\s+", " ");
		return result;
	}
	
	private String turnText(String text){
		String result = "";
		for(int i = text.length() - 1; i >= 0 ; i--){
			result += text.charAt(i);
		}
		return result;
	}
	
	private String toUpperByList(String text, List<Integer> upperPlaces){
		List<String> splitted = splitString(text);
		String result = "";
		int i = 0;
		for(Integer place : upperPlaces){
			if(place.intValue() == 1){
				result += splitted.get(i).toUpperCase();
			}else{
				result += splitted.get(i);
			}
			i++;
		}
		return result;
	}
	
	private List<String> splitString(String text){
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < text.length(); i++){
			result.add("" + text.charAt(i));
		}
		return result;
	}
 
}