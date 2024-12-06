import request from '@/utils/request.js'

export const AffineEncrypt = (affineData) => {
  const params = new URLSearchParams();
  for (let key in affineData) {
    if (affineData[key] !== null && affineData[key] !== undefined) {
      params.append(key, affineData[key]);
    }
  }
  return request.post('/affine/encrypt', params);
};

export const AffineDecrypt = (affineData) => {
  const params = new URLSearchParams();
  for (let key in affineData) {
    if (affineData[key] !== null && affineData[key] !== undefined) {
      params.append(key, affineData[key]);
    }
  }
  return request.post('/affine/decrypt', params);
}

export const AffineEncryptFile = (affineData) => {
  const params = new URLSearchParams();
  for (let key in affineData) {
    if (affineData[key] !== null && affineData[key] !== undefined) {
      params.append(key, affineData[key]);
    }
  }
  return request.post('/affine/encryptFile', params);
}

export const AffineDecryptFile = (affineData) => {
  const params = new URLSearchParams();
  for (let key in affineData) {
    if (affineData[key] !== null && affineData[key] !== undefined) {
      params.append(key, affineData[key]);
    }
  }
  return request.post('/affine/decryptFile', params);
}
