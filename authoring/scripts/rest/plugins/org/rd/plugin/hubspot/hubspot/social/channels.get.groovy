import plugins.org.rd.plugin.hubspot.Hubspot

def hubspot = new Hubspot(pluginConfig)

return hubspot.getSocialChannels()