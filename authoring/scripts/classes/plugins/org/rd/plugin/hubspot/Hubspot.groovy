package plugins.org.rd.plugin.hubspot

@Grab(group='io.github.http-builder-ng', module='http-builder-ng-core', version='1.0.4', initClass=false)

import groovy.json.JsonSlurper
import groovyx.net.http.HttpBuilder
import static groovyx.net.http.HttpBuilder.configure;



/**
 * API service wrapper for Hubspot
 */
public class Hubspot {

    def pluginConfig
    def hubspotPrivateAppToken
    def HUBSPOT_API_ENDPOINT = "https://api.hubapi.com"

   /**
     * constructor
     */
    Hubspot(pluginConfig) {
        this.pluginConfig = pluginConfig
        this.hubspotPrivateAppToken = pluginConfig.getString("hubspotPrivateAppToken")
    }

    /**
     * Get all available lead forms
     */
    def getForms() {
        def result = hubspotGet("/forms/v2/forms")
        return result
    }

    // /**
    //  * Make a PUT request to Hubspot
    //  * @param url - the API URL
    //  */
    // def hubspotPut(url) {
    //     def apiUrl = "https://api.trello.com${url}&key=${key}&token=${token}"        
    //     def result = HttpBuilder.configure {  headers.'Authorization' = "token $authToken" request.raw = apiUrl }.put()
    //     def object = [:] //(json && json != "") ? new JsonSlurper().parseText(result.text) : [:]
    //     return object
    // }

    /**
     * Make a POST request to Hubspot
     * @param url - the API URL
     */
    def hubspotPost(url) {
        def apiUrl = "https://api.trello.com${url}&key=${key}&token=${token}"
        def result = HttpBuilder.configure { request.raw = apiUrl }.post()
        def object = [:] //(json && json != "") ? new JsonSlurper().parseText(result.text) : [:]
        return object
    }

       /**
     * Make a PUT request to Hubspot
     * @param url - the API URL
     */
    def hubspotGet(url) {
        def apiUrl = "${HUBSPOT_API_ENDPOINT}${url}"
        def result = HttpBuilder.configure {  
                request.headers['Authorization'] = "Bearer "+hubspotPrivateAppToken 
                request.raw = apiUrl 
        }.get()
        return result
    }
}