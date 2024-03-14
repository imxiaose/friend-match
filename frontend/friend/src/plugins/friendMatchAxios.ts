// Set config defaults when creating the instance
import axios, {AxiosInstance} from "axios";

const IAxios:AxiosInstance = axios.create({
    baseURL: 'http://localhost:8101/api'
});

IAxios.defaults.withCredentials = true;
// 添加请求拦截器
IAxios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
IAxios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response.data;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});
export default IAxios;