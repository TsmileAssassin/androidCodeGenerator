// generate code for params
<#list viewEntity.viewProperties as property>
    private ${property.type} ${property.name};
</#list>

// generate code for find view
<#list viewEntity.viewProperties as property>
    <#if property.parentname?exists>
    ${property.name} = (${property.type}) ${property.parentname}.findViewById(R.id.${property.resourceName});
    <#else>
    ${property.name} = (${property.type}) findViewById(R.id.${property.resourceName});
    </#if>
</#list>
