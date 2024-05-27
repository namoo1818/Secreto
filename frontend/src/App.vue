<script setup lang="ts">
import { RouterView } from 'vue-router'
import HeaderBar from '@/components/organisms/common/HeaderBar.vue'
import FooterBar from '@/components/organisms/common/FooterBar.vue'
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'
import { useMenuStore } from '@/stores/menu'
// import { storeToRefs } from 'pinia'
const menuStore = useMenuStore()
// const { menuSeen } = storeToRefs(menuStore)
const { handleResize, setHeight } = menuStore

const headerSeen = ref(false)
const route = useRoute()

onMounted(() => {
    setHeight()
    window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
    window.removeEventListener('resize', handleResize)
})

const roomName = ref<string | undefined>()
const inRoom = ref<boolean>(false)
const nickName = ref<string>('')

const updateGameState = (state: boolean) => {
    inRoom.value = state
}
const updateRoomName = (name: string | undefined) => {
    roomName.value = name
}

const setNickName = (name: string) => {
    nickName.value = name
}

watch(route, () => {
    if (route.meta.hide === true) {
        headerSeen.value = false
    } else {
        headerSeen.value = true
    }
})
</script>

<template>
    <div class="flex flex-1 flex-col min-w-[360px] w-screen h-real-screen overflow-x-hidden">
        <HeaderBar v-if="headerSeen" @updateRoomName="updateRoomName" :room-name="roomName" :in-room="inRoom"
            :nick-name="nickName" />
        <RouterView @update-name="updateRoomName" @in-room="updateGameState" @set-nickname="setNickName" />
        <FooterBar v-if="headerSeen" />
    </div>
</template>

<style scoped></style>
