package com.codesroots.osamaomar.Grz.presentationn.screens.feature.chating;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.codesroots.osamaomar.Grz.models.entities.chatmessages;
import okhttp3.MultipartBody;


public class ChatViewModel extends ViewModel {

//    private ChatingRepository chatingRepository;
//    public MutableLiveData<chatmessages> chatMessages = new MutableLiveData<chatmessages>();
//    public MutableLiveData<ChhatList> chatList = new MutableLiveData<ChhatList>();
//    MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();
//    MutableLiveData<Throwable> errorchatListLiveData = new MutableLiveData<>();
//    MutableLiveData<Boolean> loading = new MutableLiveData<>();
//    public MutableLiveData<Boolean> addMessageLiveData = new MutableLiveData<>();
//    public MutableLiveData<AddMessage> addMessageLiveDataValue = new MutableLiveData<>();
//    public MutableLiveData<SeenResponse> seenLD = new MutableLiveData<>();

    public ChatViewModel() {
    }

//    public ChatViewModel(final ChatingRepository repository) {
//
//        repository.setOnSuccess(chatmessages -> {
//            chatMessages.postValue(chatmessages);
//            loading.postValue(false);
//        });
//
//
//        repository.setOnSuccessChatList(chatList1  -> {
//            chatList.postValue(chatList1);
//            loading.postValue(false);
//        });
//
//        repository.setOnError(throwable -> {
//            errorLiveData.postValue(throwable);
//            loading.postValue(false);
//        });
//
//
//        repository.setOnErrorChatList(throwable -> {
//            errorchatListLiveData.postValue(throwable);
//            loading.postValue(false);
//        });
//
//        repository.setOnErrorChatList(throwable -> {
//            errorchatListLiveData.postValue(throwable);
//            loading.postValue(false);
//        });
//
//        repository.setOnSuccessAdd(aBoolean  -> {
//            addMessageLiveData.postValue(aBoolean);
//        });
//
//        repository.setOnSuccessAddValue(addMessage   -> {
//            addMessageLiveDataValue.postValue(addMessage);
//        });
//        repository.setOnSuccessSeen(seenResponse -> {
//            seenLD.postValue(seenResponse);
//        });
//
//
//        this.chatingRepository = repository;
//    }
//
//    public void addMessaege(int userto,int userFrom  ,String roomId,String mesg, MultipartBody.Part part)
//    {
//        chatingRepository.addMessage(roomId ,userto,userFrom,mesg,part);
//    }
//
//    public void getChatData(String roomId/*int user,int to*/ ,int page)
//    {
//        chatingRepository.chatMessages(roomId ,page);
//    }
//

}
