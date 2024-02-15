import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import tsconfigPaths from 'vite-tsconfig-paths'

import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
    base: '/',
    plugins: [
        tsconfigPaths(),
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
            '@/': new URL('./src/', import.meta.url).pathname
        }
    },
    build: {}
})
