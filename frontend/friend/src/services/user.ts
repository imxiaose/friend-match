import IAxios from "../plugins/friendMatchAxios.ts";
import {getCurrentUserState, setCurrentUserState} from "../states/user.ts";

export const getCurrectUser = async () => {
    const currentUser = getCurrentUserState();
    console.log("1111",currentUser)
    // if (currentUser) {
    //     return currentUser;
    // }
    const res =  await IAxios.get("/user/getCurrentUser");
    console.log("2222",res)
    if (res.code === 0){
        setCurrentUserState(res.data);
        return res.data;
    }
    return null;

}

