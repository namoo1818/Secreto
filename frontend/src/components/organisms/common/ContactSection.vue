<script setup lang="ts">
import { PhoneOutlined, HomeOutlined, MailOutlined } from '@ant-design/icons-vue'
import TextAtom from '@/components/atoms/TextAtom.vue'
</script>

<template>
    <div
        class="flex flex-col justify-evenly bg-[#EAEEF3] md:gap-[60px] max-md:gap-[20px] max-md:p-[30px] md:py-[60px] max-md:py-[30px]"
    >
        <TextAtom custom-class="text-1 text-center">Contact Us</TextAtom>
        <TextAtom custom-class="text-3 text-center"
            >서비스에 대하여 궁금하신 사항이 있다면 얼마든지 연락주세요. <br />확인 후 빠르게
            연락드리도록 하겠습니다</TextAtom
        >
        <div class="flex flex-1 flex-col md:mx-[22%] gap-[10px] max-md:mx-auto">
            <TextAtom custom-class="text-2 text-start max-md:text-center">응대 가능 시간</TextAtom>

            <div class="flex w-full justify-between">
                <div class="flex gap-[20px]">
                    <TextAtom custom-class="text-4">Mon - Fri</TextAtom
                    ><TextAtom custom-class="text-4 text-A805Blue">9 AM - 6 PM</TextAtom>
                </div>
                <div class="flex items-center w-[210px] gap-[10px] max-md:hidden">
                    <HomeOutlined /> <TextAtom>서울시 강남구 테헤란로 212</TextAtom>
                </div>
            </div>
            <div class="flex w-full justify-between">
                <div class="flex gap-[20px]">
                    <TextAtom custom-class="text-4">Sat - Sun</TextAtom
                    ><TextAtom custom-class="text-4 text-A805Blue">OFF</TextAtom>
                </div>
                <div class="flex items-center w-[210px] gap-[10px] max-md:hidden">
                    <PhoneOutlined /> <TextAtom>1544-9001</TextAtom>
                </div>
            </div>
        </div>
        <div class="flex justify-center max-md:flex-col items-center">
            <div class="flex items-center md:gap-[20px] max-md:gap-[10px] max-md:text-center">
                <MailOutlined /> <TextAtom>문의&nbsp;</TextAtom>
            </div>
            <TextAtom>FE: crm03008@gmail.com |&nbsp;</TextAtom>
            <TextAtom>BE : yee950419@gmail.com</TextAtom>
        </div>
    </div>
</template>

<style scoped></style>
