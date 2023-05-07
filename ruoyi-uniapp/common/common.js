//大于固定字符串显示省略号
export function handleStr(str, length) {
  let handle_str;
  str.length <= length ? handle_str = str : handle_str = str.slice(0, length) + "...";
  return handle_str
}
//数组对象指定属性过滤
export function arrayObjFilter(arrayObj, keyAttributeArray, keyValue) {
  // 使用列子：
  // let arry = [{
  //  name:"dsa",
  //  key:"152"
  //  },{
  //    name:"ooo",
  //    key:"sdf"
  //  },{
  //    name:123,
  //    key:"wewe"
  //  },{
  //    name:"loe",
  //    key:"uuwe"
  //  }];
  //arrayObjFilter(arry,["name","key"],"s");
  let tmpArray = [];
  for (let i = 0; i < arrayObj.length; i++) {
    for (let j in arrayObj[i]) {
      for (let l = 0; l < keyAttributeArray.length; l++) {
        if (j == keyAttributeArray[l]) {
          if (arrayObj[i][j].toString()
            .indexOf(keyValue) != -1) {
            tmpArray.push(arrayObj[i]);
          }
        }
      }
    }
  }
  return tmpArray;
}
export function deepCopy(obj) {
  let newobj = obj.constructor === Array ? [] : {};
  if (typeof obj !== 'object') {
    return;
  }
  for (let i in obj) {
    newobj[i] = typeof obj[i] === 'object' ? cloneObj(obj[i]) : obj[i];
  }
  return newobj
};
//隐藏部分字符串
export function hideStr(str, frontLen, endLen, zhanweifu = '*') {
  var len = str.length - frontLen - endLen;
  var xing = '';
  for (var i = 0; i < len; i++) {
    xing += zhanweifu;
  }
  return str.substring(0, frontLen) + xing + str.substring(str.length - endLen);
}
export function toast(title) {
  uni.showToast({
    icon: 'none',
    title: title
  })
}
export function showConfirm(content) {
  return new Promise((resolve, reject) => {
    uni.showModal({
      title: '提示',
      content: content,
      cancelText: '取消',
      confirmText: '确定',
      success: function(res) {
        resolve(res);
      }
    });
  });
}
export function showActionSheet(content = ['确定', '取消']) {
  return new Promise((resolve, reject) => {
    uni.showActionSheet({
      itemList: content,
      success: function(res) {
        resolve(res)
      },
    });
  })
}
