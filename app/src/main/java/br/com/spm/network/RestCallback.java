package br.com.spm.network;

import br.com.spm.R;
import br.com.spm.SPMApplication;
import br.com.spm.model.domain.ResponseRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.spm.model.domain.ResponseRequest.UNEXPECTED;

public abstract class RestCallback<T extends Object> implements Callback<T> {
    public abstract void onSuccess(T response);

    public abstract void onError(ResponseRequest error);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            if (response.code() >= 500) {
                onError(ResponseRequest.getExceptionError());
            } else {
                ResponseRequest responseRequest = ResponseRequest.getResponseError(response.errorBody());
                if (responseRequest == null || responseRequest.getMessage() == null) {
                    responseRequest.setCode(UNEXPECTED);
                    responseRequest.setMessage(SPMApplication.getInstance().getString(R.string.default_error_msg));
                }
                onError(responseRequest);
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!SPMApplication.getInstance().isOnline()) {
            onError(ResponseRequest.getConnectionError());
        } else {
            onError(ResponseRequest.getExceptionError());
        }

    }
}
