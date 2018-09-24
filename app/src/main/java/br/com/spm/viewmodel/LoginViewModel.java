package br.com.spm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.model.domain.User;
import br.com.spm.model.domain.UserResponse;
import br.com.spm.model.entity.UserEntity;
import br.com.spm.network.ResponseCallback;
import br.com.spm.repository.LoginRepository;
import br.com.zup.multistatelayout.MultiStateLayout;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;
    public LoginListener listener;
    private LiveData<UserEntity> userEntity;
    public ObservableBoolean showLoading = new ObservableBoolean();

    public LoginViewModel(@NonNull Application application, LoginListener listener, LoginRepository repository) {
        super(application);
        this.listener = listener;
        this.repository = repository;
    }

    @BindingAdapter("msl_state")
    public static void setState(MultiStateLayout multiStateLayout, MultiStateLayout.State state) {
        multiStateLayout.setState(state);
    }

    public void login(User user) {
        showLoading.set(true);
        repository.login(user, new ResponseCallback<UserResponse>() {
            @Override
            public void onSuccess(UserResponse response) {
                listener.onSuccess(response);
                showLoading.set(false);
            }

            @Override
            public void onError(ResponseRequest responseRequest) {
                listener.onError(responseRequest.getMessage());
                showLoading.set(false);
            }
        });
    }

    public void onEnter() {
        listener.onEnter();
    }

    public void onRegister() {
        listener.onRegister();
    }

    public interface LoginListener {
        void onEnter();

        void onSuccess(UserResponse response);

        void onError(String error);

        void onRegister();
    }
}
