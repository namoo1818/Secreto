export const isSameDate: (date1: Date, date2: Date) => boolean = (date1, date2) => {
    if (date1.getFullYear() !== date2.getFullYear()) return false
    if (date1.getMonth() !== date2.getMonth()) return false
    if (date1.getDate() !== date2.getDate()) return false
    return true
}

export const isToday: (date: Date) => boolean = (date) => {
    return isSameDate(date, new Date())
}

export const convertStringToRegistrationDateTime = (dateString: string, start:number=0, end:number=20) => {
    if (dateString === null) return ''
    return dateString.replace(/-/gi, '.').replace('T', '. ').substring(start, end)
}
export const convertStringToRegistrationDate = (dateString: string) => {
    if (isToday(new Date(dateString))) {
        return dateString.substring(11, 16)
    }
    return dateString.substring(0, 10).replace(/-/gi, '.') + '.'
}
export const convertStringToMobileRegistrationDate = (dateString: string) => {
    if (isToday(new Date(dateString))) {
        return dateString.substring(11, 16)
    }
    return dateString.substring(2, 10).replace(/-/gi, '.') + '.'
}
