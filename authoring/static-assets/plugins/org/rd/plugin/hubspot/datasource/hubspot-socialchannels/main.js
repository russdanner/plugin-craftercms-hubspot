CStudioForms.Datasources.HubspotSocialChannels = CStudioForms.Datasources.HubspotSocialChannels ||
    function(id, form, properties, constraints)  {
        this.id = id;
        this.form = form;
        this.properties = properties;
        this.constraints = constraints;
        this.callbacks = [];
        var _self = this;
    

        var callback = { 
            success: function(response) {
               
                console.log(response.response.result);
                var hubspotChannels = response.response.result

                var dataStruct = [];

                if(hubspotChannels.length!=0) {
                    for(var i=0; i<hubspotChannels.length; i++) {

                        var hsKey = hubspotChannels[i].channelGuid;
                        var hsName = hubspotChannels[i].channelType;

                        dataStruct[i] = { key: hsKey, value: hsName };
                    }
                }
            
                _self.list = dataStruct;

            console.log("xxxxxxxxxxxxxx")            
            console.log(dataStruct)

                for(var j=0; j<_self.callbacks.length; j++) {
                    _self.callbacks[j].success(_self.list);
                }
            },
            failure: function() {
            }
        };

        CrafterCMSNext.util.ajax.get("/studio/api/2/plugin/script/plugins/org/rd/plugin/hubspot/hubspot/social/channels?siteId="+CStudioAuthoringContext.site).subscribe(
          function (response) {
            callback.success(response);
          },
          function (response) {
            callback.failure(response);
          });

        return this;
    }

YAHOO.extend(CStudioForms.Datasources.HubspotSocialChannels, CStudioForms.CStudioFormDatasource, {

    getLabel: function() {
        return CMgs.format(langBundle, "Hubspot Social Channels");
    },

    getList: function(cb) {
        if(!this.list) {
            this.callbacks[this.callbacks.length] = cb;
        }
        else {
            cb.success(this.list);
        }
    },

    getInterface : function() {
        return "item";
    },

    /*
     * Datasource controllers don't have direct access to the properties controls, only to their properties and their values.
     * Because the property control (dropdown) and the dataType property share the property value, the dataType value must stay
     * as an array of objects where each object corresponds to each one of the options of the control. In order to know exactly
     * which of the options in the control is currently selected, we loop through all of the objects in the dataType value 
     * and check their selected value.
     */
    getDataType : function getDataType () {
        return "string";
    },

    getName: function() {
        return "hubspot-socialchannels";
    },

    getSupportedProperties: function() {
        return [];
    },

    getSupportedConstraints: function() {
        return [
            { label: CMgs.format(langBundle, "required"), name: "required", type: "boolean" }
        ];
    }

});

CStudioAuthoring.Module.moduleLoaded("hubspot-socialchannels", CStudioForms.Datasources.HubspotSocialChannels);