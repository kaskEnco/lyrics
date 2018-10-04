package com.controllers;

import java.util.HashMap;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyrics.dao.PushNotificationDAO;
import com.lyrics.model.NewSongs;
@RestController
public class PushNotificationController {

	@GetMapping(value = "/newSongs", produces = "application/json")
	public HashMap<String, List<NewSongs>> newSongs(){
	
		HashMap<String, List<NewSongs>> map = new HashMap<>();
		List<NewSongs> newsongs = new PushNotificationDAO().getNewSongs();
		map.put("Added new songs", newsongs);
		return map;
		}
	
	@GetMapping(value = "/pushNotification", produces = "application/json")
	public String pushNotification(){
	
		String notificationStatus = new PushNotificationDAO().sendPushNotification();
		
		return notificationStatus;
		}
	
	@GetMapping(value = "/pushNotificationForAllTimeHits", produces = "application/json")
	public String pushNotificationForAllTimeHits(){
	
		String notificationStatus = new PushNotificationDAO().sendPushNotificationForAllTimeHits();
		
		return notificationStatus;
		}
	
	@GetMapping(value = "/pushNotificationForDecades", produces = "application/json")
	public String pushNotificationForDecades(){
	
		String notificationStatus = new PushNotificationDAO().sendPushNotificationForDecades();
		
		return notificationStatus;
		}
}
