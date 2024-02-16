<script setup lang="ts">
import InputBox from '@/components/molecules/common/InputBox.vue';
import TextAtom from '@/components/atoms/TextAtom.vue';
import ButtonAtom from '@/components/atoms/ButtonAtom.vue';
import type { RoomUserInfoType } from '@/types/room'
import { ref, onMounted, inject, type Ref, watch } from 'vue'
import { getWordCloud, sendWord } from '@/api/history'
import { useRoute } from 'vue-router';
import VueWordCloud from 'vuewordcloud'

const route = useRoute()
const inputValue = ref();
const wordCloudList = ref([])
const roomUserInfo = inject<Ref<RoomUserInfoType>>(
    'roomUserInfo',
    ref({
        roomNo: Number(route.params.roomNo),
        roomUserNo: -1,
        roomName: '',
        roomNickname: '',
        profileUrl: ''
    })
)

const buttonHandler = () => {
    sendWord(roomUserInfo.value.roomNo, inputValue.value, ({ data }) => {
        getWords(roomUserInfo.value.roomNo)
        inputValue.value = ''
    }, (error) => console.error(error))
}

const getWords = (roomNo: number) => {
    getWordCloud(roomNo, ({ data }) => {
        wordCloudList.value = data.result
    }, (error) => console.error(error))
}

const triggering = inject('trigger') as Ref<boolean>

watch(triggering, () => {
    getWords(roomUserInfo.value.roomNo)
})

onMounted(() => {
    getWords(roomUserInfo.value.roomNo)
})
</script>

<template>
    <div class="flex flex-1 max-md:min-h-[460px] justify-center ">
        <div class="flex flex-1 flex-col md:w-full  md:max-w-[1000px]  items-center justify-center md:m-[20px]  ">
            <TextAtom class="text-2 mt-[20px]  font-bold">후기 워드 클라우드</TextAtom>
            <vue-word-cloud :words="wordCloudList
                " :color="([, weight]: [string, number]) =>
        weight > 10 ? 'Red' : weight > 5 ? 'blue' : 'Indigo'
        " style="
                height: 100%;
                max-width: 1000px;
            " font-family="Roboto" />
            <div
                class="z-10 md:flex  md:justify-center md:items-center max-md:relative md:bottom-[20px]   md:w-[150%] md:min-w-[360px] md:max-w-full     md:h-[75px] md:gap-[15px]   max-md:w-screen max-md:min-h-[100px] rounded-xl  max-md:border-A805Black max-md:border-solid max-md:border-2 max-md:bg-A805">
                <InputBox place-holder="메세지를 입력해주세요" v-model="inputValue" max-length="15"
                    custom-class="flex h-full items-center justify-center text-4 md:w-full  overflow-auto md:border-solid md:border-A805Black md:border-2 md:rounded-lg"
                    @input-enter="buttonHandler" />
                <ButtonAtom
                    custom-class="max-md:absolute bg-A805Cream bottom-0 right-0 rounded-lg max-md:w-[60px] max-md:h-[30px] md:w-[200px] md:h-full border-solid border-[1px] border-A805Grey m-[5px] text-4"
                    @button-click="buttonHandler">작성</ButtonAtom>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
