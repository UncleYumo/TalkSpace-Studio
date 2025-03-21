import { createApp } from 'vue'
import { createPinia } from 'pinia'

import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import './style.css'
import App from './App.vue'
import router from './router'
import VueCookies from 'vue-cookies'
import dayjs from 'dayjs'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

const app = createApp(App)

app.use(pinia)
app.use(router)
app.use(VueCookies)
app.config.globalProperties.$dayjs = dayjs

app.mount('#app')