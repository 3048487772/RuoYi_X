<template>
  <view class="about-container">
    <view class="content-section">
      <view class="menu-list">
        <view class="list-cell list-cell-arrow">
          <view class="menu-item-box">
            <view>反馈邮箱</view>
            <view class="text-right">
               xxxx@xx.com
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        
      }
    }
  }
</script>

<style lang="scss">
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
