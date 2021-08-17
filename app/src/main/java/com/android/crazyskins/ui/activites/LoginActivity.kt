package com.android.crazyskins.ui.activites

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.crazyskins.CLIENT_ID
import com.android.crazyskins.MY_PREFS_KEY
import com.android.crazyskins.R
import com.android.crazyskins.USER_ID
import com.android.crazyskins.databinding.ActivityLoginBinding
import com.android.crazyskins.utils.snackBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val TAG = "GoogleActivity"
    private val RC_SIGN_IN = 123
    lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
//    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        databaseRef = FirebaseDatabase.getInstance().reference.child("UserData")
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(CLIENT_ID)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.emailSignInBtn.setOnClickListener {
            val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            pDialog.progressHelper.barColor = Color.parseColor("#E23E58")
            pDialog.titleText = "Please wait..."
            pDialog.show()
            pDialog.setCancelable(false)

            if (!binding.etLoginEmail.text.isNullOrEmpty() && !binding.etLoginEmail.text.isNullOrEmpty()){
                saveInfo(false,null,pDialog,binding.etLoginPassword.text.toString())
            }else{

                if (binding.etLoginEmail.text.isNullOrEmpty()){
                    binding.etLoginEmail.error = "Required"
                }else if (binding.etLoginPassword.text.isNullOrEmpty()){
                    binding.etLoginPassword.error = "Required"
                }
            }
        }


        binding.googleSignInBtn.setOnClickListener {
            if (binding.acceptPolicies.isChecked && binding.acceptTerms.isChecked) {
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            } else {
                if (!binding.acceptPolicies.isChecked) {
                    findViewById<View>(android.R.id.content).snackBar(
                        "Please accept our privacy policies",
                        null
                    )
                } else if (!binding.acceptTerms.isChecked) {
                    findViewById<View>(android.R.id.content).snackBar(
                        "Please accept our terms and conditions",
                        null
                    )
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            pDialog.progressHelper.barColor = Color.parseColor("#E23E58")
            pDialog.titleText = "Please wait..."
            pDialog.show()
            pDialog.setCancelable(false)

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task, pDialog)
        }
    }

    private fun handleGoogleSignInResult(
        completedTask: Task<GoogleSignInAccount>,
        pDialog: SweetAlertDialog
    ) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (completedTask.isSuccessful) {
                if (account != null) {
                    saveInfo(true, account, pDialog, "null")
                }
            }
        } catch (e: ApiException) {
            Log.d("LoginFromGoogle", e.message!!)
            Toast.makeText(applicationContext, "Error something went wrong", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun saveInfo(
        fromGoogle: Boolean,
        account: GoogleSignInAccount?,
        pDialog: SweetAlertDialog,
        password: String
    ) {
        if (fromGoogle) {
            if (account?.displayName != null
                && account.email != null
            ) {
                val editPrefs = getSharedPreferences(MY_PREFS_KEY, MODE_PRIVATE).edit()
                val userID: String? = account.id
                editPrefs.putString(USER_ID, userID)
                editPrefs.apply()
                editPrefs.commit()
                val dataMap = HashMap<String, Any>()
                dataMap["email"] = account.email?.toString() ?: "N/A"
                dataMap["password"] = password

                if (userID != null) {
                    databaseRef.child("LoginData").child(userID.toString()).setValue(dataMap)
                        .addOnCompleteListener { task ->
                            pDialog.dismissWithAnimation()
                            if (task.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                    applicationContext,
                                    "successfully logged in!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                finish()
                            } else {
                                pDialog.dismissWithAnimation()
                                Log.d("SignUp Exception", task.exception.toString())
                                Toast.makeText(
                                    this,
                                    "Something went wrong!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        } else {


        }
    }


}