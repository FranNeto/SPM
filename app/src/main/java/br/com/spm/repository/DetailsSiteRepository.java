package br.com.spm.repository;

import android.os.AsyncTask;

import br.com.spm.SPMApplication;
import br.com.spm.model.dao.SiteDAO;
import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.network.ResponseCallback;

public class DetailsSiteRepository {
    private SiteDAO dao;

    public DetailsSiteRepository() {
        this.dao = SPMApplication.getInstance().getDataBase().siteDAO();
    }

    public void deleteSite(SiteEntity siteEntity, ResponseCallback<SiteEntity> responseCallback) {
        new DeleteAsyncTask(dao, responseCallback).execute(siteEntity);
    }

    public void updateSite(SiteEntity siteEntity, ResponseCallback<SiteEntity> responseCallback) {
        new UpdateAsyncTask(dao, responseCallback).execute(siteEntity);
    }

    private static class DeleteAsyncTask extends AsyncTask<SiteEntity, Void, SiteEntity> {
        private SiteDAO dao;
        private Exception exception;
        private ResponseCallback<SiteEntity> restCallBack;

        DeleteAsyncTask(SiteDAO dao, ResponseCallback<SiteEntity> responseCallback) {
            this.dao = dao;
            this.restCallBack = responseCallback;
        }

        @Override
        protected SiteEntity doInBackground(final SiteEntity... params) {
            try {
                dao.deleteSite(params[0]);
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
