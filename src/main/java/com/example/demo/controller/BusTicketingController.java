package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BusTicketingController {

	public Map<String,String> userNamePasswordMap = new HashMap<String, String>();
	public Map<String,String> userNameUuidMap = new HashMap<String, String>();
	public static int formNo;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ResponseEntity<String> registration(@RequestParam String username,@RequestParam String password) {
       try {
    	   	userNamePasswordMap.put(username, password);
    	   	return new ResponseEntity<>(HttpStatus.CREATED);
	} catch (Exception e) {
		e.printStackTrace();
	   	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<String> login(@RequestParam String username,@RequestParam String password) {
		final String uuid = UUID.randomUUID().toString().replace("-", "");
        if (userNamePasswordMap.get(username).equals(password)) {
        	userNameUuidMap.put(username, uuid);
        	return new ResponseEntity<String>("{\"result\":\"success\",\"username\":\""+username+"\",\"token\":\""+uuid+"\"}",HttpStatus.OK);
        }
        else
        	return new ResponseEntity<String>("{\"result\":\"fail\",\"token\":\""+null+"\"}",HttpStatus.BAD_REQUEST);
    }
	
	@RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public ResponseEntity<String> getForm(@RequestHeader String username,@RequestHeader String Authorization) {
		String uuid = Authorization.replace("Bearer ", "");
		if (userNameUuidMap.get(username).equals(uuid)) {
			formNo++;
        	return new ResponseEntity<String>("{\"result\":\"success\",\"formNo\":\""+formNo+"\"}",HttpStatus.OK);
        }
        else
        	return new ResponseEntity<String>("{\"result\":\"fail\",\"formNo\":\""+null+"\"}",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
	
}
