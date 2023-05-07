import { toast, showConfirm } from "@/common/common"
/**
 * 手机验证
 */
export function checkPhone(value) {
  let reg = /^1\d{10}$/
  if (reg.test(value)) {
    return true;
  }
  toast('手机号格式错误');
  return false
}
/**
 * 密码验证
 */
export function checkPwd(value) {
  let reg = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}/
  if (reg.test(value)) {
    return true;
  }
  toast('您的密码复杂度太低（密码中必须包含字母、数字、特殊字符并不少于8位）');
  return false
}
/**
 * 验证码验证
 */
export function checkCode(value) {
  if (value.length == 6) {
    return true;
  }
  toast('验证码必须是6位数字');
  return false
}
/**
 * 身份证验证
 */
export function checkIdCard(value) {
  let reg = /^\d{15}|\d{18}$/
  if (reg.test(value)) {
    return true;
  }
  toast('身份证必须是15或18位数字');
  return false
}
/**
 * 银行卡验证
 */
export function checkBankCard(value) {
  let reg = /^([1-9]{1})(\d{14}|\d{15}|\d{16}|\d{18})$/
  if (reg.test(value)) {
    return true;
  }
  toast('请输入正确的银行卡号');
  return false
}
