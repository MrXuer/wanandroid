package us.xingkong.wanandroid.MVP.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import us.xingkong.wanandroid.MVP.presenter.LoginPresenter;
import us.xingkong.wanandroid.R;
import us.xingkong.wanandroid.bean.LoginBean;

public class Login extends AppCompatActivity implements LoginContract.View {

    private AppCompatEditText mUsername, mPassword;
    private CardView mLogin, mRegister;
    private CheckBox mCheckBox;
    private ProgressBar mProgressBar;
    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        new LoginPresenter(this);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void initView() {
        mUsername = findViewById(R.id.login_username);
        mPassword = findViewById(R.id.login_password);
        mLogin = findViewById(R.id.login);
        mRegister = findViewById(R.id.register);
        mCheckBox = findViewById(R.id.rememberPass);
        mProgressBar = findViewById(R.id.progressBar);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public CheckBox getCheckBox() {
        return mCheckBox;
    }

    @Override
    public AppCompatEditText getUsername() {
        return mUsername;
    }

    @Override
    public AppCompatEditText getPassword() {
        return mPassword;
    }

    @Override
    public void setVisibility(int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    @Override
    public void setEnable(boolean enable) {
        mUsername.setEnabled(enable);
        mPassword.setEnabled(enable);
        mLogin.setEnabled(enable);
        mRegister.setEnabled(enable);
    }

    @Override
    public void toOtherActivity(LoginBean.data data) {
        mPresenter.setCheckBox();
        Intent intent = new Intent(Login.this, Homepage.class);
        intent.putExtra("data", data);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
