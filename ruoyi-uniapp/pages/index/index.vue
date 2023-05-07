<template>
  <view class="content">
    <!-- 顶部选项卡 -->
    <scroll-view id="nav-bar" class="nav-bar" scroll-x scroll-with-animation :scroll-left="scrollLeft">
      <view v-for="(item,index) in tabBars" :key="item.typeId" class="nav-item" :class="{current: index === tabCurrentIndex}" :id="'tab'+index" @click="changeTab(index)">{{item.typeName}}</view>
    </scroll-view>
    <!-- 下拉刷新组件 -->
    <mix-pulldown-refresh ref="mixPulldownRefresh" class="panel-content" :top="90" @refresh="onPulldownReresh" @setEnableScroll="setEnableScroll">
      <!-- 内容部分 -->
      <swiper id="swiper" class="swiper-box" :duration="300" :current="tabCurrentIndex" @change="changeTab">
        <swiper-item v-for="tabItem in tabBars" :key="tabItem.typeId">
          <scroll-view class="panel-scroll-box" :scroll-y="enableScroll" @scrolltolower="loadMore">
            <!-- 
                            * 博客列表 
                            * 和nvue的区别只是需要把uni标签转为weex标签而已
                            * class 和 style的绑定限制了一些语法，其他并没有不同
                        -->
            <view v-for="(item, index) in tabItem.newsList" :key="index" class="news-item" @click="navToDetails(item)">
              <text class="title">{{item.title}}</text>
              <view class="img-list">
                <view class="img-wrapper">
                  <image class="img" :src="item.blogPic"></image>
                </view>
              </view>
              <!-- 空图片占位 -->
              <view class="bot">
                <text class="author">{{item.createBy}}</text>
                <text class="time">{{item.createTime}}</text>
              </view>
            </view>
            <!-- 上滑加载更多组件 -->
            <mix-load-more :status="tabItem.loadMoreStatus"></mix-load-more>
          </scroll-view>
        </swiper-item>
      </swiper>
    </mix-pulldown-refresh>
  </view>
</template>
<script>
import mixAdvert from '@/components/mix-advert/vue/mix-advert';
import mixPulldownRefresh from '@/components/mix-pulldown-refresh/mix-pulldown-refresh';
import mixLoadMore from '@/components/mix-load-more/mix-load-more';
import { cmsListBlog, getBlogDetail,cmsListByTypeId,cmsListRecommend,addBlogViews } from '@/api/cms/blog';
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
      tabCurrentIndex: 0, //当前选项卡索引
      scrollLeft: 0, //顶部选项卡左滑距离
      enableScroll: true,
      tabBars: [],
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
      total: 0,
    }
  },
  computed: {
    
  },
  async onLoad() {
    // 获取屏幕宽度
    windowWidth = uni.getSystemInfoSync().windowWidth;
    this.loadTabbars();
  },
  onReady() {

  },
  methods: {
    /**
     * 数据处理方法在vue和nvue中通用，可以直接用mixin混合
     * 这里直接写的
     * mixin使用方法看index.nuve
     */
    //获取分类
    async loadTabbars() {
		getBlogDetail(this.$route.query.id).then(response => {
		  let tabList = response.types;
		  tabList.unshift({typeName: '全部',typeId: -1})
		  tabList.forEach(item => {
		    item.newsList = [];
		    item.loadMoreStatus = 0; //加载更多 0加载前，1加载中，2没有更多了
		    item.refreshing = 0;
		  })
		  this.tabBars = tabList;
		  this.loadNewsList('add');
		}, errorRes => {
        console.log("报错:"+errorRes)
      });
    },
    //博客列表
    loadNewsList(type) {
      let tabItem = this.tabBars[this.tabCurrentIndex];

      //type add 加载更多 refresh下拉刷新
      if (type === 'add') {
        if (tabItem.loadMoreStatus === 2) {
          return;
        }
        tabItem.loadMoreStatus = 1;
      }
      else if (type === 'refresh') {
        tabItem.refreshing = true;
		tabItem.newsList = []; //刷新前清空数组
      }
	  
	  if(tabItem.typeId===-1){
		  console.log("全部:")
		  cmsListBlog(this.queryParams).then(response => {
		    let list = this.picSrc(response.rows);
			list.forEach(item => {
			  tabItem.newsList.push(item);
			})
			this.total = response.total;
			//下拉刷新 关闭刷新动画
			if (type === 'refresh') {
			  this.$refs.mixPulldownRefresh && this.$refs.mixPulldownRefresh.endPulldownRefresh();
			  tabItem.refreshing = false;
			  tabItem.loadMoreStatus = 0;
			}
			//上滑加载 处理状态
			if (type === 'add') {
			  tabItem.loadMoreStatus = tabItem.newsList.length > 40 ? 2 : 0;
			}
		  });
	  } else{
		  console.log("分类:")
		  cmsListByTypeId(tabItem.typeId).then(response => {
		    let list = this.picSrc(response.rows);
		    list.forEach(item => {
		      tabItem.newsList.push(item);
		    })
		    this.total = response.total;
			//下拉刷新 关闭刷新动画
			if (type === 'refresh') {
			  this.$refs.mixPulldownRefresh && this.$refs.mixPulldownRefresh.endPulldownRefresh();
			  tabItem.refreshing = false;
			  tabItem.loadMoreStatus = 0;
			}
			//上滑加载 处理状态
			if (type === 'add') {
			  tabItem.loadMoreStatus = tabItem.newsList.length > 40 ? 2 : 0;
			}
		  });
	  }
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

    //tab切换
    async changeTab(e) {

      if (scrollTimer) {
        //多次切换只执行最后一次
        clearTimeout(scrollTimer);
        scrollTimer = false;
      }
      let index = e;
      //e=number为点击切换，e=object为swiper滑动切换
      if (typeof e === 'object') {
        index = e.detail.current
      }
      if (typeof tabBar !== 'object') {
        tabBar = await this.getElSize("nav-bar")
      }
      //计算宽度相关
      let tabBarScrollLeft = tabBar.scrollLeft;
      let width = 0;
      let nowWidth = 0;
      //获取可滑动总宽度
      for (let i = 0; i <= index; i++) {
        let result = await this.getElSize('tab' + i);
        width += result.width;
        if (i === index) {
          nowWidth = result.width;
        }
      }
      if (typeof e === 'number') {
        //点击切换时先切换再滚动tabbar，避免同时切换视觉错位
        this.tabCurrentIndex = index;
      }
      //延迟300ms,等待swiper动画结束再修改tabbar
      scrollTimer = setTimeout(() => {
        if (width - nowWidth / 2 > windowWidth / 2) {
          //如果当前项越过中心点，将其放在屏幕中心
          this.scrollLeft = width - nowWidth / 2 - windowWidth / 2;
        } else {
          this.scrollLeft = 0;
        }
        if (typeof e === 'object') {
          this.tabCurrentIndex = index;
        }
        this.tabCurrentIndex = index;


        //第一次切换tab，动画结束后需要加载数据
        let tabItem = this.tabBars[this.tabCurrentIndex];
        if (this.tabCurrentIndex !== 0 && tabItem.loaded !== true) {
          this.loadNewsList('add');
          tabItem.loaded = true;
        }
      }, 300)

    },
    //获得元素的size
    getElSize(id) {
      return new Promise((res, rej) => {
        let el = uni.createSelectorQuery().select('#' + id);
        el.fields({
          size: true,
          scrollOffset: true,
          rect: true
        }, (data) => {
          res(data);
        }).exec();
      });
    },
  }
}
</script>
<style lang='scss' scoped>
page,
.content {
  background-color: #f8f8f8;
  height: 100%;
  overflow: hidden;
}

/* 顶部tabbar */
.nav-bar {
  position: relative;
  z-index: 10;
  height: 90rpx;
  white-space: nowrap;
  box-shadow: 0 2upx 8upx rgba(0, 0, 0, .06);
  background-color: #fff;

  .nav-item {
    display: inline-block;
    width: 150rpx;
    height: 90rpx;
    text-align: center;
    line-height: 90rpx;
    font-size: 30rpx;
    color: #303133;
    position: relative;

    &:after {
      content: '';
      width: 0;
      height: 0;
      border-bottom: 4upx solid #007aff;
      position: absolute;
      left: 50%;
      bottom: 0;
      transform: translateX(-50%);
      transition: .3s;
    }
  }

  .current {
    color: #007aff;

    &:after {
      width: 50%;
    }
  }
}

.swiper-box {
  height: 100%;
}

.panel-scroll-box {
  height: 100%;

  .panel-item {
    background: #fff;
    padding: 30px 0;
    border-bottom: 2px solid #000;
  }
}

/**
     * 新闻列表 直接拿的nvue样式修改，,
     * 一共需要修改不到10行代码, 另外px需要直接修改为upx，只有单位不一样，计算都是一样的
     * 吐槽：难道不能在编译的时候把nuve中的upx转为px? 这样就不用修改单位了
     */
.video-wrapper {
  width: 100%;
  height: 440rpx;

  .video {
    width: 100%;
  }
}

view {
  display: flex;
  flex-direction: column;
}

.img {
  width: 100%;
  height: 100%;
}

.news-item {
  position: relative;
}

/* 修改结束 */

/* 新闻列表  emmm 仅供参考 */
.news-item {
  width: 750rpx;
  padding: 24upx 30rpx;
  border-bottom-width: 1px;
  border-color: #eee;
  background-color: #fff;
}

.title {
  font-size: 32rpx;
  color: #303133;
  line-height: 46rpx;
  padding-left: 240rpx;
  padding-right: 30px;
}

.bot {
  flex-direction: row;
  padding-left: 240rpx;
  margin-top: 20rpx;
}

.author {
  font-size: 26rpx;
  color: #aaa;
}

.time {
  font-size: 26rpx;
  color: #aaa;
  margin-left: 20rpx;
}

.img-list {
  flex-shrink: 0;
  flex-direction: row;
  background-color: #fff;
  width: 220rpx;
  height: 140rpx;
  position: absolute;
  left: 30rpx;
  top: 24rpx;
}

.img-wrapper {
  flex: 1;
  flex-direction: row;
  height: 140rpx;
  position: relative;
  overflow: hidden;
}

.img {
  flex: 1;
}

</style>