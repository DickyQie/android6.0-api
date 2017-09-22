package com.zhangqie.jurisdictionrxpermission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    RxPermissions rxPermissions ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        rxPermissions= new RxPermissions(this);
    }
    private void initView(){
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                //检查版本是否大于M
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                   //单个权限

                    rxPermissions.request(Manifest.permission.CAMERA)
                            .subscribe(new Observer<Boolean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }
                                @Override
                                public void onNext(Boolean value) {
                                    if(value){
                                        showToast("同意权限");
                                    }else {
                                        showToast("拒绝权限");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                break;
            case R.id.btn2:
                    rxPermissions
                        .requestEach(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE)
                        .subscribe(new Observer<Permission>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Permission permission) {

                                if (permission.name.equals(Manifest.permission.CAMERA)){
                                    showToast("申请成功");
                                }
                            }
                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.btn3:
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 1);
                break;
            case R.id.btn4:
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + "10086");
                intent.setData(data);
                startActivity(intent);
                break;
        }
    }
    private void showToast(String string){
        Toast.makeText(MainActivity.this,string,Toast.LENGTH_LONG).show();
    }
}
