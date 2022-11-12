@Grab(group='io.github.http-builder-ng', module='http-builder-ng-core', version='1.0.4', initClass=false)

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovyx.net.http.HttpBuilder
import static groovyx.net.http.HttpBuilder.configure;
import org.craftercms.deployer.api.ChangeSet
import javax.xml.xpath.*
import javax.xml.parsers.DocumentBuilderFactory

logger.info("Hubspot Social Publishing")

def repoBasePath = deployment.getParam("repoBasePath")
def studioUrl = deployment.getParam("studioUrl")
def authToken = deployment.getParam("studioApiToken")
def titleField = deployment.getParam("titleField")
def exerptField = deployment.getParam("exerptField")
def photoField = deployment.getParam("photoField")
def siteId = deployment.getParam("siteId")

originalChangeSet.updatedFiles.each{ filePath ->

    if(filePath.startsWith("/site") && filePath.endsWith(".xml")) {
        try {
            def xmlFile = new File(repoBasePath+filePath)

            def xpath       = XPathFactory.newInstance().newXPath()
            def builder     = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            def descriptior = builder.parse(xmlFile).documentElement

            def publishInstructionsEl = xpath.evaluate( 'publishToSocialChannels_o//item', descriptior, XPathConstants.NODESET )

            publishInstructionsEl.each { 
                try{
                    def objectId = xpath.evaluate( 'key/text()', it )

                    def channelGuid = xpath.evaluate( 'key/text()', it )
                    def channelName = xpath.evaluate( 'stringmv/text()', it )

                    // Get the content
                    def titleToPost = xpath.evaluate( "${titleField}/text()", descriptior, XPathConstants.NODESET ) 
                    def exerptToPost = xpath.evaluate( "${exerptField}/text()", descriptior, XPathConstants.NODESET ) 
                    def imageToPost = xpath.evaluate( "${photoField}/text()", descriptior, XPathConstants.NODESET ) 

                    def content = ""
                    content += (titleToPost)  ? (titleToPost.item(0).getNodeValue()) : ""
                    content += (exerptToPost) ? (exerptToPost.item(0).getNodeValue()) : ""
                    def image = imageToPost.item(0).getNodeValue()

                    logger.info("==============================================")
                    logger.info("Publish to channel (${channelName})") 
                    logger.info("contentItem: ${filePath}")               
                    logger.info("to: ${channelGuid}")
                    logger.info("content: ${content}")
                    logger.info("image: ${image}")
                    logger.info("==============================================")                

                    def apiUrl = "${studioUrl}/studio/api/2/plugin/script/plugins/org/rd/plugin/hubspot/hubspot/social/post.json?siteId=${siteId}"
                    def payload = "{ \"channelId\": \"${channelGuid}\", \"content\": \"${content}\", \"photoUrl\": \"${image}\" }"


                    def result = HttpBuilder.configure {  
                        request.headers['Authorization'] = "Bearer "+authToken
                        request.contentType = "application/json" 
                        request.body =  payload
                        request.raw = apiUrl 
                    }.post()
    
                }
                catch(processingSocialErr) {
                    logger.error("error publishing social for:"+filePath, processingSocialErr)
                }
            }
        }
        finally {
            try { xmlFile.close() } catch(e) { /* nothing to do */ }
        }


    }
}

return originalChangeSet