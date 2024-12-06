import request from '@/utils/request.js';

export const PlayfairEncrypt = (playfairData) => {
  const params = new URLSearchParams();
  for (let key in playfairData) {
    if (playfairData[key] !== null && playfairData[key] !== undefined) {
      params.append(key, playfairData[key]);
    }
  }
  return request.post('/playfair/encrypt', params);
}

export const PlayfairDecrypt = (playfairData) => {
  const params = new URLSearchParams();
  for (let key in playfairData) {
    if (playfairData[key] !== null && playfairData[key] !== undefined) {
      params.append(key, playfairData[key]);
    }
  }
  return request.post('/playfair/decrypt', params);
}

export const PlayfairEncryptFile = (playfairData) => {
  const params = new URLSearchParams();
  for (let key in playfairData) {
    if (playfairData[key] !== null && playfairData[key] !== undefined) {
      params.append(key, playfairData[key]);
    }
  }
  return request.post('/playfair/encryptFile', params);
}

export const PlayfairDecryptFile = (playfairData) => {
  const params = new URLSearchParams();
  for (let key in playfairData) {
    if (playfairData[key] !== null && playfairData[key] !== undefined) {
      params.append(key, playfairData[key]);
    }
  }
  return request.post('/playfair/decryptFile', params);
}
