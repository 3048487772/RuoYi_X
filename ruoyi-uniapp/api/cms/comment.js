import {request} from '@/common/request'

// 首页查询评论列表
export function cmsListComment(query) {
  return request({
    'url': '/cms/comment/cms/list',
    'method': 'get',
    'param': query
  })
}

// 首页新增评论
export function cmsAddComment(data) {
  return request({
    'url': '/cms/comment/cms/addComment',
    'method': 'post',
    'data': data
  })
}

// 首页新增点赞
export function addCmsCommentLike(data) {
  return request({
    'url': '/cms/comment/cms/addCmsCommentLike',
    'method': 'post',
    'data': data
  })
}

// 首页删除点赞
export function delCmsCommentLike(data) {
  return request({
    'url': '/cms/comment/cms/delCmsCommentLike',
    'method': 'post',
    'data': data
  })
}
