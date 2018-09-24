package br.com.spm.ui;

import android.widget.Toast;

import javax.inject.Inject;

import br.com.spm.R;
import br.com.spm.SPMApplication;
import br.com.spm.databinding.ActivityRegisterUserBinding;
import br.com.spm.di.component.DaggerGenericComponent;
import br.com.spm.di.module.GenericModule;
import br.com.spm.model.domain.User;
import br.com.spm.utils.Utils;
import br.com.spm.viewmodel.RegisterUserViewModel;

public class RegisterUserActivity extends BaseActivity implements RegisterUserViewModel.RegisterUserListener {

    ActivityRegisterUserBinding binding;

    @Inject
    RegisterUserViewModel viewModel;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register_user;
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
        binding = (ActivityRegisterUserBinding) getDataBinding();
        binding.setRegister(viewModel);
    }

    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void onRegister() {
        User user = new User();
        user.setName(binding.editName.getEditableText().toString());
        user.setEmail(binding.editEmail.getEditableText().toString());
        user.setPassword(binding.editPassword.getEditableText().toString());
        if (Utils.regexNumberStringCharactersSpecial(user.getPassword()) && user.getPassword().length() >= 8) {
            viewModel.registerUser(user);
        } else {
            Toast.makeText(this, "Senha incorreta", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
        onBack();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
