import * as React from "react";
import WelcomeContent from "./WelcomeContent";
import AuthContent from "./AuthContent";
import LoginForm from "./LoginForm";
import { request } from "../axios_helper";

export default class AppContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            componentToShow: "Welcome"
        };
    };


    login = () => {
        this.setState({componentToShow: "Login"});
    };


    logout = () => {
        this.setState({componentToShow: "Welcome"});
    };

    onLogin = (e, username, password) => {
        e.preventDefault();
        request(
            "POST",
            "/login",
            { login: username, password: password }
        ).then((response) => {
            this.setState({componentToShow: "Messages"});
        }).catch((error) => {
            this.setState({componentToShow: "Welcome"});
        });
    };

    onRegister = (e, firstName, lastName, username, password) => {
        e.preventDefault();
        request(
            "POST",
            "/register",
            { 
                firstName: firstName,
                lastName: lastName,
                login: username, 
                password: password 
            }
        ).then((response) => {
            this.setState({componentToShow: "Messages"});
        }).catch((error) => {
            this.setState({componentToShow: "Welcome"});
        });
    };

    render() {
        return (
            <div>
                <WelcomeContent />
                <AuthContent />
                <LoginForm />
            </div>
        )
    };
}