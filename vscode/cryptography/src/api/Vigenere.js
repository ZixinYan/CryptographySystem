import request from '@/utils/request.js'

export const VigenereEncrypt = (vigenereData) => {
  const params = new URLSearchParams();
  for (let key in vigenereData) {
    if (vigenereData[key] !== null && vigenereData[key] !== undefined) {
      params.append(key, vigenereData[key]);
    }
  }
  return request.post('/vigenere/encrypt', params);
};

export const VigenereDecrypt = (vigenereData) => {
  const params = new URLSearchParams();
  for (let key in vigenereData) {
    if (vigenereData[key] !== null && vigenereData[key] !== undefined) {
      params.append(key, vigenereData[key]);
    }
  }
  return request.post('/vigenere/decrypt', params);
}

export const VigenereEncryptFile = (vigenereData) => {
  const params = new URLSearchParams();
  for (let key in vigenereData) {
    if (vigenereData[key] !== null && vigenereData[key] !== undefined) {
      params.append(key, vigenereData[key]);
    }
  }
  return request.post('/vigenere/encryptFile', params);
}

export const VigenereDecryptFile = (vigenereData) => {
  const params = new URLSearchParams();
  for (let key in vigenereData) {
    if (vigenereData[key] !== null && vigenereData[key] !== undefined) {
      params.append(key, vigenereData[key]);
    }
  }
  return request.post('/vigenere/decryptFile', params);
}
