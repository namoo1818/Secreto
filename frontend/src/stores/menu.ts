import { ref, watch } from 'vue'
import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menuStore', () => {
    // 메인메뉴 체크 여부
    const menuSeen = ref(false)

    // 모바일, PC 체크
    const isMobile = ref(window.innerWidth <= 768)

    // 현재 윈도우의 너비
    const windowWidth = ref(window.innerWidth)

    const handleClick = () => {
        menuSeen.value = !menuSeen.value
    }

    const setHeight = () => {
        const vh = window.innerHeight * 0.01
        document.documentElement.style.setProperty('--vh', `${vh}px`)
    }

    const handleResize = () => {
        setHeight()
        windowWidth.value = window.innerWidth
        // pc -> 모바일로 된 경우
        if (!isMobile.value && window.innerWidth <= 768) {
            menuSeen.value = false
            isMobile.value = true
        }
        // 모바일 -> pc
        else if (isMobile.value && window.innerWidth > 768) {
            menuSeen.value = false
            isMobile.value = false
        }
    }

    watch(windowWidth, () => handleResize)

    return { menuSeen, isMobile, windowWidth, handleResize, handleClick, setHeight }
})
