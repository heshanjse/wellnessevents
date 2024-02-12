import * as React from 'react';

function Profile(props) {
    return (
        <div className='row'>
            <div className='col-md-12 text-right'>
                <h3>{props.Profile}</h3>
                <button onClick={props.logout}> logout</button>
            </div>
        </div>
    )

}

export default Profile;