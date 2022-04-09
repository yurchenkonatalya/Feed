package com.example.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.feed.db.RoomOwner
import com.example.feed.db.RoomSingleton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Login : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var buttonInput: Button
    private lateinit var mDb: RoomSingleton
    private lateinit var signInButton: SignInButton
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 0
    private lateinit var text: String

    companion object{
        val USER_ID_ARG = "user_id_arg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        mDb = RoomSingleton.getInstance(applicationContext)

        signInButton = findViewById(R.id.buttonGoogle)
        val signOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, signOptions)
        editText = findViewById(R.id.editText)
        buttonInput = findViewById(R.id.buttonInputSave)

        signInButton.setOnClickListener {
            signIn()


        }

        buttonInput.setOnClickListener {
            if(editText.text.toString() != "")
            initDB(editText.text.toString())

        }
////        mDb = RoomSingleton.getInstance(applicationContext)
//
//        editText.setOnClickListener {
//            val roomEntity = RoomEntity(
//                date = format.format(Calendar.getInstance().getTime()),
//                amount = amountTaps
//            )
//            lifecycleScope.launch(Dispatchers.IO){
//                mDb.roomDao().insert(roomEntity)
//                println("1")
//            }
//        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account.email?.let { initDB(it) }

            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.statusCode)
        }
    }

    fun initDB(text: String){
        var id: Long?
        lifecycleScope.launch(Dispatchers.IO) {
            id = mDb.roomOwnerDao().getSimilar(text)
            if (id == null) {
                val roomOwner1 = RoomOwner(
                    name = text
                )
                mDb.roomOwnerDao().insert(roomOwner1)
            }
            val id_able = mDb.roomOwnerDao().getSimilar(text)

            withContext(Dispatchers.Main){
                val intent = Intent(this@Login, MainActivity::class.java)
                intent.putExtra(USER_ID_ARG, id_able)
                println("$id")
                startActivity(intent)
                finish()
            }
        }
    }
}