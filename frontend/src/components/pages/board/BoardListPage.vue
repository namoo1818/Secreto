<script setup lang="ts">
import { getBoard } from '@/api/board'
import { watch, ref, type Ref, computed, onMounted, inject } from 'vue'
import { BoardCategory, type BoardRequestType, type BoardResponseType } from '@/types/board'
import BoardSearchBox from '@/components/organisms/board/BoardSearchBox.vue'
import BoardTableHeader from '@/components/molecules/board/BoardTableHeader.vue'
import BoardElement from '@/components/molecules/board/BoardElement.vue'
import MobileBoardElement from '@/components/molecules/board/MobileBoardElement.vue'
import { Pagination } from 'ant-design-vue'
import { useRoute } from 'vue-router'
import router from '@/router'
import ButtonAtom from '@/components/atoms/ButtonAtom.vue'
import { EditOutlined } from '@ant-design/icons-vue'
import TextAtom from '@/components/atoms/TextAtom.vue'

const route = useRoute()
const boards: Ref<BoardResponseType[]> = ref([])
const boardCategory = computed(() => {
    return String(route.query.boardCategory)
})
const roomNo: Ref<number> = inject('roomNo', ref(-1))
const roomUserNo = inject<Ref<number>>('roomUserNo', ref(-1))
const hostRoomUserNo = inject<Ref<number>>('hostRoomUserNo', ref(-1))
const boardRequest: Ref<BoardRequestType> = ref({
    boardCategory: String(route.query.boardCategory),
    title: route.query.title === undefined ? '' : String(route.query.title),
    content: route.query.content === undefined ? '' : String(route.query.content),
    writer: route.query.writer === undefined ? '' : String(route.query.writer),
    page: Number(route.query.page) > 0 ? Number(route.query.page) : 0,
    size: 10
})
const current: Ref<number> = ref(boardRequest.value.page + 1)
const total: Ref<number> = ref(0)

const loadBoardData = () => {
    boardRequest.value.boardCategory = boardCategory.value
    getBoard(
        roomNo.value,
        boardRequest.value,
        (response) => {
            const data = response.data
            if (data.status === 'OK') {
                const pageable = data.result.pageable
                boards.value = data.result.content
                boardRequest.value.page = pageable.pageNumber
                boardRequest.value.size = pageable.pageSize
                total.value = data.result.totalElements
                console.log('list response', boards.value)
            }
        },
        (error) => console.error(error)
    )
}

const boardDetail = (boardNo: number): void => {
    router.push({
        name: 'game-board-detail',
        query: { boardNo: boardNo, ...boardRequest.value }
    })
}

const searchButtonHandler = (condition: string, keyword: string) => {
    boardRequest.value.title = null
    boardRequest.value.writer = null
    boardRequest.value.content = null
    boardRequest.value.page = 0
    switch (condition) {
        case 'title':
            boardRequest.value.title = keyword
            break
        case 'writer':
            boardRequest.value.writer = keyword
            break
        case 'content':
            boardRequest.value.content = keyword
            break
    }
    current.value = 1
    loadBoardData()
}
onMounted(() => {
    loadBoardData()
})
watch(current, () => {
    loadBoardData()
})
watch(boardCategory, () => {
    boardRequest.value.page = 0
    current.value = 1
    loadBoardData()
})
</script>

<template>
    <div
        class="flex flex-col w-full md:min-w-[568px] max-w-[1400px] max-md:min-w-0 items-center relative mt-6"
    >
        <!-- pc -->
        <table
            class="table-auto text-center max-md:hidden w-full"
            :class="boards.length === 0 ? 'min-h-[300px]' : ''"
        >
            <BoardTableHeader class="max-md:hidden" />
            <tbody>
                <BoardElement
                    v-for="board in boards"
                    :key="board.boardNo"
                    :board="board"
                    @click="() => boardDetail(board.boardNo)"
                />
            </tbody>
            <TextAtom
                class="text-[1.5rem] text-center absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-10"
                v-if="boards.length === 0"
            >
                작성된 게시글이 없습니다.
            </TextAtom>
        </table>

        <!-- mobile -->
        <div class="min-h-[150px] w-full flex flex-col items-center relative md:hidden">
            <template v-for="(board, i) in boards" :key="board.boardNo">
                <MobileBoardElement
                    :board="board"
                    :class="i == 0 ? 'border-t' : ''"
                    @click="() => boardDetail(board.boardNo)"
                />
            </template>
            <TextAtom
                class="text-[1.2rem] text-center absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
                v-if="boards.length === 0"
            >
                작성된 게시글이 없습니다.
            </TextAtom>
        </div>
        <Pagination
            class="my-[30px]"
            v-model:current="current"
            :total="total"
            :page-size="boardRequest.size"
            show-less-items
            :showSizeChanger="false"
            @change="(page, pageSize) => (boardRequest.page = page - 1)"
        />
        <div class="flex w-full justify-between px-4 items-center max-md:flex-col mb-5">
            <div></div>
            <BoardSearchBox
                @search-handle="searchButtonHandler"
                :boardCategory="boardCategory"
                :default-condition="
                    boardRequest.title
                        ? 'title'
                        : boardRequest.writer
                          ? 'writer'
                          : boardRequest.content
                            ? 'content'
                            : 'title'
                "
                :default-keyword="
                    boardRequest.title
                        ? boardRequest.title
                        : boardRequest.writer
                          ? boardRequest.writer
                          : boardRequest.content
                            ? boardRequest.content
                            : ''
                "
            />
            <div class="max-md:w-full">
                <ButtonAtom
                    custom-class="button-style-4 button-claret text-[18px] md:w-[95px] font-bold flex justify-center items-center gap-[5px] max-md:w-full h-[35px] max-md:mt-7 max-md:rounded-none"
                    v-if="boardCategory !== BoardCategory.NOTICE || roomUserNo === hostRoomUserNo"
                    @button-click="
                        () =>
                            router.push({
                                name: 'game-board-write',
                                query: { boardCategory: boardCategory }
                            })
                    "
                    ><EditOutlined /><span>글쓰기</span></ButtonAtom
                >
            </div>
        </div>
    </div>
</template>

<style scoped>
:deep(.ant-pagination-item-active) {
    border-color: #ac87c5;
}
:deep(.ant-pagination-item-active) a {
    color: #ac87c5;
}

:deep(.ant-pagination-item-active:hover) {
    border-color: #756ab6;
}
:deep(.ant-pagination-item-active:hover) a {
    color: #756ab6;
}
</style>
