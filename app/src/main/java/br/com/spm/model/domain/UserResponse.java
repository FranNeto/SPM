package br.com.spm.model.domain;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class UserResponse extends BaseObservable implements Serializable {
    private String type;
    private String token;

    public UserResponse(String type) {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
