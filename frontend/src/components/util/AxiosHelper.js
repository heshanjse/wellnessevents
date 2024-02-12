import axios from 'axios'

export const setAuthHeader = (token) => {
    window.localStorage.setItem('auth_token', token);
};

export const getAuthToken = () => {
    return window.localStorage.getItem('auth_token');
};


axios.defaults.baseURL = process.env.REACT_APP_WELNESS_EVENT_URL
axios.defaults.headers.post["Content-Type"] = 'application/json'

export const request = async (method, url, data, headers) => {
    if (getAuthToken() !== null && getAuthToken() !== "null") {
        headers = { 'Authorization': `Bearer ${getAuthToken()}` };
    }

    try {
        var response = await axios({
            method: method,
            url: url,
            headers: headers,
            data: data
        });

        return response
    } catch (error) {
        if (error.response && error.response.status === 401) {
            setAuthHeader(null);
            // Handle unauthorized access later
        }

        throw null;
    }
};
