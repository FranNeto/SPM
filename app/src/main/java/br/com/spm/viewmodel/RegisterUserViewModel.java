package br.com.spm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.model.domain.User;
import br.com.spm.model.domain.UserResponse;
import br.com.spm.network.ResponseCallback;
import br.com.spm.repository.RegisterUserRepository;
import br.com.zup.multistatelayout.MultiStateLayout;

public class RegisterUserViewModel extends AndroidViewModel {
    RegisterUserListener listener;
    private RegisterUserRepository repository;
    public ObservableBoolean showLoading = new ObservableBoolean();

    public RegisterUserViewModel(@NonNull Application application, RegisterUserListener listener, RegisterUserRepository repository) {
        super(application);
        this.listener = listener;
        this.repository = repository;
    }

    @BindingAdapter("msl_state")
    public static void setState(MultiStateLayout multiStateLayout, MultiStateLayout.State state) {
        multiStateLayout.setState(state);
    }

    public void registerUser(User user) {
        showLoading.set(true);
        repository.registerUser(user, new ResponseCallback<UserResponse>() {
            @Override
            public void onSuccess(UserResponse response) {
                showLoading.set(false);
                listener.onSuccess();
            }

            @Override
            public void onError(ResponseRequest responseRequest) {
                listener.onError(responseRequest.getMessage());
                showLoading.set(false);
            }
        });
    }

    public void onRegister() {
        listener.onRegister();
    }

    public void onBack() {
        listener.onBack();
    }

    public interface RegisterUserListener {
        void onBack();

        void onRegister();

        void onSuccess();

        void onError(String error);
    }
}
