package com.codesroots.osamaomar.grz.presentationn.screens.feature.payment;

import android.app.Activity;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.models.entities.OrderModel;
import com.codesroots.osamaomar.grz.models.helper.PreferenceHelper;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.confirmorder.FinishOrderFragment;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainactivity.MainActivity;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainfragment.MainFragment;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import company.tap.gosellapi.GoSellSDK;
import company.tap.gosellapi.internal.api.callbacks.GoSellError;
import company.tap.gosellapi.internal.api.models.Authorize;
import company.tap.gosellapi.internal.api.models.Charge;
import company.tap.gosellapi.internal.api.models.PhoneNumber;
import company.tap.gosellapi.internal.api.models.Token;
import company.tap.gosellapi.open.buttons.PayButtonView;
import company.tap.gosellapi.open.controllers.SDKSession;
import company.tap.gosellapi.open.controllers.ThemeObject;
import company.tap.gosellapi.open.delegate.SessionDelegate;
import company.tap.gosellapi.open.enums.AppearanceMode;
import company.tap.gosellapi.open.enums.TransactionMode;
import company.tap.gosellapi.open.models.CardsList;
import company.tap.gosellapi.open.models.Customer;
import company.tap.gosellapi.open.models.PaymentItem;
import company.tap.gosellapi.open.models.TapCurrency;
import company.tap.gosellapi.open.models.Tax;

import static android.app.Activity.RESULT_OK;
import static com.codesroots.osamaomar.grz.models.entities.names.ORDER;

public class PaymentFragment extends Fragment  implements SessionDelegate {

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(Config.PAYPAL_CLIENT_ID);
    ImageView paypal, cash;
    OrderModel orderModel;
    float Total;
    PaymentViewModel paymentViewModel;
    String mAuthorization = "sandbox_tgg4p67v_6vxjwdzh2zj4q267";
    private BraintreeFragment mBraintreeFragment;
    private final int SDK_REQUEST_CODE = 1001;

    private SDKSession sdkSession;
    private PayButtonView payButtonView;
    private ProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_paypal, container, false);
        paypal = view.findViewById(R.id.paypal);
        payButtonView = view.findViewById(R.id.payButtonId);

        // cash = view.findViewById(R.id.cash);
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        getActivity().startService(intent);
        orderModel = (OrderModel) getArguments().getSerializable(ORDER);
        assert orderModel != null;
        orderModel.setUser_id(PreferenceHelper.getUserId());

        for (int i = 0; i < orderModel.getOrderdetails().size(); i++)
            Total += Float.valueOf(orderModel.getOrderdetails().get(i).getTotal());

        paypal.setOnClickListener(v -> processpayment());

        GoSellSDK.init(getContext(), "sk_live_Ncyx24AohFnUpi3uZ0mSjTtR","com.codesroots.Grz");

    // to be replaced by merchant
        startSDK();

//        cash.setOnClickListener(v -> processCashpayment());
        paymentViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(PaymentViewModel.class);
        paymentViewModel.myOrdersMutableLiveData.observe(this, aBoolean -> {
            if (aBoolean) {
                paymentViewModel.cleareCart();
                ((MainActivity)getActivity()).onClearCart();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for (int i1 = 0; i1 < fm.getBackStackEntryCount(); ++i1) {
                    Fragment fragment = fm.findFragmentById(R.id.fragment);
                    if (!(fragment instanceof MainFragment))
                        fm.popBackStack();
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                        new FinishOrderFragment()).addToBackStack(null).commit();
            }
        });
      //  processCashpayment();

        paymentViewModel.throwableMutableLiveData.observe(this, throwable -> Snackbar.make(view, throwable.getCause().toString(), Snackbar.LENGTH_SHORT).show());
        return view;
    }
    private void initPayButton() {


        payButtonView.setupFontTypeFace(ThemeObject.getInstance().getPayButtonFont());

        payButtonView.setupTextColor(ThemeObject.getInstance().getPayButtonEnabledTitleColor(),
                ThemeObject.getInstance().getPayButtonDisabledTitleColor());
        //
        payButtonView.getPayButton().setTextSize(ThemeObject.getInstance().getPayButtonTextSize());
        //
        payButtonView.getSecurityIconView().setVisibility(ThemeObject.getInstance().isPayButtSecurityIconVisible()?View.VISIBLE:View.INVISIBLE);

        payButtonView.setBackgroundSelector(ThemeObject.getInstance().getPayButtonResourceId());

        if(sdkSession!=null){
            TransactionMode trx_mode = sdkSession.getTransactionMode();
            if(trx_mode!=null){

                if (TransactionMode.SAVE_CARD == trx_mode  || TransactionMode.SAVE_CARD_NO_UI ==trx_mode) {
                    payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.save_card));
                }
                else if(TransactionMode.TOKENIZE_CARD == trx_mode || TransactionMode.TOKENIZE_CARD_NO_UI == trx_mode){
                    payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.tokenize));
                }
                else {
                    payButtonView.getPayButton().setText(getString(company.tap.gosellapi.R.string.pay));
                }
            }else{
                configureSDKMode();
            }
            sdkSession.setButtonView(payButtonView, getActivity(), SDK_REQUEST_CODE);
        }


    }
    private void configureSDKMode(){

        /**
         * You have to choose only one Mode of the following modes:
         * Note:-
         *      - In case of using PayButton, then don't call sdkSession.start(this); because the SDK will start when user clicks the tap pay button.
         */
        //////////////////////////////////////////////////////    SDK with UI //////////////////////
        /**
         * 1- Start using  SDK features through SDK main activity (With Tap CARD FORM)
         */
        startSDKWithUI();

        //////////////////////////////////////////////////////    SDK Tokenization without UI //////////////////////
        /**
         * 2- Start using  SDK to tokenize your card without using SDK main activity (Without Tap CARD FORM)
         * After the SDK finishes card tokenization, it will notify this activity with tokenization result in either
         * cardTokenizedSuccessfully(@NonNull String token) or sdkError(@Nullable GoSellError goSellError)
         */
//          startSDKTokenizationWithoutUI();

        //////////////////////////////////////////////////////    SDK Saving card without UI //////////////////////
        /**
         *  3- Start using  SDK to save your card without using SDK main activity ((Without Tap CARD FORM))
         *  After the SDK finishes card tokenization, it will notify this activity with save card result in either
         *  cardSaved(@NonNull Charge charge) or sdkError(@Nullable GoSellError goSellError)
         *
         */
       //  startSDKSavingCardWithoutUI();
    }
    private void startSDKWithUI(){
        if(sdkSession!=null){
//            TransactionMode trx_mode =(settingsManager!=null)? settingsManager.getTransactionsMode("key_sdk_transaction_mode"): TransactionMode.PURCHASE;
//            // set transaction mode [TransactionMode.PURCHASE - TransactionMode.AUTHORIZE_CAPTURE - TransactionMode.SAVE_CARD - TransactionMode.TOKENIZE_CARD ]
//            sdkSession.setTransactionMode(trx_mode);    //** Required **
//            // if you are not using tap button then start SDK using the following call
//            //sdkSession.start(this);
            TransactionMode trx_mode = TransactionMode.PURCHASE;
            // set transaction mode [TransactionMode.PURCHASE - TransactionMode.AUTHORIZE_CAPTURE - TransactionMode.SAVE_CARD - TransactionMode.TOKENIZE_CARD ]
            sdkSession.setTransactionMode(trx_mode);    //** Required **
        }
    }
    private void configureSDKSession() {

        // Instantiate SDK Session
        if(sdkSession==null) sdkSession = new SDKSession();   //** Required **

        // pass your activity as a session delegate to listen to SDK internal payment process follow
        sdkSession.addSessionDelegate(this);    //** Required **

        // initiate PaymentDataSource
        sdkSession.instantiatePaymentDataSource();    //** Required **

        // set transaction currency associated to your account
        sdkSession.setTransactionCurrency(new TapCurrency("OMR"));    //** Required **

        // Using static CustomerBuilder method available inside TAP Customer Class you can populate TAP Customer object and pass it to SDK
        sdkSession.setCustomer(getCustomer());    //** Required **

        // Set Total Amount. The Total amount will be recalculated according to provided Taxes and Shipping
        sdkSession.setAmount(new BigDecimal(Total));  //** Required **
        sdkSession.isUserAllowedToSaveCard(false); //  ** Required ** you can pass boolean

        sdkSession.isRequires3DSecure(true);

        /**
         * Use this method where ever you want to show TAP SDK Main Screen.
         * This method must be called after you configured SDK as above
         * This method will be used in case of you are not using TAP PayButton in your activity.
         */
        sdkSession.start(getActivity());
    }
    private void startSDK(){
        /**
         * Required step.
         * Configure SDK with your Secret API key and App Bundle name registered with tap company.
         */


        /**
         * Optional step
         * Here you can configure your app theme (Look and Feel).
         */
        configureSDKThemeObject();

        /**
         * Required step.
         * Configure SDK Session with all required data.
         */
        configureSDKSession();

        /**
         * Required step.
         * Choose between different SDK modes
         */
        configureSDKMode();

        /**
         * If you included Tap Pay Button then configure it first, if not then ignore this step.
         */
        initPayButton();

    }
    private Customer getCustomer() { // test customer id cus_Kh1b4220191939i1KP2506448



        return new Customer.CustomerBuilder(null).email("abc@abc.com").firstName("firstname")
                .lastName("lastname").metadata("").phone(new PhoneNumber("965","65562630"))
                .middleName("middlename").build();
    }
    private void configureSDKThemeObject() {

        ThemeObject.getInstance()

                // set Appearance mode [Full Screen Mode - Windowed Mode]
                .setAppearanceMode(AppearanceMode.WINDOWED_MODE) // **Required**

                // Setup header font type face **Make sure that you already have asset folder with required fonts**

                //Setup header text color
                .setHeaderTextColor(getResources().getColor(R.color.black1))  // **Optional**

                // Setup header text size
                .setHeaderTextSize(17) // **Optional**

                // setup header background
                .setHeaderBackgroundColor(getResources().getColor(R.color.french_gray_new))//**Optional**

                // setup card form input font type

                // setup card input field text color
                .setCardInputTextColor(getResources().getColor(R.color.black))//**Optional**

                // setup card input field text color in case of invalid input
                .setCardInputInvalidTextColor(getResources().getColor(R.color.red))//**Optional**

                // setup card input hint text color
                .setCardInputPlaceholderTextColor(getResources().getColor(R.color.black))//**Optional**

                // setup Switch button Thumb Tint Color in case of Off State
                .setSaveCardSwitchOffThumbTint(getResources().getColor(R.color.gray)) // **Optional**

                // setup Switch button Thumb Tint Color in case of On State
                .setSaveCardSwitchOnThumbTint(getResources().getColor(R.color.vibrant_green)) // **Optional**

                // setup Switch button Track Tint Color in case of Off State
                .setSaveCardSwitchOffTrackTint(getResources().getColor(R.color.gray)) // **Optional**

                // setup Switch button Track Tint Color in case of On State
                .setSaveCardSwitchOnTrackTint(getResources().getColor(R.color.green_300)) // **Optional**

                // change scan icon
                .setScanIconDrawable(getResources().getDrawable(R.drawable.btn_card_scanner_normal)) // **Optional**

                // setup pay button selector [ background - round corner ]
                .setPayButtonResourceId(R.drawable.btn_pay_selector)

                // setup pay button font type face
               // .setPayButtonFont(Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_light.ttf")) // **Optional**

                // setup pay button disable title color
                .setPayButtonDisabledTitleColor(getResources().getColor(R.color.black)) // **Optional**

                // setup pay button enable title color
                .setPayButtonEnabledTitleColor(getResources().getColor(R.color.white)) // **Optional**

                //setup pay button text size
                .setPayButtonTextSize(14) // **Optional**

                // show/hide pay button loader
                .setPayButtonLoaderVisible(true) // **Optional**

                // show/hide pay button security icon
                .setPayButtonSecurityIconVisible(true) // **Optional**
        ;

    }
    private void processCashpayment() {
        orderModel.setOrder_status(1);
       // orderModel.setType(getString(R.string.cash));
        orderModel.setOrder_gtotal(String.valueOf(Total));
        orderModel.setOrder_subtotal(String.valueOf(Total));
        sendRequest();
    }


    private void processpayment() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(Total * PreferenceHelper.getDoller())),
                "USD", "Oman", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation payPalConfiguration = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (payPalConfiguration != null) {
                    try {
                        JSONObject jsonObject = payPalConfiguration.toJSONObject();
                        String state = jsonObject.getJSONObject("response").getString("state");
                        Toast.makeText(getActivity(), state, Toast.LENGTH_SHORT).show();
                        orderModel.setOrder_status(1);
                        //orderModel.setType(getString(R.string.paypal));
                        orderModel.setOrder_subtotal(String.valueOf(Total));
                        orderModel.setOrder_gtotal(String.valueOf(Total));
                        sendRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == Activity.RESULT_CANCELED)
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();

        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(getActivity(), "Invalid", Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendRequest() {
        paymentViewModel.addOrder(orderModel);
    }

    private MainViewModelFactory getViewModelFactory() {
        return new MainViewModelFactory(this.getActivity().getApplication());
    }

    @Override
    public void paymentSucceed(@NonNull Charge charge) {

        processCashpayment();
        Toast.makeText(getContext(),charge.getDescription(), Toast.LENGTH_SHORT).show();

        System.out.println("Payment Succeeded : charge status : "+ charge.getStatus());
        System.out.println("Payment Succeeded : description : "+ charge.getDescription());
        System.out.println("Payment Succeeded : message : "+ charge.getResponse().getMessage());
        System.out.println("##############################################################################");
        if(charge.getCard()!=null){
            System.out.println("Payment Succeeded : first six : "+ charge.getCard().getFirstSix());
            System.out.println("Payment Succeeded : last four: "+ charge.getCard().getLast4());
            System.out.println("Payment Succeeded : card object : "+ charge.getCard().getObject());
            System.out.println("Payment Succeeded : brand : "+ charge.getCard().getBrand());
        }

        System.out.println("##############################################################################");
        if(charge.getAcquirer()!=null){
            System.out.println("Payment Succeeded : acquirer id : "+ charge.getAcquirer().getId());
            System.out.println("Payment Succeeded : acquirer response code : "+ charge.getAcquirer().getResponse().getCode());
            System.out.println("Payment Succeeded : acquirer response message: "+ charge.getAcquirer().getResponse().getMessage());
        }
        System.out.println("##############################################################################");
        if(charge.getSource()!=null){
            System.out.println("Payment Succeeded : source id: "+ charge.getSource().getId());
            System.out.println("Payment Succeeded : source channel: "+ charge.getSource().getChannel());
            System.out.println("Payment Succeeded : source object: "+ charge.getSource().getObject());
            System.out.println("Payment Succeeded : source payment method: "+ charge.getSource().getPaymentMethodStringValue());
            System.out.println("Payment Succeeded : source payment type: "+ charge.getSource().getPaymentType());
            System.out.println("Payment Succeeded : source type: "+ charge.getSource().getType());
        }

        System.out.println("##############################################################################");
        if(charge.getExpiry()!=null){
            System.out.println("Payment Succeeded : expiry type :"+ charge.getExpiry().getType());
            System.out.println("Payment Succeeded : expiry period :"+ charge.getExpiry().getPeriod());
        }

    //    configureSDKSession();


    }

    @Override
    public void paymentFailed(@Nullable Charge charge) {
        Toast.makeText(getContext(),charge.getDescription(), Toast.LENGTH_SHORT).show();

        System.out.println("Payment Failed : "+ charge.getStatus());
        System.out.println("Payment Failed : "+ charge.getDescription());
        System.out.println("Payment Failed : "+ charge.getResponse().getMessage());


    }

    @Override
    public void authorizationSucceed(@NonNull Authorize authorize) {
        System.out.println("Authorize Succeeded : "+ authorize.getStatus());
        System.out.println("Authorize Succeeded : "+ authorize.getResponse().getMessage());

        if(authorize.getCard()!=null){
            System.out.println("Payment Authorized Succeeded : first six : "+ authorize.getCard().getFirstSix());
            System.out.println("Payment Authorized Succeeded : last four: "+ authorize.getCard().getLast4());
            System.out.println("Payment Authorized Succeeded : card object : "+ authorize.getCard().getObject());
        }

        System.out.println("##############################################################################");
        if(authorize.getAcquirer()!=null){
            System.out.println("Payment Authorized Succeeded : acquirer id : "+ authorize.getAcquirer().getId());
            System.out.println("Payment Authorized Succeeded : acquirer response code : "+ authorize.getAcquirer().getResponse().getCode());
            System.out.println("Payment Authorized Succeeded : acquirer response message: "+ authorize.getAcquirer().getResponse().getMessage());
        }
        System.out.println("##############################################################################");
        if(authorize.getSource()!=null){
            System.out.println("Payment Authorized Succeeded : source id: "+ authorize.getSource().getId());
            System.out.println("Payment Authorized Succeeded : source channel: "+ authorize.getSource().getChannel());
            System.out.println("Payment Authorized Succeeded : source object: "+ authorize.getSource().getObject());
            System.out.println("Payment Authorized Succeeded : source payment method: "+ authorize.getSource().getPaymentMethodStringValue());
            System.out.println("Payment Authorized Succeeded : source payment type: "+ authorize.getSource().getPaymentType());
            System.out.println("Payment Authorized Succeeded : source type: "+ authorize.getSource().getType());
        }

        System.out.println("##############################################################################");
        if(authorize.getExpiry()!=null){
            System.out.println("Payment Authorized Succeeded : expiry type :"+ authorize.getExpiry().getType());
            System.out.println("Payment Authorized Succeeded : expiry period :"+ authorize.getExpiry().getPeriod());
        }


       // configureSDKSession();
    }

    @Override
    public void authorizationFailed(Authorize authorize) {
        System.out.println("Authorize Failed : "+ authorize.getStatus());
        System.out.println("Authorize Failed : "+ authorize.getDescription());
        System.out.println("Authorize Failed : "+ authorize.getResponse().getMessage());
    }


    @Override
    public void cardSaved(@NonNull Charge charge) {
        // Cast charge object to SaveCard first to get all the Card info.

        System.out.println("Card Saved Succeeded : "+ charge.getStatus());
        System.out.println("Card Saved Succeeded : "+ charge.getCard().getBrand());
        System.out.println("Card Saved Succeeded : "+ charge.getDescription());
        System.out.println("Card Saved Succeeded : "+ charge.getResponse(). getMessage());
    }

    @Override
    public void cardSavingFailed(@NonNull Charge charge) {
        System.out.println("Card Saved Failed : "+ charge.getStatus());
        System.out.println("Card Saved Failed : "+ charge.getDescription());
        System.out.println("Card Saved Failed : "+ charge.getResponse().getMessage());
    }

    @Override
    public void cardTokenizedSuccessfully(@NonNull Token token) {
        System.out.println("Card Tokenized Succeeded : ");
        System.out.println("Token card : "+token.getCard().getFirstSix() + " **** "+ token.getCard().getLastFour() );
        System.out.println("Token card : "+token.getCard().getFingerprint() +  " **** "+ token.getCard().getFunding() );
        System.out.println("Token card : "+token.getCard().getId() +" ****** "+ token.getCard().getName());
        System.out.println("Token card : "+token.getCard().getAddress() +" ****** "+ token.getCard().getObject());
        System.out.println("Token card : "+token.getCard().getExpirationMonth() +" ****** "+ token.getCard().getExpirationYear());

    }

    @Override
    public void savedCardsList(@NonNull CardsList cardsList) {
        if(cardsList!=null && cardsList.getCards()!=null){
            System.out.println(" Card List Response Code : "+cardsList.getResponseCode());
            System.out.println(" Card List Top 10 : "+cardsList.getCards().size());
            System.out.println(" Card List Has More : "+cardsList.isHas_more());
            System.out.println("----------------------------------------------");

            System.out.println("----------------------------------------------");

        }

    }


    @Override
    public void sdkError(@Nullable GoSellError goSellError) {
        if(progress!=null)
            progress.dismiss();
        if(goSellError!=null) {
            System.out.println("SDK Process Error : " + goSellError.getErrorBody());
            System.out.println("SDK Process Error : " + goSellError.getErrorMessage());
            System.out.println("SDK Process Error : " + goSellError.getErrorCode());
        }
    }


    @Override
    public void sessionIsStarting() {
        System.out.println(" Session Is Starting.....");
    }

    @Override
    public void sessionHasStarted() {
        System.out.println(" Session Has Started .......");
    }


    @Override
    public void sessionCancelled() {
        Log.d("MainActivity","Session Cancelled.........");
    }

    @Override
    public void sessionFailedToStart() {
        Log.d("MainActivity","Session Failed to start.........");
    }


    @Override
    public void invalidCardDetails() {
        System.out.println(" Card details are invalid....");
    }

    @Override
    public void backendUnknownError(String message) {
        System.out.println("Backend Un-Known error.... : "+ message);
    }

    @Override
    public void invalidTransactionMode() {
        System.out.println(" invalidTransactionMode  ......");
    }

    @Override
    public void invalidCustomerID() {
        if(progress!=null)
            progress.dismiss();
        System.out.println("Invalid Customer ID .......");

    }

    @Override
    public void userEnabledSaveCardOption(boolean saveCardEnabled) {
        System.out.println("userEnabledSaveCardOption :  "+saveCardEnabled);
    }
}
