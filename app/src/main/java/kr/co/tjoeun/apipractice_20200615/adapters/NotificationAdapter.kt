package kr.co.tjoeun.apipractice_20200615.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.apipractice_20200615.R
import kr.co.tjoeun.apipractice_20200615.datas.Notification
import kr.co.tjoeun.apipractice_20200615.datas.Topic
import kr.co.tjoeun.apipractice_20200615.utils.TimeUtil

class NotificationAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:List<Notification>) : ArrayAdapter<Notification>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.notification_list_item, null)
        }

//        row가 절대 null 아님을 보장하면서 대입
        val row = tempRow!!

        val notiTitleTxt = row.findViewById<TextView>(R.id.notiTitleTxt)
        val notiMessageTxt = row.findViewById<TextView>(R.id.notiMessageTxt)
        val notiCreatedAtTxt = row.findViewById<TextView>(R.id.notiCreatedAtTxt)

        val data = mList[position]

        notiTitleTxt.text = data.title
        notiMessageTxt.text = data.message

        notiCreatedAtTxt.text = TimeUtil.getTimeAgoFromCalendar(data.createdAt)


//        완성된 row를 리스트뷰의 재료로 리턴
        return row
        
    }

}