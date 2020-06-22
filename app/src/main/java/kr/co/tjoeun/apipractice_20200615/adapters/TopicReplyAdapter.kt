package kr.co.tjoeun.apipractice_20200615.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.apipractice_20200615.R
import kr.co.tjoeun.apipractice_20200615.datas.Topic
import kr.co.tjoeun.apipractice_20200615.datas.TopicReply
import java.text.SimpleDateFormat

class TopicReplyAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:List<TopicReply>) : ArrayAdapter<TopicReply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.topic_reply_list_item, null)
        }
        
//        row가 절대 null 아님을 보장하면서 대입
        val row = tempRow!!

//        XML에서 뷰 가져오기
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val writeTimeTxt = row.findViewById<TextView>(R.id.writeTimeTxt)

        val replyBtn = row.findViewById<Button>(R.id.replyBtn)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)

//        뿌려줄 데이터 가져오기
        val data = mList[position]

//        데이터 반영

        contentTxt.text = data.content
        writerNickNameTxt.text = data.writer.nickName

//        5월 2일 오전/오후 8시 ?분 양식 출력
        val sdf = SimpleDateFormat("M월 d일 a h시 m분")
        writeTimeTxt.text = sdf.format(data.createdAt.time)

//        좋아요 / 싫어요 / 답글 갯수 표시
        likeBtn.text = "좋아요 : ${data.likeCount}개"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}개"
        replyBtn.text = "답글 : ${data.replyCount}개"

//        완성된 row를 리스트뷰의 재료로 리턴
        return row
        
    }

}