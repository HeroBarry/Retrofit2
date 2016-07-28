package com.is.retrofit2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.is.retrofit2.tool.DefaultProgressListener;
import com.is.retrofit2.tool.RetrofitUtil;
import com.is.retrofit2.tool.UploadFileRequestBody;
import com.is.retrofit2.tool.UploadService;
import com.is.retrofit2.view.UploadImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    UploadImageView uploadImageView ;

    String picPath = Environment.getExternalStorageDirectory() + "/test.jpg"  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initViews();
    }

    private void initViews() {
        uploadImageView = (UploadImageView) findViewById(R.id.iv_image);
        uploadImageView.setImageBitmap(BitmapFactory.decodeFile(picPath));
    }

    public void uploadFile(View v) {
        File file = new File(picPath);
        UploadService uploadFileService = RetrofitUtil.createService(UploadService.class);
        Map<String, String> optionMap = new HashMap<>();
        optionMap.put("Platformtype", "Android");
        optionMap.put("userName","zhangsan") ;

        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, new DefaultProgressListener(mHandler,1));
        requestBodyMap.put("file\"; filename=\"" + file.getName(), fileRequestBody);

        uploadFileService.uploadFileInfo(optionMap, requestBodyMap).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("---the error is ---" + e);
                    }

                    @Override
                    public void onNext(ResponseBody s) {
                        try {
                            System.out.println("---the next string is --" + s.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    if (msg.what > 0) {
                        uploadImageView.updatePercent(msg.what);
                    }
                    break;
            }
        }
    };
}
