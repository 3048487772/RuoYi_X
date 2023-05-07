<template>
  <view class="content">
	  <!-- 下拉刷新组件 -->
	  <mix-pulldown-refresh ref="mixPulldownRefresh" class="panel-content" :top="90" @refresh="onPulldownReresh" @setEnableScroll="setEnableScroll">
    <scroll-view class="scroll"  :scroll-y="enableScroll" @scrolltolower="loadMore">
      <view class="scroll-content">
        <view class="container" v-show="loading === false">
          <!-- 留言 -->
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
					  <text class="content" v-show="reply.delFlag=='0'">{{reply.content}}</text>
					  <text class="content" v-show="reply.delFlag=='1'" style="color: #909399;">该评论已被删除！</text>
					</view>
				</view>
			  </view>
            </view>
          </view>
        </view>
	    <!-- 上滑加载更多组件 -->
	    <mix-load-more :status="loadMoreStatus"></mix-load-more>
        <!-- 加载图标 -->
        <mixLoading class="mix-loading" v-if="loading"></mixLoading>
      </view>
    </scroll-view>
	</mix-pulldown-refresh>
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
import mixPulldownRefresh from '@/components/mix-pulldown-refresh/mix-pulldown-refresh';
import mixLoadMore from '@/components/mix-load-more/mix-load-more';
import { cmsListMessage, cmsAddMessage,addCmsMessageLike,delCmsMessageLike } from '@/api/cms/message';
export default {
  components: {
	mixPulldownRefresh,
	mixLoadMore,
    mixLoading
  },
  data() {
    return {
      loading: true,
      evaList: [],
	  loadMoreStatus: 0,
	  refreshing: 0,
	  enableScroll: true,
	  // 总条数
	  total: 0,
	  // 查询参数
	  queryParams: {
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
  onLoad() {
	this.loadMoreStatus = 0; //加载更多 0加载前，1加载中，2没有更多了
	this.refreshing = 0;
    this.loadEvaList('add');
  },
  methods: {
    //获取留言列表
    async loadEvaList(type) {
		//type add 加载更多 refresh下拉刷新
		if (type === 'add') {
		  if (this.loadMoreStatus === 2) {
		    return;
		  }
		  this.loadMoreStatus = 1;
		}
		else if (type === 'refresh') {
		  this.refreshing = true;
		  this.evaList = []; //刷新前清空数组
		  this.total = 0;
		}
		cmsListMessage(this.queryParams).then(response => {
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
			this.evaList.push(response.rows[i]);
		  };
		  this.total = response.total;
		  //下拉刷新 关闭刷新动画
		  if (type === 'refresh') {
		    this.$refs.mixPulldownRefresh && this.$refs.mixPulldownRefresh.endPulldownRefresh();
		    this.refreshing = false;
		    this.loadMoreStatus = 0;
		  }
		  //上滑加载 处理状态
		  if (type === 'add') {
		    this.loadMoreStatus = this.evaList.length > 40 ? 2 : 0;
		  }
		  this.loading = false;
		});
    },
	//下拉刷新
	onPulldownReresh() {
	  this.loadEvaList('refresh');
	},
	//上滑加载
	loadMore() {
	  this.loadEvaList('add');
	},
	//设置scroll-view是否允许滚动，在小程序里下拉刷新时避免列表可以滑动
	setEnableScroll(enable) {
	  if (this.enableScroll !== enable) {
	    this.enableScroll = enable;
	  }
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

.s-header {
  padding: 20upx 30rpx;
  font-size: 30rpx;
  color: #303133;
  background: #fff;
  text-align: center;

  &:before {
    content: '';
    width: 0;
    height: 40rpx;
  }
}

/* 留言 */
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