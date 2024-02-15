<script setup lang="ts">
import HeaderLogo from '@/components/molecules/common/HeaderLogo.vue'
import { useUserStore } from '@/stores/user'
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import { Dropdown, Menu, MenuItem } from 'ant-design-vue'
const emit = defineEmits(['myPageHandle', 'logoutHandle'])

const userStore = useUserStore()
</script>

<template>
    <div class="px-5 flex items-center w-full justify-between">
        <HeaderLogo class="max-w-[170px]" />

        <Dropdown :trigger="['click']">
            <AvatarAtom
                custom-class="header-profile cursor-pointer w-[4rem] h-[4rem] hover:scale-[1.05] transition-transform"
                :image-url="userStore.userInfo.profileUrl"
            />
            <template #overlay>
                <Menu>
                    <p class="text-center text-[1rem] font-bold my-2 px-3">
                        {{ userStore.userInfo.nickname }}
                    </p>
                    <MenuItem @click="() => emit('myPageHandle')"> 마이페이지 </MenuItem>
                    <MenuItem @click="() => emit('logoutHandle')"> 로그아웃 </MenuItem>
                </Menu>
            </template>
        </Dropdown>
    </div>
</template>

<style scoped></style>
