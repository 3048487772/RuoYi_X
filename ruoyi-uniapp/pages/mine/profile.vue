<template>
  <view class="container">
	  <view class="avatar">
	    <view class="imgAvatar">
	      <view class="iavatar" :style="'background: url(' +
	    avatar+') no-repeat center/cover #eeeeee;'"></view>
	    </view>
	  </view>
	<view style="padding: 0rpx 40rpx 40rpx 40rpx;">
	  <u--form :model="userInfo" ref="uForm" labelWidth="160rpx" labelAlign="left">
	    <u-form-item label="姓名" prop="userName" class="u-border-bottom">
	      <u--input
		    disabled
			disabledColor='#ffffff'
	        placeholder="请输入内容"
	        border="none"
	        v-model="userInfo.userName"
	      ></u--input>
	    </u-form-item>
	    <u-form-item label="性别" prop="sex" class="u-border-bottom">
	      <u-radio-group v-model="userInfo.sex" size="36rpx">
	        <u-radio shape="circle" label="男" name="0" labelSize="32rpx"></u-radio>
	        <u-radio shape="circle" label="女" name="1" labelSize="32rpx" style="margin-left: 36rpx;"></u-radio>
	      </u-radio-group>
	    </u-form-item>
	    <u-form-item label="手机号码" prop="phonenumber" class="u-border-bottom">
	      <u--input
	        placeholder="请输入内容"
	        border="none"
	        v-model="userInfo.phonenumber"
	      ></u--input>
	    </u-form-item>
	    <u-form-item label="邮箱" prop="email" class="u-border-bottom">
	      <u--input
	        placeholder="请输入内容"
	        border="none"
	        v-model="userInfo.email"
	      ></u--input>
	    </u-form-item>
	  </u--form>
	</view>
	<view class="ui-all">
	  <button class="mySave" @tap="savaInfo">保存</button>
	  <!-- <button class="logOut" @tap="goBack">返回</button> -->
	</view>
  </view>
</template>
<script>
import config from '@/config'
import store from '@/store/store'
import { commonUpload } from "@/api/business.js"
import { updateUser,getUserProfile,getUser } from "@/api/user.js"
import { toast, showConfirm } from '@/common/common'
export default {
  data() {
    return {
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
		  })
		  getUserProfile().then(res => {
		      //移动端获取个人信息接口异常,此处调用用户管理获取详情接口
			  getUser(res.data.userId).then(res => {
				  this.userInfo = res.data
			  })
		    });
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
    avatarChoose() {
      let that = this;
      uni.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        success(res) {
          that.imgUpload(res.tempFilePaths);
        }
      });
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
    imgUpload(file) {
      commonUpload(file[0])
        .then(res => {
          this.avatar = this.baseUrl + res.fileName
          let avatar = this.avatar.replace(this.baseUrl, '')
          let param = {
            userId: this.$store.state.userInfo.userId,
            avatar: avatar,
          }
        });
    },
	goBack() {
	  uni.navigateBack({ delta: 1});
	},
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
  top: 20rpx;
  height: 60rpx;
  line-height: 60rpx;
  margin-top: 20rpx;
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

</style>
