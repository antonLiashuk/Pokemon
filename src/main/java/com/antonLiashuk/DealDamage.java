package com.zadanie2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DealDamage {
    private String damageFrom;
    private String damageTo;

    final static String doubleDamageTo = "double_damage_to";
    final static String halfDamageTo = "half_damage_to";
    final static String noDamageTo = "no_damage_to";
    final static String POKEAPI_URL = "https://pokeapi.co/api/v2/type/";

    public DealDamage(String damageFrom, String damageTo) throws IOException, JSONException {
        this.damageFrom = damageFrom;
        this.damageTo = damageTo;
        attack();
    }

    public void attack() throws IOException, JSONException {
        double totalDamage = 0;
        String response = getResponseFromURL(POKEAPI_URL, damageFrom);
            //1) Parsing (String)StringBuffer into a JSONObject
            JSONObject wholeJson = new JSONObject(response.toString());

            //2) After we are entering the next JSONObject called "damage_relations"
            JSONObject mappedJson = wholeJson.getJSONObject("damage_relations");

            //3) We need to parse a JSONObject into JSONArray
            if(identifyDamageValue(mappedJson, doubleDamageTo)){
                totalDamage += 2;
            } else if(identifyDamageValue(mappedJson, halfDamageTo)){
                totalDamage += 0.5;
            } else if(identifyDamageValue(mappedJson, noDamageTo)){
                totalDamage = 0;
            } else {
                totalDamage = 1;
            }

            System.out.println(totalDamage + "x");

    }
    public String getResponseFromURL(String url, String damageFrom) throws IOException {
        String http = url + damageFrom;
        URL obj = new URL(http);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }else {
            return "ERR: " + responseCode;
        }
    }
    public boolean identifyDamageValue(JSONObject jsonObject, String powerOfDamage){
        JSONArray jsonArray = jsonObject.getJSONArray(powerOfDamage);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject name = jsonArray.getJSONObject(i);
            if(damageTo.equals(name.get("name"))){
                return true;
            }
        }
        return false;
    }
    public JSONObject parseJSONArrayElementIntoJSONObject(JSONArray jsonArray, int index){
        return jsonArray.getJSONObject(index);
    }
}
