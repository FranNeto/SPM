package br.com.spm.repository;

import br.com.spm.model.domain.ResponseRequest;
import br.com.spm.model.domain.User;
import br.com.spm.model.domain.UserResponse;
import br.com.spm.network.APIClient;
import br.com.spm.network.ResponseCallback;
import br.com.spm.network.RestCallback;
import br.com.spm.network.SPMService;

public class LoginRepository extends BaseRepository{

    private SPMService service;

    public LoginRepository(){
        service = APIClient.createService(SPMService.class);
    }

    public void login(User user,  final ResponseCallback<UserResponse> responseCallback) {
        doRequest(service.login(user), new RestCallback<UserResponse>() {
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

}
