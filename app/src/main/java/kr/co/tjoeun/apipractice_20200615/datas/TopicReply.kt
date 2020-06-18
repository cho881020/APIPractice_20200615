package kr.co.tjoeun.apipractice_20200615.datas

import org.json.JSONObject

class TopicReply {

    var id = 0
    var content = ""
    var topicId = 0
    var sideId = 0
    var userId = 0

    lateinit var writer : User

    companion object {

        fun getTopicReplyFromJson(json: JSONObject) : TopicReply {
            val tr = TopicReply()

            tr.id = json.getInt("id")
            tr.content = json.getString("content")
            tr.topicId = json.getInt("topic_id")
            tr.sideId = json.getInt("side_id")
            tr.userId = json.getInt("user_id")

            val userObj = json.getJSONObject("user")
            tr.writer = User.getUserFromJson(userObj)

            return tr
        }

    }

}