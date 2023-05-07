import config from '@/config'
import store from '@/store/store'
import { toast, showConfirm } from '@/common/common'
const errorCode = {
  '401': '认证失败，无法访问系统资源',
  '403': '当前操作没有权限',
  '404': '访问资源不存在',
  'default': '系统未知错误，请反馈给管理员'
}
let isShow = false
const request = data => {
  data.header = data.header || {}
 
  data.header['Authorization'] = 'Bearer ' + store.state.token
  
  // get请求映射params参数
  if(data.params) {
    let url = data.url + '?'
    for(const propName of Object.keys(data.params)) {
      const value = data.params[propName]
      var part = encodeURIComponent(propName) + '='
      if(value !== null && typeof value !== 'undefined') {
        if(typeof value === 'object') {
          for(const key of Object.keys(value)) {
            if(value[key] !== null && typeof value[key] !== 'undefined') {
              let params = propName + '[' + key + ']'
              let subPart = encodeURIComponent(params) + '='
              url += subPart + encodeURIComponent(value[key]) + '&'
            }
          }
        } else {
          url += part + encodeURIComponent(value) + '&'
        }
      }
    }
    url = url.slice(0, -1)
	// if(url.lastIndexOf('dev-api')!==-1){
	// 	let len = url.length
	// 	let Uindex = url.lastIndexOf('dev-api')
	// 	url = url.substring(Uindex,len)
	// } else if(url.lastIndexOf('prod-api')!==-1){
	// 	let len = url.length
	// 	let Uindex = url.lastIndexOf('prod-api')
	// 	url = url.substring(Uindex,len)
	// }
    data.url = url
  }
  return new Promise((resolve, reject) => {
    uni.request({
        method: data.method || 'get',
        url: config.baseUrl + data.url,
        data: data.param,
        header: data.header,
        dataType: 'json',
      })
      .then(response => {
        let [error, res] = response
        uni.stopPullDownRefresh()
        uni.hideLoading()
        if(error) {
          toast('后端接口连接异常')
          reject('后端接口连接异常')
          return
        }
        const msg = errorCode[code] || res.data.msg || errorCode['default']
        const code = res.data.code || 200
        if(code === 401 && !isShow) {
          isShow = true
          showConfirm('登录状态已过期，您可以继续留在该页面，或者重新登录?')
            .then(res => {
              if(res.confirm) {
                store.dispatch('LogOut')
                  .then(res => {
                    uni.reLaunch({ url: '/pages/login/login' })
                  })
              }
            })
            .catch(() => {
              isShow = false
            })
          reject('无效的会话，或者会话已过期，请重新登录。')
        } else if(code === 500) {
          toast(msg)
          reject('500')
        } else if(code !== 200) {
          toast(msg)
          reject(code)
        }
        resolve(res.data)
      })
      .catch(error => {
        uni.stopPullDownRefresh()
        uni.hideLoading()
        let { message } = error
        if(message === 'Network Error') {
          message = '后端接口连接异常'
        } else if(message.includes('timeout')) {
          message = '系统接口请求超时'
        } else if(message.includes('Request failed with status code')) {
          message = '系统接口' + message.substr(message.length - 3) + '异常'
        }
        toast(message)
        reject(error)
      })
  })
}
const upload = data => {
  let header = {}
  header['Authorization'] = 'Bearer ' + store.state.token
  return new Promise((resolve, reject) => {
    uni.uploadFile({
      url: config.baseUrl + data.url,
      filePath: data.path,
      name: name,
      header: header,
      success: response => {
        uni.stopPullDownRefresh()
        uni.hideLoading()
        var res = JSON.parse(response.data)
        if(res.code === 200) {
          resolve(res)
        } else if(res.code === 401) {
          showConfirm('登录状态已过期，您可以继续留在该页面，或者重新登录?')
            .then(res => {
              if(res.confirm) {
                store.dispatch('LogOut')
                  .then(res => {
                    uni.reLaunch({ url: '/pages/login/login' })
                  })
              }
            })
          reject('无效的会话，或者会话已过期，请重新登录。')
        } else if(res.code === 500) {
          toast(res.msg)
          reject('500')
        }
      },
      fail: error => {
        uni.stopPullDownRefresh()
        uni.hideLoading()
        toast(error.errMsg)
        reject(error.errMsg)
      }
    })
  })
}
module.exports = { request, upload }
