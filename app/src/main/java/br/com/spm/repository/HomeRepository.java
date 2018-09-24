package br.com.spm.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import br.com.spm.SPMApplication;
import br.com.spm.model.dao.SiteDAO;
import br.com.spm.model.entity.SiteEntity;

public class HomeRepository {
    private SiteDAO dao;

    public HomeRepository(){
        dao = SPMApplication.getInstance().getDataBase().siteDAO();
    }

    public LiveData<List<SiteEntity>> getLogoEntityAll(){
        return dao.getSiteEntityAll();
    }

}
