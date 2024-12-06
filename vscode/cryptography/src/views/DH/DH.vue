<!-- eslint-disable vue/valid-v-model -->
<!-- eslint-disable no-unused-vars -->
<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Document,Edit, Delete, Key } from '@element-plus/icons-vue'
import { jwtDecode } from "jwt-decode";
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import {
    dhUpdateService,
    dhAddService,
    dhDeleteService,
    dhListService,
    dhCalculateAService,
    dhGetPrivateKeyService,
    dhCalculateBService
} from '@/api/DH.js'
import { listUserService } from '@/api/user'
import { useTokenStore } from '@/pinia/token.js'

// 用户数据
const users = ref([])
// 信件数据
const dhs = ref([])

// 筛选条件
const createUserId = ref('')
const targetUserId = ref('')

// token
const tokenStore = useTokenStore()

// 分页
const pageNum = ref(1)
const total = ref(20)
const pageSize = ref(3)

// 抽屉控制
const visibleDrawer = ref(false)
const title = ref('')
const ifStart = ref(false)
const configModalVisible = ref(false);
const ifKey = ref(false);

// 添加信件模型
const dhModel = ref({
    id: 0,
    a:"",
    b:"",
    p:"",
    g:"",
    key:"",
    privateKey:"",
    createUser: '',
    targetUser: '',
})

const temp = ref({
  id: 1,
  a: '',
  b: '',
  p: '',
  g: '',
});



// 加载用户列表
const userList = async () => {
    const result = await listUserService()
    users.value = result.data.map((item) => ({
        id: item.id,
        username: item.username
    }))
}
const token = tokenStore.getToken();
const currentUser = jwtDecode(token).claims.id;
console.log(currentUser);
const currentUserName = jwtDecode(token).claims.username;

// 加载信件列表
const dhList = async () => {
  const targetSearchValue = dhModel.value.targetUser;
  const createSearchValue = dhModel.value.createUser;

  // 检查 targetUser 是否有值且没有找到匹配的用户
  if (targetSearchValue && !users.value.some(user => user.username === targetSearchValue)) {
    ElMessage.error('没有找到目标用户，请检查用户名');
    return; // 返回，避免继续执行后续逻辑
  }

  // 检查 createUser 是否有值且没有找到匹配的用户
  if (createSearchValue && !users.value.some(user => user.username === createSearchValue)) {
    ElMessage.error('没有找到用户，请检查用户名');
    return; // 返回，避免继续执行后续逻辑
  }

  // 查找目标用户并更新 ID
  const targetUser = users.value.find(user => targetSearchValue === user.username);
  if (targetUser) {
    dhModel.value.targetUser = targetUser.id; // 将目标用户的 ID 设置到 dhModel
  }

  // 查找创建用户并更新 ID
  const createUser = users.value.find(user => createSearchValue === user.username);
  if (createUser) {
    dhModel.value.createUser = createUser.id; // 将创建用户的 ID 设置到 dhModel
  }


const param = {
  pageNum: pageNum.value,
  pageSize: pageSize.value,
  createUser: createUser?.id,
  targetUser: targetUser?.id
};

const result = await dhListService(param);
console.log(result)
total.value = result.data.total;
dhs.value = result.data.items.map(dh => ({
  ...dh,
  p: dh.p,
  g: dh.g,
  createUser: users.value.find(u => u.id === dh.createUser)?.username || dh.createUser,
  targetUser: users.value.find(u => u.id === dh.targetUser)?.username || dh.targetUser,
  ifComplete: !!(dh.a && dh.b),
  ifSender: !!(dh.createUser == currentUser)
}));
const targetUserFromId = users.value.find(user => user.id === dhModel.value.targetUser);
const createUserFromId = users.value.find(user => user.id === dhModel.value.createUser);

  if (targetUserFromId) {
    dhModel.value.targetUser = targetUserFromId.username;
  }

  if (createUserFromId) {
    dhModel.value.createUser = createUserFromId.username;
  }
}

// 改变分页大小或当前页
const onSizeChange = size => {
    pageSize.value = size
    dhList()
}

const onCurrentChange = num => {
    pageNum.value = num
    dhList()
}

const openSendDrawer = () => {
    clearForm() // 清理表单，确保没有残留数据
    title.value = '申请DH'
    ifStart.value = true
    visibleDrawer.value = true
}

const showDialog = (row) => {
    dhModel.value = { ...row }
    visibleDrawer.value = true;
    title.value = '响应请求'
    dhModel.value.id = row.id
}

// 打开抽屉时清理表单数据
const clearForm = () => {
    dhModel.value = {
        id: 0,
        a:"",
        b:"",
        p:"",
        g:"",
        key:'',
        privateKey:'',
        createUser: '',
        targetUser: '',
    }
}

// 发送信件
const dhAdd = async () => {
  const user = users.value.find(user => dhModel.value.targetUser === user.username);
  if (user) {
  // 如果找到用户，将目标用户设置为其 id
    dhModel.value.targetUser = user.id;
  } else {
  // 如果未找到用户，显示错误消息
    ElMessage.error('没有找到对应用户，请检查用户名');
  }

  if(currentUser === dhModel.value.targetUser){
    ElMessage.error('发送人和接收人不能相同');
    return;
  }else if(dhModel.value.privateKey === ''){
    ElMessage.error('选定私钥不能为空');
    return;
  }
    console.log(dhModel.value)
    await dhAddService(dhModel.value)
    ElMessage.success('添加成功')
    visibleDrawer.value = false
    clearForm()
    dhList()
}

const getPrivateKey = async () => {
  const res = await dhGetPrivateKeyService(dhModel.value)
  dhModel.value.privateKey = res.data.privateKey
  ElMessage.success('获取成功')
}

// 删除信件
const deletedh = async (row) => {
    try {
        await ElMessageBox.confirm('此操作将永久删除, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        })
        const result = await dhDeleteService(row.id)
        ElMessage.success(result.message || '删除成功')
        dhList()
    } catch {
        ElMessage.info('已取消删除')
    }
}

const updatedh = async () => {
  dhModel.value.createUser = ''
  dhModel.value.targetUser = ''
  if(dhModel.value.privateKey === ''){
    ElMessage.error('选定私钥不能为空');
    return;
  }
  console.log(dhModel.value)
    await dhUpdateService(dhModel.value)
    ElMessage.success('更新成功')
    visibleDrawer.value = false
    clearForm()
    dhList()
}

const searchreset = () => {
    dhModel.value.createUser = ''
    dhModel.value.targetUser = ''
    dhList()
}

const flag = ref(false)
const openConfigModal= (row)=> {
  temp.value = row
  configModalVisible.value = true;
  if(row.createUser === currentUserName){
    flag.value = true
  }else{
    flag.value = false
  }
}
const closeConfigModal=()=> {
  configModalVisible.value = false;
}


const calcuate = async () => {
  dhModel.value.id = temp.value.id
  dhModel.value.a = temp.value.a
  dhModel.value.b = temp.value.b
  dhModel.value.p = temp.value.p
  dhModel.value.g = temp.value.g
  if(flag.value){
    const res = await dhCalculateAService(dhModel.value)
    dhModel.value.key = res.data.key
    ifKey.value = true
  }else{
    const res = await dhCalculateBService(dhModel.value)
    dhModel.value.key = res.data.key
    console.log(dhModel.value)
    ifKey.value = true
  }
  configModalVisible.value = false;
}

const closeIfKey = () => {
  ifKey.value = false
}

// 初始化
userList()
dhList()

</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>DH密钥交换</span>
                <div class="extra">
                    <el-button type="primary" @click="openSendDrawer">申请DH</el-button>
                </div>
            </div>
        </template>

    <div v-if="configModalVisible" class="modal" @click.self="closeConfigModal">
      <div class="modal-content">
        <h3>输入私钥</h3>
        <div class="form-group">
          <label for="fieldB">输入你的私钥：</label>
          <input id="fieldB" v-model="dhModel.privateKey" />
        </div>
        <el-button type="primary" @click="calcuate()">确认</el-button>
      </div>
    </div>

    <div v-if="ifKey" class="modal">
      <div class="modal-content">
        <h3>请妥善保管生成的密钥</h3>
        <div class="form-group">
          <input id="fieldB" v-model="dhModel.key" />
        </div>
        <el-button type="primary" @click="closeIfKey()">确认</el-button>
      </div>
    </div>



        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="发送人：">
              <el-input v-model="dhModel.createUser" placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item label="接收人：">
              <el-input v-model="dhModel.targetUser" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="dhList">搜索</el-button>
                <el-button @click="searchreset">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 信件列表 -->
        <el-table :data="dhs" style="width: 100%">
            <el-table-column label="A" width="200" prop="a"></el-table-column>
            <el-table-column label="B" width="200" prop="b"></el-table-column>
            <el-table-column label="申请方" prop="createUser" />
            <el-table-column label="响应方" prop="targetUser" />
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)" v-if="!row.ifSender && !row.ifComplete"></el-button>
                    <el-button :icon="Document" circle plain type="primary" @click="openConfigModal(row)" v-if="row.ifComplete"/>
                    <el-button :icon="Delete" circle plain type="danger" @click="deletedh(row)" />
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页条 -->
        <el-pagination
            v-model:current-page="pageNum"
            v-model:page-size="pageSize"
            :page-sizes="[2, 3, 5 ,10, 15]"
            layout="jumper, total, sizes, prev, pager, next"
            background
            :total="total"
            @size-change="onSizeChange"
            @current-change="onCurrentChange"
            style="margin-top: 20px; justify-content: flex-end"
        />

        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="60%">
            <el-form :model="dhModel" label-width="100px">
                <el-form-item label="私钥">
                  <el-input v-model="dhModel.privateKey" placeholder="私钥应该是一个数字" />
                </el-form-item>
                <el-form-item label="自动获取私钥">
                   <el-button type="primary" @click="getPrivateKey()">获取</el-button>
                </el-form-item>
                <el-form-item label="接收方" v-if="ifSender || ifStart">
                    <el-input v-model="dhModel.targetUser" placeholder="请输入用户名"/>
                </el-form-item>
                <el-form-item >
                    <el-button type="primary" @click="title=='申请DH'?dhAdd():updatedh()">发送</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </el-card>
</template>

<style lang="scss" scoped>

.page-container {
    min-height: 100%;
    box-sizing: border-box;
    user-select: none;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        user-select: none;
    }
}
.file-uploader {
    :deep() {
        .file {
            width: 200px;
            height: 300px;
            display: block;
        }
/*
        .el-upload {

            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);

        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }
*/

        .el-icon.file-uploader-icon {
            width: 300px;
            height: 178px;
            background-size: cover; /* 或者使用 contain，根据需要 */
            border: 2px solid #8c939d; /* 添加边框 */
            box-sizing: border-box; /* 确保边框不会影响容器的尺寸 */
            outline: none;
        }
    }
}
.editor {
  width: 100%;
}

:deep(.el-textarea__inner) {
  vertical-align: top; /* 确保内容从顶部对齐 */
  padding-top: 10px; /* 调整顶部空间 */
  line-height: 1.5; /* 设置合理行高 */
  resize: none; /* 禁用手动调整大小 */
}

.file-link {
  color: #409eff; /* 链接颜色 */
  text-decoration: underline;
  cursor: pointer;
}

span {
  color: #909399; /* 灰色文本 */
  cursor: default; /* 鼠标箭头 */
}

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
  z-index: 1000; /* 确保弹窗位于最前 */
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
  margin-right: 15px
}

.modal-content label {
  color: #00bfff;
  font-size: 14px;
  margin-bottom: 5px;
  display: inline-block;
}

.modal-content input {
  width: 100%;
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #00bfff;
  background-color: #1c1c1c;
  color: white;
}

.modal-content input:focus {
  border-color: #00e6ff;
  box-shadow: 0 0 6px rgba(0, 230, 255, 0.8);
}

</style>
