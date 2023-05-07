<template>
  <view class="container"> 
    ------------- 权限测试 -------------
    <view v-if="hasRole(['common'])">角色权限测试</view>
    <view v-if="hasPerimission(['system:user:list'])">按钮权限测试</view>
     ------------- 字典测试 -------------
     ----------------字典一-----------------------
      
    <dict-tag :options="dict.type.sys_user_sex" :value="1" />
    <view v-for="dict in dict.type.sys_user_sex" :key="dict.value"> {{dict.label}}</view> 
    ------------------字典二---------------------
    
    <dict-tag :options="dict.type.yes_and_no" :value="1" />
    <view v-for="dict in dict.type.yes_and_no" :key="dict.value"> {{dict.label}}</view> 
    <!-- ------------- 手写测试------------- -->
    <!-- <htz-signature @sumbit="sumbit" cid="ceshi1"></htz-signature> -->
    <!-- <web-view src="https://nav.hzwdd.cn/" ></web-view> -->
  </view>
</template>
<script>
import htzSignature from '@/components/htz-signature/htz-signature.vue'
export default {
  computed: {},
  dicts: ['sys_user_sex', 'yes_and_no'],
  components: {
    htzSignature,
  },
  data() {
    return {}
  },
  onLoad() {},
  onShow() {
    console.log(uni.getStorageSync("zqtoken"));
  },
  methods: {
    sumbit(res) {}
  }
}

</script>
<style lang="scss" scoped>
</style>
