import {request} from '@/common/request'

// 首页查询留言列表
export function cmsListMessage(query) {
  return request({
    'url': '/cms/message/cms/list',
    'method': 'get',
    'param': query
  })
}

// 首页新增留言
export function cmsAddMessage(data) {
  return request({
    'url': '/cms/message/cms/addMessage',
    'method': 'post',
    'data': data
  })
}

// 首页新增点赞
export function addCmsMessageLike(data) {
  return request({
    'url': '/cms/message/cms/addCmsMessageLike',
    'method': 'post',
    'data': data
  })
}

// 首页删除点赞
export function delCmsMessageLike(data) {
  return request({
    'url': '/cms/message/cms/delCmsMessageLike',
    'method': 'post',
    'data': data
  })
}
