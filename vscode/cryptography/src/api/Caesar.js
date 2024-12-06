import request from '@/utils/request.js'

export const CaesarEncrypt = (caesarData) => {
  const params = new URLSearchParams();
  for (let key in caesarData) {
    if (caesarData[key] !== null && caesarData[key] !== undefined) {
      params.append(key, caesarData[key]);
    }
  }
  return request.post('/caesar/encrypt', params);
};

export const CaesarDecrypt = (caesarData) => {
  const params = new URLSearchParams();
  for (let key in caesarData) {
    if (caesarData[key] !== null && caesarData[key] !== undefined) {
      params.append(key, caesarData[key]);
    }
  }
  return request.post('/caesar/decrypt', params);
}

export const CaesarEncryptFile = (caesarData) => {
  const params = new URLSearchParams();
  for (let key in caesarData) {
    if (caesarData[key] !== null && caesarData[key] !== undefined) {
      params.append(key, caesarData[key]);
    }
  }
  return request.post('/caesar/encryptFile', params);
}

export const CaesarDecryptFile = (caesarData) => {
  const params = new URLSearchParams();
  for (let key in caesarData) {
    if (caesarData[key] !== null && caesarData[key] !== undefined) {
      params.append(key, caesarData[key]);
    }
  }
  return request.post('/caesar/decryptFile', params);
}
