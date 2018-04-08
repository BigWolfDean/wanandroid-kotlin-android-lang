package com.lang.ui.activity

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import com.lang.R
import com.lang.model.RegisterLoginModel
import com.lang.util.*
import com.ohmerhe.kolley.request.Http
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.toast
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var usernameInput: TextInputLayout

    private lateinit var passwordInput: TextInputLayout

    private lateinit var usernameET: EditText

    private lateinit var passwordET: EditText

    private lateinit var registerButton: Button

    private lateinit var loginButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        initToolbar(toolbar)
        setToolbarTitle(toolbar_title, "注册/登录")

        usernameInput = findViewById(R.id.username_input_layout)
        passwordInput = findViewById(R.id.password_input_layout)
        usernameET = findViewById(R.id.username_et)
        passwordET = findViewById(R.id.password_et)
        loginButton = findViewById(R.id.login)
        registerButton = findViewById(R.id.sign_in)


        loginButton.setOnClickListener(this)
        registerButton.setOnClickListener(this)

    }


    private fun checkInput() {
        if (TextUtils.isEmpty(usernameET.text.toString())) {
            usernameInput.error = "请输入用户名"
            return
        }
        if (TextUtils.isEmpty(passwordET.text.toString())) {
            passwordInput.error = "请输入密码"
            return
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login -> {
                checkInput()
                Http.post {
                    url = "http://www.wanandroid.com/user/login"

                    params {
                        "username" - usernameET.text.toString()
                        "password" - passwordET.text.toString()
                    }
                    onSuccess {
                        val result = it.toString(Charset.defaultCharset())
                        val gson = Gson()
                        val loginModel = gson.fromJson<RegisterLoginModel>(result, RegisterLoginModel::class.java)
                        when (loginModel.errorCode) {
                            0 -> {
                                setUserName(loginModel.data.username)
                                setUserPwd(loginModel.data.password)
                                setIsLogin(true)
                                finish()
                            }

                            1 -> {
                                usernameInput.error = loginModel.errorMsg
                                toast(loginModel.errorMsg)
                            }

                        }

                    }

                    onFail {
                        toast(it.message.toString())
                    }
                }
            }

            R.id.sign_in -> {
                checkInput()
                Http.post {
                    url = "http://www.wanandroid.com/user/register"

                    params {
                        "username" - usernameET.text.toString()
                        "password" - passwordET.text.toString()
                        "repassword" - passwordET.text.toString()
                    }

                    onSuccess {
                        val result = it.toString(Charset.defaultCharset())
                        val gson = Gson()
                        val registerModel = gson.fromJson<RegisterLoginModel>(result, RegisterLoginModel::class.java)
                        when (registerModel.errorCode) {
                            0 -> {
                                toast("注册成功，请登录")
                            }

                            1 -> {
                                usernameInput.error = registerModel.errorMsg
                            }

                        }
                    }

                    onFail {
                        toast(it.message.toString())
                    }
                }
            }
        }
    }
}
