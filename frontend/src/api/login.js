import request from "@/utils/request"
import { useUserStore } from "@/stores/user"

export const getEmailCode = (email, type) => {
    const data = {
        email: email,
        type: 1
    };
    const jsonData = JSON.stringify(data)
    return request.post('/api/verify', jsonData)
}


export const userRegisterService = (username, email, password, code) => {
    const data = {
        username: username,
        email: email,
        password: password,
        verifyCode: code
    };
    const jsonData = JSON.stringify(data)
    return request.post('/api/user/register', jsonData)
}

export const userLoginService = (username, email, password) => {
    const data = {
        username: username,
        email: email,
        password: password
    };
    const jsonData = JSON.stringify(data)
    return request.post('/api/user/login', jsonData)
}

export const userResetPasswordService = (email, password, code) => {
    const data = {
        email: email,
        password: password,
        code: code
    };
    const jsonData = JSON.stringify(data)
    return request.put('/api/user/reset', jsonData)
}

export const getUserInformation = () => {
    console.log(useUserStore().token)
    return request.get('/api/user',{
        headers: {
            'token': useUserStore().token
       }
    })
}
