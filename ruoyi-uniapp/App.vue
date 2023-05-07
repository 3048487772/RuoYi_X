<script>
export default {
  onLaunch: function() {
    console.log('App Launch')
  },
  onShow: function() {
    console.log('App Show')
  },
  onHide: function() {
    console.log('App Hide')
  }
}

</script>
<style lang="scss">
@import "@/uni_modules/uview-ui/index.scss";
page {
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  min-height: 100%;
  height: auto;
}

view {
  font-size: 30rpx;
  line-height: inherit;
}

.pt5 {
  padding-top: 10rpx;
}

.pr5 {
  padding-right: 10rpx;
}

.pb5 {
  padding-bottom: 10rpx;
}

.mt5 {
  margin-top: 10rpx;
}

.mr5 {
  margin-right: 10rpx;
}

.mb5 {
  margin-bottom: 10rpx;
}

.ml5 {
  margin-left: 10rpx;
}

.mt10 {
  margin-top: 20rpx;
}

.mr10 {
  margin-right: 20rpx;
}

.mb10 {
  margin-bottom: 20rpx;
}

.ml0 {
  margin-left: 20rpx;
}

.mt20 {
  margin-top: 40rpx;
}

.mr20 {
  margin-right: 40rpx;
}

.mb20 {
  margin-bottom: 40rpx;
}

.m20 {
  margin-left: 40rpx;
}

.ui-all {
  padding: 20rpx 40rpx;

  .avatar {
    width: 100%;
    text-align: left;
    padding: 20rpx 0;
    border-bottom: solid 1rpx #f2f2f2;
    position: relative;

    .imgAvatar {
      width: 140rpx;
      height: 140rpx;
      border-radius: 50%;
      display: inline-block;
      vertical-align: middle;
      overflow: hidden;

      .iavatar {
        width: 100%;
        height: 100%;
        display: block;
      }
    }

    text {
      display: inline-block;
      vertical-align: middle;
      color: #8e8e93;
      font-size: 28rpx;
      margin-left: 40rpx;
    }

    &:after {
      content: ' ';
      width: 20rpx;
      height: 20rpx;
      border-top: solid 1rpx #030303;
      border-right: solid 1rpx #030303;
      transform: rotate(45deg);
      -ms-transform: rotate(45deg);
      /* IE 9 */
      -moz-transform: rotate(45deg);
      /* Firefox */
      -webkit-transform: rotate(45deg);
      /* Safari 和 Chrome */
      -o-transform: rotate(45deg);
      position: absolute;
      top: 85rpx;
      right: 0;
    }
  }

  .ui-list {
    width: 100%;
    text-align: left;
    padding: 20rpx 0;
    border-bottom: solid 1rpx #f2f2f2;
    position: relative;

    text {
      color: #4a4a4a;
      font-size: 28rpx;
      display: inline-block;
      vertical-align: middle;
      width: 150rpx;
    }

    uni-radio-group {
      color: #030303;
      font-size: 30rpx;
      display: inline-block;
      vertical-align: middle;
      background: none;
      margin: 0;
      padding: 0;
    }

    input {
      color: #030303;
      font-size: 30rpx;
      display: inline-block;
      vertical-align: middle;
      left: 150rpx;
      width: calc(100% - 150rpx);
    }

    button {
      color: #030303;
      font-size: 30rpx;
      display: inline-block;
      vertical-align: middle;
      background: none;
      width: calc(100% - 150rpx);
      margin: 0;
      text-align: left;
      padding: 0;
      height: 30rpx;
      line-height: 30rpx;

      &::after {
        display: none;
      }
    }

    picker {
      width: calc(100% - 150rpx);
      color: #030303;
      font-size: 30rpx;
      display: inline-block;
      vertical-align: middle;
      position: absolute;
      left: 150rpx;
      top: 25rpx;
    }

    .continer {
      width: calc(100% - 150rpx);
      color: #030303;
      font-size: 30rpx;
      display: inline-block;
      vertical-align: middle;
      left: 150rpx;
    }

    textarea {
      color: #030303;
      font-size: 30rpx;
      vertical-align: middle;
      height: 150rpx;
      width: 100%;
      margin-top: 50rpx;
      left: 150rpx;
    }
  }

  .right:after {
    content: ' ';
    width: 20rpx;
    height: 20rpx;
    border-top: solid 1rpx #030303;
    border-right: solid 1rpx #030303;
    transform: rotate(45deg);
    -ms-transform: rotate(45deg);
    /* IE 9 */
    -moz-transform: rotate(45deg);
    /* Firefox */
    -webkit-transform: rotate(45deg);
    /* Safari 和 Chrome */
    -o-transform: rotate(45deg);
    position: absolute;
    top: 30rpx;
    right: 0;
  }

  .input-body {
    width: 90%;
    color: #030303;
    font-size: 30rpx;
    display: inline-block;
    vertical-align: middle;
    position: absolute;
    top: 30rpx;
    left: 150rpx;
  }

  .save {
    background: #030303;
    border: none;
    color: #ffffff;
    margin-top: 40rpx;
    font-size: 28rpx;
  }
}

</style>
