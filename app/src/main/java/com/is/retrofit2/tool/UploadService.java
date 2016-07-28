package com.is.retrofit2.tool;


import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by guoli on 2016/7/4.
 * 上传文件接口
 */
public interface UploadService {

    @POST("upload")
    @Multipart
    Observable<ResponseBody> uploadFileInfo(@QueryMap Map<String, String> options,
                                            @PartMap Map<String, RequestBody> externalFileParameters) ;

}
