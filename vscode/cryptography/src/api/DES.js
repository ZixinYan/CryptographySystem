import request from '@/utils/request.js'

export const DESEncrypt = (DESData) => {
  const params = new URLSearchParams();
  for (let key in DESData) {
    if (DESData[key] !== null && DESData[key] !== undefined) {
      params.append(key, DESData[key]);
    }
  }
  return request.post('/des/encrypt', params);
};

export const DESDecrypt = (DESData) => {
  const params = new URLSearchParams();
  for (let key in DESData) {
    if (DESData[key] !== null && DESData[key] !== undefined) {
      params.append(key, DESData[key]);
    }
  }
  return request.post('/des/decrypt', params);
}

export const DESEncryptFile = (DESData) => {
  const params = new URLSearchParams();
  for (let key in DESData) {
    if (DESData[key] !== null && DESData[key] !== undefined) {
      params.append(key, DESData[key]);
    }
  }
  return request.post('/des/encryptFile', params);
}

export const DESDecryptFile = (DESData) => {
  const params = new URLSearchParams();
  for (let key in DESData) {
    if (DESData[key] !== null && DESData[key] !== undefined) {
      params.append(key, DESData[key]);
    }
  }
  return request.post('/des/decryptFile', params);
}
