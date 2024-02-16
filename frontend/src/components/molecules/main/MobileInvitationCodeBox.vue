<script setup lang="ts">
import InputBox from '@/components/molecules/common/InputBox.vue'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { defineModel } from 'vue'
import {ref} from 'vue'
const entryCode = ref('')
const emit = defineEmits(['submitButtonHandle'])


</script>


<template>
    <div class="bg-A805RealWhite flex flex-col w-full p-5 gap-[10px]">
        <InputBox
            custom-class="input-box-style-2 bg-A805White w-full h-[50px]"
            input-class="text-center text-[24px]"
            place-holder="초대코드 입력"
            v-model="entryCode"
        />
        <ButtonAtom
            class="button-style-4 w-full button-claret"
            @button-click="() => emit('submitButtonHandle', entryCode)"
        >
            입장
        </ButtonAtom>
    </div>
</template>

<style scoped></style>
