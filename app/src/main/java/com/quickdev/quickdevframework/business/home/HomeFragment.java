package com.quickdev.quickdevframework.business.home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.quickdev.baseframework.base.BaseActivity;
import com.quickdev.baseframework.base.BaseFragment;
import com.quickdev.baseframework.base.EmptyModel;
import com.quickdev.baseframework.base.EmptyPresenter;
import com.quickdev.baseframework.base.EmptyView;
import com.quickdev.quickdevframework.MainActivity;
import com.quickdev.quickdevframework.R;
import com.quickdev.quickdevframework.utils.permission.PermissionHelper;
import com.quickdev.quickdevframework.utils.permission.PermissionInterface;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Description : 家庭页面
 * Created by 宋盼想 on 2020/1/7.
 */
public class HomeFragment extends BaseFragment<EmptyPresenter, EmptyModel> implements EmptyView, PermissionInterface {
    @BindView(R.id.tv_getpermission)
    TextView tvGetpermission;
    private PermissionHelper mPermissionHelper;


    @Override
    public int getLayoutResId() {
        return R.layout.fg_home;
    }

    @Override
    public BaseActivity.HEADER_TYPE getHeaderType() {
        return BaseActivity.HEADER_TYPE.TYPE_NORMAL;
    }

    @Override
    public boolean isRegisteredEventBus() {
        return false;
    }

    @Override
    public void setContentViewAfter(Bundle savedInstanceState) {

    }


    @OnClick(R.id.tv_getpermission)
    public void onViewClicked() {
//        mPermissionHelper = new PermissionHelper(mContext, this);
//        mPermissionHelper.requestPermissions();
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public int getPermissionsRequestCode() {
        return 2001;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{
                Manifest.permission.CAMERA,
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        Log.i("xiangxiang", "requestPermissionsSuccess");

    }

    @Override
    public void requestPermissionsFail() {
        Log.i("xiangxiang", "requestPermissionsFail");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        try {
            if (mPermissionHelper == null) {
                mPermissionHelper = new PermissionHelper(mContext, this);
            }
            if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
                return;
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } catch (Exception e) {
            Log.i("xiangxiang", "e -- " + e.toString());
        }
    }
}
