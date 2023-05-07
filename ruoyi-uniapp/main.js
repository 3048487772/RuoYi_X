import Vue from 'vue'
import App from './App'
import store from '@/store/store'
import DictData from '@/common/dict/init'
import DictTag from '@/common/dict/DictTag'
import { hasRole, hasPerimission } from '@/common/auth.js'
import { toast, showConfirm } from "@/common/common.js"
import uView from 'uni_modules/uview-ui'
Vue.use(uView)
Vue.component('DictTag', DictTag)
Vue.prototype.$store = store;
Vue.prototype.toast = toast
Vue.prototype.showConfirm = showConfirm
Vue.prototype.hasRole = hasRole
Vue.prototype.hasPerimission = hasPerimission
Vue.config.productionTip = false
DictData.install()
App.mpType = 'app'
const app = new Vue({ ...App
})
app.$mount()
