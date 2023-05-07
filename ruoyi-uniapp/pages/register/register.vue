<template>
  <view class="ui-all">
    <view class="ui-list">
      <text>姓名</text>
      <input v-model="registerForm.nickName" maxlength="11" type="text" placeholder="请输入姓名" class="input">
    </view>
    <view class="ui-list right">
      <text>性别</text>
      <picker @change="bindXingbieChange" :value="index" :range="xingbiearray">
        <view>{{sex}}</view>
      </picker>
    </view>
    <view class="ui-list">
      <text>身份证号</text>
      <input v-model="registerForm.idNumber" type="text" placeholder="填写身份证号" class="input">
    </view>
    <view class="ui-list">
      <text>手机号</text>
      <input v-model="registerForm.phonenumber" maxlength="11" type="number" placeholder="请输入手机号" class="input">
    </view>
    <view class="ui-list">
      <text>家庭住址</text>
      <input v-model="registerForm.address" type="text" placeholder="填写家庭住址" class="input">
    </view>
    <view class="ui-list">
      <text>用户照片</text>
      <view class="continer">
        <uni-file-picker v-model="yhimageValue" file-mediatype="image" mode="grid" file-extname="png,jpg" :limit="1" @select="selectyh" />
      </view>
    </view>
    <view class="ui-list">
      <text>密码</text>
      <input v-model="registerForm.password" type="text" :password="isHidePassword?true:false" placeholder="请输入密码" maxlength="20" class="input" />
      <image @click="isHidePasswordClick" class="eye" :src="isHidePassword?'/static/img/attention.png':'/static/img/attention_forbid.png'"></image>
    </view>
    <view class="ui-list">
      <text>验证码</text>
      <input v-model="registerForm.code" type="number" placeholder="请输入验证码" maxlength="20" class="input" />
      <image class="codeImg" :src="codeUrl" @click="getCode"></image>
    </view>
    <button class="mt5 zhuce" size="small" @click="submit">注册</button>
  </view>
</template>
<script>
let timer = '';
import {
  checkPhone,
  checkName,
  checkPwd,
  checkZhuzhi,
  checkIdCard,
  checkZhaopian,
  encrypt,
  plusXing,
  deepCopy,
  toast
} from "@/common/common.js"

import { register, getBumen } from "@/api/register.js"
import { getCodeImg } from '@/api/login'
import { commonUpload } from "@/api/business.js"
import config from '@/config'
export default {
  data() {
    return {
      sex: "请选择",
      radioGroup: ['国网职工', '外协人员'],
      yhimageValue: [],
      danweiMc: '请选择',
      bumenMc: '请选择',
      registerForm: {
        phonenumber: "",
        nickName: "",
        company: "",
        sex: "",
        password: "", //密码
        idNumber: "",
        address: "",
        companyId: '',
        deptId: "",
        avatar: "",
        userExtend1: "国网职工",
        'code': '',
        'uuid': '',
        allData: ""
      },
      xingbiearray: ['男', '女'],
      danweiArray: [],
      bumenArray: [{ deptName: '请选择单位' }],
      index: 0,
      isHidePassword: true,
      codeUrl: "",
    };
  },
  onLoad() {

    this.getCode()
    getBumen(100).then(res => {
      this.danweiArray = res.data
    })
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = 'data:image/gif;base64,' + res.img
        this.registerForm.uuid = res.uuid
      })
    },
    yonghuleixingchang(e) {
      this.registerForm.userExtend1 = e.detail.value;
      this.danweiMc = '请选择'
      this.bumenMc = '请选择'
      this.registerForm.company = ''
      this.bumenArray = [{ deptName: "请选择单位" }]
    },
    // 获取上传状态
    selectyh(e) {
      var tempFilePaths = e.tempFilePaths
      commonUpload(tempFilePaths[0])
        .then(res => {
          this.registerForm.avatar = res.fileName
        });
    },
    bindXingbieChange: function(e) {
      this.sex = this.xingbiearray[e.target.value]
      this.registerForm.sex = e.target.value
    },
    danweiChange: function(e) {
      getBumen(this.danweiArray[e.target.value].deptId).then(res => {
        this.bumenArray = res.data
      })
      this.danweiMc = this.danweiArray[e.target.value].deptName
      this.registerForm.companyId = this.danweiArray[e.target.value].deptId
    },
    bumenChange: function(e) {
      if (this.bumenArray[e.target.value].deptName == '请选择单位') {
        return
      }
      this.bumenMc = this.bumenArray[e.target.value].deptName
      this.registerForm.deptId = this.bumenArray[e.target.value].deptId
    },
    isHidePasswordClick() {
      this.isHidePassword = !this.isHidePassword
    },
    submit() {
      if (!this.registerForm.nickName) {
        toast("请输入姓名")
        return;
      };
      if (!checkIdCard(this.registerForm.idNumber)) {
        return;
      };
      if (!checkPhone(this.registerForm.phonenumber)) {
        return;
      };

      if (this.registerForm.userExtend1 == "国网职工") {
        if (this.danweiMc == '请选择') {
          toast('请选择工作单位')
          return;
        }
        if (this.bumenMc == '请选择') {
          toast('请填写工作部门')
          return;
        }
      } else {
        if (this.danweiMc == '请选择') {
          toast('请选择服务单位')
          return;
        }
        if (this.bumenMc == '请选择') {
          toast('请填写支撑部门')
          return;
        }
        if (this.registerForm.company == '') {
          toast('请填写所属公司')
          return;
        }

      }
      if (!this.registerForm.address) {
        toast("请输入家庭住址")
        return;
      };

      if (!this.registerForm.avatar) {
        toast("请选择用户照片")
        return;
      };

      if (!checkPwd(this.registerForm.password)) {
        return;
      };

      this.registerForm.allData = encrypt(this.registerForm.idNumber + '|' + this.registerForm.phonenumber)
      let param = deepCopy(this.registerForm)
      param.idNumber = plusXing(this.registerForm.idNumber, 6, 4)
      param.phonenumber = plusXing(this.registerForm.phonenumber, 3, 4)

      register(param).then(res => {
        toast("注册成功,正在前往登录")
        let name = (param.idNumber + param.phonenumber.substr(3)).replace(/\*/g, '')
        this.$store.commit("update", ["userInfo", { userName: name }])
        setTimeout(function() {
          uni.reLaunch({
            url: '/pages/login/login'
          })
        }, 800);
      }).catch(() => {
        this.getCode()
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

.zhuce {
  width: 200rpx;
  color: white;
  height: 75rpx;
  font-size: 30rpx;
  background-color: #69c8fa;
  position: absolute;
  bottom: 40rpx;
  right: 50%;
  transform: translate(50%);
}

.codeImg {
  height: 64rpx;
  position: absolute;
  right: 0;
  top: 10rpx;
  width: 200rpx;
}
</style>