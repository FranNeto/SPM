package br.com.spm.network;

import br.com.spm.model.domain.ResponseRequest;

public interface ResponseCallback<T> {
    void onSuccess(T response);
    void onError(ResponseRequest responseRequest);
}
