// Set config defaults when creating the instance
import axios from "axios";

const IAxios = axios.create({
    baseURL: 'http://localhost:8101/api'
});

export default IAxios;