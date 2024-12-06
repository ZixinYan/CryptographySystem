
import request from '@/utils/request.js'


export const dhListService = (param) => {
    return request.get(`/dh/list`, { params: param })
}

export const dhAddService = (dhModel) => {
    return request.post('/dh/add', dhModel)
}

export const dhDeleteService = (id) => {
    return request.delete(`/dh/delete?id=${id}`)
}

export const dhUpdateService = (dhModel) => {
    return request.put('/dh/update', dhModel)
}

export const dhCalculateAService = (dhModel) => {
  const params = new URLSearchParams();
  for (let key in dhModel) {
    if (dhModel[key] !== null && dhModel[key] !== undefined) {
      params.append(key, dhModel[key]);
    }
  }
    return request.post('/dh/generateAKey', dhModel)
}

export const dhCalculateBService = (dhModel) => {
  const params = new URLSearchParams();
  for (let key in dhModel) {
    if (dhModel[key] !== null && dhModel[key] !== undefined) {
      params.append(key, dhModel[key]);
    }
  }
    return request.post('/dh/generateBKey', dhModel)
}

export const dhGetPrivateKeyService = (dhModel) => {
    return request.post(`/dh/generatePrivateKey`, dhModel)
}
