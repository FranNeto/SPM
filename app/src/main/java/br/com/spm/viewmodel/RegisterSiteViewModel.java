package br.com.spm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.network.ResponseCallback;
import br.com.spm.network.RestCallback;
import br.com.spm.repository.RegisterSiteRepository;

public class RegisterSiteViewModel extends AndroidViewModel {
    RegisterSiteListener listener;
    private RegisterSiteRepository repository;

    public RegisterSiteViewModel(@NonNull Application application, RegisterSiteListener listener, RegisterSiteRepository repository) {
        super(application);
        this.listener = listener;
        this.repository = repository;
    }

    public void insertUser(SiteEntity siteEntity){
        repository.insert(siteEntity, new RestCallback<SiteEntity>() {
            @Override
            public void onSuccess(SiteEntity response) {
                listener.onSuccess();
            }

            @Override
            public void onError(ResponseRequest error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public void update(SiteEntity siteEntity){
        repository.updateSite(siteEntity, new ResponseCallback<SiteEntity>() {
            @Override
            public void onSuccess(SiteEntity response) {
                listener.onSuccess();
            }

            @Override
            public void onError(ResponseRequest responseRequest) {
                listener.onError(responseRequest.getMessage());
            }
        });
    }

    public void onRegister(){
        listener.onRegister();
    }

    public void onBack(){
        listener.onBack();
    }

    public interface RegisterSiteListener {
        void onBack();
        void onRegister();
        void onSuccess();
        void onError(String error);
    }
}
