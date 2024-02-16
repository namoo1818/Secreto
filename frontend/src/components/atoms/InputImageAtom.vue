<script setup lang="ts">
import { imageUpload } from '@/api/upload'
import { ref, type Ref } from 'vue'

const emit = defineEmits(['imageUploadHandle'])

defineProps(['customClass'])
const uploadHandler = (event: any) => {
    const files = event.target.files
    if (files.length > 0) {
        const file = files[0]
        const formData = new FormData()
        formData.append('file', file)
        imageUpload(
            formData,
            (response) => {
                const data = response.data
                if (data.status === 'OK') {
                    const url = data.result.saveFile
                    emit('imageUploadHandle', url)
                }
                console.log(response)
            },
            (error) => {
                alert('Upload failed')
                console.error(error)
            }
        )
    }
}
</script>

<template>
    <div class="">
        <label for="file-upload" class="custom-class"><slot></slot></label>
        <input
            type="file"
            id="file-upload"
            class="hidden"
            accept="image/*"
            required
            @input="uploadHandler"
        />
    </div>
</template>

<style scoped></style>
