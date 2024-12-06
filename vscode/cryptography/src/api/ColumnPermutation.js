import request from '@/utils/request.js';

export const ColumnPermutationEncrypt = (columnpermutationData) => {
  const params = new URLSearchParams();
  for (let key in columnpermutationData) {
    if (columnpermutationData[key] !== null && columnpermutationData[key] !== undefined) {
      params.append(key, columnpermutationData[key]);
    }
  }
  return request.post('/columnPermutation/encrypt', params);
}

export const ColumnPermutationDecrypt = (columnpermutationData) => {
  const params = new URLSearchParams();
  for (let key in columnpermutationData) {
    if (columnpermutationData[key] !== null && columnpermutationData[key] !== undefined) {
      params.append(key, columnpermutationData[key]);
    }
  }
  return request.post('/columnPermutation/decrypt', params);
}

export const ColumnPermutationEncryptFile = (columnpermutationData) => {
  const params = new URLSearchParams();
  for (let key in columnpermutationData) {
    if (columnpermutationData[key] !== null && columnpermutationData[key] !== undefined) {
      params.append(key, columnpermutationData[key]);
    }
  }
  return request.post('/columnPermutation/encryptFile', params);
}

export const ColumnPermutationDecryptFile = (columnpermutationData) => {
  const params = new URLSearchParams();
  for (let key in columnpermutationData) {
    if (columnpermutationData[key] !== null && columnpermutationData[key] !== undefined) {
      params.append(key, columnpermutationData[key]);
    }
  }
  return request.post('/columnPermutation/decryptFile', params);
}
