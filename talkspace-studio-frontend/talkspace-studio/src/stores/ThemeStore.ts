import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { theme } from 'ant-design-vue'
import variables from '../styles/variables.module.scss'

/**
 * app 配置 开启持久化
 */
export const useThemeStore = defineStore(
  'tss-theme',
  () => {
    const themeName = ref('red') // 主题名称
    const darkMode = ref('light') // 颜色模式
    const darkModeComp = computed(() => {
      document.documentElement.setAttribute('data-dark', darkMode.value)
      return darkMode.value
    })
    const themeConfig = computed(() => {
      document.documentElement.setAttribute('data-theme', themeName.value)
      // 主题配置
      return {
        token: {
          colorPrimary: variables[themeName.value] || '#27ba9b',
          colorSuccess: '#1dc779',
          colorWarning: '#ffb302',
          colorError: '#cf4444',
          colorInfo: variables[themeName.value] || '#27ba9b',
          colorBgHeader: darkMode.value === 'light' ? '#FFFFFF' : '#000000',
          wireframe: true
        },
        algorithm: darkMode.value === 'light' ? theme.defaultAlgorithm : theme.darkAlgorithm
      }
    })
    const setThemeName = (value: string) => {
      themeName.value = value
    }
    const getThemeModeName = () => {
      // 返回light或dark
      return darkMode.value
    }
    const toggleDarkMode = () => {
      darkMode.value = darkMode.value === 'light' ? 'dark' : 'light'
    }
    // 根据暗黑模式状态返回对应logo路径
    const logoPath = computed(() => {
      return `http://uncleyumo.cn:9000/talkspace-studio/frontend_assets/logo/talkspace_studio_logo_${darkMode.value}.png`
    })

    return { themeName, themeConfig, darkMode, darkModeComp, logoPath, setThemeName, toggleDarkMode, getThemeModeName }
  }, {
  // 持久化存储
  persist: true
})
