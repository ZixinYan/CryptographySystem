import request from '@/utils/request.js';

export const RSAEncrypt = (RSAData) => {
  const params = new URLSearchParams();
  for (let key in RSAData) {
    if (RSAData[key] !== null && RSAData[key] !== undefined) {
      params.append(key, RSAData[key]);
    }
  }
  return request.post('/rsa/encrypt', params);
}

export const RSADecrypt = (RSAData) => {
  const params = new URLSearchParams();
  for (let key in RSAData) {
    if (RSAData[key] !== null && RSAData[key] !== undefined) {
      params.append(key, RSAData[key]);
    }
  }
  return request.post('/rsa/decrypt', params);
}

export const RSAEncryptFile = (RSAData) => {
  const params = new URLSearchParams();
  for (let key in RSAData) {
    if (RSAData[key] !== null && RSAData[key] !== undefined) {
      params.append(key, RSAData[key]);
    }
  }
  return request.post('/rsa/encryptFile', params);
}

export const RSADecryptFile = (RSAData) => {
  const params = new URLSearchParams();
  for (let key in RSAData) {
    if (RSAData[key] !== null && RSAData[key] !== undefined) {
      params.append(key, RSAData[key]);
    }
  }
  return request.post('/rsa/decryptFile', params);
}

export const RSAGenerateKey = (RSAData) => {
  const params = new URLSearchParams();
  for (let key in RSAData) {
    if (RSAData[key] !== null && RSAData[key] !== undefined) {
      params.append(key, RSAData[key]);
    }
  }
  return request.post('/rsa/generateKey', params);
}
