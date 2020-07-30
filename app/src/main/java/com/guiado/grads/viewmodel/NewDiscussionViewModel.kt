package com.guiado.grads.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import com.guiado.grads.BR
import com.guiado.grads.R
import com.guiado.grads.handler.NetworkChangeHandler
import com.guiado.grads.listeners.MultipleClickListener
import com.guiado.grads.model_sales.Authenticaiton
import com.guiado.grads.model_sales.CreateIdeas
import com.guiado.grads.util.MultipleClickHandler
import com.guiado.grads.view.FragmentNewDiscusssion
import com.news.list.communication.GetServiceNews
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level


class NewDiscussionViewModel(private val context: Context, private val fragmentSignin: FragmentNewDiscusssion) :
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
    var challangeID: String? = "aEU3D0000008PKtWAM"
        set(price) {
            field = price
            notifyPropertyChanged(BR.challangeID)
        }

    @get:Bindable
    var ideaBrief: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaBrief)
        }

    @get:Bindable
    var ideaFeatures: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaFeatures)
        }

    @get:Bindable
    var ideaImplementation: String? = ""
        set(price) {
            field = price
            notifyPropertyChanged(BR.ideaImplementation)
        }

    @get:Bindable
    var status: String? = "DRAFT"
        set(price) {
            field = price
            notifyPropertyChanged(BR.status)
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
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.infoMsg), Toast.LENGTH_LONG).show()
                return@OnClickListener
            } else if (ideaTitle!!.length < 10) {
                Toast.makeText(fragmentSignin, fragmentSignin.resources.getString(R.string.infoMsg_discussion), Toast.LENGTH_LONG).show()
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


        val post = CreateIdeas()
        post.challengeC = challangeID
        post.incDetailsC = ideaBrief
        post.incFeaturesC = ideaFeatures
        post.incImplementationApproachC = ideaImplementation
        post.incNameC = ideaTitle
        post.statusC = status

        postsService.createQueryIdeas(post, "Bearer " + getAccessToken())!!
                .enqueue(object : Callback<CreateIdeas?> {
                    override fun onResponse(call: Call<CreateIdeas?>?, response: Response<CreateIdeas?>) {
                        response.body().toString()
                         showProgresss(false)
                        Toast.makeText(context,"Thanks for your idea. \n Idea added successfully to challenge.",Toast.LENGTH_LONG).show()
                        fragmentSignin.finish()
                    }


                    override fun onFailure(call: Call<CreateIdeas?>?, t: Throwable) {
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
