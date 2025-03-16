import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  envDir: './', // 指定环境文件所在目录
  plugins: [
    vue(),
    tailwindcss()
  ],
})
