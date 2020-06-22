package kr.co.tjoeun.apipractice_20200615

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_reply.*
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class EditReplyActivity : BaseActivity() {

    var mTopicId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        postBtn.setOnClickListener {

            val content = contentEdt.text.toString()

            if (content.length < 5) {
                Toast.makeText(mContext, "의견은 최소 5자는 되어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("의견 등록")
            alert.setMessage("정말 의견을 등록하시겠습니까? 한번 의견을 등록하면 투표를 변경할 수 없고, 내용을 수정할 수 없습니다.")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->

//                서버에 실제 요청
                ServerUtil.postRequestReply(mContext, mTopicId, content, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

                    }

                })

            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {

//        의견 작성후 버튼 클릭 =>
//        "정말 의견을 등록하시겠습니까? 한번 의견을 등록하면 투표를 변경할 수 없고, 내용을 수정할 수 없습니다."
//        확인 누르면 => 실제로 의견 등록 처리.
//        완료 후 토론 진행 화면 복귀 => 자동 새로고침

//        화면 진입시 첨부한 관련 정보 표시
        topicTitleTxt.text = intent.getStringExtra("topicTitle")
        mySideTitleTxt.text = intent.getStringExtra("mySideTitle")

        mTopicId = intent.getIntExtra("topicId", -1)
        
    }

}