<template>
  <view class="content">
    <!-- 下拉刷新组件 -->
    <mix-pulldown-refresh ref="mixPulldownRefresh" class="panel-content" :top="90" @refresh="onPulldownReresh" @setEnableScroll="setEnableScroll">
      <!-- 内容部分 -->
          <scroll-view class="panel-scroll-box" :scroll-y="enableScroll" @scrolltolower="loadMore">
            <!-- 
                            * 博客列表 
                            * 和nvue的区别只是需要把uni标签转为weex标签而已
                            * class 和 style的绑定限制了一些语法，其他并没有不同
                        -->
            <view v-for="(item, index) in essayList" :key="index" >
				<view class="author">
					<view style="display: inline-block;width:10px; height:10px;border-radius:50%;background-color:rgba(255,190,23,0.9);"></view>
					<text style="margin-left: 10px;">{{item.createTime}}</text>
					<text style="margin-left: 10px;">{{item.createBy}}</text>
				</view>
				<view class="essay-item">
					<view class="essay-item-in">
						<text class="title">{{item.title}}</text>
						<view class="bot">
						  <view class="content">
							  <rich-text class="typo m-padded-lr-responsive m-padded-tb-large ql-editor" :nodes="item.content"></rich-text>
						  </view>
						</view>
					</view>
				</view>
            </view>
            <!-- 上滑加载更多组件 -->
            <mix-load-more :status="loadMoreStatus"></mix-load-more>
          </scroll-view>
    </mix-pulldown-refresh>
  </view>
</template>
<script>
import mixAdvert from '@/components/mix-advert/vue/mix-advert';
import mixPulldownRefresh from '@/components/mix-pulldown-refresh/mix-pulldown-refresh';
import mixLoadMore from '@/components/mix-load-more/mix-load-more';
import { cmsEssayList } from '@/api/cms/blog';
let windowWidth = 0,
  scrollTimer = false,
  tabBar;
export default {
  components: {
    mixPulldownRefresh,
    mixLoadMore,
    mixAdvert
  },
  data() {
    return {
      enableScroll: true,
	  loadMoreStatus: 0,
	  refreshing: 0,
      essayList: [],
	  // 查询参数
	  queryParams: {
	    pageNum: 1,
	    pageSize: 10,
	    title: null,
	    type: 2,
	    content: null,
	    top: null,
	    views: null,
	    status: null,
	  },
      // 总条数
      total: 0,
    }
  },
  computed: {
    
  },
  async onLoad() {
    // 获取屏幕宽度
    windowWidth = uni.getSystemInfoSync().windowWidth;
	this.loadMoreStatus = 0; //加载更多 0加载前，1加载中，2没有更多了
	this.refreshing = 0;
    this.loadNewsList('add');
  },
  onReady() {

  },
  methods: {
    /**
     * 数据处理方法在vue和nvue中通用，可以直接用mixin混合
     * 这里直接写的
     * mixin使用方法看index.nuve
     */
    //博客列表
    loadNewsList(type) {
      //type add 加载更多 refresh下拉刷新
      if (type === 'add') {
        if (this.loadMoreStatus === 2) {
          return;
        }
        this.loadMoreStatus = 1;
      }
      else if (type === 'refresh') {
        this.refreshing = true;
		this.essayList = []; //刷新前清空数组
      }
	  
	  cmsEssayList(this.queryParams).then(response => {
	    let list = this.picSrc(response.rows);
		list.forEach(item => {
		  this.essayList.push(item);
		})
		this.total = response.total;
		//下拉刷新 关闭刷新动画
		if (type === 'refresh') {
		  this.$refs.mixPulldownRefresh && this.$refs.mixPulldownRefresh.endPulldownRefresh();
		  this.refreshing = false;
		  this.loadMoreStatus = 0;
		}
		//上滑加载 处理状态
		if (type === 'add') {
		  this.loadMoreStatus = this.essayList.length > 40 ? 2 : 0;
		}
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

    //下拉刷新
    onPulldownReresh() {
      this.loadNewsList('refresh');
    },
    //上滑加载
    loadMore() {
      this.loadNewsList('add');
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
<style lang='scss' scoped>
page,
/**
     * 新闻列表 直接拿的nvue样式修改，,
     * 一共需要修改不到10行代码, 另外px需要直接修改为upx，只有单位不一样，计算都是一样的
     * 吐槽：难道不能在编译的时候把nuve中的upx转为px? 这样就不用修改单位了
     */

view {
  display: flex;
  flex-direction: column;
}

.panel-scroll-box {
	margin-top: 10px;
}

.img {
  width: 100%;
  height: 100%;
}

.essay-item {
  position: relative;
}

/* 修改结束 */

/* 新闻列表  emmm 仅供参考 */
.essay-item {
  margin: 0upx 30rpx;
  border-bottom-width: 1px;
  border-left: 1px solid rgba(170,170,170,1);
}

.essay-item-in {
	margin: 24upx 20rpx;
	background-color: #fff;
	border-radius:5px;
	border: 1px solid rgba(255,190,23,0.9);
	box-shadow: 0 0 30px -10px ;
}

.title {
  font-size: 32rpx;
  color: #303133;
  line-height: 46rpx;
  padding-left: 40rpx;
  padding-right: 30px;
  margin-top: 20rpx;
}

.bot {
  flex-direction: row;
  padding-left: 40rpx;
  margin-top: 20rpx;
  margin-bottom: 20rpx;
}

.author {
  display: inline-block;
  margin-left: 10px;
  font-size: 26rpx;
  color: #aaa;
}

.content {
  font-size: 26rpx;
  color: #aaa;
  margin-left: 20rpx;
}

</style>