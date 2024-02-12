
import React, { useState } from 'react';
import { request, setAuthHeader } from '../util/AxiosHelper';


function LoginForm({ onAuthentication }) {
  const [active, setActive] = useState('hrLogin');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const onChangeUnHandler = (event) => {
    const { name, value } = event.target;
    setUsername(value);
  };

  const onChangePwHandler = (event) => {
    const { name, value } = event.target;
    setPassword(value);
  };

  const onSubmitHrLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await request('post', '/api/v1/auth/authenticate', {
        username,
        password,
      });

      const { access_token, role, company_name } = response.data;
      setAuthHeader(access_token);
      onAuthentication(access_token, role, company_name);

    } catch (error) {
      console.error('Authentication error:', error);
      // Handle authentication error later
    }
  };

  return (
    <div className="row justify-content-center div-padding">
      <div className="col-5">
        <form onSubmit={onSubmitHrLogin}>
          <div className="form-outline mb-4">
            <input
              type="login"
              id="hrloginName"
              name="login"
              className="form-control"
              onChange={onChangeUnHandler}
            />
            <label className="form-label" htmlFor="loginName">
              Username
            </label>
          </div>

          <div className="form-outline mb-4">
            <input
              type="password"
              id="hrloginPassword"
              name="password"
              className="form-control"
              onChange={onChangePwHandler}
            />
            <label className="form-label" htmlFor="loginPassword">
              Password
            </label>
          </div>

          <button type="submit" className="btn btn-primary btn-block mb-4">
            Sign in
          </button>
        </form>
      </div>
    </div>
  );
}

export default LoginForm;