package kr.co.tjoeun.apipractice_20200615.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {

//    토큰이 새로 발급되면 실행되는 함수. (보통 앱 최초 실행시 2~3초 이후에 실행됨)

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.d("새 토큰 발급", p0)

    }

//    실제 푸시알림 수신시 실행되는 함수
//    앱을 화면에 띄운 상태에서 수신시에 실행되는 함수

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Log.d("푸시알림 수신", p0.notification?.title)
    }


}