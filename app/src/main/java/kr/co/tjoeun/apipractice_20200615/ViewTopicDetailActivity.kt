package kr.co.tjoeun.apipractice_20200615

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import kr.co.tjoeun.apipractice_20200615.adapters.TopicReplyAdapter
import kr.co.tjoeun.apipractice_20200615.datas.Topic
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

//    다른화면에서 보내주는 주제 id값 저장 변수
//    -1 ? 정상적인 id는 절대 -1일 수 없다.
//    만약 이 값이 계속 -1이라면 => 잘못 받아왔거나 등의 예외처리.
    var mTopicId = -1
//    서버에서 받아온 주제 정보를 저장할 멤버변수
    lateinit var mTopic : Topic

//    의견 목록을 뿌려주는 어댑터
    lateinit var mReplyAdapter : TopicReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }


    override fun setupEvents() {

//        의견 등록하기 버튼 이벤트 추가
        postReplyBtn.setOnClickListener {
//            의견 작성 화면으로 이동

//            선택진영이 있을때만 (투표를 했어야만) 의견 작성 화면 이동

            mTopic.mySideInfo?.let {
                val myIntent = Intent(mContext, EditReplyActivity::class.java)
                myIntent.putExtra("topicTitle", mTopic.title)
                myIntent.putExtra("topicId", mTopicId)
                myIntent.putExtra("mySideTitle", it.title)
                startActivity(myIntent)
            }.let {
                if (it == null) {
//                null이 맞을때 (투표 안했을때)
                    Toast.makeText(mContext, "투표를 해야만 의견 작성이 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }




        }


//        두개의 투표하기 버튼 이벤트 추가
        voteToFirstSideBtn.setOnClickListener {

            ServerUtil.postRequestVote(mContext, mTopic.sideList[0].id, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    getTopicDetailFromServer()
                }
            })


        }

        voteToSecondSideBtn.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopic.sideList[1].id, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {
                    getTopicDetailFromServer()
                }
            })
        }

    }

    override fun setValues() {

        setTitle("토론 진행 현황")

        mTopicId = intent.getIntExtra("topic_id", -1)

        if (mTopicId == -1) {
            Toast.makeText(mContext, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
//            추가 진행을 막자
            return
        }

    }

    override fun onResume() {
        super.onResume()

//        화면이 다시 나타날때 마다 => 서버에서 주제 최신으로 갱신.
//       제대로 id값을 받아온 경우 => 서버에 해당 토픽 진행상황 조회
        getTopicDetailFromServer()
    }

//    진행상황을 받아와주는 함수 (별도 기능)
    fun getTopicDetailFromServer() {

        ServerUtil.getRequestTopicDetail(mContext, mTopicId, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val topic = data.getJSONObject("topic")

                val topicObj = Topic.getTopicFromJson(topic)
                mTopic = topicObj

                runOnUiThread {
                    topicTitleTxt.text = mTopic.title
                    Glide.with(mContext).load(mTopic.imageUrl).into(topicImg)

                    firstSideTitleTxt.text = mTopic.sideList[0].title
                    secondSideTitleTxt.text = mTopic.sideList[1].title

//                    득표수 표시
                    firstSideVoteCountTxt.text = "${mTopic.sideList[0].voteCount}표"
                    secondSideVoteCountTxt.text = "${mTopic.sideList[1].voteCount}표"

//                    내가 투표를 어디에 했냐에 따라 다른 문구로 버튼 변경

                    if (mTopic.mySelectedSideIndex == -1) {
//                        아무데도 투표를 안한경우
                        voteToFirstSideBtn.text = "투표하기"
                        voteToSecondSideBtn.text = "투표하기"
                    }
                    else if (mTopic.mySelectedSideIndex == 0) {
                        voteToFirstSideBtn.text = "투표취소"
                        voteToSecondSideBtn.text = "갈아타기"
                    }
                    else {
                        voteToFirstSideBtn.text = "갈아타기"
                        voteToSecondSideBtn.text = "투표취소"
                    }

//                    주제가 들고있는 => 의견 목록을 리스트뷰에 뿌려주기
                    mReplyAdapter = TopicReplyAdapter(mContext, R.layout.topic_reply_list_item, mTopic.replyList)
                    replyListView.adapter = mReplyAdapter
                }

            }

        })

    }

}