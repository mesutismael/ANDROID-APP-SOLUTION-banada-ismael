package me.esmael.newsnow.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import me.esmael.newsnow.R;
import me.esmael.newsnow.activities.MainActivity;
import me.esmael.newsnow.api.ApiHelper;
import me.esmael.newsnow.models.Article;
import me.esmael.newsnow.utils.DialogHelper;
import me.esmael.newsnow.utils.NavigationUtils;
import me.esmael.newsnow.utils.Observer;
import me.esmael.newsnow.utils.PreferenceHelper;


/**
 * Created by esmael256 on 4/11/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private MaterialDialog dialogProgress;
    private TextView textViewRegister;
    private Observer<Article> loginObserver = new Observer<Article>() {
        @Override
        public void onCompleted() {

            if (LoginFragment.this.dialogProgress != null) {
                LoginFragment.this.dialogProgress.dismiss();
            }

            LoginFragment.this.startNextActivity();
        }

        @Override
        public void onError(Throwable e) {
            Log.d("LoginFragment", e.getMessage());
      /*      if (LoginFragment.this.dialogProgress != null) {
                LoginFragment.this.dialogProgress.dismiss();
            }

            if (LoginFragment.this.getContext() != null && DialogHelper.canShowDialog(LoginFragment.this)) {
                LoginFragment.this.showLoginErrorDialog(LoginFragment.this.getContext());
            }*/

            LoginFragment.this.startNextActivity();
        }
    };

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        this.editTextUsername = (EditText) view.findViewById(R.id.editText_username);
        this.editTextPassword = (EditText) view.findViewById(R.id.editText_password);
        this.textViewRegister = (TextView) view.findViewById(R.id.register_textView);
        Button buttonLogIn = (Button) view.findViewById(R.id.button_logIn);
        buttonLogIn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.button_logIn:
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String email = this.editTextUsername.getText().toString();
                String password = this.editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    this.showIncompleteDialog(view.getContext());
                } else {
                    PreferenceHelper.savePreferenceEmail(LoginFragment.this.getContext(), email);
                    PreferenceHelper.savePreferencePassword(LoginFragment.this.getContext(),password);
                    this.showProgressDialog(view.getContext());
                    ApiHelper.logIn(view.getContext(), email, password).subscribe(this.loginObserver);
                }
                break;
            case R.id.register_textView:
                Toast.makeText(getContext(),"Registration here",Toast.LENGTH_LONG).show();
        }
    }

    private void showIncompleteDialog(Context context) {
        new MaterialDialog.Builder(context)
                .title(R.string.dialog_error)
                .content(R.string.login_incomplete_error)
                .positiveText(R.string.dialog_positive)
                .show();
    }

    private void showLoginErrorDialog(Context context) {
        new MaterialDialog.Builder(context)
                .title(R.string.dialog_error)
                .content(R.string.login_login_error)
                .positiveText(R.string.dialog_positive)
                .show();
    }

    private void showProgressDialog(Context context) {
        this.dialogProgress = new MaterialDialog.Builder(context)
                .content(R.string.login_progress)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    private void startNextActivity() {
        if (this.getContext() != null) {
            Intent intent = MainActivity.getIntent(this.getContext());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        }
    }


}

