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
        JSONObject mainObject = new JSONObject();
        JSONArray skills = new JSONArray();
        mainObject.put("skills", skills);
        String[] hiscoreArray = rawHiscore.split("\n");
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
