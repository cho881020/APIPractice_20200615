package kr.co.tjoeun.apipractice_20200615.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.apipractice_20200615.R
import kr.co.tjoeun.apipractice_20200615.ViewReplyDetailActivity
import kr.co.tjoeun.apipractice_20200615.datas.Topic
import kr.co.tjoeun.apipractice_20200615.datas.TopicReply
import kr.co.tjoeun.apipractice_20200615.utils.ServerUtil
import kr.co.tjoeun.apipractice_20200615.utils.TimeUtil
import org.json.JSONObject
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

        val selectedSideTitleTxt = row.findViewById<TextView>(R.id.selectedSideTitleTxt)

//        뿌려줄 데이터 가져오기
        val data = mList[position]

//        데이터 반영

        contentTxt.text = data.content
        writerNickNameTxt.text = data.writer.nickName

        writeTimeTxt.text = TimeUtil.getTimeAgoFromCalendar(data.createdAt)

//        좋아요 / 싫어요 / 답글 갯수 표시
        likeBtn.text = "좋아요 : ${data.likeCount}개"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}개"
        replyBtn.text = "답글 : ${data.replyCount}개"

//        내 좋아요 여부 / 싫어요 여부 표시
//        내 좋아요 : 좋아요 빨강 / 싫어요 회색
//        내 싫어요 : 좋아요 회색 / 싫어요 파랑
//        그 외 (둘다 안찍은경우) : 좋아요 싫어요 둘다 회색
//        글씨 색도 같은 색으로.

        if (data.isMyLike) {
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.naverRed))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.gray))

        }
        else if (data.isMyDislike) {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)


            likeBtn.setTextColor(mContext.resources.getColor(R.color.gray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.naverBlue))
        }
        else {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)


            likeBtn.setTextColor(mContext.resources.getColor(R.color.gray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.gray))
        }


//        선택 진영 정보 반영

        selectedSideTitleTxt.text = "(${data.selectedSide.title})"

//        좋아요 / 싫어요 클릭 이벤트 처리

        val likeOrDislikeEvent = View.OnClickListener {

//            좋아요 : is_like - true
//            싫어요 : is_like - false


//            눌린 버튼의 id값이 => R.id.like인지 비교. 맞다면 true (좋아요 버튼) 아니라면 (싫어요버튼)
            val isLike = it.id == R.id.likeBtn

            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, isLike, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    목록을 뿌릴때 쓰는 data와 이름이 겹쳐서 변경 (dataObj) => data도 활용 예정
                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

//                    data변수 내부 값중 좋아요 /싫어요 갯수 변경
                    data.likeCount = reply.getInt("like_count")
                    data.dislikeCount = reply.getInt("dislike_count")

//                    data변수 내부 값중 내 좋아요 / 싫어요 변경
                    data.isMyLike = reply.getBoolean("my_like")
                    data.isMyDislike = reply.getBoolean("my_dislike")

//                    리스트뷰에 뿌려지는 데이터에 내용 변경 => notifyDataSetChanged 필요
//                    어댑터변수.notify~ 실행. 어댑터변수 X.

//                    어댑터 내부에서는 직접 새로고침 가능 => runOnUiThread 필요

//                    runUiThread 대체재
                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()
                    }

                }
            })

        }

//        좋아요도 싫어요도 할일이 likeOrDislikeEvent 안에 적혀있다.
        likeBtn.setOnClickListener(likeOrDislikeEvent)
        dislikeBtn.setOnClickListener(likeOrDislikeEvent)

//        답글 버튼을 누르면 의견 상세 조회 화면으로 이동

        replyBtn.setOnClickListener {
            val myIntent = Intent(mContext, ViewReplyDetailActivity::class.java)
            myIntent.putExtra("replyId", data.id)
            mContext.startActivity(myIntent)
        }


//        완성된 row를 리스트뷰의 재료로 리턴
        return row
        
    }

}