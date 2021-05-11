package nl.emmerson.firebaseauthdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        //redirect to register page
        findViewById<TextView>(R.id.tv_register).setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        //login function
        findViewById<Button>(R.id.btn_login).setOnClickListener() {
            //check if email is filled in
            when {
                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.et_login_email).text.toString()
                        .trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            //check if password is filled in
            when {
                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.et_login_password).text.toString()
                        .trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val email: String = findViewById<EditText>(R.id.et_login_email).text.toString()
                        .trim { it <= ' ' }
                    val password: String =
                        findViewById<EditText>(R.id.et_login_password).text.toString()
                            .trim { it <= ' ' }

                    Log.d("Email", email)

                    //create an instance and create a register a user with email and password.
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {


                                Toast.makeText(
                                    this@LoginActivity,
                                    "You were logged in succesfully.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                /*User is automatically logged in so the user gets send back to the main activity*/
                                val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            } else {
                                //if registering is not successful then show error message
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        }


                }


            }
        }

    }
}