package br.com.spm.repository;

import android.os.AsyncTask;

import br.com.spm.model.dao.UserDAO;
import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.model.domain.User;
import br.com.spm.model.domain.UserResponse;
import br.com.spm.model.entity.UserEntity;
import br.com.spm.network.APIClient;
import br.com.spm.network.ResponseCallback;
import br.com.spm.network.RestCallback;
import br.com.spm.network.SPMService;

public class RegisterUserRepository extends BaseRepository {
    private UserDAO dao;
    private SPMService service;

    public RegisterUserRepository() {
        service = APIClient.createService(SPMService.class);
    }

    public void insert(UserEntity userEntity, RestCallback<UserEntity> restCallBack) {
        new InsertAsyncTask(dao, restCallBack).execute(userEntity);
    }

    public void registerUser(User user, final ResponseCallback<UserResponse> responseCallback) {
        doRequest(service.sendRegister(user), new RestCallback<UserResponse>() {
            @Override
            public void onSuccess(UserResponse response) {
                responseCallback.onSuccess(response);
            }

            @Override
            public void onError(ResponseRequest error) {
                responseCallback.onError(error);
            }
        });
    }

    private static class InsertAsyncTask extends AsyncTask<UserEntity, Void, UserEntity> {

        private UserDAO dao;
        private Exception exception;
        private RestCallback<UserEntity> restCallBack;

        public InsertAsyncTask(UserDAO dao, RestCallback<UserEntity> restCallBack) {
            this.dao = dao;
            this.restCallBack = restCallBack;
        }

        @Override
        protected UserEntity doInBackground(UserEntity... userEntities) {
            try {
                dao.insert(userEntities[0]);
            } catch (Exception exception) {
                this.exception = exception;
            }
            return null;
        }

        @Override
        protected void onPostExecute(UserEntity userEntities) {
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
}
