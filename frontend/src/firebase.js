import firebase from 'firebase/app'
// import 'firebase/auth'
import 'firebase/firestore'

// import Filter from 'bad-words'
import { ref, onUnmounted } from 'vue'

import { myConfig } from './firebaseConfig.js'
firebase.initializeApp(myConfig)

// const auth = firebase.auth()

// export function useAuth() {
//   const user = ref(null)
//   const unsubscribe = auth.onAuthStateChanged(_user => (user.value = _user))
//   onUnmounted(unsubscribe)
//   const isLogin = computed(() => user.value !== null)

//   const signIn = async () => {
//     const googleProvider = new firebase.auth.GoogleAuthProvider()
//     await auth.signInWithPopup(googleProvider)
//   }
//   const signOut = () => auth.signOut()

//   return { user, isLogin, signIn, signOut }
// }

const firestore = firebase.firestore()
// const filter = new Filter()

export function useChat(collectionId) {
  const messages = ref([])
  const messagesCollection = firestore.collection(collectionId)
  const messagesQuery = messagesCollection.orderBy('createdAt', 'desc').limit(100)

  const unsubscribe = messagesQuery.onSnapshot(snapshot => {
    messages.value = snapshot.docs
      .map(doc => ({ id: doc.id, ...doc.data() }))
      .reverse()
  })
  onUnmounted(unsubscribe)

  // const { user, isLogin } = useAuth()
  const sendMessage = (text, userId) => {
    // if (!isLogin.value) return
    // const { photoURL, uid, displayName } = user.value
    
    messagesCollection.add({
      userId: userId,
      text: text,
      createdAt: firebase.firestore.FieldValue.serverTimestamp()
    })
  }

  return { messages, sendMessage }
}