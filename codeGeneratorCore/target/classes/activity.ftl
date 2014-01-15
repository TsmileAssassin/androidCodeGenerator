package ${viewEntity.packageName};

import android.app.Activity;
import android.os.Bundle;
<#list viewEntity.importSet as importName>
import ${importName};
</#list>

public class ${viewEntity.className} extends Activity <#if viewEntity.clickAble>implements View.OnClickListener </#if>{
    
<#list viewEntity.viewProperties as property>
    private ${property.type} ${property.name};
</#list>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.${viewEntity.resourceName});
        initView();
    }

    <#if viewEntity.clickAble>
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        <#list viewEntity.viewProperties as property>
        <#if property.clickAble>
        case R.id.${property.resourceName}:
            // TODO the code for click 
            break;
        </#if>
        </#list>
        default:
            break;
        }
    }
    </#if>
    private void initView() {
    <#list viewEntity.viewProperties as property>
    	<#if property.parentname?exists>
        ${property.name} = (${property.type}) ${property.parentname}.findViewById(R.id.${property.resourceName});
        <#else>
        ${property.name} = (${property.type}) findViewById(R.id.${property.resourceName});
        </#if>        
        <#if property.clickAble>
        ${property.name}.setOnClickListener(this);
        </#if>
    </#list>
    }
}
