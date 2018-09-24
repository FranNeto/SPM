package br.com.spm.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getContentLayoutId();
    protected abstract void initInjectors();
    protected abstract void initBinding();

    ViewDataBinding dataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, getContentLayoutId());
        initInjectors();
        initBinding();
    }

    public ViewDataBinding getDataBinding() {
        return dataBinding;
    }
}
