package kr.co.tjoeun.apipractice_20200615

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_notification_list.*
import kr.co.tjoeun.apipractice_20200615.adapters.NotificationAdapter
import kr.co.tjoeun.apipractice_20200615.datas.Notification
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil
import org.json.JSONObject

class NotificationListActivity : BaseActivity() {

    val mNotiList = ArrayList<Notification>()

    lateinit var mNotiAdapter : NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        Base에서 상속받은 알림 레이아웃 통째로 숨기기
        notiFrameLayout.visibility = View.GONE

        mNotiAdapter = NotificationAdapter(mContext, R.layout.notification_list_item, mNotiList)
        notiListView.adapter = mNotiAdapter

    }

    override fun onResume() {
        super.onResume()
        getNotificationsFromServer()
    }

    fun getNotificationsFromServer() {

        ServerUtil.getRequestNotificaionList(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")

                val notis = data.getJSONArray("notifications")

//                새 알림 목록을 담기 전에, 기존 목록 날려버리기. (중복 제거)
                mNotiList.clear()

                for (i in 0..notis.length()-1) {

                    val noti = notis.getJSONObject(i)
                    mNotiList.add(Notification.getNotificationFromJson(noti))

                }

//                알림을 받았으면, 받은 최신 알림의 id를 서버로 전파
//                여기까지 알림을 읽었다고 서버에 알려줌. (unread_noti_count를 0으로)

                ServerUtil.postRequestNotification(mContext, mNotiList[0].id, null)


                runOnUiThread {
                    mNotiAdapter.notifyDataSetChanged()
                }

            }

        })

    }

}