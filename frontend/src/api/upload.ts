import type { AxiosInstance, AxiosResponse } from 'axios'
import { localAxios } from '@/utils/http-commons'
const s3Instance: AxiosInstance = localAxios('multipart/form-data')

function imageUpload(
    file: FormData,
    success: (response: AxiosResponse) => void,
    fail: (error: any) => void
) {
    s3Instance.post(`/upload`, file).then(success).catch(fail)
}

export { imageUpload }
