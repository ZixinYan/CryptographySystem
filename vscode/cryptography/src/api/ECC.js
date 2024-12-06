import request from '@/utils/request.js';

export const ECCEncrypt = (eccData) => {
  const params = new URLSearchParams();
  for (let key in eccData) {
    if (eccData[key] !== null && eccData[key] !== undefined) {
      params.append(key, eccData[key]);
    }
  }
  return request.post('/ecc/encrypt', params);
}

export const ECCDecrypt = (eccData) => {
  const params = new URLSearchParams();
  for (let key in eccData) {
    if (eccData[key] !== null && eccData[key] !== undefined) {
      params.append(key, eccData[key]);
    }
  }
  return request.post('/ecc/decrypt', params);
}

export const ECCEncryptFile = (eccData) => {
  const params = new URLSearchParams();
  for (let key in eccData) {
    if (eccData[key] !== null && eccData[key] !== undefined) {
      params.append(key, eccData[key]);
    }
  }
  return request.post('/ecc/encryptFile', params);
}

export const ECCDecryptFile = (eccData) => {
  const params = new URLSearchParams();
  for (let key in eccData) {
    if (eccData[key] !== null && eccData[key] !== undefined) {
      params.append(key, eccData[key]);
    }
  }
  return request.post('/ecc/decryptFile', params);
}

export const ECCGenerateKey = (eccData) => {
  const params = new URLSearchParams();
  for (let key in eccData) {
    if (eccData[key] !== null && eccData[key] !== undefined) {
      params.append(key, eccData[key]);
    }
  }
  return request.post('/ecc/generateKey', params);
}
