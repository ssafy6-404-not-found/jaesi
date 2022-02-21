<template>
  <div class="w-100 h-100 position-absolute bg-white top-0 start-0">
    <div id="session" v-if="session">
      <div class="d-grid" style="grid-template-columns: 1fr 400px;">
        <!-- 라이브 방송 부분 -->
        <div id="main-video" class="main-video w-100 d-grid" style="grid-template-rows: 1fr 160px;">
          <div id="video-container" class="m-3">
            <user-video
              v-if="isHost"
              :stream-manager="publisher"
              @click="updateMainVideoStreamManager(publisher)"
            />
            <user-video
              v-else
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              :stream-manager="sub"
              @click="updateMainVideoStreamManager(sub)"
            />
          </div>
          <div id="live-info" class="d-flex justify-content-between">
            <div class="d-flex">
              <img :src="unKnown" alt="" width="80" height="80">
              <div class="d-flex flex-column text-start ps-2">
                <span style="font-size: 22px;">{{ video.hostNickname }}</span>
                <span style="font-size: 18px;">{{ video.title }}</span>
                <span style="font-size: 18px;">{{ video.description }}</span>
                <div>
                  <span class="font-bold">현재 시청자수 : </span>
                  <span class="font-bold"> {{ numberOfElements }}</span
                  ><span class="font-bold"> 명 </span>
                </div>
              </div>
            </div>
            <div id="live-controller">
              음소거
              영상송출 정지
              채팅방 ON/OFF
              설정
              <div class="mb-4">
                <div class="Live-button">
                  <input
                    v-if="!isHost"
                    class="btn-out mt-4"
                    type="button"
                    id="buttonLeaveSession"
                    @click="leaveSession"
                    value="라이브 나가기"
                  />
                  <input
                    v-if="isHost"
                    class="btn-end mt-4"
                    type="button"
                    id="buttonEndSession"
                    @click="endSession"
                    value="라이브 종료"
                  />
                </div>
              </div>
            </div>
            
          </div> 
        </div>

        <!-- 채팅 부분 -->
        <live-chat class="m-3"
          :msgs="msgs"
          :hostId="mySessionId"
          :myUserId="myUserId"
          :myUserNickname="myUserNickname"
          @send-message="sendMessage" 
        />
      </div>
    </div>
    <canvas id="canvas" class="d-none" />
  </div>
</template>

<script>
import { computed } from 'vue'

import axios from "axios"
import { OpenVidu } from "openvidu-browser"
import UserVideo from "./OpenVidu/User-video"

import firebase from 'firebase/app'
import 'firebase/storage'

import LiveChat from './Chat/Chat'

import unKnown from '@/components/Chat/Components/unKnown.png'

axios.defaults.headers.post["Content-Type"] = "application/json"

const SERVER_URL = process.env.VUE_APP_SERVER_URL
const OPENVIDU_SERVER_URL = process.env.VUE_APP_OPENVIDU_SERVER_URL
const OPENVIDU_SERVER_SECRET = process.env.VUE_APP_OPENVIDU_SERVER_SECRET

export default {
  name: "LiveRoom",
  components: {
    UserVideo,
    LiveChat,
  },
  data: function() {
    return {
      // OpenVidu Setting
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      
      // mySessionId 가 OpenVidu에서 활용하는 Conference별 이름같은 것
      // 목록에서 넘어올 때 클릭 시 해당 값으로 대체해야함
      mySessionId: '', 
      myUserId: 0,
      myUserNickname: '',
      myLiveRoomId: 0,

      unKnown,
      
      //chatting
      msgs: [],
      vOnOff: true,
      aOnOff: true,
      size: true,
      chatting: false,
      isHost: false,

      video: Object,
      // title: "",
      // discription: "",
      // RoomInfo: [],
      connections: [],
      numberOfElements: 0,

      intervalID: '',
    }
  },
  mounted: function() {
    this.mySessionId = this.$route.params.hostId
    this.myLiveRoomId = this.$route.params.liveRoomId
    // 비디오 정보 가져오기

    this.video = this.$route.params
    if(!this.video.hostNickname) {
      location.href="/live/"
    }

    var userInfo = { ...this.$store.state.Login.userInfo }
    if (userInfo) {
      this.myUserId = userInfo.userId
      this.myUserNickname = userInfo.nickname
    }

    this.isHost = this.myUserId == this.mySessionId ? true : false

    this.joinSession()

    if(this.isHost){
      setTimeout(() => {
        this.intervalID = setInterval(this.sendLiveData(), 300000)
      }, 30000);
    }
  },
  methods: {
    // 채팅관련함수
    toggleChat: function() {

    },
    chattingOnOff() {
      this.chatting = !this.chatting;
      console.log(" 공유 여부 ");
      console.log(this.subscribers[1].stream.typeOfVideo)
    },
    sendMessage(msg) {
      // Sender of the message (after 'session.connect')
      this.session
        .signal({
          data: msg, // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: "my-chat", // The type of message (optional)
        })
        .then(() => {
          console.log("Message successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },
    
    // OpenVidu 관련 함수
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();
      // --- Init a session ---
      this.session = this.OV.initSession();
      // --- Specify the actions when events take place in the session ---
      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream)
        this.subscribers.push(subscriber)
      });
      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0)
        if (index >= 0) {
          this.subscribers.splice(index, 1)
        }
      });
      this.session.on("connectionCreated", ({ stream }) => {
        this.numberOfElements++
      });
      this.session.on("sessionDisconnected", ({ stream }) => {
        // const subscriber = this.session.subscribe(stream);
        // this.subscribers.push(subscriber);
        alert("라이브가 종료되었습니다.")
        location.href = "/live/"
      });
      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        // alert("종료된 방송입니다.")
        // 여러번 호출되는데?
        // location.href = "/live/"
        console.warn(exception)
      });
      this.session.on("signal:my-chat", (event) => {
        // console.log(event.data); // Message
        // console.log(event.from); // Connection object of the sender
        // console.log(event.type); // The type of message ("my-chat")
        const tmp = this.msgs.slice()
        tmp.push(event.data)
        this.msgs = tmp
      });
      // --- Connect to the session with a valid user token ---
      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "1280x720", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });
            this.mainStreamManager = publisher;
            this.publisher = publisher;
            // --- Publish your stream ---
            this.session.publish(this.publisher);
            // hit 수 증가
            axios({
              method: "put",
              url: `${SERVER_URL}/live/hit`,
              data: { 
                hostId: this.mySessionId,
              },
            })
              .then((res) => {
                console.log(res)
              })
              .catch((err) => {
                console.log(err)
              })
          })
          .catch((error) => {
            console.log("There was an error connecting to the session:", error.code, error.message);
          });
      });
      window.addEventListener("beforeunload", this.leaveSession);
    },
    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---

      if(this.isHost) {
        this.endSession()
      }
      if (this.session) this.session.disconnect();
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      this.numberOfElements--;
      console.log(this.numberOfElements);
      clearInterval(this.intervalID)
      // host일 경우 endsession
      window.removeEventListener("beforeunload", this.leaveSession);

      // this.$emit('leave-live')
    },
    // 라이브 종료
    endSession() {
      let mySessionId = this.mySessionId
      if (this.isHost && confirm("라이브를 종료하시겠습니까?")) {
        // 권한 체크
        axios({
          method: "delete",
          url: `${SERVER_URL}/live`,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('jwt')}`
          },
          data: { 
            liveRoomId: this.myLiveRoomId,
          },
        })
          .then((res) => {
            console.log(res)
            axios
              .delete(
                `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${mySessionId}`,
                {
                  auth: {
                    username: "OPENVIDUAPP",
                    password: OPENVIDU_SERVER_SECRET,
                  },
                }
              )
              .then((response) => {
                // this.$emit('leave-live')
                location.href = "/live"
              })
              .catch((error) => {
                if (error.response.status === 404) {
                  console.log("실패~!~!!~")
                }
              });
          })
          .catch((err) => {
            // 에러 종류 2개
            console.log(err)
            console.log("실패함")
          })
      }
    },
    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return
      this.mainStreamManager = stream
    },
    deleteMainVideoStreamManager() {
      this.mainOnOff = false
    },
    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */
    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) => this.createToken(sessionId))
    },
    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId)

            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              )
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`)
              }
              reject(error.response)
            }
          })
        // axios.get("/live/detail/" + this.mySessionId).then((res) => {
        //   console.log(res.data);
        //   console.log(res.data.title);
        //   this.RoomInfo = res.data;
        // });
        let mySessionId = this.mySessionId;
      });
    },
    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      let myrole = this.myUserId == sessionId ? "PUBLISHER" : "SUBSCRIBER"
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {
              role: myrole,
            },
            {
                auth: {
                  username: "OPENVIDUAPP",
                  password: OPENVIDU_SERVER_SECRET,
                },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
    sendLiveData: function() {
      // thumbnail 캡쳐, 구글에 전송
      if(this.isHost) {
        // 이미지 등록 프로세스, url 가져오기
        const video = document.querySelector('#video')
        const canvas = document.querySelector('#canvas')

        var context = canvas.getContext('2d')
        context.drawImage(video, 0, 0, 320, 180)

        canvas.toBlob(blob => {
          var image = new Image();
          image.src = blob
          const storageRef = firebase.storage().ref(`images/thumbnail-${this.mySessionId}.png`).put(blob)
          storageRef.on("state_changed", 
          snapshot => {
            console.log(snapshot);
          },
          error => {
            console.log(error);
          },
          () => {
            storageRef.snapshot.ref.getDownloadURL().then( url => {
              axios({
                method: "put",
                url: `${SERVER_URL}/live/update`,
                headers: {
                  Authorization: `Bearer ${localStorage.getItem('jwt')}`
                },
                data: {
                  hostId: this.mySessionId,
                  realTimeViewers: this.numberOfElements,
                  thumbnail: url
                },
              })
                .then((res) => {
                  console.log(res)
                })
                .catch((err) => {
                  console.log(err)
                })
            })
          })      
        }, 'image/png')

      }
      setTimeout(() => {
        this.sendLiveData()
      }, 300000);
    }
  },
};
</script>
<style>
.LiveImg {
  display: inline;
}
.btn-chat {
  color: white;
  width: 100%;
  height: 30px;
  background-color: #a4b5f0;
  border-right: #a4b5f0 1px solid;
  border-left: #a4b5f0 1px solid;
  border-top: #a4b5f0 1px solid;
  border-bottom: #a4b5f0 1px solid;
  border-style: none;
  border-radius: 12px;
  margin-bottom: 3px;
}
.btn-out {
  color: rgb(255, 255, 255);
  width: 150px;
  height: 50px;
  background-color: #b2abee;
  border-style: none;
  border-radius: 12px;
  margin-left: 10px;
}
.btn-end {
  color: rgb(255, 255, 255);
  width: 150px;
  height: 50px;
  background-color: #fe9090;
  border-style: none;
  border-radius: 12px;
  margin-left: 10px;
}
.RoomInfo {
  padding-top: 3%;
  font-weight: bold;
  font-size: 30px;
  text-align: center;
}
.RoomInfo-con {
  /* //font-weight: bold; */
  font-size: 18px;
}
.Live-button {
  text-align: center;
}
.title-live {
  padding-top: 8%;
}
</style>
