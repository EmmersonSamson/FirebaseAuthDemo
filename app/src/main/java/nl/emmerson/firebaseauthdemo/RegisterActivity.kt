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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import nl.emmerson.firebaseauthdemo.databinding.ActivityMainBinding


class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        
        //all the seperate ID's used from the XML
        val tv_email = findViewById<TextView>(R.id.tv_email)



        //go to login page with login button
        tv_email.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }



        findViewById<EditText>(R.id.et_register_email).text.toString()









        findViewById<Button>(R.id.btn_register).setOnClickListener(){
            //check if email is filled in
            when{
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_email).text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            //check if password is filled in
            when{
                TextUtils.isEmpty(findViewById<EditText>(R.id.et_register_password).text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val email: String = findViewById<EditText>(R.id.et_register_email).text.toString().trim { it <= ' ' }
                    val password: String = findViewById<EditText>(R.id.et_register_password).text.toString().trim { it <= ' ' }

                    Log.d("Email", email)

                    //create an instance and create a register a user with email and password.
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->

                                if (task.isSuccessful) {

                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You were registered succesfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    /*User is automatically logged in so the user gets send back to the main activity*/
                                    val intent =
                                        Intent(this@RegisterActivity, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        intent.putExtra("user_id", firebaseUser.uid)
                                        intent.putExtra("email_id", email)
                                        startActivity(intent)
                                        finish()
                                } else{
                                    //if registering is not successful then show error message
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    )
                }


            }



        }
    }
}