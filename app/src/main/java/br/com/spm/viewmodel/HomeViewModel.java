package br.com.spm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import br.com.spm.model.entity.SiteEntity;
import br.com.spm.repository.HomeRepository;

public class HomeViewModel extends AndroidViewModel {

    private HomeListener listener;
    private HomeRepository repository;

    public HomeViewModel(Application application, HomeListener listener, HomeRepository repository) {
        super(application);
        this.listener = listener;
        this.repository = repository;
    }

    public LiveData<List<SiteEntity>> getLogo(){
        return repository.getLogoEntityAll();
    }

    public void onAdd(){
        listener.onAdd();
    }

    public interface HomeListener {
        void onBack();
        void onAdd();
    }
}
