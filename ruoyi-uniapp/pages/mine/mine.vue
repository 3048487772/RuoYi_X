<template>
  <view class="container">
	  <view class="user-info">
	    <view>
	      <view class="user-info--name">{{ userInfo.userName }}</view>
	      <text class="user-info--account">{{ userInfo.email || userInfo.phonenumber || userInfo.userName }}</text>
	    </view>
	    <view>
	      <u-avatar :src="avatar" size="120rpx"></u-avatar>
	    </view>
	  </view>
	  <view style="padding: 40rpx; background-color: #fff;">
	    <u-grid>
	      <u-grid-item @click="navigateTo('/pages/mine/profile')">
	        <u-icon name="account-fill" color="#69c8fa" size="60rpx"></u-icon>
	        <text class="btn-text">个人资料</text>
	      </u-grid-item>
	      <u-grid-item @click="resetPassword = true">
	        <u-icon name="lock-fill" color="#69c8fa" size="60rpx"></u-icon>
	        <text class="btn-text">修改密码</text>
	      </u-grid-item>
	    </u-grid>
	  </view>
	<Password :show="resetPassword" @close="resetPassword = false"></Password>
  </view>
</template>
<script>
import config from '@/config'
import store from '@/store/store'
import { commonUpload } from "@/api/business.js"
import { updateUser,getUser } from "@/api/user.js"
import { toast, showConfirm } from '@/common/common'
import Password from './password.vue'
export default {
	components: {
	  Password,
	},
  data() {
    return {
	  resetPassword: false,
      avatar: this.$store.state.userInfo.avatar,
      baseUrl: config.baseUrl,
	  userInfo: {
	    nickName: null,
	  	userName: null,
	    sex: null,
	    email: null,
	    phonenumber: null
	  }
    }
  },
  methods: {
    onLoad() {
	  if(store.state.token){
		  this.$store.dispatch('GetInfo').then(res => {
		    this.avatar = res.user.avatar
		    //移动端获取个人信息接口异常,此处调用用户管理获取详情接口
		    getUser(res.user.userId).then(res => {
		    	this.userInfo = res.data
		    })
		  })
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
    navigateTo (url) {
      uni.navigateTo({ url: url })
    },
    savaInfo() {
      // let avatar = this.avatar.replace(this.baseUrl, '')
      let param = {
        userId: this.$store.state.userInfo.userId,
        // avatar: avatar,
        sex: this.userInfo.sex,
        phonenumber: this.userInfo.phonenumber,
        email: this.userInfo.email,
      }
      updateUser(param)
        .then(res => {
          this.toast("修改成功")
        })
    },
    onNavigationBarButtonTap() {
      this.logOut()
    },
    logOut() {
      this.showConfirm("确认退出登录吗?")
        .then(res => {
          if (res.confirm) {
            this.$store.dispatch('LogOut')
              .then(res => {
                uni.reLaunch({
                  url: '/pages/login/login'
                })
              });
          }
        })
    }
  },
}

</script>
<style lang="scss" scoped>
.mySave {
  color: white;
  background-color: #69c8fa;
  top: 20rpx;
  height: 60rpx;
  line-height: 60rpx;
}

.logOut {
  color: white;
  background-color: #69c8fa;
  position: absolute;
  bottom: 40rpx;
  right: 50%;
  transform: translate(50%);
}

#xgmm {
  color: #69c8fa;
  margin-top: -10rpx;
  // line-height: 15rpx;
  // text-decoration: underline;
}

.avatar {
    width: 100%;
    text-align: center;
    padding: 20rpx 0;
    border-bottom: solid 1rpx #f2f2f2;

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
  }

.user-info {
  display: flex;
  justify-content: space-between;
  padding: 40rpx;
  max-width: 100%;
  color: #303133;
  border-bottom: solid 1rpx #f2f2f2;

  &--name {
    font-size: 56rpx;
    font-weight: bold;
    width: 100%
  }

  &--account {
    font-size: 26rpx;
    color: #909399;
  }

}

</style>
