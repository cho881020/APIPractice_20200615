package kr.co.tjoeun.apipractice_20200615.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Notification {

    var id = 0
    var receiveUserId = 0
    var actUserId = 0
    var title = ""
    var type = ""
    var message = ""
    var referenceUi = ""
    var focusObjectId = 0
    val createdAt = Calendar.getInstance()

    companion object {

        fun getNotificationFromJson(json : JSONObject) : Notification {

            val noti = Notification()

            noti.id = json.getInt("id")
            noti.receiveUserId = json.getInt("receive_user_id")
            noti.actUserId = json.getInt("act_user_id")
            noti.title = json.getString("title")
            noti.message = json.getString("message")
            noti.type = json.getString("type")
            noti.referenceUi = json.getString("reference_ui")
            noti.focusObjectId = json.getInt("focus_object_id")

//            서버의 String 시간 => Kotlin Date (Sdf) => noti.createAt의 시간으로 반영.
            val createAtStr = json.getString("created_at")

//            파싱용 양식 생성
            val parsingFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            noti.createdAt.time = parsingFormat.parse(createAtStr)

            val myPhoneTimeZone = noti.createdAt.timeZone
            val timeOffset = myPhoneTimeZone.rawOffset / 1000 / 60 / 60
            noti.createdAt.add(Calendar.HOUR, timeOffset)

            return noti
        }

    }

}