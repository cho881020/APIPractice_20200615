package kr.co.tjoeun.apipractice_20200615

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        loginBtn.setOnClickListener {
            val email = emailEdt.text.toString()
            val pw = passwordEdt.text.toString()

//            입력한 ID / PW가 진짜 회원인지 서버에 물어봐야함 (요청 필요)

            ServerUtil.postRequestLogin(mContext, email, pw, null)

        }

    }

    override fun setValues() {

    }

}
