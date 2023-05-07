<template>
  <view>
    <view class="ui-all">
      <view class="ui-list">
        <text>旧密码</text>
        <input v-model="formData.oldPassword" type="text" :password="isHideoPassword?true:false" placeholder="请输入密码" maxlength="20" />
        <image @click="isHideoPassword=!isHideoPassword" class="eye" :src="isHideoPassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
      </view>
      <view class="ui-list">
        <text>新密码</text>
        <input v-model="formData.newPassword" type="text" :password="isHidenPassword?true:false" placeholder="请输入新密码" maxlength="20" />
        <image @click="isHidenPassword=!isHidenPassword" class="eye" :src="isHidenPassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
      </view>
      <view class="ui-list">
        <text>确认密码</text>
        <input v-model="formData.confirmPassword" type="text" :password="isHidecPassword?true:false" placeholder="请确认密码" maxlength="20" />
        <image @click="isHidecPassword=!isHidecPassword" class="eye" :src="isHidecPassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
      </view>
    </view>
    <button class="login mt5" @click="submit">保存</button>
  </view>
</template>
<script>
import { resetUserPwd } from '@/api/user'
import { checkPwd } from '@/common/validate'
export default {
  data() {
    return {
      isHideoPassword: true,
      isHidenPassword: true,
      isHidecPassword: true,
      'formData': {
        'oldPassword': "",
        'newPassword': '',
        'confirmPassword': '',
      },
    };
  },
  onLoad() {},
  methods: {
    submit() {
      if (!this.formData.oldPassword) {
        this.toast("请填写旧密码");
        return;
      };
      if (!checkPwd(this.formData.newPassword)) {
        return;
      };
      if (this.formData.confirmPassword != this.formData.newPassword) {
        this.toast("两次输入的密码不一致");
        return;
      };
      resetUserPwd(this.formData.oldPassword, this.formData.newPassword)
        .then(response => {
          this.toast("修改成功");
          setTimeout(function() {
            uni.reLaunch({
              url: '/pages/mine/mine'
            })
          }, 800);
        })
    }
  }
}

</script>
<style lang="scss" scoped>
.eye {
  position: absolute;
  height: 50rpx;
  width: 50rpx;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
}

.login {
  width: 200rpx;
  color: white;
  height: 75rpx;
  font-size: 30rpx;
  background-color: #69c8fa;
}

</style>
