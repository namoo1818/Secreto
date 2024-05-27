<script setup lang="ts">
import member1 from '@/assets/images/member/member1.png'
import member2 from '@/assets/images/member/member2.png'
import member3 from '@/assets/images/member/member3.png'
import member4 from '@/assets/images/member/member4.jpg'
import member5 from '@/assets/images/member/member5.jpg'
import member6 from '@/assets/images/member/member6.png'
import master1 from '@/assets/images/guides/master1.png'
import master2 from '@/assets/images/guides/master2.png'
import master3 from '@/assets/images/guides/master3.png'
import master4 from '@/assets/images/guides/master4.png'
import master5 from '@/assets/images/guides/master5.png'
import master6 from '@/assets/images/guides/master6.png'
import master7 from '@/assets/images/guides/master7.png'
import master8 from '@/assets/images/guides/master8.png'
import user1 from '@/assets/images/guides/user1.png'
import user2 from '@/assets/images/guides/user2.png'
import user3 from '@/assets/images/guides/user3.png'
import user4 from '@/assets/images/guides/user4.png'
import user5 from '@/assets/images/guides/user5.png'
import user6 from '@/assets/images/guides/user6.png'
import user7 from '@/assets/images/guides/user7.png'
import user8 from '@/assets/images/guides/user8.png'
import user9 from '@/assets/images/guides/user9.png'
import user10 from '@/assets/images/guides/user10.png'
import user11 from '@/assets/images/guides/user11.png'
import user12 from '@/assets/images/guides/user12.png'
import user13 from '@/assets/images/guides/user13.png'
import user14 from '@/assets/images/guides/user14.png'
import TextAtom from '@/components/atoms/TextAtom.vue'
import HeaderLogo from '@/components/molecules/common/HeaderLogo.vue'
import CharacterAtom from '@/components/atoms/CharacterAtom.vue'
import ServiceFeature from '@/components/molecules/main/ServiceFeature.vue'
import { onMounted } from 'vue'
import { HomeFilled, UserOutlined, LeftOutlined, RightOutlined } from '@ant-design/icons-vue'
import { ref } from 'vue'

type Guide = {
    img: string
    text: string
}

const showIndex = ref(0)

const setting = ref<Array<Guide>>([
    {
        img: master1,
        text: '1. 메인화면 방리스트 조회 항목에서 가장 마지막 영역에 빈 카드에 + 버튼이 있는 카드를 눌러 방만들기를 할 수 있습니다.'
    },
    {
        img: master2,
        text: '2. 방만들기 전 방 이름과 방에서 쓸 닉네임을 설정할 수 있습니다.'
    },
    {
        img: master3,
        text: '3. 방에 들어오시면 방 설정을 하실 수 있습니다.'
    },
    {
        img: master4,
        text: '4. 게임기간, 미션의 주기, 방장의 참여여부, 미션의 종류, 참여 유저 관리를 하실 수 있습니다.'
    },
    {
        img: master5,
        text: '5. 게임 시작버튼을 눌러 게임을 시작 하실 수 있습니다.'
    },
    {
        img: master6,
        text: '6. 게임이 시작되면 방 설정에서 돌발 미션을 거실수 있어요.'
    },
    {
        img: master7,
        text: '7. 게임중 추가 인원 발생시 전체 셔플 또는 끼워넣기를 하실 수 있어요.'
    },
    {
        img: master8,
        text: '8. 방 설정에서 게임을 종료하거나 방장을 위임할 수 있어요'
    }
])

const secreto = ref<Array<Guide>>([
    {
        img: member1,
        text: '1. 이번 시크리또 프로젝트 팀장이자 BE 리더 이상학 입니다.'
    },
    {
        img: member2,
        text: '2. 이번 시크리또 프로젝트 BE 역할을 맡은 이승우 입니다 '
    },
    {
        img: member3,
        text: '3. 이번 시크리또 프로젝트 BE 역할을 맡은 이승우 입니다'
    },
    {
        img: member4,
        text: '4. 이번 시크리또 프로젝트 FE 리더 지인성 입니다.'
    },
    {
        img: member5,
        text: '5. 이번 시크리또 프로젝트 FE 역할을 맡은 김현창 입니다 '
    },
    {
        img: member6,
        text: '6. 이번 시크리또 프로젝트 FE 역할을 맡은 신시원 입니다'
    }
])

const user = ref<Array<Guide>>([
    {
        img: user1,
        text: '1. 메인화면 방리스트 조회 항목에서 초대코드입력을 누르고 Enter를 눌러 방에 입장할 수 있어요'
    },
    {
        img: user2,
        text: '2. 방에서 쓸 닉네임을 지정해주세요'
    },
    {
        img: user3,
        text: '3.방장이 입장을 승인해주는지 기다려주세요, 실시간으로 상태를 확인할 수 있어요'
    },
    {
        img: user4,
        text: '4. 입장이 승인되고 게임이 시작되면 자동으로 방으로 이동합니다.'
    },
    {
        img: user5,
        text: '5. 참여인원 탭을 누르면 참여인원을 확인할 수 있어요'
    },
    {
        img: user6,
        text: '6. 참여인원중 나의 마니띠는 파란색 버튼으로 표시가 됩니다.'
    },
    {
        img: user7,
        text: '7. 마니또라고 생각드는 사람이 있나면 마니또 예측하기 기능을 활용해보세요'
    },
    {
        img: user8,
        text: '8. 예측 사유를 적어주시면 추후 타임라인으로 제공이 됩니다'
    },
    {
        img: user9,
        text: '9. 내 미션 탭에서는 나에게 주어진 미션, 전체미션을 확인할 수 있어요.'
    },
    {
        img: user10,
        text: '10. 미션을 수행하셨나요? 인증게시판을 통해 미션을 인증해보세요'
    },
    {
        img: user11,
        text: '11. 마니또로부터 선물을 받으셨나요? 자랑게시판을 통해 자랑해보세요'
    },
    {
        img: user12,
        text: '12. 채팅 메뉴를 이용하시면 마니또, 마니띠와 익명으로 채팅하거나 단체채팅도 할 수 있어요'
    },
    {
        img: user13,
        text: '13. 게임이 종료되면 게임 통계와 타임라인을 확인해보세요'
    },
    {
        img: user14,
        text: '14. 후기를 남겨보세요, 실시간으로 사람들의 반응을 볼 수 있어요'
    }
])

const showData = ref<Array<Guide>>([
    {
        img: '',
        text: ''
    }
])

const leftHandler = () => {
    if (showIndex.value !== 0) showIndex.value--
}

const rightHandler = () => {
    if (showIndex.value !== showData.value.length - 1) showIndex.value++
}

const fetchData = async (data: Array<Guide>) => {
    showData.value = data
    showIndex.value = 0
}

const Secreto = async () => {
    await fetchData(secreto.value)
}

const Setting = async () => {
    await fetchData(setting.value)
}

const User = async () => {
    await fetchData(user.value)
}

onMounted(() => {
    Setting()
})
</script>

<template>
    <div class="flex flex-1 flex-col gap-[30px]">
        <div class="flex flex-col items-center md:mx-[20%] gap-[20px] max-md:gap-[15px]">
            <HeaderLogo />
            <TextAtom custom-class="md:text-1 max-md:text-2">시크리또를 소개합니다</TextAtom>
            <TextAtom custom-class="text-4">시크리또는 마니또 게임에 필요한 다양한 기능을 제공합니다.</TextAtom>
            <ServiceFeature />
        </div>
        <div class="flex flex-1 flex-col">
            <div class="flex flex-col w-full items-center justify-evenly">
                <TextAtom custom-class="md:text-1 max-md:text-2">서비스 사용법</TextAtom>
                <div class="flex w-full justify-evenly">
                    <div class="flex flex-col items-center justify-between cursor-pointer" @click="Secreto">
                        <CharacterAtom custom-class="w-[60px]" />
                        <TextAtom>시크리또 멤버</TextAtom>
                    </div>
                    <div class="flex flex-col items-center justify-between cursor-pointer" @click="Setting">
                        <HomeFilled style="font-size: 40px" /><text>방장 가이드</text>
                    </div>
                    <div class="flex flex-col items-center justify-between cursor-pointer" @click="User">
                        <UserOutlined style="font-size: 40px" /><text>유저 가이드</text>
                    </div>
                </div>
            </div>
            <div class="flex justify-center md:h-[700px] max-md:h-[400px]">
                <div class="flex max-w-[1200px] flex-col gap-[10px] w-full">
                    <div
                        class="flex relative items-center min-h-[600px] max-h-[600px] max-md:min-h-[320px] max-md:max-h-[320px]">
                        <LeftOutlined style="font-size: 24px; cursor: pointer" class="absolute left-0 z-10"
                            @click="leftHandler" />
                        <img :src="showData[showIndex].img" class="w-full h-full object-contain" />
                        <RightOutlined style="font-size: 24px; cursor: pointer" class="absolute right-0 z-10"
                            @click="rightHandler" />
                    </div>

                    <div class="flex h-[100px] bg-A805White items-center justify-center max-md:h-[80px]">
                        <TextAtom>{{ showData[showIndex].text }}</TextAtom>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>
