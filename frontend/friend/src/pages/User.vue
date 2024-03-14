<script setup lang="ts">
import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {getCurrectUser} from "../services/user.ts";

const router = useRouter();


const user = ref()

onMounted(async () => {
  user.value = await getCurrectUser();

})

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
  <template v-if="user">
  <van-cell title="昵称" is-link to="/user/edit" :value="user.username" @click="toEdit('username','昵称',user.username)"/>
  <van-cell title="账号"  to="/user/edit" :value="user.userAccount" @click="toEdit('userAccount','账号',user.userAccount)"/>
  <van-cell title="头像" is-link to="/user/edit" :value="user.avatarUrl" @click="toEdit('avatarUrl','头像',user.avatarUrl)">
    <img height="48px" :src="user.avatarUrl"/>
  </van-cell>
  <van-cell title="性别" is-link to="/user/edit" :value="user.gender" @click="toEdit('gender','性别',user.gender)"/>
  <van-cell title="手机" is-link to="/user/edit" :value="user.phone" @click="toEdit('phone','手机',user.phone)" />
  <van-cell title="邮箱" is-link to="/user/edit" :value="user.email" @click="toEdit('email','邮箱',user.email)"/>
<!--  <van-cell title="标签" is-link to="/user/edit" :value="user.tags"/>-->
  <van-cell title="创建时间"  to="/user/edit" :value="user.createTime"/>
  </template>
</template>

<style scoped>

</style>