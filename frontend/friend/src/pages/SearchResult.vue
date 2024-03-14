<script setup lang="ts">
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import IAxios from "../plugins/friendMatchAxios.ts"
import qs from 'qs'
import {Toast} from "vant";
import {UserType} from "../models/user";


const route = useRoute();
const {tags} = route.query;
const userList = ref([]);

onMounted(async () => {
  const userListData: UserType[] = await IAxios.get('/user/search/tags', {
    params: {
      tagNameList: tags
    },
    paramsSerializer: params => {
      return qs.stringify(params,{ indices:false })
    }
  })
      .then(function (response) {
        console.log(response);
        return response.data?.data;
      })
      .catch(function (error) {
        console.log(error);
      });
  if (userListData){
    userListData.forEach(user => {
      if (user.tags){
        user.tags = JSON.parse(user.tags)
      }
    })
    console.log(userListData)
    userList.value = userListData
  }
})




</script>

<template>
  <van-card
      v-for="user in userList"
      :desc="user.profile"
      :title="user.username"
      :thumb="user.avatarUrl"
  >
    <template #tags>
      <van-tag plain type="primary" v-for="tag in user.tags" style="margin-right: 8px; margin-top: 8px"> {{ tag }} </van-tag>
    </template>
    <template #footer>
      <van-button size="mini">待开发。。。</van-button>
    </template>
  </van-card>
  <van-empty v-if="!userList || userList.length<1" description="没有数据"></van-empty>
</template>

<style scoped>

</style>