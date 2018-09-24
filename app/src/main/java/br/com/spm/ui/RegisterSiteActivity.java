package br.com.spm.ui;

import android.content.Intent;
import android.widget.Toast;

import javax.inject.Inject;

import br.com.spm.R;
import br.com.spm.SPMApplication;
import br.com.spm.databinding.ActivityRegisterSiteBinding;
import br.com.spm.di.component.DaggerGenericComponent;
import br.com.spm.di.module.GenericModule;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.network.NetworkConfig;
import br.com.spm.viewmodel.RegisterSiteViewModel;

import static br.com.spm.ui.HomeActivity.USER_DATA;

public class RegisterSiteActivity extends BaseActivity implements RegisterSiteViewModel.RegisterSiteListener {

    ActivityRegisterSiteBinding binding;

    @Inject
    RegisterSiteViewModel viewModel;
    private SiteEntity siteEntity;
    private boolean isUpdate;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register_site;
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
        binding = (ActivityRegisterSiteBinding) getDataBinding();
        binding.setSite(viewModel);
        getUser();
    }

    public void getUser() {
        if (isGetExtra(USER_DATA)) {
            siteEntity = (SiteEntity) getIntent().getExtras().getSerializable(USER_DATA);
            NetworkConfig.setAuthorization(siteEntity.getToken());
        }
    }

    public boolean isGetExtra(String extra) {
        return getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().get(extra) != null ? true : false;
    }

    @Override
    public void onBack() {
        if(isUpdate){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            onBackPressed();
        }

    }

    @Override
    public void onRegister() {
        siteEntity.setUrl(binding.editSite.getEditableText().toString());
        siteEntity.setName("Nome Fulano");
        if(siteEntity.getId() > 0){
            siteEntity.setName("Alterado Fulano");
            viewModel.update(siteEntity);
            isUpdate = true;
        }else{
            viewModel.insertUser(siteEntity);
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
