import groovy.json.JsonSlurper
import plugins.org.rd.plugin.hubspot.Hubspot

def toParse = request.reader.text

def jsonSlurper = new JsonSlurper()
def payload = jsonSlurper.parseText(toParse)

def hubspot = new Hubspot(pluginConfig)

return hubspot.createSocialPost(payload.channelId, payload.content, payload.photoUrl)