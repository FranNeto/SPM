package br.com.spm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.network.ResponseCallback;
import br.com.spm.repository.DetailsSiteRepository;

public class DetailsSiteViewModel extends AndroidViewModel {

    DetailsSiteListener listener;
    private DetailsSiteRepository repository;

    public DetailsSiteViewModel(@NonNull Application application, DetailsSiteListener listener, DetailsSiteRepository repository) {
        super(application);
        this.listener = listener;
        this.repository = repository;
    }

    public void delete(SiteEntity siteEntity){
        repository.deleteSite(siteEntity, new ResponseCallback<SiteEntity>() {
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

    public void onBack(){
        listener.onBack();
    }

    public void onMenu(){
        listener.onMenu();
    }

    public void onCopy(){
        listener.onCopy();
    }

    public interface DetailsSiteListener {
        void onBack();

        void onMenu();

        void onCopy();

        void onSuccess();

        void onError(String message);
    }
}
