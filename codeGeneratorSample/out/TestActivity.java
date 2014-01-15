package com.example.codeGenerator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TestActivity extends Activity implements View.OnClickListener {
    
    private LinearLayout mSetLayout;
    private Button mSetTest1;
    private Button mSetTest2;
    private EditText mParams;
    private Button mCallFunc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.set_test1:
            // TODO the code for click 
            break;
        case R.id.set_test2:
            // TODO the code for click 
            break;
        case R.id.call_func:
            // TODO the code for click 
            break;
        default:
            break;
        }
    }
    private void initView() {
        mSetLayout = (LinearLayout) findViewById(R.id.set_layout);
        mSetTest1 = (Button) mSetLayout.findViewById(R.id.set_test1);
        mSetTest1.setOnClickListener(this);
        mSetTest2 = (Button) mSetLayout.findViewById(R.id.set_test2);
        mSetTest2.setOnClickListener(this);
        mParams = (EditText) findViewById(R.id.params);
        mCallFunc = (Button) findViewById(R.id.call_func);
        mCallFunc.setOnClickListener(this);
    }
}
