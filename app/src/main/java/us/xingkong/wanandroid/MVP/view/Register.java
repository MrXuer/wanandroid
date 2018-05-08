package us.xingkong.wanandroid.MVP.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import us.xingkong.wanandroid.MVP.presenter.RegisterPresenter;
import us.xingkong.wanandroid.R;

public class Register extends AppCompatActivity implements RegisterContract.View {

    private CardView submit;
    private AppCompatEditText mUsername, mPassword, mRepassword;
    private ProgressBar mProgressBar;
    private RegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        new RegisterPresenter(this);
        mPresenter.start();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.register();
            }
        });
    }

    private void initView() {
        submit = findViewById(R.id.submit);
        mUsername = findViewById(R.id.register_username);
        mPassword = findViewById(R.id.register_password);
        mRepassword = findViewById(R.id.register_repassword);
        mProgressBar = findViewById(R.id.progressBar);
    }

    @Override
    public String getUserName() {
        return mUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPassword.getText().toString();
    }

    @Override
    public String getRepassword() {
        return mRepassword.getText().toString();
    }

    @Override
    public void setVisibility(int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    @Override
    public void setEnable(boolean enable) {
        mUsername.setEnabled(enable);
        mPassword.setEnabled(enable);
        mRepassword.setEnabled(enable);
        submit.setEnabled(enable);
    }

    @Override
    public void toOtherActivity() {
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
