import request from '@/utils/request.js'

export const RC4Encrypt = (RC4Data) => {
  const params = new URLSearchParams();
  for (let key in RC4Data) {
    if (RC4Data[key] !== null && RC4Data[key] !== undefined) {
      params.append(key, RC4Data[key]);
    }
  }
  return request.post('/rc4/encrypt', params);
};

export const RC4Decrypt = (RC4Data) => {
  const params = new URLSearchParams();
  for (let key in RC4Data) {
    if (RC4Data[key] !== null && RC4Data[key] !== undefined) {
      params.append(key, RC4Data[key]);
    }
  }
  return request.post('/rc4/decrypt', params);
}

export const RC4EncryptFile = (RC4Data) => {
  const params = new URLSearchParams();
  for (let key in RC4Data) {
    if (RC4Data[key] !== null && RC4Data[key] !== undefined) {
      params.append(key, RC4Data[key]);
    }
  }
  return request.post('/rc4/encryptFile', params);
}

export const RC4DecryptFile = (RC4Data) => {
  const params = new URLSearchParams();
  for (let key in RC4Data) {
    if (RC4Data[key] !== null && RC4Data[key] !== undefined) {
      params.append(key, RC4Data[key]);
    }
  }
  return request.post('/rc4/decryptFile', params);
}
