package br.com.spm.di.component;

import android.app.Application;

import javax.inject.Singleton;

import br.com.spm.di.module.AppModule;
import br.com.spm.network.APIClient;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Application application();
}
