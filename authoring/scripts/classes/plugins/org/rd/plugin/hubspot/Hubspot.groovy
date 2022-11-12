package plugins.org.rd.plugin.hubspot

@Grab(group='io.github.http-builder-ng', module='http-builder-ng-core', version='1.0.4', initClass=false)

import groovy.json.JsonOutput
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

    /**
     * get a list of social channels
     */
    def getSocialChannels() {
        def result = hubspotGet("/broadcast/v1/channels/setting/publish/current")
        return result
    }

    /**
     * Create social post
     */
    def createSocialPost(channelId, content, photoUrl) {
        def payload = [:]
        payload.channelGuid = channelId
        payload.content = [:]
        payload.content.body = content

        if(photoUrl && photoUrl != "") {
            payload.content.photoUrl = photoUrl
        }

        def result = hubspotPost("/broadcast/v1/broadcasts", payload)

        return result
    }

    /**
     * Make a POST request to Hubspot
     * @param url - the API URL
     */
    def hubspotPost(url, payload) {
        def payloadAsJsonText = JsonOutput.toJson(payload)
 
        def apiUrl = "${HUBSPOT_API_ENDPOINT}${url}"
        def result = HttpBuilder.configure {  
                request.headers['Authorization'] = "Bearer "+hubspotPrivateAppToken
                request.contentType = "application/json" 
                request.body =  payloadAsJsonText
                request.raw = apiUrl 
        }.post()
        return result
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