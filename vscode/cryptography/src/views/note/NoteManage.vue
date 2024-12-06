<!-- eslint-disable no-unused-vars -->
<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Edit, Delete } from '@element-plus/icons-vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

import {
    noteAddService,
    noteDeleteService,
    noteListService
} from '@/api/note.js'
import { listUserService } from '@/api/user'
import { useTokenStore } from '@/pinia/token.js'

// 用户数据
const users = ref([])
// 信件数据
const notes = ref([])

// 筛选条件
const createUserId = ref('')
const targetUserId = ref('')

// token
const tokenStore = useTokenStore()
//console.log('token:', tokenStore.getToken())

// 分页
const pageNum = ref(1)
const total = ref(20)
const pageSize = ref(3)

// 抽屉控制
const visibleDrawer = ref(false)
const visibleBtn = ref(false) // 决定是否显示发送按钮
const title = ref('') // 抽屉标题

// 添加信件模型
const noteModel = ref({
    id: 0,
    title: '',
    createUser: '',
    targetUser: '',
    content: '',
    fileUrl: '',
    algorithm: '',
    signature: '',
})

// 加载用户列表
const userList = async () => {
    const result = await listUserService()
    users.value = result.data.map((item) => ({
        id: item.id,
        username: item.username
    }))
}

// 加载信件列表
const noteList = async () => {
  const targetSearchValue = noteModel.value.targetUser;
  const createSearchValue = noteModel.value.createUser;

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
    noteModel.value.targetUser = targetUser.id; // 将目标用户的 ID 设置到 noteModel
  }

  // 查找创建用户并更新 ID
  const createUser = users.value.find(user => createSearchValue === user.username);
  if (createUser) {
    noteModel.value.createUser = createUser.id; // 将创建用户的 ID 设置到 noteModel
  }
// 构造请求参数
const param = {
  pageNum: pageNum.value,
  pageSize: pageSize.value,
  createUser: createUser?.id, // 使用查找到的创建用户 ID
  targetUser: targetUser?.id  // 使用查找到的目标用户 ID
};

// 调用服务
const result = await noteListService(param);

// 处理返回的数据
total.value = result.data.total;
notes.value = result.data.items.map(note => ({
  ...note,
  createUser: users.value.find(u => u.id === note.createUser)?.username || note.createUser,
  targetUser: users.value.find(u => u.id === note.targetUser)?.username || note.targetUser
}));
const targetUserFromId = users.value.find(user => user.id === noteModel.value.targetUser);
  const createUserFromId = users.value.find(user => user.id === noteModel.value.createUser);

  if (targetUserFromId) {
    noteModel.value.targetUser = targetUserFromId.username; // 将 ID 转换为用户名
  }

  if (createUserFromId) {
    noteModel.value.createUser = createUserFromId.username; // 将 ID 转换为用户名
  }
}

const uploadSuccess = (res) => {
    noteModel.value.fileUrl = res.data;
    ElMessage.success('上传成功')
}

// 改变分页大小或当前页
const onSizeChange = size => {
    pageSize.value = size
    noteList()
}

const onCurrentChange = num => {
    pageNum.value = num
    noteList()
}

// 打开抽屉时清理表单数据
const clearForm = () => {
    noteModel.value = {
        id: 0,
        title: '',
        createUser: '',
        targetUser: '',
        content: '',
        algorithm: '',
        fileUrl: '',
        signature: ''
    }
}
const isReadOnly = ref(false); // 控制表单是否为只读

const openSendDrawer = () => {
    clearForm() // 清理表单，确保没有残留数据
    title.value = '发送信件'
    visibleBtn.value = true // 显示按钮
    isReadOnly.value = false // 设置为可编辑
    visibleDrawer.value = true
}

const openViewDrawer = (row) => {
    title.value = '查看信件'
    visibleBtn.value = false // 隐藏按钮
    isReadOnly.value = true // 设置为只读
    noteModel.value = { ...row } // 加载信件数据
    visibleDrawer.value = true
}

const reselectFile = ()=> {
      // 清除当前文件 URL，触发重新上传
      this.noteModel.fileUrl = '';
}

// 发送信件
const noteAdd = async () => {
  const user = users.value.find(user => noteModel.value.targetUser === user.username);

  if (user) {
  // 如果找到用户，将目标用户设置为其 id
    noteModel.value.targetUser = user.id;
  } else {
  // 如果未找到用户，显示错误消息
    ElMessage.error('没有找到对应用户，请检查用户名');
  }
  if(noteModel.value.title === ''){
    ElMessage.error('标题不能为空');
    return;
  }else if(noteModel.value.content === '' && noteModel.value.fileUrl === ''){
    ElMessage.error('内容不能为空');
    return;
  }
    await noteAddService(noteModel.value)
    ElMessage.success('添加成功')
    visibleDrawer.value = false
    clearForm()
    noteList()
}

// 删除信件
const deletenote = async (row) => {
    try {
        await ElMessageBox.confirm('此操作将永久删除, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        })
        const result = await noteDeleteService(row.id)
        ElMessage.success(result.message || '删除成功')
        noteList()
    } catch {
        ElMessage.info('已取消删除')
    }
}

const searchreset = () => {
    noteModel.value.createUser = ''
    noteModel.value.targetUser = ''
    noteList()
}

// 初始化
userList()
noteList()

</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>信件管理</span>
                <div class="extra">
                    <el-button type="primary" @click="openSendDrawer">发送信件</el-button>
                </div>
            </div>
        </template>

        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="发送人：">
              <el-input v-model="noteModel.createUser" placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item label="接收人：">
              <el-input v-model="noteModel.targetUser" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="noteList">搜索</el-button>
                <el-button @click="searchreset">重置</el-button>
            </el-form-item>
        </el-form>

        <!-- 信件列表 -->
        <el-table :data="notes" style="width: 100%">
            <el-table-column label="文件标题" width="400" prop="title">
                <template #default="{ row }">
                    <el-link :underline="false" type="primary" @click="openViewDrawer(row)">{{ row.title }}</el-link>
                </template>
            </el-table-column>
            <el-table-column label="发送人" prop="createUser" />
            <el-table-column label="接收人" prop="targetUser" />
            <el-table-column label="加密算法" prop="algorithm" />
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Delete" circle plain type="danger" @click="deletenote(row)" />
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
            <el-form :model="noteModel" label-width="100px">
                <el-form-item label="文件标题">
                    <el-input v-model="noteModel.title" placeholder="请输入标题" :disabled="isReadOnly"/>
                </el-form-item>
                <el-form-item label="接收方">
                    <el-input v-model="noteModel.targetUser" placeholder="请输入用户名" :disabled="isReadOnly"/>
                </el-form-item>
                <el-form-item label="加密算法">
                    <el-input v-model="noteModel.algorithm" placeholder="使用的加密算法" :disabled="isReadOnly"/>
                </el-form-item>
                <el-form-item label="附件文件">
    <!-- 上传文件 (仅在发送信件时显示) -->
    <el-upload
      v-if="title === '发送信件'"
      class="file-uploader"
      :auto-upload="true"
      :show-file-list="false"
      action="/api/upload"
      name="file"
      :headers="{'Authorization': tokenStore.getToken()}"
      :on-success="uploadSuccess"
    >
      <template v-if="noteModel.fileUrl">
        <button type="button" class="reselect-btn" @click="reselectFile">重选文件</button>
      </template>
      <template v-else>
        <el-icon class="file-uploader-icon"><Upload /></el-icon>
      </template>
    </el-upload>

    <!-- 查看信件时显示下载链接 (仅在查看信件时显示) -->
    <template v-if="title === '查看信件'">
      <a v-if="noteModel.fileUrl" :href="noteModel.fileUrl" download target="_blank">下载文件</a>
      <span v-else class="no-file-message">无文件可下载</span>
    </template>
  </el-form-item>
                <el-form-item label="文件内容" >
                    <el-input v-model="noteModel.content" class="editor" placeholder="请输入信件内容" :disabled="isReadOnly"/>
                </el-form-item>
                <el-form-item v-if="visibleBtn">
                    <el-button type="primary" @click="noteAdd">发送</el-button>
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

</style>
