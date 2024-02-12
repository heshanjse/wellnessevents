import React, { createContext } from 'react'

const LoginContext = createContext({
    loggedIn: false,
    updateLogin: (status) => { },
})

export default LoginContext;