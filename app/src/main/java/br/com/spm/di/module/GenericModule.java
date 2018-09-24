package br.com.spm.di.module;

import android.app.Application;

import br.com.spm.di.scope.Activity;
import br.com.spm.repository.DetailsSiteRepository;
import br.com.spm.repository.HomeRepository;
import br.com.spm.repository.LoginRepository;
import br.com.spm.repository.RegisterSiteRepository;
import br.com.spm.repository.RegisterUserRepository;
import br.com.spm.viewmodel.DetailsSiteViewModel;
import br.com.spm.viewmodel.HomeViewModel;
import br.com.spm.viewmodel.LoginViewModel;
import br.com.spm.viewmodel.RegisterSiteViewModel;
import br.com.spm.viewmodel.RegisterUserViewModel;
import dagger.Module;
import dagger.Provides;

@Module
public class GenericModule {

    RegisterUserViewModel.RegisterUserListener registerListener;
    RegisterSiteViewModel.RegisterSiteListener registerSiteListener;
    LoginViewModel.LoginListener loginListener;
    HomeViewModel.HomeListener listener;
    DetailsSiteViewModel.DetailsSiteListener detailsSiteListener;


    public GenericModule(HomeViewModel.HomeListener listener) {
        this.listener = listener;
    }

    public GenericModule(LoginViewModel.LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public GenericModule(RegisterUserViewModel.RegisterUserListener registerListener) {
        this.registerListener = registerListener;
    }

    public GenericModule(RegisterSiteViewModel.RegisterSiteListener registerSiteListener) {
        this.registerSiteListener = registerSiteListener;
    }

    public GenericModule(DetailsSiteViewModel.DetailsSiteListener detailsSiteListener){
        this.detailsSiteListener = detailsSiteListener;
    }

    @Provides
    @Activity
    HomeViewModel provideHomeViewModel() {
        return new HomeViewModel(new Application(), this.listener, new HomeRepository());
    }

    @Provides
    @Activity
    LoginViewModel provideLoginViewModel() {
        return new LoginViewModel(new Application(), this.loginListener, new LoginRepository());
    }

    @Provides
    @Activity
    RegisterUserViewModel provideRegisterUserViewModel() {
        return new RegisterUserViewModel(new Application(), this.registerListener, new RegisterUserRepository());
    }

    @Provides
    @Activity
    RegisterSiteViewModel provideRegisterSiteViewModel() {
        return new RegisterSiteViewModel(new Application(), this.registerSiteListener, new RegisterSiteRepository());
    }

    @Provides
    @Activity
    DetailsSiteViewModel provideDetailsSiteViewModel() {
        return new DetailsSiteViewModel(new Application(), this.detailsSiteListener, new DetailsSiteRepository());
    }
}
