import Vue from 'vue'
import Vuex from 'vuex'
// import {getRequest} from "@/utils/api";
// import SockJS from 'sockjs-client'
// import Stomp from 'stompjs'

Vue.use(Vuex)
const now = new Date();

const store = new Vuex.Store({
    state:{
        routes:[],
        // sessions:[],
        // admins:[],
        // currentSessionId:-1,
        // filterKey:'',
        // stomp:null,
    },
    getters:{

    },
    mutations:{
        initRoutes(state,data){
            state.routes = data;
        },
        // changeCurrentSessionId (state,id) {
        //     state.currentSessionId = id;
        // },
        // addMessage (state,msg) {
        //     state.sessions[state.currentSessionId-1].messages.push({
        //         content:msg,
        //         date: new Date(),
        //         self:true
        //     })
        // },
        // INIT_DATA (state) {
        //     //浏览器本地聊天记录
        //     // let data = localStorage.getItem('vue-chat-session');
        //     // //console.log(data)
        //     // if (data) {
        //     //     state.sessions = JSON.parse(data);
        //     // }
        // },
        // INIT_ADMINS(state,data){
        //     state.admins = data
        // },
    },
    actions:{
        // initData (context) {
        //     getRequest('/chat/admin').then(resp=>{
        //         if(resp){
        //             context.commit('INIT_ADMINS',resp)
        //         }
        //     })
        //     context.commit('INIT_DATA')
        // },
        // connect(context){
            // context.state.stomp = Stomp.over(new SockJS('/ws/ep'));
            // let token = sessionStorage.getItem('tokenStr')
            // context.state.stomp.connect({'Auth-Token':token},success=>{
            //     context.state.stomp.subscribe('user/queue/chat',msg=>{
            //         console.log(msg.body);
            //     })
            // },error=>{
            //
            // })
        // }
    }
})


// store.watch(function (state) {
//     return state.sessions
// },function (val) {
//     console.log('CHANGE: ', val);
//     localStorage.setItem('vue-chat-session', JSON.stringify(val));
// },{
//     deep:true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
// })


export default store;
// export default new Vuex.Store({
//     state:{
//         routes:[]
//     },
//     mutations:{
//         initRoutes(state,data){
//             state.routes = data;
//         }
//     },
//     actions:{
//
//     }
// })