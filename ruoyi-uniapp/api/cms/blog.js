import {request} from '@/common/request'
import { praseStrEmpty } from "@/common/ruoyi";

// 首页查询文章列表
export function cmsListBlog(query) {
  return request({
    'url': '/cms/blog/cms/cmsList',
    'method': 'get',
    'param': query
  })
}

// 首页查询文章详细
export function getBlogDetail(id) {
  return request({
    'url': '/cms/blog/cms/detail/' + praseStrEmpty(id),
    'method': 'get'
  })
}

// 首页按分类查询文章列表
export function cmsListByTypeId(id) {
  return request({
    'url': '/cms/blog/cms/cmsListByType/' + id,
    'method': 'get'
  })
}

// 首页按标签查询文章列表
export function cmsListByTagId(id) {
  return request({
    'url': '/cms/blog/cms/cmsListByTag/' + id,
    'method': 'get'
  })
}

// 首页查询推荐文章列表
export function cmsListRecommend(query) {
  return request({
    'url': '/cms/blog/cms/cmsListRecommend',
    'method': 'get',
    'param': query
  })
}

// 首页增加阅读量
export function addBlogViews(id) {
  return request({
    'url': '/cms/blog/cms/addBlogViews/' + id,
    'method': 'get'
  })
}

// 随笔页查询文章列表
export function cmsEssayList(query) {
  return request({
    'url': '/cms/blog/cms/cmsEssayList',
    'method': 'get',
    'param': query
  })
}

