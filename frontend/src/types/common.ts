export type Handler = () => void
export type DataHandler<T> = (data: T) => void

export type WideCardTemplateType = {
    title: string
    titleClass?: string
    contentMessages: string[]
    contentClass?: string
    buttonLabel: string
    buttonClickHandler?: Handler | null
}
