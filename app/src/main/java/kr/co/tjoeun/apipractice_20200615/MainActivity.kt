package kr.co.tjoeun.apipractice_20200615

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.apipractice_20200615.adapters.TopicAdapter
import kr.co.tjoeun.apipractice_20200615.datas.Topic
import kr.co.tjoeun.apipractice_20200615.datas.User
import kr.co.tjoeun.apipractice_20200615.utils.ContextUtil
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val topicList = ArrayList<Topic>()

    lateinit var topicAdapter : TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        topicListView.setOnItemClickListener { parent, view, position, id ->

            val clickedTopic = topicList[position]

            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)
            myIntent.putExtra("topic_id", clickedTopic.id)
            startActivity(myIntent)
        }

//        응용문제.
//        로그아웃 버튼이 눌리면 => 정말 로그아웃할건지 확인을 받자.
//        확인버튼을 누르면 실제 로그아웃 처리 진행.
//        저장된  토큰을 다시 빈칸으로 돌려주자. (로그아웃)
//        메인액티비티 종료 => 로그인화면으로 이동

        logoutBtn.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->

//                저장 안되있을때의 기본값으로 세팅 (없던것과 동일하게 처리)
                ContextUtil.setUserToken(mContext, "")

                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)

                finish()


            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {

//        화면 제목 변경 => 제목 설정 해제. (기본 그림이 나오도록)
//        setTitle("토론 목록")

        topicAdapter = TopicAdapter(mContext, R.layout.topic_list_item, topicList)
        topicListView.adapter = topicAdapter

//        기기의 고유 값 (DeviceToken)이 어떤 값인지?
        Log.d("기기토큰값", FirebaseInstanceId.getInstance().token)

//        진행중인 토론 목록이 어떤게 있는지? 서버에 물어보자.
        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")

                val topics = data.getJSONArray("topics")

                for (i in 0..topics.length()-1) {
                    val topicJson = topics.getJSONObject(i)

//                    주제 하나에 대응되는 JSON을 넣어서 Topic객체로 얻어내자.
                    val topic = Topic.getTopicFromJson(topicJson)

//                    받아온 주제 목록을 리스트뷰의 재료로 추가
                    topicList.add(topic)
                }

                runOnUiThread {

//                리스트뷰의 내용 추가
                    topicAdapter.notifyDataSetChanged()
                }

            }

        })
    }



}