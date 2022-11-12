# Hubspot Marketing Automation for CrafterCMS

- Embed Hubpot Forms
- Publish to social channels (Facebook, Twitter, etc) via Hubspot Social

# Installation

Install the plugin via Studio's Plugin Management UI under `Site Tools` > `Plugin Management`.
You will need the following information:
- Hubspot Private App Token 

# Generating API Keys for Hubspot

1. Go to the following URL to generate a Hubspot Private App Token 
https://app.hubspot.com/private-apps/{YOUR_HUB_ID}

# Publish to Hubpspot Social
This is an ptional publisher extension to publish to social channels via Hubspot

## CrafterCMS deployer application
Modify the deployer groovy sandbox rules or disable them: `DEPLOYER_HOME/config/application.yaml`
```
    scripting:
      sandbox:
        # Indicates if the sandbox should be enabled for all targets
        enabled: false
```
## Move the script processor to the deployer
`DEPLOYER_HOME/processors/scripts`

## Configure the processor in your target
```
      - processorName: scriptProcessor
        scriptPath: 'publish-social-processor.groovy'
        studioUrl: "http://localhost:8080"
        studioApiToken: "TOKENHERE"
        titleField: "title_t"
        exerptField: "blogPostExcerpt_html"
        photoField: "blogPostImage_s"
        repoBasePath: "/Users/rdanner/Desktop/demo-craftercms/data/repos/sites/sharefile/sandbox"
        siteId: "siteId"
```
