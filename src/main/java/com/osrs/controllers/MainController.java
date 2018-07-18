package com.osrs.controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {
    final String[] SKILLS_MAP = {"Overall", "Attack", "Defence", "Strength", "Hitpoints",
            "Ranged", "Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking",
            "Crafting", "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming",
            "Runecrafting", "Hunter", "Construction"};

    @RequestMapping(path = "/hiscore", method = RequestMethod.GET, produces = "application/json")
    String getHiscores(@RequestParam(value = "username", required = true) String username) {
        System.out.println(username+" made it!");

        RestTemplate restTemplate = new RestTemplate();
        String hiscoreUrl
                = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player="+username;
        ResponseEntity<String> response
                = restTemplate.getForEntity(hiscoreUrl, String.class);
        assert(response.getStatusCode().equals(HttpStatus.OK));
        return parseHiscore(response.getBody());
    }
    
    @RequestMapping(path = "/mockHiscore", method = RequestMethod.GET, produces = "application/json")
    String getMockHiscores(@RequestParam(value = "username", required = true) String username) {
        System.out.println(username+" made it!");

        String erimar = "213335,1606,28476731 244044,81,2318518 232775,80,2161530 311096,86,3921884 308677,86,3923461 323877,84,3049345 223124,70,758061 422534,77,1541093 225978,77,1475630 219436,75,1290531 227777,78,1629252 225700,74,1100897 433598,58,232899 299869,65,457621 368814,57,208073 212829,68,663358 240728,60,278536 303365,66,502479 190520,63,377278 199387,79,1827593 272646,54,151410 191103,54,156110 267388,60,300046 248926,54,151126 97433,14 120777,19 150352,66 -1,-1 -1,-1 137471,33 -1,-1 -1,-1 -1,-1";


        String bamboozling = "78747,1862,71167111 122779,93,7201782 183840,85,3293697 182717,94,8717920 152942,96,9905397 203338,90,5873822 53412,84,3011143 211391,90,5389838 196655,80,1995350 271952,73,994780 208109,80,1986089 184307,77,1476267 178462,75,1273709 93022,77,1487641 109646,74,1122277 144194,72,905385 95808,78,1629252 162551,71,858906 70645,81,2192896 91794,89,5060801 66314,83,2673544 78869,67,589536 133257,70,788109 36155,83,2738970 74560,18 97130,25 78423,147 -1,-1 -1,-1 71670,84 -1,-1 35370,19 80530,1";

        return parseMockHiscore((username.equals("erimar") ? erimar:bamboozling));
    }

    public String parseHiscore(String rawHiscore) {
         /*
            {
                skills: [
                    overall: {
                        rank: 123
                        level: 81
                        exp: 456
                    },
                    attack: {
                        rank: 123
                        level: 81
                        exp: 456
                    },
                    def: {
                        rank: 123
                        level: 81
                        exp: 456
                    },
                ]
            }

          */
    	
    	System.out.println(rawHiscore.toString());
        JSONObject mainObject = new JSONObject();
        JSONArray skills = new JSONArray();
        mainObject.put("skills", skills);
        String[] hiscoreArray = rawHiscore.split("/n");
        for(int i = 0; i < 24; i++) {
            String[] skillArray = hiscoreArray[i].split(",");
            JSONObject skill = new JSONObject();
            skill.put("rank", skillArray[0]);
            skill.put("level", skillArray[1]);
            skill.put("exp", skillArray[2]);
            skills.put(skill);
            System.out.println(skill.toString());
            System.out.println("hiscoreArray[i] "+hiscoreArray[i]);
        }

        System.out.println(mainObject.toString());
        //return new ArrayList<>();
        /*
        overall
        att
        def
        str
        hp
        ranged
        prayer
        magic
        cooking
        woodcutting
        fletching
        fishing
        firemaking
        crafting
        smithing
        mining
        herblore
        agility
        thieving
        slayer
        farming
        rc
        hunter
        con
        easy clues
        mediums
        all
        something
        something
        hards
        something
        something
        something
         */
        return mainObject.toString();
    }
    
    public String parseMockHiscore(String rawHiscore) {
        /*
           {
               skills: [
                   overall: {
                       rank: 123
                       level: 81
                       exp: 456
                   },
                   attack: {
                       rank: 123
                       level: 81
                       exp: 456
                   },
                   def: {
                       rank: 123
                       level: 81
                       exp: 456
                   },
               ]
           }

         */
   	
   	System.out.println(rawHiscore.toString());
       JSONObject mainObject = new JSONObject();
       JSONArray skills = new JSONArray();
       mainObject.put("skills", skills);
       String[] hiscoreArray = rawHiscore.split(" ");
       for(int i = 0; i < 24; i++) {
           String[] skillArray = hiscoreArray[i].split(",");
           JSONObject skill = new JSONObject();
           skill.put("rank", skillArray[0]);
           skill.put("level", skillArray[1]);
           skill.put("exp", skillArray[2]);
           skills.put(skill);
           System.out.println(skill.toString());
           System.out.println("hiscoreArray[i] "+hiscoreArray[i]);
       }

       System.out.println(mainObject.toString());
       //return new ArrayList<>();
       /*
       overall
       att
       def
       str
       hp
       ranged
       prayer
       magic
       cooking
       woodcutting
       fletching
       fishing
       firemaking
       crafting
       smithing
       mining
       herblore
       agility
       thieving
       slayer
       farming
       rc
       hunter
       con
       easy clues
       mediums
       all
       something
       something
       hards
       something
       something
       something
        */
       return mainObject.toString();
   }
}
