package com.joon.koreanman_in_tokyo_app.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.joon.koreanman_in_tokyo_app.MainActivity
import com.joon.koreanman_in_tokyo_app.R
import com.joon.koreanman_in_tokyo_app.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityJoinBinding

    val MINIMUM_LENGTH = 8

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.signupBtn.setOnClickListener {

            var isGoToJoin = true

            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()
            val passwordCheck = binding.passwordCheckArea.text.toString()

            // 値が空いているか確認
            if(email.isEmpty()){
                Toast.makeText(this,"emailを入力してください", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if(password.isEmpty()){
                Toast.makeText(this,"passwordを入力してください", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if(passwordCheck.isEmpty()){
                Toast.makeText(this,"passwordチェックを入力してください", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            //パスワード入力チェック
            if(password.equals(passwordCheck)) {
                Toast.makeText(this, "passwordとpasswordチェックが異なります。", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            //パスワード桁数チェック
            if( MINIMUM_LENGTH <= password.length){
                Toast.makeText(this, "passwordは８桁以上入力してください。", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if(isGoToJoin){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "成功", Toast.LENGTH_LONG).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                    }else {
                        Toast.makeText(this,"失敗", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }



    }
}






