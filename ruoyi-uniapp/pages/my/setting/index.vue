<template>
  <view class="setting-container" :style="{height: `${windowHeight}px`}">
    <view class="menu-list">
      <!-- <view class="list-cell list-cell-arrow" @click="handleToPwd">
        <view class="menu-item-box">
          <view class="iconfont icon-password menu-icon"></view>
          <view>修改密码</view>
        </view>
      </view> -->
      <view class="list-cell list-cell-arrow" @click="handleToUpgrade">
        <view class="menu-item-box">
          <view class="iconfont icon-refresh menu-icon"></view>
          <view>检查更新</view>
        </view>
      </view>
      <view class="list-cell list-cell-arrow" @click="handleCleanTmp">
        <view class="menu-item-box">
          <view class="iconfont icon-clean menu-icon"></view>
          <view>清理缓存</view>
        </view>
      </view>
    </view>
    <view class="cu-list menu">
      <view class="cu-item item-box">
        <view class="content text-center" @click="handleLogout">
          <text class="text-black">退出登录</text>
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
        windowHeight: uni.getSystemInfoSync().windowHeight
      }
    },
    methods: {
      handleToPwd() {
		uni.navigateTo({url: "/pages/my/pwd/index"})
      },
      handleToUpgrade() {
		this.toast('模块建设中~');
      },
      handleCleanTmp() {
		this.toast('模块建设中~');
      },
      handleLogout() {
		showConfirm('是否确定退出登录?').then(res => {
		  if(res.confirm) {
		    store.dispatch('LogOut').then(res => {
			  uni.reLaunch({ url: '/pages/login/login' }) //先关闭了内存中所有保留的页面，再跳转到目标页面。
		    })
		  }
	    }).catch(() => {})
      }
    }
  }
</script>

<style lang="scss" scoped>
	@import "@/static/scss/colorui.scss";
	@import "@/static/scss/global.scss";
	@import "@/static/font/iconfont.css";
  page {
    background-color: #f8f8f8;
  }

  .item-box {
    background-color: #FFFFFF;
    margin: 30rpx;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 10rpx;
    border-radius: 8rpx;
    color: #303133;
    font-size: 32rpx;
  }
</style>
