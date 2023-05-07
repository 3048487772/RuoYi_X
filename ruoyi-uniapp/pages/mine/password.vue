<template>
  <u-modal :show="show" title="修改密码" showCancelButton @confirm="confirm" @cancel="cancel">
    <view class="slot-content">
      <u--form :model="pwd" ref="uForm" labelWidth="160rpx" labelAlign="left">
        <u-form-item label="原密码" prop="origiPwd" class="u-border-bottom">
          <u--input v-model="pwd.origiPwd" type="text" :password="isHideoPassword?true:false" placeholder="请输入原密码" border="none"></u--input>
		  <image @click="isHideoPassword=!isHideoPassword" class="eye" :src="isHideoPassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
        </u-form-item>
        <u-form-item label="新密码" prop="newPwd" class="u-border-bottom">
          <u--input v-model="pwd.newPwd" type="text" :password="isHidenPassword?true:false" placeholder="请输入新密码" border="none"></u--input>
		  <image @click="isHidenPassword=!isHidenPassword" class="eye" :src="isHidenPassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
        </u-form-item>
        <u-form-item label="确认密码" prop="confirmPwd" class="u-border-bottom">
          <u--input v-model="pwd.confirmPwd" type="text" :password="isHidecPassword?true:false" placeholder="请输入确认密码" border="none"></u--input>
		  <image @click="isHidecPassword=!isHidecPassword" class="eye" :src="isHidecPassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
        </u-form-item>
      </u--form>
    </view>
  </u-modal>
</template>

<script>
import { resetUserPwd } from '@/api/user'
import { checkPwd } from '@/common/validate'
export default {
  props: {
    show: Boolean
  },
  data () {
    return {
		isHideoPassword: true,
		isHidenPassword: true,
		isHidecPassword: true,
      pwd: {
        origiPwd: '',
        newPwd: '',
        confirmPwd: ''
      }
    }
  },
  methods: {
    confirm () {
      if (!this.pwd.origiPwd) {
        this.toast("请填写旧密码");
        return;
      };
      if (!checkPwd(this.pwd.newPwd)) {
        return;
      };
      if (this.pwd.confirmPwd != this.pwd.newPwd) {
        this.toast("两次输入的密码不一致");
        return;
      };
      resetUserPwd(this.pwd.origiPwd, this.pwd.newPwd)
        .then(response => {
          this.toast("修改成功");
          this.$emit('close')
        })
    },
    cancel () {
      this.$emit('close')
    }
  }
}
</script>

<style lang="scss" scoped>
	.eye {
	  height: 50rpx;
	  width: 50rpx;
	  right: 0;
	  top: 50%;
	}
</style>