package com.codesroots.osamaomar.grz.presentationn.screens.feature.chating;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codesroots.osamaomar.grz.datalayer.apidata.ServerGateway;
import com.codesroots.osamaomar.grz.models.entities.Addmessage;
import com.codesroots.osamaomar.grz.models.entities.ChatList;
import com.codesroots.osamaomar.grz.models.helper.PreferenceHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ChatViewModel extends ViewModel {

    public MutableLiveData<Throwable> error = new MutableLiveData<>();
    public MutableLiveData<ChatList> chatListMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Addmessage> addmessageMutableLiveData = new MutableLiveData<>();
    private ServerGateway serverGateway;
    private int TIME_TOLOAD_MESSAGES = 10;


    public ChatViewModel(ServerGateway serverGateway) {
        this.serverGateway = serverGateway;
        Observable.interval(TIME_TOLOAD_MESSAGES, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(Long aLong) {
                getChatData(PreferenceHelper.getUserId());
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {

            }
        });
    }

    public ChatViewModel() {
    }

//    @SuppressLint("CheckResult")
//    public void getChatList(int userid)
//    {
//        serverGateway.getChatList(userid).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(chatList -> chatListMutableLiveData.postValue(chatList),
//                    throwable -> error.postValue(throwable));
//    }


    @SuppressLint("CheckResult")
    public void getChatData(int userid)
    {
        serverGateway.getChatList(userid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chatList -> chatListMutableLiveData.postValue(chatList),
                        throwable -> error.postValue(throwable));
    }


    @SuppressLint("CheckResult")
    public void addmessge(int userid,String message)
    {
        Integer Sender;

        if (PreferenceHelper.getUSER_GROUP_ID() == 1 ){
            Sender = 2;
       }else {
            Sender = 1;
        }
        serverGateway.addmessageChat(userid,Sender,message).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addmessage -> addmessageMutableLiveData.postValue(addmessage),
                        throwable -> error.postValue(throwable));
    }

}
