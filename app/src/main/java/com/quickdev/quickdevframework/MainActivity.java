package com.quickdev.quickdevframework;

import android.view.View;
import android.widget.Button;

import com.quickdev.baseframework.base.BaseActivity;
import com.quickdev.baseframework.base.BaseModel;
import com.quickdev.baseframework.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<BasePresenter, BaseModel> {
    private TestDialog mTestDialog;
    @BindView(R.id.bt_test)
    Button mTestBtn;
    @Override
    protected void setLayout() {
        setView(R.layout.activity_main, TYPE_HEADER);
        setHeaderBar("hello world");
    }

    @Override
    protected void processData() {
        mTestDialog = new TestDialog();
        mTestDialog.setShowBottomEnable(false);
        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestDialog.show(getSupportFragmentManager(), "testdialog");
            }
        });
    }

    @Override
    protected void onLeftButtonClick() {
        super.onLeftButtonClick();
        finish();
    }

    @OnClick(R.id.bt_test)
    public void onClickView(View view) {
        switch (view.getId()){
            case R.id.bt_test:
                if(!mTestDialog.getDialog().isShowing())
                    mTestDialog.show(getSupportFragmentManager(), "testdialog");
                else{
                    mTestDialog.dismiss();
                }
                break;
        }
    }
}
