<script setup lang="ts">
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import CheckBox from '@/components/molecules/common/CheckBox.vue'
import InputBox from '@/components/molecules/common/InputBox.vue'
import { computed, inject, onMounted, ref, type Ref } from 'vue'
import { BoardCategory, type BoardModifyRequestType } from '@/types/board'
import { modifyPost, getPost } from '@/api/board'
import { useRoute } from 'vue-router'
import { toolbarOptions, modules, getFirstImageUrl } from '@/utils/editor'
import router from '@/router'

const route = useRoute()
const roomNo: Ref<number> = inject('roomNo', ref(-1))
const boardNo = computed(() => {
    return Number(route.query.boardNo)
})
const boardCategory = computed(() => {
    return String(route.query.boardCategory)
})
const boardModifyRequest: Ref<BoardModifyRequestType> = ref({
    title: '',
    content: '',
    imgUrl: null,
    publicYn: false
})
const userMission: Ref<string> = ref('')
const submitButtonHandle = () => {
    console.log(boardModifyRequest.value)
    if (boardCategory.value !== BoardCategory.CERTIFICATE) {
        boardModifyRequest.value.publicYn = true
    }
    boardModifyRequest.value.imgUrl = getFirstImageUrl(boardModifyRequest.value.content)
    modifyPost(
        boardNo.value,
        roomNo.value,
        boardModifyRequest.value,
        (response) => {
            if (response.data.status === 'OK') {
                console.log()
                router.push({
                    name: 'game-board-detail',
                    query: { boardCategory: boardCategory.value, boardNo: response.data.result }
                })
            }
        },
        (error) => {
            alert(error.message)
            console.error(error)
        }
    )
}

onMounted(() => {
    getPost(
        roomNo.value,
        boardNo.value,
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                console.log(data.message)
                console.log(data.result)
                boardModifyRequest.value.title = data.result.title
                boardModifyRequest.value.content = data.result.content
                boardModifyRequest.value.publicYn = data.result.publicYn
                userMission.value = data.result.userMission
            }
        },
        (error) => alert('게시글 조회 실패')
    )
})
</script>

<template>
    <div class="w-full flex justify-center">
        <div
            class="flex flex-col w-full md:min-w-[568px] max-w-[1400px] max-md:min-w-0 items-center px-[20px] gap-[10px]"
        >
            <div class="w-full flex flex-col gap-[10px] my-[20px]">
                <div class="flex flex-col gap-[15px] border border-A805LightGrey p-5 rounded-sm">
                    <div
                        class="flex justify-between md:h-[30px] max-md:flex-col-reverse max-md:gap-3"
                        v-if="boardCategory === BoardCategory.CERTIFICATE"
                    >
                        <InputBox
                            type="text"
                            input-class="input-box-style-2 line-lightGrey bg-A805White text-A805Black cursor-not-allowed"
                            custom-class="w-[65%] max-md:w-full max-md:h-[30px]"
                            custom-id="missionCategory"
                            v-model="userMission"
                            readonly="true"
                        ></InputBox>
                        <CheckBox
                            class="gap-[10px] md:justify-end max-md:justify-center accent-[#E0AED0]"
                            custom-id="publicYn"
                            v-model="boardModifyRequest.publicYn"
                            >인증글 공개</CheckBox
                        >
                    </div>
                    <InputBox
                        type="text"
                        label-class="ps-[10px]"
                        input-class="input-box-style-2 line-lightGrey bg-A805White text-A805Black"
                        custom-class="w-full"
                        custom-id="title"
                        place-holder="제목을 입력해 주세요."
                        v-model="boardModifyRequest.title"
                    ></InputBox>
                </div>
                <div class="w-full h-[500px] mb-16">
                    <QuillEditor
                        theme="snow"
                        :toolbar="toolbarOptions"
                        :modules="modules"
                        v-model:content="boardModifyRequest.content"
                        content-type="html"
                    />
                </div>
                <div class="flex justify-end max-md:mt-14">
                    <ButtonAtom
                        custom-class="button-style-4 button-claret text-[18px] font-bold flex justify-center items-center gap-[5px] max-md:w-full"
                        @button-click="submitButtonHandle"
                        >수정</ButtonAtom
                    >
                </div>
            </div>
        </div>
    </div>
</template>
