<template>
  <div id="app">
    <!-- 主容器 -->
    <div class="container">
      <!-- 左侧面板 -->
      <div class="panel left-panel">
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label for="key">Key:</label>
            <input type="text" id="key" v-model="DES.key" placeholder="Enter Key" />
          </div>
          <div class="form-group">
            <label ><input type="checkbox" id="dualMachineMode"/>启动双机通信模式</label>
          </div>
          <button type="button" class="submit-btn" @click="openConfigModal">双机通信配置</button>
          <button type="submit" class="submit-btn2" @click="encrypt()">加密！</button>
          <button type="submit" class="submit-btn2" @click="decrypt()">解密！</button>
        </form>
      </div>

      <!-- 垂直拖动条 -->
      <div class="vertical-divider" @mousedown="startResizeHorizontal"></div>

      <!-- 右侧面板 -->
      <div class="panel right-panel">
        <div class="section top-section">
          <div class="operations">
            <h1 id="title">DES加密解密</h1>
            <div class="operations2">
                <button class="clear-btn" @click="clearInputs()">清除</button>
                <button @click="handleFileUpload" class="upload_btn">选择文件</button>
            </div>
          </div>
          <textarea id="input" placeholder="在此输入文本..."></textarea>
        </div>
        <div class="horizontal-divider" @mousedown="startResizeVertical"></div>
        <div class="section bottom-section">
          <button id="copy-btn" class="copy-btn" @click="copy()">复  制</button>
          <textarea id="output" readonly="true" placeholder="这里是输出"></textarea>
        </div>
      </div>
    </div>

    <!-- 配置弹窗 -->
    <div v-if="configModalVisible" class="modal">
      <div class="modal-content">
        <h3>双机通信配置</h3>
        <form @submit.prevent="submitConfig">
          <div class="form-group">
            <label for="localIp">本机IP:</label>
            <input type="text" id="localIp" v-model="DES.sourceIp" placeholder="Enter local IP" />
          </div>
          <div class="form-group">
            <label for="targetIp">目标IP:</label>
            <input type="text" id="targetIp" v-model="DES.targetIp" placeholder="Enter target IP" />
          </div>
          <div class="form-group">
            <label for="port">端口:</label>
            <input type="number" id="port" v-model="DES.port" placeholder="Enter port" />
          </div>
          <button type="submit" class="submit-btn">确认</button>
          <button type="button" class="submit-btn" @click="closeConfigModal">取消</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { DESEncrypt,DESDecrypt,DESEncryptFile,DESDecryptFile } from '@/api/DES.js';
import request from '@/utils/request.js';
import useTokenStore from '@/pinia/token.js'
const tokenStore = useTokenStore();
export default {
  name: "DESCipher",
  data() {
    return {
      dualMachineMode: false, // 双机通信模式是否启动
      configModalVisible: false, // 配置弹窗显示状态
      selectedFile: null,
      DES: {
        id: "",
        key: "",
        plaintext: "",
        plainFile: "",
        ciphertext: "",
        cipherFile: "",
        createUser: "",
        sourceIp: "",
        targetIp: "",
        port: "",
      },
    };
  },
  methods: {
    // 打开配置弹窗
    openConfigModal() {
      this.configModalVisible = true;
    },
    closeConfigModal() {
      this.configModalVisible = false;
    },
    submitConfig() {
      this.closeConfigModal();
    },
    async handleFileUpload() {
    if (window.showOpenFilePicker) {
        const [fileHandle] = await window.showOpenFilePicker(); // 浏览器文件选择
        const file = await fileHandle.getFile();
        this.selectedFile = file;
        this.$message.success("文件上传成功" + "  " + this.selectedFile.name);
    } else {
      // 如果不支持现代 API，使用备用方案
      const fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.style.display = "none";
      document.body.appendChild(fileInput);

      // 监听文件选择事件
      fileInput.addEventListener("change", (event) => {
        const files = event.target.files;
        if (files && files.length > 0) {
          this.selectedFile = files[0];
          this.$message.success("文件上传成功: " + this.selectedFile.name);
        } else {
          this.$message.error("未选择文件");
          this.selectedFile = null;
        }
        document.body.removeChild(fileInput); // 移除临时元素
      });

      fileInput.click(); // 触发文件选择
    }
  },

  clearInputs() {
      this.DES.plaintext = "";
      this.DES.plainFile = "";
      this.DES.ciphertext = "";
      this.DES.cipherFile = "";
      this.selectedFile = null;
      document.getElementById("input").value = "";
      document.getElementById("output").value = "";
    },
    handleSubmit() {
      if (document.getElementById("input").value && this.selectedFile) {
        this.$message.error("只能选择一种输入方式");
        return;
      }
      if (!document.getElementById("input").value && !this.selectedFile) {
        this.$message.error("请在文本框输入或选择文件");
        return;
      }
      //console.log("提交成功: ", this.DES);
    },
    startResizeHorizontal(event) {
      // 开始拖动水平分隔条
      this.resizing = true;
      this.resizingDirection = "horizontal";
      this.startX = event.clientX;
      this.startWidth = this.$el.querySelector(".left-panel").offsetWidth;
      document.addEventListener("mousemove", this.handleResize);
      document.addEventListener("mouseup", this.stopResize);
    },
    startResizeVertical(event) {
      // 开始拖动垂直分隔条
      this.resizing = true;
      this.resizingDirection = "vertical";
      this.startY = event.clientY;
      this.startHeight = this.$el.querySelector(".top-section").offsetHeight;
      document.addEventListener("mousemove", this.handleResize);
      document.addEventListener("mouseup", this.stopResize);
    },
    handleResize(event) {
      if (!this.resizing) return;
      if (this.resizingDirection === "horizontal") {
        const deltaX = event.clientX - this.startX;
        const newWidth = this.startWidth + deltaX;
        this.$el.querySelector(".left-panel").style.width = `${newWidth}px`;
      } else if (this.resizingDirection === "vertical") {
        const deltaY = event.clientY - this.startY;
        const newHeight = this.startHeight + deltaY;
        this.$el.querySelector(".top-section").style.height = `${newHeight}px`;
      }
    },
    stopResize() {
      this.resizing = false;
      this.resizingDirection = null;
      document.removeEventListener("mousemove", this.handleResize);
      document.removeEventListener("mouseup", this.stopResize);
    },
    copy() {
    const outputText = document.getElementById("output").value;
    navigator.clipboard.writeText(outputText)
      .then(() => {
        this.$message.success('复制成功！');
      })
      .catch(err => {
        console.error('复制失败: ', err);
        this.$message.error('复制失败！');
      });
     },

    // 后端交互
    async uploadFile() {
      if (!this.selectedFile) {
        this.$message.error("Please select a file first.");
        return;
      }
      const formData = new FormData();
      //console.log([...formData.entries()]);
      formData.append("file", this.selectedFile);
      try {
        const response = await request.post("/uploadFile", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            "Authorization": tokenStore.getToken(),
          },
        });
        if(response.code === 0){
          return response.data;
        }
      } catch (error) {
        this.$message.error(`File upload failed: ${error.message}`);
        return null;
      }

    },
    async encrypt(){
      if(this.DES.key === "" ){
        this.$message.error("请输入密钥");
        return;
      }
      const regex = /^\d{8}$/;
      if (!regex.test(this.DES.key)) {
        this.$message.error("密钥必须是 8 位数字");
        return;
      }
      if(this.selectedFile === null){
        this.DES.plaintext = document.getElementById("input").value;
        let res = await DESEncrypt(this.DES);
        if(res.code === 0){
          this.$message.success(res.message);
        }else{
          this.$message.error(res.message);
        }
        document.getElementById("output").value = res.data.ciphertext;
      }else{
        let path = await this.uploadFile();
        this.DES.plainFile = path;
        //console.log(this.DES.plainFile);
        let res = await DESEncryptFile(this.DES);
        if(res.code === 0){
          this.$message.success(res.message);
        }else{
          this.$message.error(res.message);
        }
        document.getElementById("output").value = res.data.cipherFile;
      }
      this.DES.plaintext = "";
      this.DES.plainFile = "";
      this.DES.ciphertext = "";
      this.DES.cipherFile = "";
      this.DES.key = "";
      this.selectedFile = null;
    },



    async decrypt(){
      if(this.DES.key === "" ){
        this.$message.error("请输入密钥");
        return;
      }
      const regex = /^\d{8}$/;
      if (!regex.test(this.DES.key)) {
        this.$message.error("密钥必须是 8 位数字");
        return;
      }
      if(this.selectedFile === null){
        this.DES.ciphertext = document.getElementById("input").value;
        this.DES.plaintext = null;
        let res = await DESDecrypt(this.DES);
        if(res.code === 0){
          this.DES.plaintext = res.data.plaintext;
          this.$message.success(res.message);
        }else{
          this.$message.error(res.message);
        }
        document.getElementById("output").value = this.DES.plaintext;
      }else{
        let path = await this.uploadFile();
        this.DES.cipherFile = path;
        this.DES.plainFile =  null;
       //console.log(this.DES.cipherFile);
        let res = await DESDecryptFile(this.DES);
        if(res.code === 0){
          this.DES.plainFile = res.data.plainFile;
          this.$message.success(res.message);
        }else{
          this.$message.error(res.message);
        }
        document.getElementById("output").value = this.DES.plainFile;
      }
      this.DES.plaintext = "";
      this.DES.plainFile = "";
      this.DES.ciphertext = "";
      this.DES.cipherFile = "";
      this.DES.key = "";
      this.selectedFile = null;
    },
  },

};
</script>

<style scoped>
/* 页面样式 */
#app {
  height: 100vh;
  display: flex;
  background-color: #121212;
  color: #ffffff;
  overflow: hidden; /* 禁止滚动条 */
}

.container {
  display: flex;
  width: 100%;
  height: 100%;
}

.panel {
  display: flex;
  flex-direction: column;
  padding: 10px;
  background-color: rgba(0, 0, 0, 0.8);
  border: 2px solid #00bfff;
  border-radius: 8px;
  overflow: hidden;
  gap: 10px; /* 添加面板内子元素间隙 */
}

/* 左侧面板 */
.left-panel {
  width: 30%; /* 左侧面板宽度 */
  padding: 30px;
}

.left-panel .form-group {
  margin-bottom: 15px;
  margin-right: 30px;
}

.left-panel .form-group label {
  display: block;
  margin-bottom: 8px;
  margin-right: 10px;
  font-weight: bold;
  font-size: 14px;
  color: #00bfff;
  text-shadow: 1px 1px 2px #000;
}

.left-panel .form-group input[type="text"],
.left-panel .form-group input[type="number"] {
  width: 100%;
  padding: 12px 15px;
  margin-right: 10px;
  border: 1px solid #00bfff;
  border-radius: 6px;
  background-color: #1c1c1c;
  color: #ffffff;
  font-size: 14px;
  outline: none;
  transition: all 0.3s ease;
}

.left-panel .form-group input[type="text"]:focus,
.left-panel .form-group input[type="number"]:focus {
  border-color: #00e6ff;
  box-shadow: 0 0 8px rgba(0, 230, 255, 0.8);
}

.operations {
  margin-top: -10px;
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  gap: 10px; /* 添加间隙 */
}

.operations2 {
  display: flex;
  gap: 10px; /* 按钮之间的间距 */
}


/* 按钮样式 */
button {
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  color: #121212;
  background-color: #00bfff;
  transition: all 0.3s ease;
}

button:hover {
  background-color: #00a5e0;
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.5);
}

button:active {
  background-color: #008db8;
  transform: translateY(1px);
  box-shadow: inset 0 4px 6px rgba(0, 0, 0, 0.6);
}



.cancel-btn:hover {
  background-color: #666;
}

.cancel-btn:active {
  background-color: #333;
}

.left-panel .form-group input {
  margin-bottom: 10px; /* 添加下边距，增加间距 */
}

.submit-btn {
  width: 100%; /* 按钮宽度占满 */
  margin-bottom: 10px; /* 添加下边距 */
}

.submit-btn2 {
  width: calc(50% - 10px); /* 按钮宽度占50%减去间隙的一半 */
  margin: 0 5px 10px 5px; /* 左右间距 5px，下边距 10px */
  display: inline-block; /* 使按钮在同一行排列 */
  text-align: center; /* 居中文本 */
  padding: 10px 0; /* 增加内边距 */
  box-sizing: border-box; /* 确保宽度包括内边距和边框 */
}

/* 上传文件按钮 */
.upload-btn {
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  padding: 12px 20px;
  border: none;
  border-radius: 6px;
  color: #121212;
  background-color: #00bfff;
  transition: all 0.3s ease;
}

.upload-btn:hover {
  background-color: #00a5e0;
  transform: translateY(-2px);
  box-shadow: 0 6px 8px rgba(0, 0, 0, 0.5);
}

.upload-btn:active {
  background-color: #008db8;
  transform: translateY(1px);
  box-shadow: inset 0 4px 6px rgba(0, 0, 0, 0.6);
}

.upload-btn input[type="file"] {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
}

/* 清除按钮 */
.clear-btn {
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  padding: 12px 20px;
  border: none;
  border-radius: 6px;
  color: #121212;
  background-color: #00bfff;
  transition: all 0.3s ease;
}

.clear-btn:hover {
  background-color: #ff2e2e;
}

.vertical-divider {
  width: 3px; /* 增加宽度，便于操作 */
  background-color: #00bfff;
  cursor: ew-resize;
  transition: background-color 0.2s;
}
.vertical-divider:hover {
  background-color: #00e6ff; /* 提示用户拖动 */
}

/* 右侧面板 */
.right-panel {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  gap: 10px; /* 添加间隙 */
}

.section {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  padding: 15px;
  background-color: rgba(20, 20, 20, 0.9);
  border-radius: 6px;
  overflow: hidden;
}

.horizontal-divider {
  height: 5px; /* 增加高度，便于操作 */
  background-color: #00bfff;
  cursor: ns-resize;
  transition: background-color 0.2s;
}
.horizontal-divider:hover {
  background-color: #00e6ff; /* 提示用户拖动 */
}


textarea {
  flex-grow: 1;
  padding: 15px;
  background-color: #1c1c1c;
  color: white;
  border: 1px solid #00bfff;
  border-radius: 6px;
  resize: none;
  font-size: 14px;
  outline: none;
  transition: all 0.3s ease;
}

textarea:focus {
  border-color: #00e6ff;
  box-shadow: 0 0 8px rgba(0, 230, 255, 0.8);
}

/* 弹窗样式 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: #121212;
  padding: 30px;
  border: 2px solid #00bfff;
  border-radius: 8px;
  width: 400px;
  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.5);
}

.modal-content h3 {
  color: #00e6ff;
  font-size: 18px;
  margin-bottom: 20px;
  text-align: center;
  text-shadow: 1px 1px 2px #000;
}

.modal-content .form-group {
  margin-bottom: 15px;
  margin-right: 20px;
}

.modal-content .form-group label {
  color: #00bfff;
  font-size: 14px;
}

.modal-content input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 6px;
  border: 1px solid #00bfff;
  background-color: #1c1c1c;
  color: white;
}

.modal-content input:focus {
  border-color: #00e6ff;
  box-shadow: 0 0 6px rgba(0, 230, 255, 0.8);
}

#output {
  caret-color: transparent; /* 隐藏光标 */
  resize: none; /* 禁止手动调整大小 */
  background-color: none;
  color: #333; /* 设置文本颜色 */
  border: 1px solid #ccc; /* 设置边框颜色 */
  padding: 10px; /* 添加内边距 */
  font-size: 14px; /* 设置字体大小 */
}

.copy-container {
  position: relative;
  display: flex;
  align-items: center;
  gap: 10px; /* 按钮和文本框的间距 */
}

#output {
  resize: none; /* 禁止调整大小 */
  background-color: #121212; /* 深色背景 */
  color: #00bfff; /* 亮蓝色文字 */
  border: 1px solid #00bfff; /* 边框为亮蓝色 */
  padding: 10px;
  font-size: 14px;
  border-radius: 6px;
  caret-color: transparent; /* 隐藏光标 */
  outline: none;
  font-family: 'Courier New', Courier, monospace; /* 程序员风格字体 */
}

.copy-btn {
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  padding: 10px 15px;
  margin-top: -5px;
  margin-bottom: 10px;
  border: none;
  border-radius: 6px;
  color: #fff;
  background-color: #00bfff;
  transition: all 0.3s ease;
}

.copy-btn:hover {
  background-color: #00a5e0;
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
}

.copy-btn:active {
  background-color: #008db8;
  transform: translateY(1px);
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.6);
}

#dualMachineMode{
  width: 15px;
  height: 15px;
  vertical-align: middle
}

#title {
  font-size: 18px; /* 字体大小 */
  font-weight: bold; /* 加粗 */
  margin: 0; /* 去掉默认外边距 */
  padding: 0; /* 去掉内边距 */
  line-height: 40px; /* 设置与按钮一致的高度 */
}

</style>
