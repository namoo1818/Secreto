import axios from 'axios'
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'
import { httpStatusCode } from './http-status'
import { regenerateToken } from '@/api/user'
const { VITE_API_BASE_URL } = import.meta.env
import { useRouter } from 'vue-router'

/**
 * 헤더 동적 셋팅을 적용한 axios 인스턴스 생성부
 * @author 지인성
 *
 * 이미지 업로드를 위해 localAxios 생성 시 content type을 입력받을 수 있도록 수정
 * 이미지 업로드 시에는 'Content-Type'을 multipart/form-data로 설정
 * @author 김현창
 */

const whiteList = ['/users/log-in', '/users/sign-up', '/users/log-out', '/oauth/redirect']

function localAxios(contentType?: string) {
    const instance = axios.create({
        baseURL: VITE_API_BASE_URL
    })

    // 모든 요청에 대해 기본 헤더 속성 설정
    instance.defaults.headers.common['Type'] = 'bearer'
    instance.defaults.headers.common['Content-Type'] = 'application/json;charset=utf-8'
    instance.defaults.headers.common['AccessToken'] = ''
    instance.defaults.headers.common['RefreshToken'] = ''
    if (contentType) {
        instance.defaults.headers.common['Content-Type'] = contentType
    }

    // 특정 요청에 대해서만 헤더 설정이 필요할때는
    // instance.defaults.headers.{RestApi}['타입명'] = '값' 써주기

    // 리퀘스트 요청전 피니아에 있는 최신 토큰 값 (AccessToken, RefreshToken)을 세팅
    instance.interceptors.request.use(
        (config) => {
            // Store를 호출 직전에 불러오지 않으면 초기화 에러가 나기 때문에 주의!
            const userStore = useUserStore()
            const { accessToken, refreshToken } = storeToRefs(userStore)

            if (!whiteList.includes(String(config.url))) {
                config.headers['AccessToken'] = 'bearer ' + accessToken.value
                config.headers['RefreshToken'] = 'bearer ' + refreshToken.value
            }

            return config
        },

        function (error) {
            return Promise.reject(error)
        }
    )

    // 토큰 갱신하는 함수
    const refreshing = async () => {
        console.log('토큰 만료.... 재갱신 요청 합니다.')
        const userStore = useUserStore()
        const { accessToken, refreshToken } = storeToRefs(userStore)

        await regenerateToken(
            ({ data }) => {
                console.log('토큰을 재갱신 완료했습니다.')
                accessToken.value = data.result.accessToken
                refreshToken.value = data.result.refreshToken
            },
            (error) => {
                console.log('토큰 갱신이 실패 하였습니다.')
                // 토근 정보 초기화
                accessToken.value = ''
                refreshToken.value = ''
                // 로그인 페이지로 이동
                location.href = '/'
            }
        )
    }

    // 토큰의 리프레쉬 여부를 감지하는 변수
    let isRefreshing = false

    // 응답 데이터를 인터셉터하여 처리해주는 부분
    instance.interceptors.response.use(
        // 성공한 데이터는 그대로 반환

        function (response) {
            return response
        },

        // 오류가 발생한 경우
        async function (error) {
            const {
                config,
                response: { status }
            } = error

            if (status == httpStatusCode.UNAUTHORIZED) {
                const originalRequest = config

                if (!isRefreshing) {
                    const userStore = useUserStore()
                    const { accessToken, refreshToken } = storeToRefs(userStore)
                    isRefreshing = true

                    await refreshing()

                    // 리프레쉬 토큰을 이용해 엑세스 토큰을 갱신, 갱신 성공시 피니아에 최신 데이터 반영,
                    // isRefreshing을 false로 변경하고 요청을 재시도 하는 코드 추가 필요 (api 구현되면 추가 예정)
                    originalRequest.headers['AccessToken'] = 'bearer ' + accessToken.value
                    originalRequest.headers['RefreshToken'] = 'bearer ' + refreshToken.value

                    console.log('토큰 갱신후 기존 api요청 재 전송')
                    isRefreshing = false
                    return instance(originalRequest)
                }
            } else if (status == httpStatusCode.FORBIDDEN) {
                alert(error.response.data.message)
            }
            return Promise.reject(error)
        }
    )

    return instance
}

export { localAxios }
