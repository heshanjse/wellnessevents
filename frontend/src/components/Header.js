import React, { useContext } from "react";
import LoginContext from "./LoginContext";
import { Button } from "react-bootstrap";

function Header(props) {
    const loggedContext = useContext(LoginContext);
    return (
        <header className="App-header">
            <h2>{props.pageTitle}</h2>
            {loggedContext.loggedIn && (
                <Button
                    variant="dark"
                    onClick={() => {
                        loggedContext.updateLogin(false);
                    }}
                >
                    Log Out
                </Button>
            )}
        </header>
    );
}

export default Header;