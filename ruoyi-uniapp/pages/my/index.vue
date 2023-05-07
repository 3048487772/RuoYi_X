<template>
  <view class="mine-container" :style="{height: `${windowHeight}px`}">
    <!--顶部个人信息栏-->
    <view class="header-section">
      <view class="flex padding justify-between">
        <view class="flex align-center">
          <view v-if="!avatar" class="cu-avatar xl round bg-white">
            <view class="iconfont icon-people text-gray icon"></view>
          </view>
          <image v-if="avatar" :src="avatar" class="cu-avatar xl round" mode="widthFix">
          </image>
          <view v-if="!name" @click="handleToLogin" class="login-tip">
            点击登录
          </view>
          <view v-if="name" @click="handleToInfo" class="user-info">
            <view class="u_title">
              用户名：{{ name }}
            </view>
          </view>
        </view>
        <view @click="handleToInfo" class="flex align-center">
          <text>个人信息</text>
          <view class="iconfont icon-right"></view>
        </view>
      </view>
    </view>

    <view class="content-section">
      <view class="mine-actions grid col-4 text-center">
        <view class="action-item" @click="handleBuilding('交流群~')">
          <view class="iconfont icon-friendfill text-pink icon"></view>
          <text class="text">交流群</text>
        </view>
        <view class="action-item" @click="handleBuilding('在线客服~')">
          <view class="iconfont icon-service text-blue icon"></view>
          <text class="text">在线客服</text>
        </view>
        <view class="action-item" @click="handleBuilding('反馈社区~')">
          <view class="iconfont icon-community text-mauve icon"></view>
          <text class="text">反馈社区</text>
        </view>
        <view class="action-item" @click="handleBuilding('点赞我们~')">
          <view class="iconfont icon-dianzan text-green icon"></view>
          <text class="text">点赞我们</text>
        </view>
      </view>

      <view class="menu-list">
        <view class="list-cell list-cell-arrow" @click="handleToEditInfo">
          <view class="menu-item-box">
            <view class="iconfont icon-user menu-icon"></view>
            <view>编辑资料</view>
          </view>
        </view>
        <view class="list-cell list-cell-arrow" @click="handleHelp">
          <view class="menu-item-box">
            <view class="iconfont icon-help menu-icon"></view>
            <view>问题反馈</view>
          </view>
        </view>
        <view class="list-cell list-cell-arrow" @click="handleAbout">
          <view class="menu-item-box">
            <view class="iconfont icon-aixin menu-icon"></view>
            <view>关于我们</view>
          </view>
        </view>
        <view class="list-cell list-cell-arrow" @click="handleToSetting">
          <view class="menu-item-box">
            <view class="iconfont icon-setting menu-icon"></view>
            <view>应用设置</view>
          </view>
        </view>
      </view>

    </view>
  </view>
</template>

<script>
import store from '@/store/store'
import { toast, showConfirm } from '@/common/common'
  
  export default {
    data() {
      return {
        name: '',
        version: '',
		avatar: '',
		popupMessg: '',
      }
    },
    computed: {
      windowHeight() {
        return uni.getSystemInfoSync().windowHeight - 50
      }
    },
	onShow() {
		if(uni.getStorageSync('token')){
		  const user = uni.getStorageSync('userInfo')
		  this.name = (user == null || user.userName == "" || user.userName == null) ? "" : user.userName
		  this.avatar = (user == null || user.avatar == "" || user.avatar == null) ? require("@/static/logo.png") : user.avatar
		} else {
		  showConfirm('未登录，是否登录?').then(res => {
		      if(res.confirm) {
		        store.dispatch('LogOut').then(res => {
		          uni.reLaunch({ url: '/pages/login/login' })
		        })
		      }else{
				store.dispatch('LogOut').then(res => {
				  uni.reLaunch({ url: '/pages/index/index' })
				})
			  }
		    }).catch(() => {})
	  }
	},
    methods: {
      handleToInfo() {
		uni.navigateTo({url: "/pages/my/info/profile"}) //保留当前页面跳转，使用navigateBack可以返回
      },
      handleToEditInfo() {
		uni.navigateTo({url: "/pages/mine/profile"}) //保留当前页面跳转，使用navigateBack可以返回
      },
      handleToSetting() {
        uni.navigateTo({url: "/pages/my/setting/index"})
      },
      handleToLogin() {
		uni.redirectTo({ //关闭当前页面跳转，减少占据运行内存，但返回时页面需要重新加载，增加了返回页面的显示时间。
			url: "/pages/login/login"
		})
      },
      handleHelp() {
		uni.navigateTo({url: "/pages/my/help/index"})
      },
      handleAbout() {
		uni.navigateTo({url: "/pages/my/about/index"})
      },
      handleBuilding(popupMessg) {
		this.toast(popupMessg);
      },
    }
  }
</script>

<style lang="scss" scoped>
	@import "@/static/scss/colorui.scss";
	@import "@/static/scss/global.scss";
	@import "@/static/font/iconfont.css";
  page {
    background-color: #f5f6f7;
  }

  .mine-container {
    width: 100%;
    height: 100%;


    .header-section {
      padding: 15px 15px 45px 15px;
      background-color: rgb(8, 182, 242);
      color: white;

      .login-tip {
        font-size: 18px;
        margin-left: 10px;
      }

      .cu-avatar {
        border: 2px solid #eaeaea;

        .icon {
          font-size: 40px;
        }
      }

      .user-info {
        margin-left: 15px;

        .u_title {
          font-size: 18px;
          line-height: 30px;
        }
      }
    }

    .content-section {
      position: relative;
      top: -50px;

      .mine-actions {
        margin: 15px 15px;
        padding: 20px 0px;
        border-radius: 8px;
        background-color: white;

        .action-item {
          .icon {
            font-size: 28px;
          }

          .text {
            display: block;
            font-size: 13px;
            margin: 8px 0px;
          }
        }
      }
    }
  }
</style>
