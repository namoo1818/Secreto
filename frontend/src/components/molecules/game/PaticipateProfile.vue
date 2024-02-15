<script setup lang="ts">
import { ref } from 'vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
import AvatarAtom from '@/components/atoms/AvatarAtom.vue'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue';

const { imageUrl, nickName, customClass, maniti, predictType } = defineProps(['customClass', 'imageUrl', 'nickName', 'maniti', 'mine', 'predictType'])

const emit = defineEmits(['predict-manito'])

const predictButtonClick = () => {
    emit('predict-manito')
}

</script>

<template>
    <div class="relative flex flex-col text-center w-[200px] h-[200px]  group" :class="customClass">
        <ButtonAtom v-if="maniti"
            class="absolute w-[150px] rounded-lg text-white self-center top-[-30px] p-[5px] bg-A805Blue ">나의
            마니띠
        </ButtonAtom>
        <ButtonAtom v-else-if="mine"
            class="absolute w-[150px] rounded-lg text-white self-center top-[-30px] p-[5px] bg-A805Black ">
            나
        </ButtonAtom>
        <ButtonAtom v-else
            class="absolute hidden group-hover:block w-[150px] rounded-lg text-white self-center p-[5px]  bg-A805Khaki z-10"
            @click="predictButtonClick">
            마니또
            예측하기
        </ButtonAtom>
        <AvatarAtom :imageUrl="imageUrl" customClass="w-full h-full" :predict-type="predictType"></AvatarAtom>
        <TextAtom>{{ nickName }}</TextAtom>
    </div>
</template>

<style scoped></style>

