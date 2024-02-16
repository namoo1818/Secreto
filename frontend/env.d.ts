/// <reference types="vite/client" />

declare namespace NodeJS {
    interface ProcessEnv {
        VITE_API_BASE_URL: string
    }
}
declare module 'vuewordcloud'
declare module 'event-source-polyfill'
declare module 'quill-image-uploader'
declare module 'sockjs-client'
declare module 'stompjs'
declare module '*.vue' {
    import type { DefineComponent } from 'vue'
    const component: DefineComponent<{}, {}, any>
    export default component
}
