import {createRouter, createWebHistory} from 'vue-router'
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

// 1. Define route components.
const routes = [
    {path: '/', component: LoginVue},
    {path:'/findpassword',component:()=>import('@/views/FindPassword.vue')},
    {path:'/download',component:()=>import('@/views/Download.vue')},
    {path: '/page', component: LayoutVue,redirect:'/main',children:[
        {path:'/user/avatar',component:()=>import('@/views/user/UserAvatar.vue')},
        {path:'/user/info',component:()=>import('@/views/user/UserInfo.vue')},
        {path:'/user/resetpassword',component:()=>import('@/views/user/UserResetPassword.vue')},
        {path:'/single/caesar',component:()=>import('@/views/single/Caesar.vue')},
        {path:'/single/affine',component:()=>import('@/views/single/Affine.vue')},
        {path:'/single/vigenere',component:()=>import('@/views/single/Vigenere.vue')},
        {path:'/single/autokey',component:()=>import('@/views/single/Autokey.vue')},
        {path:'/single/permutation',component:()=>import('@/views/single/Permutation.vue')},
        {path:'/single/columnpermutation',component:()=>import('@/views/single/ColumnPermutation.vue')},
        {path:'/single/playfair',component:()=>import('@/views/single/Playfair.vue')},
        {path:'/single/RC4',component:()=>import('@/views/single/RC4.vue')},
        {path:'/single/DES',component:()=>import('@/views/single/DES.vue')},
        {path:'/signature/ECC',component:()=>import('@/views/signature/ECC.vue')},
        {path:'/note',component:()=>import('@/views/note/NoteManage.vue')},
        {path:'/single/rsa',component:()=>import('@/views/single/RSA.vue')},
        {path:'/main',component:()=>import('@/views/main.vue')},
        {path:'/dh',component:()=>import('@/views/DH/DH.vue')},
    ]
    },
]

// 2. Create the router instance and pass the `routes` option
const router = createRouter({
    history: createWebHistory(),
    routes:routes
})

// 3. Export the router instance
export default router



        /*
      import('@/assets/login/1.png'),
        import('@/assets/login/2.png'),
        import('@/assets/login/3.png'),
        import('@/assets/login/4.png'),
        import('@/assets/login/5.png'),
        import('@/assets/login/6.png'),
        import('@/assets/login/7.png'),
        */
