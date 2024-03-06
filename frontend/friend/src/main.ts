import { createApp } from 'vue'
import App from './App.vue'
import { NavBar,Toast } from 'vant';
import * as VueRouter from 'vue-router';
import routes from "./config/route.ts";

const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes
})

const app = createApp(App);
app.use(NavBar)
app.use(Toast)
app.use(router)
app.mount('#app')

