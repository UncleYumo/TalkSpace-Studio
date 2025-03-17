import { createApp } from 'vue'
import './style.css'
import axios from 'axios'
import App from './App.vue'

const app = createApp(App)

app.use(axios)
    .mount('#app')