package kr.co.tjoeun.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        emailCheckBtn.setOnClickListener {

//            입력한 이메일 받아오기
            val email = emailEdt.text.toString()

//            서버에 중복확인 요청
            ServerUtil.getRequestDuplicatedCheck(mContext, "EMAIL", email, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    runOnUiThread {
//                        UI처리 쓰레드에서 결과 확인 / 화면 반영
                        if (code == 200) {
                            emailCheckResultTxt.text = "사용해도 좋습니다."
                        }
                        else {
                            emailCheckResultTxt.text = "중복된 이메일이라 사용 불가합니다."
                        }
                    }


                }

            })

        }

    }

    override fun setValues() {

    }

}
