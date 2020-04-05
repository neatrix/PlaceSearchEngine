package com.netas;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.places.FoundPlaceParamaters;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class PlaceSearchEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaceSearchEngineApplication.class, args);
	}

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String message(Principal principal) {
		String message = "<html><head>" + "<h1 align=center>Hello " + principal.getName() + "</h1>"
				+ "<h1 align=center> Welcome to Nearby Search Engine</h1>" + "</head></html>";

		return message;

	}

	@RequestMapping(value = "/googleLocation", method = RequestMethod.GET)

	public List<FoundPlaceParamaters> googleLocation9(Principal principal, HttpServletRequest request

	) {

		List<FoundPlaceParamaters> list = new ArrayList<FoundPlaceParamaters>();
		GooglePlaces client = new GooglePlaces("AIzaSyC6-NSy2YL7ZK_szfdIQlvtqogk4lURCVo");

		double lng = Double.parseDouble(request.getParameter("longitude").toString());
		double lat = Double.parseDouble(request.getParameter("latitude").toString());
		double rad = Double.parseDouble(request.getParameter("radius").toString());

		List<Place> places = client.getNearbyPlaces(lng, lat, rad, GooglePlaces.MAXIMUM_RESULTS);
		for (Place p : places) {
			FoundPlaceParamaters res = new FoundPlaceParamaters();
			res.setPlaceName(p.getName());
			res.setLatitude(p.getLatitude());
			res.setLongitude(p.getLongitude());

			list.add(res);
		}
		return list;

	}

}
