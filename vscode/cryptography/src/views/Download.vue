<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="download-page">
    <p>正在准备下载...</p>
  </div>
</template>

<script>
import useTokenStore from '@/pinia/token';
import request from '@/utils/request';
export default {
  data() {
    return {
      fileName: null, // 文件名
      downloadUrl: '/download', // 后端文件下载接口
    };
  },
  mounted() {
    // 从 URL 参数中获取文件名
    const params = new URLSearchParams(window.location.search);
    this.fileName = params.get('fileName'); // 获取 `fileName` 参数
    //console.log('fileName:', this.fileName);
    if (this.fileName) {
      this.downloadFile();
    }
  },
  methods: {
    // Base64 解码工具函数
    base64ToBlob(base64, contentType = '') {
      const byteCharacters = atob(base64); // 解码 Base64 数据
      const byteArrays = [];

      for (let offset = 0; offset < byteCharacters.length; offset += 512) {
        const slice = byteCharacters.slice(offset, offset + 512);
        const byteNumbers = new Array(slice.length).fill(0).map((_, i) => slice.charCodeAt(i));
        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
      }

      return new Blob(byteArrays, { type: contentType });
    },

    // 自动下载文件
    async downloadFile() {
      const tokenStore = useTokenStore();
      //console.log('token:', tokenStore.getToken());
      await request.get('/download', {
  params: {
    fileName: this.fileName, // 将 fileName 放入 params 中
  },
  headers: {
    "Authorization": tokenStore.getToken(), // 添加授权头
  },
})

.then((response) => {
  // 检查后端返回的数据结构
  if (!response.data) {
    console.error('无效的文件数据');
    this.$Message.error('文件数据无效');
    return;
  }
  //console.log(response.data);


  // 确保 data 中包含所需字段
  const { fileName, contentType, content } = response.data;
  if (!fileName || !contentType || !content) {
    console.error('缺少文件信息');
    this.$Message.error('文件信息缺失');
    return;
  }

  // 构造文件 Blob
  const blob = this.base64ToBlob(content, contentType);

  // 创建一个下载链接并触发下载
  const link = document.createElement('a');
  const url = window.URL.createObjectURL(blob);
  link.href = url;
  link.download = fileName;
  link.click();

  // 释放 URL 对象
  window.URL.revokeObjectURL(url);

  // 模拟下载完成后关闭页面
  setTimeout(() => {
    window.close();
  }, 5000);
})

        .catch((error) => {
          console.error('文件下载请求失败:', error);
          this.$Message.error('文件下载失败，请稍后重试');
        });
    },
  },
};
</script>

<style scoped>
.download-page {
  text-align: center;
  margin-top: 50px;
}
</style>
