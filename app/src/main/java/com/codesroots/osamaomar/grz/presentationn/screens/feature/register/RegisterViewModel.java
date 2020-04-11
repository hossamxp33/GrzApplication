package com.codesroots.osamaomar.grz.presentationn.screens.feature.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;

import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.datalayer.apidata.ServerGateway;
import com.codesroots.osamaomar.grz.models.entities.LoginResponse;
import com.codesroots.osamaomar.grz.models.entities.Register;
import com.codesroots.osamaomar.grz.models.entities.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class RegisterViewModel extends ViewModel {
    // TODO: Implement the ViewModel.

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Register> registerMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Throwable> errorinRegister = new MutableLiveData<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private ServerGateway serverGateway ;
    private Context context;

    RegisterViewModel(ServerGateway apiService1,Context con) {
        serverGateway = apiService1;
        context = con;
    }

    public void addUser(User user)
    {
        if (validate(user))
        {
            registerUser(user);
        }
    }

   public void userLogin(User user)
    {
        if (validateLogin(user))
        {
            loginUser(user);
        }
    }

    private void registerUser(User user) {
        mCompositeDisposable.add(
                serverGateway.userregister(user.getUsername(),user.getPassword(),
                            user.getMobile(),
                            user.getEmailverified())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::postDataResponseForRegister,
                                this::postError));
    }

    private void loginUser(User user) {
        mCompositeDisposable.add(
                serverGateway.userlogin(user.getUsername(),user.getPassword())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::postDataResponseForLogin,
                                this::postError));
    }


    private void postDataResponseForLogin(LoginResponse response) {
        loginResponseMutableLiveData.postValue(response);
    }

    private void postDataResponseForRegister(Register register) {
        registerMutableLiveData.postValue(register);
    }

    private void postError(Throwable throwable) {
        errorinRegister.postValue(throwable);
    }


    private boolean validate(User user) {
        if (user.getUsername().matches("")||user.getPassword().matches("")||
                user.getMobile().matches("")||user.getRepassword().matches("")) {
            errorMessage.postValue(context.getText(R.string.complete).toString());
            return false;
        }
        else if (!user.getPassword().matches(user.getRepassword())) {
            errorMessage.postValue(context.getText(R.string.passworsnotmatch).toString());
            return false;
        }
        else
            return true;
    }

    private boolean validateLogin(User user) {
        if (user.getUsername().matches("")||user.getPassword().matches("")) {
            errorMessage.postValue(context.getText(R.string.complete).toString());
            return false;
        }
        else
            return true;
    }
}
