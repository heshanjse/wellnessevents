import React, { createContext, useState } from 'react';
import './App.css';
import Header from './Header';
import AppView from './view/AppView';
import LoginForm from './login/LoginForm';
import LoginContext from './LoginContext';


function App() {

  const [authStatus, setAuthStatus] = useState(false);
  const [accessToken, setAccessToken] = useState("");
  const [userRole, setUserRole] = useState("");
  const [userCompany, setUserCompany] = useState("");

  const handleAuthentication = (accessToken, userRole, userCompany) => {
    setAccessToken(accessToken);
    setUserRole(userRole);
    setUserCompany(userCompany);
    setAuthStatus(true);
  };


  return (
    <div className="App">
      <LoginContext.Provider
        value={{ loggedIn: authStatus, updateLogin: setAuthStatus }}
      >
        <Header pageTitle="wellness events booking system" />
        <div className="container-fluid">
          <div className="row">
            <div className="col">
              {authStatus ? (
                <AppView accessToken={accessToken} userRole={userRole} userCompany={userCompany} />
              ) : (
                <LoginForm onAuthentication={handleAuthentication} />
              )}
            </div>
          </div>
        </div>
      </LoginContext.Provider>
    </div>
  );

}

export default App;