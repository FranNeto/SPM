package br.com.spm.repository;

import android.os.AsyncTask;

import br.com.spm.SPMApplication;
import br.com.spm.model.dao.SiteDAO;
import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.network.ResponseCallback;
import br.com.spm.network.RestCallback;

public class RegisterSiteRepository extends BaseRepository {
    private SiteDAO dao;

    public RegisterSiteRepository() {
        dao = SPMApplication.getInstance().getDataBase().siteDAO();
    }

    public void insert(SiteEntity siteEntity, RestCallback<SiteEntity> restCallBack) {
        new InsertAsyncTask(dao, restCallBack).execute(siteEntity);
    }

    public void updateSite(SiteEntity siteEntity, ResponseCallback<SiteEntity> responseCallback) {
        new UpdateAsyncTask(dao, responseCallback).execute(siteEntity);
    }

    private static class InsertAsyncTask extends AsyncTask<SiteEntity, Void, SiteEntity> {

        private SiteDAO dao;
        private Exception exception;
        private RestCallback<SiteEntity> restCallBack;

        public InsertAsyncTask(SiteDAO dao, RestCallback<SiteEntity> restCallBack) {
            this.dao = dao;
            this.restCallBack = restCallBack;
        }

        @Override
        protected SiteEntity doInBackground(SiteEntity... userEntities) {
            try {
                dao.insert(userEntities[0]);
            } catch (Exception exception) {
                this.exception = exception;
            }
            return null;
        }

        @Override
        protected void onPostExecute(SiteEntity userEntities) {
            super.onPostExecute(userEntities);
            if (exception == null) {
                restCallBack.onSuccess(userEntities);
            } else {
                ResponseRequest responseRequest = new ResponseRequest();
                responseRequest.setMessage(exception.getMessage());
                restCallBack.onError(responseRequest);
            }
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<SiteEntity, Void, SiteEntity> {
        private SiteDAO dao;
        private Exception exception;
        private ResponseCallback<SiteEntity> restCallBack;

        UpdateAsyncTask(SiteDAO dao, ResponseCallback<SiteEntity> responseCallback) {
            this.dao = dao;
            this.restCallBack = responseCallback;
        }

        @Override
        protected SiteEntity doInBackground(final SiteEntity... params) {
            try {
                dao.updateSite(params[0]);
            } catch (Exception exception) {
                this.exception = exception;
            }

            return null;
        }

        @Override
        protected void onPostExecute(SiteEntity siteEntity) {
            super.onPostExecute(siteEntity);
            if (exception == null) {
                restCallBack.onSuccess(siteEntity);
            } else {
                ResponseRequest response = new ResponseRequest();
                response.setMessage(exception.getMessage());
                restCallBack.onError(response);
            }
        }
    }
}
