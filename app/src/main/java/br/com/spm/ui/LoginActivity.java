package br.com.spm.ui;

import android.content.Intent;
import android.widget.Toast;

import javax.inject.Inject;

import br.com.spm.R;
import br.com.spm.SPMApplication;
import br.com.spm.databinding.ActivityLoginBinding;
import br.com.spm.di.component.DaggerGenericComponent;
import br.com.spm.di.module.GenericModule;
import br.com.spm.model.bo.SPMBO;
import br.com.spm.model.domain.User;
import br.com.spm.model.domain.UserResponse;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity implements LoginViewModel.LoginListener {

    ActivityLoginBinding binding;

    @Inject
    LoginViewModel viewModel;

    private User user;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjectors() {
        DaggerGenericComponent.builder()
                .appComponent(SPMApplication.getInstance().getAppComponent())
                .genericModule(new GenericModule(this))
                .build().inject(this);
    }

    @Override
    protected void initBinding() {
        binding = (ActivityLoginBinding) getDataBinding();
        binding.setLogin(viewModel);
        SPMBO.getInstance().setToken("");
    }

    @Override
    public void onEnter() {
        user = new User();
        user.setEmail(binding.editRegister.getEditableText().toString());
        user.setPassword(binding.editPassword.getEditableText().toString());

        if (user != null && user.getEmail() != null && user.getPassword() != null) {
            viewModel.login(user);
        } else {
            Toast.makeText(this, "Campos obrigatórios", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(UserResponse response) {

        SPMBO.getInstance().setToken(response.getToken());

        SiteEntity siteEntity = new SiteEntity();
        siteEntity.setEmail(user.getEmail());
        siteEntity.setPassword(user.getPassword());
        siteEntity.setToken(response.getToken());

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(HomeActivity.USER_DATA, siteEntity);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegister() {
        startActivity(new Intent(this, RegisterUserActivity.class));
    }
}
