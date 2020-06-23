package kr.co.tjoeun.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

//    어떤 의견에 대해 보러온건지
    var mReplyId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mReplyId = intent.getIntExtra("replyId", -1)
    }

    override fun onResume() {
        super.onResume()
        getReplyDetailFromServer()
    }

    fun getReplyDetailFromServer() {
        ServerUtil.getRequestReplyDetail(mContext, mReplyId, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

            }

        })
    }

}