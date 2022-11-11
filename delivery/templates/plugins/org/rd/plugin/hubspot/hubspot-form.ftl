<#import "/templates/system/common/crafter.ftl" as crafter />

<@crafter.div style='margin:10px'>
    <#if contentModel.hubspotForm_s?? && contentModel.hubspotForm_s!="">
      <#assign portalId = contentModel.hubspotForm_s?split("|")[0] />
      <#assign formId = contentModel.hubspotForm_s?split("|")[1] />      
      <script charset="utf-8" type="text/javascript" src="//js.hsforms.net/forms/embed/v2.js"></script>
      <script>
        hbspt.forms.create({
          region: "na1",
          portalId: "${portalId!''}",
          formId: "${formId!''}"
        });
      </script>
    <#else>
      Please choose a Hubspot form to display.
    </#if>
</@crafter.div>
