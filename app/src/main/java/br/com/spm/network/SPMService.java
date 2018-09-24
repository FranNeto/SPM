package br.com.spm.network;

import br.com.spm.model.domain.ResponseEmpty;
import br.com.spm.model.domain.User;
import br.com.spm.model.domain.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SPMService {
    @POST("register")
    Call<UserResponse> sendRegister(@Body User user);

    @POST("login")
    Call<UserResponse> login(@Body User user);

    @GET("logo/")
    Call<ResponseEmpty> getLogo();
}
