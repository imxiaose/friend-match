<script setup lang="ts">
import {useRouter} from "vue-router";
const router = useRouter();

const user = {
  id: 1,
  username: 'xiaose',
  userAccount: "123",
  avatarUrl:'https://img2.baidu.com/it/u=1284413808,930446668&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1705165200&t=a28004fcf2200a343c05ede0bf7c1ff1',
  gender: '男',
  phone: '123456678',
  email: "123@qq.com",
  tags:['java','python'],
  /**
   * 创建时间
   */
  createTime: new Date(),
}

const toEdit = (editKey:any, editName:any ,currentValue:any) =>{
  router.push({
    path: 'user/edit',
    query:{
      editKey,
      editName,
      currentValue
    }
  })
}
</script>

<template>
  <van-cell title="昵称" is-link to="/user/edit" :value="user.username" @click="toEdit('userName','昵称',user.username)"/>
  <van-cell title="账号"  to="/user/edit" :value="user.userAccount"/>
  <van-cell title="头像" is-link to="/user/edit" :value="user.avatarUrl">
    <img height="48px" :src="user.avatarUrl"/>
  </van-cell>
  <van-cell title="性别" is-link to="/user/edit" :value="user.gender"/>
  <van-cell title="手机" is-link to="/user/edit" :value="user.phone"/>
  <van-cell title="邮箱" is-link to="/user/edit" :value="user.email"/>
<!--  <van-cell title="标签" is-link to="/user/edit" :value="user.tags"/>-->
  <van-cell title="创建时间"  to="/user/edit" :value="user.createTime.toDateString()"/>

</template>

<style scoped>

</style>