package com.nta.app.api;

import java.util.List;

import com.nta.app.model.Phone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {

    @GET("/api/phones")
    Call<List<Phone>> getPhones();

    @PUT("/api/phone/edit/{id}")
    Call<Phone> editPhone(@Path("id") String id, @Body Phone phone);

    @DELETE("/api/phone/delete/{id}")
    Call<Phone> deletePhone(@Path("id") String id);

}