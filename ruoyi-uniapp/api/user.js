import { request } from '@/common/request'
import { praseStrEmpty } from "@/common/ruoyi";
// 查询用户个人信息
export function getUserProfile() {
  return request({
    'url': '/system/user/profile',
    'method': 'get',
  })
}
// 修改用户
export function updateUser(param) {
  return request({
    'url': '/system/user/profile',
    'method': 'PUT',
    'param': param
  })
}
// 用户密码重置
export function resetUserPwd(oldPassword, newPassword) {
  const param = {
    oldPassword,
    newPassword
  }
  return request({
    'url': '/system/user/profile/updatePwd',
    'method': 'PUT',
    'params': param
  })
}
// 用户头像上传
export function uploadAvatar(data) {
  return request({
    'url': '/system/user/profile/avatar',
    'method': 'post',
    'data': data
  })
}
// 查询用户详细
export function getUser(userId) {
  return request({
    'url': '/system/user/' + praseStrEmpty(userId),
    'method': 'get'
  })
}