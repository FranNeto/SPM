package br.com.spm.di.component;

import br.com.spm.di.module.GenericModule;
import br.com.spm.di.scope.Activity;
import br.com.spm.ui.DetailsSiteActivity;
import br.com.spm.ui.HomeActivity;
import br.com.spm.ui.LoginActivity;
import br.com.spm.ui.RegisterSiteActivity;
import br.com.spm.ui.RegisterUserActivity;
import dagger.Component;

@Activity
@Component(dependencies = AppComponent.class, modules = GenericModule.class)
public interface GenericComponent {
    void inject(HomeActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterUserActivity activity);

    void inject(RegisterSiteActivity activity);

    void inject(DetailsSiteActivity activity);
}
