import Vue from 'vue'
import Vuex from 'vuex'
import {
  login,
  logout,
  getInfo
} from "@/api/login.js"
import config from '@/config'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    //所有参数需要前后对应
    token: uni.getStorageSync("token"),
    permissions: uni.getStorageSync("permissions") || ['test'],
    roles: uni.getStorageSync("roles") || ['test'],
    userInfo: uni.getStorageSync("userInfo") || {},
  },
  mutations: {
    update(state, [key, value]) {
      state[key] = value;
      uni.setStorageSync(key, value)
    },
  },
  actions: {
    // 登录
    Login({ commit }, userInfo) {
      userInfo.username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(userInfo)
          .then(res => {
            commit('update', ['token', res.token])
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo()
          .then(res => {
            const user = res.user
            user.avatar = user.avatar == '' ? require('@/static/img/profile.jpg') : config.baseUrl + user.avatar
            if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
              commit('update', ['roles', res.roles])
              commit('update', ['permissions', res.permissions])
            } else {
              commit('update', ['roles', 'ROLE_DEFAULT'])
            }
            commit('update', ["userInfo", user])
            resolve(res)
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token)
          .then(() => {
            uni.clearStorageSync()
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },
  }
})
export default store
