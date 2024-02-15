
<script setup lang="ts">
import HeaderLogo from '@/components/molecules/common/HeaderLogo.vue'
import HeaderProfile from '@/components/molecules/common/HeaderProfile.vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import { MenuOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/stores/user'
import { ref, computed, onMounted, onUnmounted, watch } from 'vue'
import { storeToRefs } from 'pinia'
import { useMenuStore } from '@/stores/menu'
import { useRouter } from 'vue-router'

const prop = defineProps({
    roomName: {
        type: String as () => string | null,
        default: ''
    },
    inRoom: {
        type: Boolean as () => boolean,
        default: false
    },
    nickName: {
        type: String as () => string,
        default: ''
    }
})

const router = useRouter()
const userStore = useUserStore()
const menuStore = useMenuStore()
const { userInfo } = storeToRefs(userStore)
const { menuSeen } = storeToRefs(menuStore)

watch(() => { prop.inRoom }, () => {
    console.log(prop.inRoom)
})

watch(() => { prop.nickName }, () => {
    console.log(prop.nickName)
})

const windowWidth = ref(window.innerWidth)
const isMdOrLarger = computed(() => windowWidth.value >= 768) // 예시에서는 md의 화면 크기가 768px이라고 가정

const handleResize = () => {
    windowWidth.value = window.innerWidth
}

const clickHandler = () => {
    console.log('clickHandler', userInfo.value)
}

const menuClick = () => {
    menuSeen.value = !menuSeen.value
}

onMounted(() => {
    handleResize()
    window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
    window.removeEventListener('resize', handleResize)
})
</script>

<template>
    <div
        class="relative flex md:min-h-[140px] max-md:min-h-[100px] bg-A805Cream w-full md:justify-between items-center shadow-lg">
        <MenuOutlined v-if="!isMdOrLarger" style="font-size: 24px" class="ml-[40px]" @click="menuClick" />
        <HeaderLogo class="md:ml-[20px] max-md:mx-auto cursor-pointer" @click="router.push('/main')" />

        <!-- 게임중에만 표시되도록 상태 표시 필요. -->
        <TextAtom class="text-2 truncate max-w-[20%]" v-if="isMdOrLarger && inRoom">{{ roomName }}</TextAtom>
        <HeaderProfile @click="clickHandler" v-if="isMdOrLarger" :imageUrl="userInfo.profileUrl"
            :name="inRoom ? nickName : userInfo.nickname" class="mr-[40px]" />
    </div>
</template>

<style scoped></style>

