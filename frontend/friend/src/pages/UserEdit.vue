<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import IAxios from "../plugins/friendMatchAxios.ts";
import {showDialog, showFailToast, showSuccessToast, Toast} from "vant";
import {getCurrectUser} from "../services/user.ts";
import {getCurrentUserState} from "../states/user.ts";
const route = useRoute();
const router = useRouter();

const editUser = ref({
  editKey: route.query.editKey,
  currentValue: route.query.currentValue,
  editName: route.query.editName
})



const onSubmit = async () => {
  const currentUser = await getCurrentUserState();

  if (!currentUser){
    showFailToast("用户未登录")
    return;
  }

  const res = await IAxios.post("user/update", {
        'id':currentUser.id,
        [editUser.value.editKey as string]: editUser.value.currentValue}
  )
  console.log('123123',res)

  if (res.code === 0 && res.data > 0){
    showSuccessToast("修改成功")
    router.back();
  } else {
    showFailToast("修改失败")
  }

};
</script>

<template>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="editUser.currentValue"
          :name="editUser.editKey"
          :label="editUser.editName"
          :placeholder="`请输入${editUser.editName}`"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>

</template>

<style scoped>

</style>