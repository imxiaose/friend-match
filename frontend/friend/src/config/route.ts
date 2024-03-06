import Index from '../pages/Index.vue';

import User from "../pages/User.vue";
import Team from "../pages/Team.vue";
import Search from "../pages/Search.vue";
import UserEdit from "../pages/UserEdit.vue";
import SearchResult from "../pages/SearchResult.vue";

const routes:any = [
    {path: '/', component: Index},
    {path: '/team', component: Team},
    {path: '/user', component: User},
    {path: '/user/edit', component: UserEdit},
    {path: '/user/list', component: SearchResult},
    {path: '/search', component: Search},
]

export default routes;