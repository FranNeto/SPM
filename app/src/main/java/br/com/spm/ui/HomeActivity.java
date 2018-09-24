package br.com.spm.ui;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import br.com.spm.R;
import br.com.spm.SPMApplication;
import br.com.spm.databinding.ActivityHomeBinding;
import br.com.spm.di.component.DaggerGenericComponent;
import br.com.spm.di.module.GenericModule;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.ui.adapter.HomeAdapter;
import br.com.spm.viewmodel.HomeViewModel;

public class HomeActivity extends BaseActivity implements HomeViewModel.HomeListener, HomeAdapter.HomeAdapterListener {

    public static final String USER_DATA = "user_data";
    ActivityHomeBinding binding;

    @Inject
    HomeViewModel viewModel;

    private HomeAdapter adapter;
    private SiteEntity siteEntity;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_home;
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
        binding = (ActivityHomeBinding) getDataBinding();
        binding.setHome(viewModel);
        getUser();
        initView();
    }

    public void initView() {
        adapter = new HomeAdapter(this);
        binding.recyclerHome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerHome.setAdapter(adapter);

        viewModel.getLogo().observe(this, new Observer<List<SiteEntity>>() {
            @Override
            public void onChanged(@Nullable List<SiteEntity> siteEntityList) {
                if (siteEntityList != null && siteEntityList.size() > 0) {
                    binding.recyclerHome.setVisibility(View.VISIBLE);
                    binding.linearEmpty.setVisibility(View.GONE);
                } else {
                    binding.linearEmpty.setVisibility(View.VISIBLE);
                    binding.recyclerHome.setVisibility(View.GONE);
                }
                adapter.setLogoEntity(siteEntityList);
            }
        });
    }

    public void getUser() {
        if (isGetExtra(USER_DATA)) {
            siteEntity = (SiteEntity) getIntent().getExtras().getSerializable(USER_DATA);
        }
    }

    public boolean isGetExtra(String extra) {
        return getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().get(extra) != null ? true : false;
    }

    @Override
    public void onBack() {

    }

    @Override
    public void onAdd() {
        Intent intent = new Intent(this, RegisterSiteActivity.class);
        intent.putExtra(USER_DATA, siteEntity);
        startActivity(intent);
    }

    @Override
    public void onSelectedItem(SiteEntity siteEntity) {
        Intent intent = new Intent(this, DetailsSiteActivity.class);
        intent.putExtra(USER_DATA, siteEntity);
        startActivity(intent);
    }

}
