const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()
  return [year, month, day].map(formatNumber)
    .join('-') + ' ' + [hour, minute, second].map(formatNumber)
    .join(':')
}
Date.prototype.format = function(format) {
  var o = {
    "M+": this.getMonth() + 1,
    "d+": this.getDate(),
    "h+": this.getHours(),
    "m+": this.getMinutes(),
    "s+": this.getSeconds(),
    "q+": Math.floor((this.getMonth() + 3) / 3),
    "S": this.getMilliseconds()
  };
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + "")
      .substr(4 - RegExp.$1.length))
  }
  for (var k in o) {
    if (new RegExp("(" + k + ")")
      .test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
        .substr(("" + o[k])
          .length))
    }
  }
  return format
};
const formatDate = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  return [year, month, day].map(formatNumber)
    .join('-')
}
const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}
const wxuuid = function() {
  var s = [];
  var hexDigits = "0123456789abcdef";
  for (var i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
  }
  s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
  s[8] = s[13] = s[18] = s[23] = "";
  var uuid = s.join("");
  return uuid
}

function timeFormat(time, format, emptyReturn) {
  if (!time || time == "0000-00-00 00:00:00" || time == null) {
    if (emptyReturn != undefined) {
      return emptyReturn
    }
    return "-"
  }
  if (!time || time == "0000-00-00" || time == null) {
    if (emptyReturn != undefined) {
      return emptyReturn
    }
    return ""
  }
  if (typeof time == 'object') {
    if (format == 'date') {
      return time.format('yyyy-MM-dd');
    } else if (format == 'datetime') {
      return time.format('yyyy-MM-dd hh:mm');
    } else if (format == 'datetimes') {
      return time.format('yyyy-MM-dd hh:mm:ss');
    }
  } else {
    if (format == 'date') {
      return time.split(" ")[0];
    } else if (format == 'datetime') {
      return time.split(":")[0] + ":" + time.split(":")[1]
    }
    return time
  }
}
module.exports = {
  formatTime: formatTime,
  timeFormat: timeFormat,
  wxuuid: wxuuid,
  formatDate: formatDate
}
