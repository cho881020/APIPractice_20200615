package kr.co.tjoeun.apipractice_20200615.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kr.co.tjoeun.apipractice_20200615.R
import kr.co.tjoeun.apipractice_20200615.datas.Topic

class TopicAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:List<Topic>) : ArrayAdapter<Topic>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.topic_list_item, null)
        }
        
//        row가 절대 null 아님을 보장하면서 대입
        val row = tempRow!!

        
//        완성된 row를 리스트뷰의 재료로 리턴
        return row
        
    }

}