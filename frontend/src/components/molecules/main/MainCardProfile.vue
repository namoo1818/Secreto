<script setup lang="ts">
import { useUserStore } from '@/stores/user'
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import { Dropdown, Menu, MenuItem } from 'ant-design-vue'
import TextAtom from '@/components/atoms/TextAtom.vue'

const emit = defineEmits(['myPageHandle', 'logoutHandle'])
const userStore = useUserStore()
</script>

<template>
    <div class="px-5 flex items-center w-full justify-between">
        <Dropdown class="cursor-pointer">
            <div class="flex justify-center items-center w-full">
                <AvatarAtom
                    custom-class="header-profile cursor-pointer w-[6rem] h-[6rem]"
                    :image-url="userStore.userInfo.profileUrl"
                />
                <TextAtom custom-class="text-[1.5rem] mx-3 font-bold max-w-[200px] truncate">
                    {{ userStore.userInfo.nickname }}
                </TextAtom>
            </div>
            <template #overlay>
                <Menu>
                    <p class="text-center text-[1.2rem] font-bold my-2 px-3">
                        {{ userStore.userInfo.nickname }}
                    </p>
                    <MenuItem @click="() => emit('myPageHandle')">
                        <p class="text-[1.1rem]">마이페이지</p>
                    </MenuItem>
                    <MenuItem @click="() => emit('logoutHandle')">
                        <p class="text-[1.1rem]">로그아웃</p>
                    </MenuItem>
                </Menu>
            </template>
        </Dropdown>
    </div>
</template>

<style scoped></style>
