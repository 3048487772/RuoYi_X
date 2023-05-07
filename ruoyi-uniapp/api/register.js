import { request } from '@/common/request'
// 注册方法
export function register(param) {
  return request({
    'url': '/register',
    'method': 'POST',
    'param': param
  })
}
