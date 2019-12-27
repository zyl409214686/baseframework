package com.quickdev.quickdevframework;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpAPI {

    /**
     * trx 的转账记录
     *
     * @param address
     * @param asset_name
     * @param start
     * @param limit
     * @param sort
     * @return
//     */
//    @GET("/api/simple-transfer")
//    Observable<TransferOutput> getTRXTransfer(
//            @Query("address") String address,
//            @Query("to") String to,
//            @Query("from") String from,
//            @Query("asset_name") String asset_name,
//            @Query("token_id") String token_id,
//            @Query("start") int start,
//            @Query("limit") int limit,
//            @Query("sort") String sort);


    /**
     * Dapp弹窗接口
     */
    @GET("/dapphouseapp/activity&lan={lan}&address={address}")
    Observable<DappRecommendOutput> getDappRecommend(@Path("lan") String lan, @Path("address") String address);

    @GET("top250")
    Observable<String> getTop250 (@Query("start") int start , @Query("count") int count);
}
