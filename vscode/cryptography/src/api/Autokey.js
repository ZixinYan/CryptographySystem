import request from '@/utils/request.js';

export const AutokeyEncrypt = (autokeyData) => {
  const params = new URLSearchParams();
  for (let key in autokeyData) {
    if (autokeyData[key] !== null && autokeyData[key] !== undefined) {
      params.append(key, autokeyData[key]);
    }
  }
  return request.post('/autokey/encrypt', params);
}

export const AutokeyDecrypt = (autokeyData) => {
  const params = new URLSearchParams();
  for (let key in autokeyData) {
    if (autokeyData[key] !== null && autokeyData[key] !== undefined) {
      params.append(key, autokeyData[key]);
    }
  }
  return request.post('/autokey/decrypt', params);
}

export const AutokeyEncryptFile = (autokeyData) => {
  const params = new URLSearchParams();
  for (let key in autokeyData) {
    if (autokeyData[key] !== null && autokeyData[key] !== undefined) {
      params.append(key, autokeyData[key]);
    }
  }
  return request.post('/autokey/encryptFile', params);
}

export const AutokeyDecryptFile = (autokeyData) => {
  const params = new URLSearchParams();
  for (let key in autokeyData) {
    if (autokeyData[key] !== null && autokeyData[key] !== undefined) {
      params.append(key, autokeyData[key]);
    }
  }
  return request.post('/autokey/decryptFile', params);
}
