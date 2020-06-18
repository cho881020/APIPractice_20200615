package kr.co.tjoeun.apipractice_20200615.datas

import org.json.JSONObject

// 토론에서 선택 가능 진영 정보 저장 클래스
class TopicSide {

    var id = 0
    var topicId = 0
    var title = ""
    var voteCount = 0

    companion object {

//        json 덩어리 input => 내용이 모두 적힌 TopicSide 객체 output

        fun getTopicSideFromJson(json: JSONObject) : TopicSide {
            val ts = TopicSide()

            ts.id = json.getInt("id")
            ts.topicId = json.getInt("topic_id")
            ts.title = json.getString("title")
            ts.voteCount = json.getInt("vote_count")

            return ts
        }
    }

}