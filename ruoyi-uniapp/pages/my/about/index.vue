<template>
  <view class="about-container">
    <view class="header-section text-center">
      <image style="width: 150rpx;height: 150rpx;" src="/static/loading.png" mode="widthFix">
      </image>
      <view style="margin-top: 10px;font-size: 18px;color: #999;">Blog移动端</view>
    </view>

    <view class="content-section">
      <view class="menu-list">
        <view class="list-cell list-cell-arrow">
          <view class="menu-item-box">
            <view>版本信息</view>
            <view class="text-right">v{{version}}</view>
          </view>
        </view>
        <view class="list-cell list-cell-arrow">
          <view class="menu-item-box">
            <view>官方邮箱</view>
            <view class="text-right">xxxx@xx.com</view>
          </view>
        </view>
        <view class="list-cell list-cell-arrow">
          <view class="menu-item-box">
            <view>服务热线</view>
            <view class="text-right">400-999-9999</view>
          </view>
        </view>
        <!-- <view class="list-cell list-cell-arrow">
          <view class="menu-item-box">
            <view>公司网站</view>
            <view class="text-right">
               xxxx.com
            </view>
          </view>
        </view> -->
      </view>
    </view>

    <view class="copyright">
      <view>Copyright &copy; 2022 Ning All Rights Reserved.</view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        version: '1.0.0'
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

  .copyright {
    margin-top: 50rpx;
    text-align: center;
    line-height: 60rpx;
    color: #999;
  }

  .header-section {
    display: flex;
    padding: 30rpx 0 0;
    flex-direction: column;
    align-items: center;
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
