<template>
  <view class="content">
    <scroll-view class="scroll" scroll-y>
      <view class="scroll-content">
        <view class="introduce-section" v-show="detailLoading === false">
          <text class="title">{{detailData.title}}</text>
          <view class="introduce">
            <text>{{detailData.createBy}}</text>
            <text>{{detailData.views}}阅读</text>
            <text>{{detailData.createTime}}</text>
          </view>
          <rich-text :nodes="detailData.content" class="typo m-padded-lr-responsive m-padded-tb-large ql-editor"></rich-text>
          <view class="tags" v-show="detailData.types!==null">
			<view class="tag-item" style="margin-left: 0px;color: #909399;">分类:</view>
            <view class="tag-item" v-for="tag in detailData.types" :key="tag.typeId">
				<view class="tag">
				  {{tag.typeName}}
				</view>
            </view>
          </view>
        </view>
        <view class="container" v-show="loading === false">
          <!-- 推荐 -->
          <view class="s-header">
            <text class="tit">最新推荐</text>
          </view>
          <view class="rec-section" v-for="item in newsList" :key="item.id" @click="navToDetails(item)">
            <view class="rec-item">
              <view class="left">
                <text class="title">{{item.title}}</text>
                <view class="bot">
                  <text class="author">{{item.createBy}}</text>
                  <text class="time">{{item.createTime}}</text>
                </view>
              </view>
              <view class="right">
                <image class="img" :src="item.blogPic" mode="aspectFill"></image>
              </view>
            </view>
          </view>
          <!-- 评论 -->
          <view class="s-header">
            <text class="tit">最新评论</text>
          </view>
          <view class="evalution">
            <view v-for="(item, index) in evaList" :key="index" class="eva-item">
				<view style="display: flex;">
					<image :src="item.avatar" mode="aspectFill"></image>
					<view class="eva-right">
					  <text>{{item.createBy}}</text>
					  <text>{{item.createTime}}</text>
					  <view class="zan-box">
					    <text style="font-size: 14px;margin-right: 5px;">{{item.likeNum}}</text>
						<image style="width: 18px; height: 18px;color: #909399;" src="../../static/fabulous.png"></image>
					  </view>
					  <text class="content">{{item.content}}</text>
					</view>
				</view>
			  <view v-for="(reply, index) in item.children" :key="index" class="eva-item" style="margin-left: 20px;">
				  <view style="display: flex;">
					<image :src="reply.avatar" mode="aspectFill" style="width: 20px; height: 20px;"></image>
					<view class="eva-right">
					  <text v-show="reply.parentId===reply.mainId">{{reply.createBy}}</text>
					  <text v-show="reply.parentId!==reply.mainId">{{reply.createBy}}@{{reply.pcreateBy}}</text>
					  <text>{{reply.createTime}}</text>
					  <span v-show="reply.delFlag=='0'">{{reply.content}}</span>
					  <span v-show="reply.delFlag=='1'" style="color: #909399;">该评论已被删除！</span>
					</view>
				</view>
			  </view>
            </view>
          </view>
        </view>
        <!-- 加载图标 -->
        <mixLoading class="mix-loading" v-if="loading"></mixLoading>
      </view>
    </scroll-view>
    <view class="bottom">
      <view class="input-box">
        <text class="yticon icon-huifu"></text>
        <input class="input" type="text" placeholder="点评一下吧" placeholder-style="color:#adb1b9;" />
      </view>
      <text class="confirm-btn">提交</text>
    </view>
  </view>
</template>
<script>
import mixLoading from '@/components/mix-loading/mix-loading';
import { getBlogDetail, cmsListRecommend,addBlogViews } from '@/api/cms/blog';
import { cmsListComment, cmsAddComment,addCmsCommentLike,delCmsCommentLike } from '@/api/cms/comment';
export default {
  components: {
    mixLoading
  },
  data() {
    return {
	  detailLoading: true,
      loading: true,
      detailData: {},
      newsList: [],
      evaList: [],
	  // 查询参数
	  queryParams: {
	    pageNum: 1,
	    pageSize: 10,
	    title: null,
	    type: 1,
	    content: null,
	    top: null,
	    views: null,
	    status: null,
	    createBy: null
	  },
	  // 总条数
	  totalComment: 0,
	  // 查询参数
	  queryParamsComment: {
	    pageNum: 1,
	    pageSize: 10,
	    parentId: null,
	    mainId: null,
	    likeNum: null,
	    content: null,
	    type: null,
	    blogId: null,
	    userId: null,
	    delFlag: null,
	    createBy: null,
	  },
    }
  },
  onLoad(options) {
    this.detailData = JSON.parse(options.data);
	this.queryParamsComment.blogId = this.detailData.id
	getBlogDetail(this.detailData.id).then(response => {
		if(response.data.content===null){
			response.data.content=''
		}
	    this.detailData = response.data
		this.detailLoading = false;
	  });
    this.loadNewsList();
    this.loadEvaList();
  },
  methods: {
    //获取推荐列表
    async loadNewsList() {
		cmsListRecommend(this.queryParams).then(response => {
		  let list = this.picSrc(response.rows);;
		  this.newsList = list.slice(0, 4);
		  this.loading = false;
		});
    },
    //获取评论列表
    async loadEvaList() {
		cmsListComment(this.queryParamsComment).then(response => {
		  for (let i = 0; i < response.rows.length; i++) {
		    let mesInfo = response.rows[i];
			if(mesInfo.likeNum===null){
				mesInfo.likeNum = 0
			}
		    if (mesInfo.avatar != null && mesInfo.avatar != "") {
		      response.rows[i].avatar = config.baseUrl + mesInfo.avatar
		    }else{
			  response.rows[i].avatar = '/static/user.png'
			}
		    if (mesInfo.children != null && mesInfo.children != "") {
		      for (let j = 0; j < response.rows[i].children.length; j++) {
		        let children = response.rows[i].children;
		        if (children.avatar != null && children.avatar != "") {
		          response.rows[i].children[j].avatar = config.baseUrl + children.avatar
		        }else{
				  response.rows[i].children[j].avatar = '/static/user.png'
				}
		      };
		    }
		  };
		  this.evaList = response.rows;
		  this.totalComment = response.total;
		});
    },
	//首图地址修改
	picSrc(blogList){
	  for (let i = 0; i < blogList.length; i++) {
	    let blogInfo = blogList[i];
	    if (blogInfo.blogPic.length > 0) {
	      blogList[i].blogPic = config.baseUrl + blogInfo.blogPic
	    }else{
	      blogList[i].blogPic = '/static/errorImg.jpg'
	    }
	  };
	  return blogList
	},
	//博客详情
	navToDetails(item) {
	  // 增加阅读量
	  addBlogViews(item.id);
	  let data = {
	    id: item.id,
	  }
	  uni.navigateTo({
	    url: `/pages/details/blogDetails?data=${JSON.stringify(data)}`
	  })
	},
  }
}
</script>
<style lang="scss" scoped>
page {
  height: 100%;
}

.content {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: #fff;
}

.video-wrapper {
  height: 422rpx;

  .video {
    width: 100%;
    height: 100%;
  }
}

.scroll {
  flex: 1;
  position: relative;
  background-color: #f8f8f8;
  height: 0;
}

.scroll-content {
  display: flex;
  flex-direction: column;
}

/* 简介 */
.introduce-section {
  display: flex;
  flex-direction: column;
  padding: 20upx 30rpx;
  background: #fff;
  line-height: 1.5;

  .title {
    font-size: 36rpx;
    color: #303133;
    margin-bottom: 16rpx;
  }

  .introduce {
    display: flex;
    font-size: 26rpx;
    color: #909399;

    text {
      margin-right: 16rpx;
    }
  }
}

 .tags {
    display: flex;
    align-items: center;
	overflow: auto;
	margin-top: 20px;
  }

  .tag-item {
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-left: 10px;
    margin-bottom: 20px;
	font-size: 26rpx;
  }

  .tag {
    padding-left: 10px;
    padding-right: 10px;
    border-radius: 5px;
    background-color: #f4f4f5;
    border: 1px solid #e9e9eb;
    color: #909399;
    display: flex;
	font-size: 26rpx;
  }

.s-header {
  padding: 20upx 30rpx;
  font-size: 30rpx;
  color: #303133;
  background: #fff;
  margin-top: 16rpx;

  &:before {
    content: '';
    width: 0;
    height: 40rpx;
    margin-right: 24rpx;
    border-left: 6upx solid #ec706b;
  }
}

/* 推荐列表 */
.rec-section {
  background-color: #fff;

  .rec-item {
    display: flex;
    padding: 20upx 30rpx;
    position: relative;

    &:after {
      content: '';
      position: absolute;
      left: 30rpx;
      right: 0;
      bottom: 0;
      height: 0;
      border-bottom: 1px solid #eee;
      transform: scaleY(50%);
    }
  }

  .left {
    flex: 1;
    padding-right: 10rpx;
    overflow: hidden;
    position: relative;
    padding-bottom: 52rpx;

    .title {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      overflow: hidden;
      font-size: 32rpx;
      color: #303133;
      line-height: 44rpx;
    }

    .bot {
      position: absolute;
      left: 0;
      bottom: 4rpx;
      font-size: 26rpx;
      color: #909399;
    }

    .time {
      margin-left: 20rpx;
    }
  }

  .right {
    width: 220rpx;
    height: 140rpx;
    flex-shrink: 0;
    position: relative;

    .img {
      width: 100%;
      height: 100%;
    }

  }
}

/* 评论 */
.evalution {
  display: flex;
  flex-direction: column;
  background: #fff;
  padding: 20upx 0;
}

.eva-item {
  padding: 20upx 30rpx;
  position: relative;

  image {
    width: 60rpx;
    height: 60rpx;
    border-radius: 50px;
    flex-shrink: 0;
    margin-right: 24rpx;
  }

  &:after {
    content: '';
    position: absolute;
    left: 30rpx;
    bottom: 0;
    right: 0;
    height: 0;
    border-bottom: 1px solid #eee;
    transform: translateY(50%);
  }

  &:last-child:after {
    border: 0;
  }
}

.eva-right {
  display: flex;
  flex-direction: column;
  flex: 1;
  font-size: 26rpx;
  color: #909399;
  position: relative;

  .zan-box {
    display: flex;
    align-items: base-line;
    position: absolute;
    top: 10rpx;
    right: 10rpx;

    .yticon {
      margin-left: 8rpx;
    }
  }

  .content {
    font-size: 28rpx;
    color: #333;
    padding-top: 20rpx;
  }
}

/* 底部 */
.bottom {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  height: 90rpx;
  padding: 0 30rpx;
  box-shadow: 0 -1px 3px rgba(0, 0, 0, .04);
  position: relative;
  z-index: 1;

  .input-box {
    display: flex;
    align-items: center;
    flex: 1;
    height: 60rpx;
    background: #f2f3f3;
    border-radius: 100px;
    padding-left: 30rpx;

    .icon-huifu {
      font-size: 28rpx;
      color: #909399;
    }

    .input {
      padding: 0 20rpx;
      font-size: 28rpx;
      color: #303133;
    }
  }

  .confirm-btn {
    font-size: 30rpx;
    padding-left: 20rpx;
    color: #0d9fff;
  }
}

.input-box {
  padding: 0
}
</style>