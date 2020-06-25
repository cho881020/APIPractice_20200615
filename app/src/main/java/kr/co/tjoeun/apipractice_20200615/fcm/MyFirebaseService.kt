package kr.co.tjoeun.apipractice_20200615.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseService : FirebaseMessagingService() {

//    토큰이 새로 발급되면 실행되는 함수. (보통 앱 최초 실행시 2~3초 이후에 실행됨)

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.d("새 토큰 발급", p0)

    }

}