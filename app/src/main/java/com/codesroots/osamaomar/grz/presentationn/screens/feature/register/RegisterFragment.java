package com.codesroots.osamaomar.grz.presentationn.screens.feature.register;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.models.entities.User;
import com.codesroots.osamaomar.grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainfragment.MainFragment;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;

    TextView registerbtn;
    EditText username,password,repassword,phone;
    private User user = new User();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this,getViewModelFactory()).get(RegisterViewModel.class);

        View  view = inflater.inflate(R.layout.register_fragment, container, false);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        repassword = view.findViewById(R.id.repassword);
        phone = view.findViewById(R.id.phone);
        registerbtn = view.findViewById(R.id.register);
        registerbtn.setOnClickListener(v -> {
            User  user = new User();
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.setRepassword(repassword.getText().toString());
            user.setMobile(phone.getText().toString());
            mViewModel.addUser(user);
        });

        mViewModel.errorMessage.observe(this,s ->
                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show());

        mViewModel.registerMutableLiveData.observe(this,register ->
                {
                 if (register.isSuccess()) {

                     PreferenceHelper.setUserId(register.getData().getId());
                     PreferenceHelper.setUserName(register.getData().getUsername());
                     PreferenceHelper.setToken(register.getData().getToken());
                     Toast.makeText(getActivity(),getText(R.string.registersuccess),Toast.LENGTH_SHORT).show();
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @NonNull
    private ViewModelProvider.Factory getViewModelFactory() {
        return new RegisterViewModelFactory(getActivity().getApplication());
    }

}
