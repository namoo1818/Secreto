import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
    base: './',
    plugins: [
        vue({
            script: {
                defineModel: true,
                propsDestructure: true
            }
        }),
        vueJsx()
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url)),
            'ant-design-vue': fileURLToPath(
                new URL('./node_modules/ant-design-vue/es/index.js', import.meta.url)
            )
        }
    },
    build: {
        rollupOptions: {
            external: ['socket.io-client', 'ant-design-vue', 'vue-clipboard3', 'dayjs']
        }
    }
})
