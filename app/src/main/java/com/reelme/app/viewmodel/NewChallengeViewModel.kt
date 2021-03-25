package com.reelme.app.viewmodel

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import com.reelme.app.BR
import com.reelme.app.GetServiceNews
import com.reelme.app.R
import com.reelme.app.handler.NetworkChangeHandler
import com.reelme.app.listeners.MultipleClickListener
import com.reelme.app.model_sales.Authenticaiton
import com.reelme.app.model_sales.newchallenge.Newchallenge
import com.reelme.app.util.MultipleClickHandler
import com.reelme.app.view.FragmentNewChallenge
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewChallengeViewModel(private val context: Context, private val fragmentSignin: FragmentNewChallenge) :
        BaseObservable(), NetworkChangeHandler.NetworkChangeListener, MultipleClickListener {

    private val TAG = "ProfileEditViewModel"

    private var networkStateHandler: NetworkChangeHandler? = null

    private var isInternetConnected: Boolean = false


    init {
        networkHandler()
    }


    @get:Bindable
    var ideaTitle: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaTitle)
        }



    @get:Bindable
    var ideaBrief: String? = "aEV3D0000004Em9"
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaBrief)
        }




    @get:Bindable
    var showSignUpProgress: Int = View.INVISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpProgress)
        }

    @get:Bindable
    var showSignUpBtn: Int = View.VISIBLE
        set(dataEmail) {
            field = dataEmail
            notifyPropertyChanged(BR.showSignUpBtn)
        }


    fun showProgresss(isShow : Boolean){
        if(isShow){
            showSignUpBtn = View.INVISIBLE
            showSignUpProgress = View.VISIBLE }
        else{
            showSignUpBtn = View.VISIBLE
            showSignUpProgress = View.INVISIBLE
        }
    }


    fun doPostEvents() = View.OnClickListener {

        if (!handleMultipleClicks()) {
            if (ideaTitle.isNullOrEmpty()) {
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.infoMsg), Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
                return@OnClickListener
            } else if (ideaTitle!!.length < 10) {
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.infoMsg_discussion), Toast.LENGTH_LONG).apply {setGravity(Gravity.TOP, 0, 0); show() }
                return@OnClickListener
            }

            //   Log.d("Authenticaiton token", "testing the value" );


            doSignInUser()
//            Log.d(TAG, "DocumentSnapshot onSuccess ")
//            fragmentSignin.finish()
//            val intent = Intent(fragmentSignin, FragmentGameChooser::class.java)
//            intent.putExtra(Constants.POSTAD_OBJECT, ideaTitle)
//            fragmentSignin.startActivity(intent)

        }
    }


    lateinit var postsService: GetServiceNews
    private fun doSignInUser() {

        if (isInternetConnected) {
            showToast(R.string.network_ErrorMsg)
        } else {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://philipscrm--pocinc.my.salesforce.com/services/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            postsService = retrofit.create(GetServiceNews::class.java)
            sendPost()


        }
    }

    private fun sendPost() {

        Log.d("Authenticaiton token", "");


        showProgresss(true)


        val post = Newchallenge()
        post.descriptionC = ideaBrief
        post.nameC = ideaTitle

        postsService.createQueryChallenge(post, "Bearer " + getAccessToken())!!
                .enqueue(object : Callback<Newchallenge?> {
                    override fun onResponse(call: Call<Newchallenge?>?, response: Response<Newchallenge?>) {
                        response.body().toString()
                         showProgresss(false)
                        Toast.makeText(context,"Thanks for your idea. \n Idea added successfully to challenge.",Toast.LENGTH_LONG).show()
                      //  fragmentSignin.finish()
                    }


                    override fun onFailure(call: Call<Newchallenge?>?, t: Throwable) {
                        Log.d("Authenticaiton token", t.toString())
                        showProgresss(false)
                    }
                })
    }

    fun getAccessToken(): String {
        val sharedPreference = context.getSharedPreferences("AUTH_INFO", Context.MODE_PRIVATE)
        val coronaJson = sharedPreference.getString("AUTH_INFO", "");
        try {
            val auth = Gson().fromJson(coronaJson, Authenticaiton::class.java)
            Log.d("Authenticaiton token", auth.accessToken)
            return auth.accessToken

        } catch (e: java.lang.Exception) {
            Log.d("Authenticaiton token", "Exception")

            return ""
        }
    }


    private fun networkHandler() {
        networkStateHandler = NetworkChangeHandler()
    }

    fun registerListeners() {
        networkStateHandler!!.registerNetWorkStateBroadCast(context)
        networkStateHandler!!.setNetworkStateListener(this)
    }

    fun unRegisterListeners() {
        networkStateHandler!!.unRegisterNetWorkStateBroadCast(context)
    }

    override fun networkChangeReceived(state: Boolean) {
        isInternetConnected = !state
        if (!state) {
            showToast(R.string.network_ErrorMsg)
        }
    }

    private fun showToast(id: Int) {
        Toast.makeText(context, context.resources.getString(id), Toast.LENGTH_LONG).show()
    }

    override fun handleMultipleClicks(): Boolean {
        return MultipleClickHandler.handleMultipleClicks()
    }
}
