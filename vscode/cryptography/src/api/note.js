 
import request from '@/utils/request.js'


export const noteListService = (param) => {
    return request.get(`/note/list`, { params: param })
}

export const noteAddService = (noteModel) => {
    return request.post('/note/add', noteModel)
}

export const noteDeleteService = (id) => {
    return request.delete(`/note/delete?id=${id}`)
}
