<template>
  <view class="t-login">
  	<!-- 页面装饰图片 -->
  	<image class="img-a" src="/static/img/b-1.png"></image>
  	<image class="img-b" src="/static/img/b-2.png"></image>
  	<!-- 标题 -->
  	<view style="display: flex;padding-left: 35%;padding-top: 200rpx;">
  		<image class="blog-img" src="/static/loading.png"></image>
  		<view class="logo">Blog</view>
  	</view>
  	<form class="cl">
  		<!-- 登录账号 -->
  		<view class="login-form-item">
  			<u-input v-model="loginForm.username" placeholder="请输入登录用户名" maxlength="30">
  				<u-icon slot="prefix" name="account" size="25px"></u-icon>
  			</u-input>
  		</view>
  		<!-- 登录密码 -->
  		<view class="login-form-item">
  			<u-input v-model="loginForm.password" type="text" :password="isHidePassword ? true : false" placeholder="请输入登录密码" maxlength="16">
  				<u-icon slot="prefix" name="lock" size="25px"></u-icon>
  			</u-input>
			<image @click="isHidePasswordClick" class="eye" :src="isHidePassword ? '/static/img/attention.png' : '/static/img/attention_forbid.png'"></image>
  		</view>
  		<!-- 图形验证码 -->
  		<view class="login-form-item t-captcha">
  			<u-input v-model="loginForm.code" type="number" placeholder="请输入验证码" maxlength="4">
				<u-icon slot="prefix" name="fingerprint" size="25px"></u-icon>
  			</u-input>
  			<image :src="codeUrl" @click="getCode" class="t-captcha-img"></image>
  		</view>
		<view class="register"><navigator url="/pages/register/register" hover-class="none">注册</navigator></view>
		<view style="display: flex;">
			<button style="background: rgba(255,190,23,0.9);" @tap="goBack">返回首页</button>
			<button @tap="submit">登 录</button>
		</view>
  	</form>
  </view>
  
</template>
<script>
import { checkPwd, checkShenfenzheng } from '@/common/validate.js';
import { getCodeImg } from '@/api/login';
import { updateUser } from '@/api/user.js';
export default {
  data() {
    return {
      isHidePassword: true,
      codeUrl: '',
      captchaOnOff: true,
      loginForm: {
        username: this.$store.state.userInfo.userName,
        password: '',
        rememberMe: false,
        code: '',
        uuid: ''
      },
      register: false,
      userExtend2: ''
    };
  },
  onLoad() {
    // #ifdef APP-PLUS
    // 获取客户端标识
    let pinf = plus.push.getClientInfo();
    if (pinf && pinf.clientid) uni.setStorageSync('cid', pinf.clientid);
    else {
      var obtainingCIDTimer = setInterval(function() {
        pinf = plus.push.getClientInfo();
        if (pinf.clientid) {
          uni.setStorageSync('cid', pinf.clientid);
          clearInterval(obtainingCIDTimer);
        }
      }, 50);
    }
    setTimeout(() => {
      console.log('cid', uni.getStorageSync('cid'));
    }, 500);
    // #endif
    this.getCode();
  },
  methods: {
    isHidePasswordClick() {
      this.isHidePassword = !this.isHidePassword;
    },
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff = res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = 'data:image/gif;base64,' + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    submit() {
      if (!this.loginForm.username) {
        this.toast('请填写账号');
        return;
      }
      if (!this.loginForm.password) {
        this.toast('请填写密码');
        return;
      }
      this.toast('登录中...');
      this.$store
        .dispatch('Login', this.loginForm)
        .then(() => {
          this.$store.dispatch('GetInfo').then(res => {
            let param = {
              userExtend2: uni.getStorageSync('cid')
            };
            updateUser(param).then(res => {
              uni.reLaunch({
                url: '/pages/my/index'
              });
            });
          });
        })
        .catch(() => {
          if (this.captchaOnOff) {
            this.getCode();
          }
        });
    },
	goBack() {
	  uni.reLaunch({ url: '/pages/index/index' });
	},
  }
};
</script>
<style lang="scss" scoped>
@import 'index.scss';
.blog-img {
	width: 30px;
	height: 30px;
	margin: 94rpx 0 80rpx 0;
}
	
.logo {
	width: 80%;
	font-size:64rpx;
	color: rgba(255,190,23,0.9);
	margin: 80rpx auto 80rpx 0;
}

.eye {
  position: absolute;
  height: 50rpx;
  width: 50rpx;
  right: 70rpx;
  top: 608rpx;
}

.register {
  margin-top: -26rpx;
  margin-bottom: 26rpx;
  color: #69c8fa;
  text-align: right;
}

.button {
	color: #ffffff;
	font-size: 32rpx;
	width: 80%;
	height: 80rpx;
	background: #69c8fa;
	box-shadow: 0rpx 0rpx 13rpx 0rpx rgba(15, 168, 250, 0.4);
	border-radius: 10rpx;
	line-height: 80rpx;
	text-align: center;
	margin: 50rpx auto 0;
}

.codeImg {
  height: 64rpx;
  position: absolute;
  right: 0;
  top: 10rpx;
  width: 200rpx;
}
</style>
