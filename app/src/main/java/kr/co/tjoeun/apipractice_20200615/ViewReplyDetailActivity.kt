package kr.co.tjoeun.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import kr.co.tjoeun.apipractice_20200615.adapters.TopicReReplyAdapter
import kr.co.tjoeun.apipractice_20200615.datas.TopicReply
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {


//    어떤 의견에 대해 보러온건지
    var mReplyId = -1

//    서버에서 받아온 의견 저장
    lateinit var mReply : TopicReply

//    서버에서 보내주는 답글 목록을 저장할 배열
    val mReReplyList = ArrayList<TopicReply>()

//    답글 목록 뿌리는 어댑터
    lateinit var mReReplyAdapter : TopicReReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        postBtn.setOnClickListener {

            val inputContent = contentEdt.text.toString()

            if (inputContent.length < 5) {
                Toast.makeText(mContext, "답글의 길이는 5자 이상이어야합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ServerUtil.postRequestReReply(mContext, mReplyId, inputContent, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    입력한 답글의 내용도 비워주기.
                    runOnUiThread {
                        contentEdt.setText("")
                        Toast.makeText(mContext, "답글을 등록했습니다.", Toast.LENGTH_SHORT).show()
                    }

//                    의견에 달린 답글들을 다시 불러와서 리스트뷰에 뿌려주기
                    getReplyDetailFromServer()

                }

            })

        }

    }

    override fun setValues() {
        mReplyId = intent.getIntExtra("replyId", -1)

        mReReplyAdapter = TopicReReplyAdapter(mContext, R.layout.topic_re_reply_list_item, mReReplyList)
        reReplyListView.adapter = mReReplyAdapter
    }

    override fun onResume() {
        super.onResume()
        getReplyDetailFromServer()
    }

    fun getReplyDetailFromServer() {
        ServerUtil.getRequestReplyDetail(mContext, mReplyId, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val reply = data.getJSONObject("reply")
                mReply = TopicReply.getTopicReplyFromJson(reply)

//                이부분에서 mReReplyList를 채워넣고 => 새로고침 하자.

//                mReReplyList 내부에 이미 들어있던 데이터가 중복으로 나와서 문제 발생.
                mReReplyList.clear()

//                reply내부의 답글 목록 JSONArray를 이용해서 채워넣자.

                val replies = reply.getJSONArray("replies")

                for (i in 0..replies.length()-1) {
                    val replyObj = TopicReply.getTopicReplyFromJson(replies.getJSONObject(i))

                    mReReplyList.add(replyObj)
                }


                runOnUiThread {
                    contentTxt.text = mReply.content
                    writerNickNameTxt.text = mReply.writer.nickName
                    selectedSideTitleTxt.text = "(${mReply.selectedSide.title})"

                    mReReplyAdapter.notifyDataSetChanged()

//                    리스트뷰의 스크롤을 맨 밑 (마지막 포지션) 으로 이동
                    reReplyListView.smoothScrollToPosition(mReReplyList.size - 1)
                }

            }
        })
    }

}