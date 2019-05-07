package com.codesroots.osamaomar.Grz.presentationn.screens.feature.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.User;
import com.codesroots.osamaomar.Grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.home.mainfragment.MainFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.register.RegisterFragment;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.register.RegisterViewModel;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.register.RegisterViewModelFactory;

public class LoginFragment extends Fragment {

    private RegisterViewModel mViewModel;
    TextView loginbtn;
    EditText username,password;
    private User user = new User();
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    TextView gotoregister;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.login_fragment, container, false);
        mViewModel = ViewModelProviders.of(this,getViewModelFactory()).get(RegisterViewModel.class);

        loginbtn = view.findViewById(R.id.login);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        loginbtn.setOnClickListener(v -> {
            User user = new User();
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            mViewModel.userLogin(user);
        });

        mViewModel.loginResponseMutableLiveData.observe(this,response ->
                {
                    if (response.isSuccess())
                    {
                        PreferenceHelper.setUserId(response.getData().getId());
                        PreferenceHelper.setUserName(response.getData().getUsername());
                        PreferenceHelper.setToken(response.getData().getToken());
                        Toast.makeText(getActivity(),getText(R.string.hello)+" "+response.getData().getUsername(),Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MainFragment()).addToBackStack(null).commit();

                    }
                    else
                        Toast.makeText(getActivity(),getText(R.string.error_tryagani),Toast.LENGTH_SHORT).show();

                });

        mViewModel.errorinRegister.observe(this,throwable ->
                Toast.makeText(getActivity(),throwable.toString(),Toast.LENGTH_SHORT).show() );


        gotoregister = view.findViewById(R.id.gotoregister);
        gotoregister.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new RegisterFragment()).addToBackStack(null).commit());
        return  view;
    }


    @NonNull
    private ViewModelProvider.Factory getViewModelFactory() {
        return new RegisterViewModelFactory(getActivity().getApplication());
    }

}