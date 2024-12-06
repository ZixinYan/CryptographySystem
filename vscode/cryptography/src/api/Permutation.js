import request from '@/utils/request.js'

export const PermutationEncrypt = (permutationData) => {
  const params = new URLSearchParams();
  for (let key in permutationData) {
    if (permutationData[key] !== null && permutationData[key] !== undefined) {
      params.append(key, permutationData[key]);
    }
  }
  return request.post('/permutation/encrypt', params);
};

export const PermutationDecrypt = (permutationData) => {
  const params = new URLSearchParams();
  for (let key in permutationData) {
    if (permutationData[key] !== null && permutationData[key] !== undefined) {
      params.append(key, permutationData[key]);
    }
  }
  return request.post('/permutation/decrypt', params);
}

export const PermutationEncryptFile = (permutationData) => {
  const params = new URLSearchParams();
  for (let key in permutationData) {
    if (permutationData[key] !== null && permutationData[key] !== undefined) {
      params.append(key, permutationData[key]);
    }
  }
  return request.post('/permutation/encryptFile', params);
}

export const PermutationDecryptFile = (permutationData) => {
  const params = new URLSearchParams();
  for (let key in permutationData) {
    if (permutationData[key] !== null && permutationData[key] !== undefined) {
      params.append(key, permutationData[key]);
    }
  }
  return request.post('/permutation/decryptFile', params);
}
