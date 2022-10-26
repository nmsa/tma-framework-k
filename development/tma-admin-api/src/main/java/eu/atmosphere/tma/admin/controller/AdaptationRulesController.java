/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tma.admin.controller;

import eu.atmosphere.tma.admin.util.PropertiesManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a Rest Controller. It handles every request made to the
 * server related with Adaptation Rules. Also, these requests are redirected internally to the planning component server.
 *
 * @author João Ribeiro  <jdribeiro@student.dei.uc.pt>
 */
@CrossOrigin
@RestController
public class AdaptationRulesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdaptationRulesController.class);
    
    private PropertiesManager pm = PropertiesManager.getInstance();
    private String planningAPI =  pm.getProperty("planningApiUrlProduction");
            
    @DeleteMapping("/removeRule/{ruleName}")
    public ResponseEntity<String> updateRules(
            @PathVariable(name= "ruleName") String ruleName, 
            HttpServletResponse response) throws UnsupportedEncodingException, MalformedURLException {
        
        String safeRuleName = processRuleNameUnsafeChars(ruleName);
        URL url = new URL(urlBuilder(new HashMap(),"/removeRule/" + safeRuleName));
        HttpURLConnection con;
        
        try {
            con = (HttpURLConnection) url.openConnection();
        
        
            //establish connection type and timeouts
            con.setRequestMethod("DELETE");
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);

            int status = con.getResponseCode();
            String responseData = getResponse(con,status);
            
            if(status >= 200 && status < 300){
                return new ResponseEntity<>(responseData,HttpStatus.valueOf(response.getStatus()));
            }
            else{
                LOGGER.error("Planning's API server responded with error to the request");
                return new ResponseEntity<>(responseData, HttpStatus.resolve(status));
            }
        }
        catch (IOException ex) {
            LOGGER.error("There was a problem with the connection to Planning's API", ex);
            return new ResponseEntity<>(
                    "There was a problem with the connection to Planning's API. Try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getRules")
    public ResponseEntity<String> getRules(
            @RequestParam Map<String,String> allRequestParams,
            HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException{
        
        try {
            URL url = new URL(urlBuilder(allRequestParams,"/getRules"));
            HttpURLConnection con;
            
            con = (HttpURLConnection) url.openConnection();
            
            //establish connection type and timeouts
            con.setRequestMethod("GET");
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);
            
            int status = con.getResponseCode();
            String responseData = getResponse(con,status);
            
            if(status >= 200 && status < 300){
                return new ResponseEntity<>(responseData,HttpStatus.valueOf(response.getStatus()));
            }
            else{
                LOGGER.error("Planning's API server responded with error to the request");
                return new ResponseEntity<>(responseData, HttpStatus.resolve(status));
            }
        } 
        catch (IOException ex) {
            LOGGER.error("There was a problem with the connection to Planning's API", ex);
            return new ResponseEntity<>(
                    "There was a problem with the connection to Planning's API. Try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/getRules/{ruleName}")
    public ResponseEntity<String> getRule(
            @PathVariable(name= "ruleName") String ruleName, 
            HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException {
        
        String safeRuleName = processRuleNameUnsafeChars(ruleName);
        
        URL url = new URL(urlBuilder(new HashMap(),"/getRules/" + safeRuleName));
        HttpURLConnection con;
        
        try {
            con = (HttpURLConnection) url.openConnection();
        
        
            //establish connection type and timeouts
            con.setRequestMethod("GET");
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);

            int status = con.getResponseCode();
            String responseData = getResponse(con,status);
            
            if(status >= 200 && status < 300){
                return new ResponseEntity<>(responseData,HttpStatus.valueOf(response.getStatus()));
            }
            else{
                LOGGER.error("Planning's API server responded with error to the request");
                return new ResponseEntity<>(responseData, HttpStatus.resolve(status));
            }
        }
        catch (IOException ex) {
            LOGGER.error("There was a problem with the connection to Planning's API", ex);
            return new ResponseEntity<>(
                    "There was a problem with the connection to Planning's API. Try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @PostMapping("/addRule")
    public ResponseEntity<String> addRule(@RequestBody String payload, HttpServletResponse response) {
        try {
            URL url = new URL(urlBuilder(new HashMap(),"/addRule"));
            HttpURLConnection con;
            con = (HttpURLConnection) url.openConnection();
            
            //establish connection type and timeouts
            con.setRequestMethod("POST");
            con.setConnectTimeout(2000);
            con.setReadTimeout(2000);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            
            //write body parameters
            try{
                writePostParamsString(payload,con);
            }catch(UnsupportedEncodingException ex){
                LOGGER.error("Could not convert params to param string to send on the request to Planning's API server", ex);
                return new ResponseEntity<>(
                "There was a problem establishing connection to Planning's API. Try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR);
            }catch(IOException ex){
                LOGGER.error("Could not write param string to send on the request to Planning's API server", ex);
                return new ResponseEntity<>(
                "There was a problem establishing connection to Planning's API. Try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            int status = con.getResponseCode();
            String responseData = getResponse(con,status);
            
            if(status >= 200 && status < 300){
                return new ResponseEntity<>(responseData,HttpStatus.valueOf(response.getStatus()));
            }
            else{
                LOGGER.error("Planning's API server responded with error to the request");
                return new ResponseEntity<>(responseData, HttpStatus.resolve(status));
            }
        } 
        catch (IOException ex) {
            LOGGER.error("There was a problem with the connection to Planning's API", ex);
            return new ResponseEntity<>(
                    "There was a problem with the connection to Planning's API. Try again later.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private void writePostParamsString(String params, HttpURLConnection con) 
      throws UnsupportedEncodingException, IOException{
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = params.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }
    
    private String getResponse(HttpURLConnection con, int statusCode) throws IOException{
        //Chose which input reader to use based on status code
        Reader streamReader;
        if( statusCode >= 200 && statusCode < 300){
            streamReader = new InputStreamReader(con.getInputStream());
        }
        else{
            streamReader = new InputStreamReader(con.getErrorStream());
        }
        
        //read input from the chosen 'channel'
        StringBuffer content;
        try (BufferedReader in = new BufferedReader(streamReader)) {
            String inputLine;
            content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        
        //close connection and return response data
        con.disconnect();
        return content.toString();
    }
    
    private String getParamsString(Map<String, String> params) throws UnsupportedEncodingException{
        //conver hashmap of parameters into a param string
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
          result.append("=");
          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
          result.append("&");
        }

        String resultString = result.toString();
        if(resultString.length() > 0){
            resultString = resultString.substring(0, resultString.length() - 1);
        }
        
        return resultString;
    }
    
    private String urlBuilder(Map<String,String> allRequestParams, String addingPath) throws UnsupportedEncodingException{
        String paramsString = getParamsString(allRequestParams);
        String url = planningAPI + addingPath;
        
        if(paramsString.length() > 0){
            url += "?" + paramsString;
        }
        return url;
    }
    
    private String processRuleNameUnsafeChars(String ruleName) throws UnsupportedEncodingException{
        String ruleNameSplitedBySpace[] = ruleName.split(" ");
        
        String safeRuleName = ruleNameSplitedBySpace[0];
        for(int i = 1; i < ruleNameSplitedBySpace.length; i++){
            //encode any additional URL unsafe characters
            //deal with spaces first, because URLEncoder converts spaces into '+' and that's not supported either by 
            //this API or planning's API
            safeRuleName += "%20" + URLEncoder.encode(ruleNameSplitedBySpace[i], "UTF-8");
        }
        return safeRuleName;
    }
}
