package br.com.spm.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.spm.R;
import br.com.spm.SPMApplication;
import br.com.spm.databinding.ActivityDetailsSiteBinding;
import br.com.spm.di.component.DaggerGenericComponent;
import br.com.spm.di.module.GenericModule;
import br.com.spm.model.entity.SiteEntity;
import br.com.spm.network.APIClient;
import br.com.spm.network.NetworkConfig;
import br.com.spm.viewmodel.DetailsSiteViewModel;

import static br.com.spm.ui.HomeActivity.USER_DATA;

public class DetailsSiteActivity extends BaseActivity implements DetailsSiteViewModel.DetailsSiteListener, PopupMenu.OnMenuItemClickListener {

    ActivityDetailsSiteBinding binding;

    @Inject
    DetailsSiteViewModel viewModel;

    private SiteEntity siteEntity;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_details_site;
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
        binding = (ActivityDetailsSiteBinding) getDataBinding();
        binding.setDetails(viewModel);
        initView();
    }

    public void initView() {
        getSite();
    }

    public void getSite() {
        if (isGetExtra(USER_DATA)) {
            siteEntity = (SiteEntity) getIntent().getExtras().getSerializable(USER_DATA);

            Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(NetworkConfig.getClient()))
                    .build();

            picasso.load(APIClient.API_BASE_URL + "logo/" + siteEntity.getUrl())
                    .placeholder(R.drawable.ic_site)
                    .into(binding.imageLogo);

            binding.textUrl.setText(siteEntity.getUrl());
            binding.textName.setText(siteEntity.getName());
            binding.textEmail.setText(siteEntity.getEmail());
            binding.textPassword.setText(siteEntity.getPassword());
        }
    }

    public boolean isGetExtra(String extra) {
        return getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().get(extra) != null ? true : false;
    }

    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void onMenu() {
        PopupMenu popup = new PopupMenu(this, binding.btMenu);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.details_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                viewModel.delete(siteEntity);
                return true;
            case R.id.action_edit:
                showRegisterSite();
                return true;
            default:
                return false;
        }
    }

    private void showRegisterSite(){
        Intent intent = new Intent(this, RegisterSiteActivity.class);
        intent.putExtra(HomeActivity.USER_DATA, siteEntity);
        startActivity(intent);
    }

    @Override
    public void onCopy() {
        ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        copy.setText(binding.textPassword.getEditableText());
        Toast.makeText(this, "Item copiado para área de transferência", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess() {
        onBackPressed();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
