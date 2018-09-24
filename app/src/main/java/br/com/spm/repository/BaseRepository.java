package br.com.spm.repository;

import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.network.RestCallback;
import retrofit2.Call;

public class BaseRepository {

    public ObservableBoolean showLoading = new ObservableBoolean();
    private List<Call> mCalls;

    public <T> void doRequest(final Call call, final RestCallback<T> restCallback) {

        if (mCalls == null)
            mCalls = new ArrayList<>();

        mCalls.add(call);
        call.enqueue(new RestCallback<T>() {
            @Override
            public void onSuccess(T response) {
                mCalls.remove(call);
                showLoading.set(false);
                restCallback.onSuccess(response);
            }

            @Override
            public void onError(ResponseRequest error) {
                mCalls.remove(call);
                showLoading.set(false);
                restCallback.onError(error);
            }
        });
    }
}
