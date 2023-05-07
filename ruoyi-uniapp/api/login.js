import { request } from '@/common/request'
// 登录方法
export function login(param) {
  return request({
    'url': '/login',
    'method': 'POST',
    'param': param
  })
}
// 获取用户详细信息
export function getInfo() {
  return request({
    'url': '/getInfo',
    'method': 'get'
  })
}
// 退出方法
export function logout() {
  return request({
    'url': '/logout',
    'method': 'POST'
  })
}
// 获取验证码
export function getCodeImg() {
  return request({
    'url': '/captchaImage',
  })
}
