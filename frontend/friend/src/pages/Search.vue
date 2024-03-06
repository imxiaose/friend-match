<script setup lang="ts">

import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter()
const activeIds = ref([])
const activeIndex = ref(0)
const initTagList =
    [{
      text: '浙江',
      children: [
        {
          text: '杭州', id: '杭州'
        },
        {
          text: '温州', id: '温州'
        },
        {
          text: '宁波', id: '宁波'
        },
      ]
    },
      {
        text: '江苏',
        children: [
          {
            text: '南京', id: '南京'
          },
          {
            text: '无锡', id: '无锡'
          },
          {
            text: '徐州', id: '徐州'
          },
        ]
      }]
let items = ref(initTagList)

const searchText = ref('');
const onSearch = (val) => {
  items.value = initTagList.map(parentTag => {
    const tempParentTag = {...parentTag};
    tempParentTag.children = tempParentTag.children.filter(item => item.text.includes(searchText.value))
    return tempParentTag;
  });
};


const onCancel = () => {
  searchText.value = '';
  items.value = initTagList
};


const doClose = (tag) => {
  activeIds.value = activeIds.value.filter(item => {
    return item !== tag
  })
}

const doSearchResult = ()=>{
  router.push({
    path: 'user/list',
    query:{
      tags: activeIds.value
    }
  })
}
</script>

<template>
  <form action="/">
    <van-search
        v-model="searchText"
        show-action
        placeholder="请输入关键词"
        @search="onSearch"
        @cancel="onCancel"
    />
  </form>
  <van-divider content-position="left">已选标签</van-divider>
  <div v-if="activeIds.length === 0">
    请选择标签
  </div>
  <van-row gutter="10" style="padding: 0 16px">
    <van-col v-for="tag in activeIds">
      <van-tag closeable size="medium" type="primary" @close="doClose(tag)">
        {{ tag }}
      </van-tag>
    </van-col>
  </van-row>
  <van-divider content-position="left">选择标签</van-divider>
  <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items
  />
  <van-button plain type="primary" @click="doSearchResult">朴素按钮</van-button>
</template>

<style scoped>

</style>